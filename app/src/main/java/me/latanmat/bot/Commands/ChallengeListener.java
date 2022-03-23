package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.util.List;

public class ChallengeListener extends ListenerAdapter implements Runnable{
    public static TextChannel channel;
    public static String prefix = "!";
    public static boolean isChallengeStart = false;
    public static boolean canShoot = false;
    private static String userID;
    private static String opponentID;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        try{
            Litcoin currentUser = new Litcoin(event.getAuthor().getId(), event.getGuild().getId(), event.getAuthor().getName());
            if(args[0].startsWith(prefix)){
                if(args[0].equalsIgnoreCase(prefix + "challenge")){
                    if(args.length == 2) {
                        //get mentioned users
                        List<User> opponent = event.getMessage().getMentionedUsers();
                        if (opponent.size() == 1 && !isChallengeStart) {
                            opponentID = opponent.get(0).getId();
                            userID = currentUser.getUserID();
                            channel = event.getChannel();

                            //start the challenge
                            ChallengeListener chListen = new ChallengeListener();
                            Thread thread = new Thread(chListen);

                            event.getChannel().sendMessage(opponent.get(0).getName() + " and " + event.getAuthor().getName() +
                                    " are in a duel! Get ready! Send the message \"SHOOT\" when I say so.").queue();
                            thread.start();
                        }
                        else{
                            event.getMessage().reply("Usage: !challenge [name of user to challenge]").queue();
                        }
                    }
                    else{
                        event.getMessage().reply("Usage: !challenge [name of user to challenge]").queue();
                    }
                }
            }
            if(canShoot) {
                if (event.getMessage().getContentRaw().equalsIgnoreCase("SHOOT") && currentUser.getUserID().equals(userID)) {
                    String name = event.getAuthor().getName();
                    event.getChannel().sendMessage(name + " won!").queue();
                    canShoot = false;
                    isChallengeStart = false;
                } else if (event.getMessage().getContentRaw().equalsIgnoreCase("SHOOT") && currentUser.getUserID().equals(opponentID)) {
                    String name = event.getAuthor().getName();
                    event.getChannel().sendMessage(name + " won!").queue();
                    canShoot = false;
                    isChallengeStart = false;
                }
            }
        }
        catch(SQLException ex) {
            System.out.printf("SQLException: %s%nSQLState: %s%nVendorError: %s%n",
                    ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
        }
    }

    public void run(){
        isChallengeStart = true;

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        canShoot = true;
        channel.sendMessage("FIRE!").queue();

    }
}
