package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.sql.SQLException;

public class Listener extends ListenerAdapter {
    public String prefix = "!";
    public Bankheist bankheist; //for the bankheist threading
    public BankheistThread bhMainThread = new BankheistThread();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        try {
            Litcoin currentUser = new Litcoin(event.getAuthor().getId(), event.getGuild().getId(), event.getAuthor().getName());

            if (args[0].startsWith(prefix)) {
                //check through all of the commands
                if (args[0].equalsIgnoreCase(prefix + "shinyhunterdestroyer")) {
                    //example line to show how to send a message in reply to a command
                    //event.getChannel().sendMessage("This is a pretty sick test isn't it.").queue();

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("ShinyHunterDestroyer", "https://www.youtube.com/shinyhunterdestroyer");
                    embed.setDescription("Sometimes he uploads most of the time he doesn't");
                    embed.setColor(Color.CYAN);
                    embed.addField("Current Anime", "Digimon: Ghost Game \n Digimon Adventures 02 \n Pokemon (2019)", false);
                    embed.addField("Favorite Anime", "Hunter x Hunter", false);
                    embed.setFooter("Bot created by Dez", event.getGuild().getOwner().getUser().getAvatarUrl());
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }

                else if (args[0].equalsIgnoreCase(prefix + "litcoin")) {
                    currentUser.litcoinCommand(event);
                }

                else if (args[0].equalsIgnoreCase(prefix + "currency-help")){
                    currentUser.currencyHelp(event);
                }

                else if(args[0].equalsIgnoreCase(prefix + "flipcoin")){
                    if(args.length == 2) {
                        try {
                            int coinsBidded = Integer.parseInt(args[1]);
                            if(coinsBidded <= currentUser.getNumberOfCoins()) { //checks if the user has enough coins
                                if (Flipcoin.flipcoinCommand(event)) {
                                    currentUser.addCoins(coinsBidded);
                                } else {
                                    currentUser.negateCoins(coinsBidded);
                                }
                            }
                            else{
                                event.getMessage().reply("You don't have enough coins! Please input a valid number of coins" +
                                        " to bet.").queue();
                            }
                        }
                        catch(NumberFormatException ex){
                            System.out.println("NumberFormatException: " + ex.getMessage());
                        }
                    }
                }

                else if(args[0].equalsIgnoreCase(prefix + "bankheist")){
                    if(args.length == 2) {
                        try {
                            int coinsBidded = Integer.parseInt(args[1]);
                            if (coinsBidded <= currentUser.getNumberOfCoins()) {
                                //if there is no bankheist started
                                if (bhMainThread.getBankheist() == null) {
                                    bankheist = new Bankheist();
                                    bhMainThread = new BankheistThread(event, bankheist);
                                    Thread thread = new Thread(bhMainThread);
                                    thread.start();
                                }
                                currentUser.negateCoins(coinsBidded);
                                bankheist.addUserToBankheist(currentUser, coinsBidded, event);
                            } else {
                                event.getMessage().reply("You don't have enough coins! Please input a valid number of coins" +
                                        " to bet.").queue();
                            }
                        } catch (NumberFormatException ex) {
                            System.out.println("NumberFormatException: " + ex.getMessage());
                        }
                    }
                }

                else if(args[0].equalsIgnoreCase(prefix + "challenge")){
                    if(args.length == 2){
                        List<User> mentioned = event.getMessage().getMentionedUsers();
                        Litcoin idOfOpponent = new Litcoin(mentioned.get(0).getId(), event.getGuild().getId(), mentioned.get(0).getName());
                        ChallengeThread cThread = new ChallengeThread(event, idOfOpponent, currentUser );
                        Thread cMThread = new Thread(cThread);
                        cMThread.start();
                    }
                    else {
                        event.getMessage().reply("Usage: !challenge [name of user to challenge]").queue();
                    }
                }

                else if(args[0].equalsIgnoreCase(prefix + "userinfo")){
                    UserInfo.userInfoCommand(event, currentUser);
                }

                else if(args[0].equalsIgnoreCase(prefix + "git")){
                    EmbedBuilder embed = new EmbedBuilder();

                    embed.setTitle("GitHub page for the bot", "https://github.com/matthew-latanafrancia/SHDBotProject");
                    embed.setColor(Color.CYAN);
                    embed.setFooter("Bot created by Dez", event.getGuild().getOwner().getUser().getAvatarUrl());
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();

                }

                else if(args[0].equalsIgnoreCase(prefix + "help")){
                    EmbedBuilder embed = new EmbedBuilder();

                    embed.setTitle("Littenbot Commands");
                    embed.addField("Commands" ,Bankheist.helpToString() +
                            Flipcoin.helpToString() +
                            Litcoin.helpToString() +
                            UserInfo.helpToString() +
                            "**!git**: Get the code for this bot!\n" +
                            "**!help**: How did you get here and not know what this command was.\n", false);
                    embed.setColor(Color.CYAN);
                    embed.setFooter("Bot created by Dez", event.getGuild().getOwner().getUser().getAvatarUrl());
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                }

            } else {
                //add a coin to the litcoin
                currentUser.addCoins(1);
            }

        } catch(SQLException ex){
            System.out.printf("SQLException: %s%nSQLState: %s%nVendorError: %s%n",
                    ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
        }
    }

}
