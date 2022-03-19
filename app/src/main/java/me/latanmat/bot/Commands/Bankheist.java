package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Bankheist {
    private ArrayList<Litcoin> usersEntered;

    public Bankheist(GuildMessageReceivedEvent event) throws SQLException {
        startBankheist(event);
    }

    public void startBankheist(GuildMessageReceivedEvent event) throws SQLException{
        String name = event.getAuthor().getName();
        String stringToSend = "A new bankheist has been started by " + name + "! If you want" +
                " to participate in this bankheist, type in the command !bankheist [# of coins].  You CAN'T" +
                " change the amount of coins you bet once you type the command!";

        event.getChannel().sendMessage(stringToSend).queue();

        try {
            Thread.sleep(180 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stringToSend = "The bankheist has commenced! Let's see what kind of money we can get";

        event.getChannel().sendMessage(stringToSend).queue();
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executeBankheist(event);
    }

    public void addUserToBankheist(Litcoin currentUser, GuildMessageReceivedEvent event) throws SQLException{
        usersEntered.add(currentUser);
        event.getMessage().reply("You have been added into the bankheist! Good luck!").queue();
    }

    public void executeBankheist(GuildMessageReceivedEvent event) throws SQLException{
        /*
         * There will be random chance events to occur that will
         * determine what kind of multiplier the users in the bankheist get.
         * However, some characters have a chance in dying and that is determined
         * randomly.
         * x3: multiplier   (10%)
         * x2: multiplier   (20%)
         * x1.5: multipler  (30%)
         * x0.5: multiplier (40%)
         * x0: DEAD
         */

        Random rand = new Random();
        int caseChecker = (rand.nextInt(1000) % 6);

        //testing if the bankheist executes
        usersEntered.get(0).addCoins(40);
        event.getChannel().sendMessage("Test bankheist done!");

        /*
        switch (caseChecker) {
            case 0: ;
            case 1: ;
            case 2: ;
            case 3: ;
            case 4: ;
            case 5: ;
        }
        */

    }

    public static String helpToString(){
        String retString = "**!bankheist [# of coins]**: Start a new bankheist with other people in the server!\n";

        return retString;
    }
}
