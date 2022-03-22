package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Bankheist  {
    private static ArrayList<Litcoin> usersEntered;
    private static ArrayList<Integer> coinsBidded;

    public Bankheist(){
        usersEntered = new ArrayList<>();
        coinsBidded = new ArrayList<>();
    }

    public void startBankheist(GuildMessageReceivedEvent event) throws SQLException{
        String name = event.getAuthor().getName();
        String stringToSend = "A new bankheist has been started by " + name + "! If you want" +
                " to participate in this bankheist, type in the command !bankheist [# of coins].  You CAN'T" +
                " change the amount of coins you bet once you type the command!";

        event.getChannel().sendMessage(stringToSend).queue();

        try {
            Thread.sleep(60 * 1000);
            stringToSend = "The bankheist has commenced! Let's see what kind of money we can get";
            event.getChannel().sendMessage(stringToSend).queue();
            Thread.sleep(5 * 1000);
            executeBankheist(event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addUserToBankheist(Litcoin currentUser, int coins, GuildMessageReceivedEvent event) throws SQLException{
        usersEntered.add(currentUser);
        coinsBidded.add(coins);
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
        int numberOfParticipants = usersEntered.size();
        double multiplier = 0;
        String stringToSend = "**Here are our wins from the heist:\n";

        Random rand = new Random();
        int caseChecker = (rand.nextInt(500) % 6);

        String heistDescr = "";
        int numberToRemove = 0;

        switch (caseChecker) {
            case 0:
                heistDescr = "We seemed to get a big load for today's heist" +
                        " but we had many casualties in the process. Exactly half of us" +
                        " went out swinging. They won't be forgotten. We must remember the" +
                        " the real ones that we lost on the way here."; //multiplier 3 // half dead
                numberToRemove = usersEntered.size()/2;
                multiplier = 3;
                break;
            case 1:
                heistDescr = "The cops had no chance against us! What can we do when we're just" +
                        " so efficient with our plan. We had such an efficient plan that everybody came out with" +
                        " some spoils.  We didn't need to leave anybody behind thankfully."; //multiplier 2 // no dead
                multiplier = 2;
                //numberToRemove stays 0
                break;
            case 2:
                heistDescr = "Decent haul today. However, our guy who was carrying the most of our" +
                        " money decided it would be a good idea to jump in front of a bullet. Better" +
                        " luck next time I suppose?"; //multiplier 1.5 // 1 dead
                numberToRemove = 1;
                multiplier = 1.5;
                break;
            case 3:
                heistDescr = "We came out as we started. Our plan was so unsuccessful that security" +
                        " thought we were joking and left us with only a warning. We need a better" +
                        " plan next time if we want to be able to get something out of the next heist." +
                        " For now, it's better to be glad that we were able to make it out of" +
                        " that whole situation **alive**."; //multiplier 1 //no casualties
                multiplier = 1;
                break;
            case 4:
                heistDescr = "We were so close but ended up failing where it really counted. There is nothing else to say. As the" +
                        " last one to survive, goodbye..."; //all dead
                usersEntered.clear();
                coinsBidded.clear();
                break;
            case 5:
                heistDescr = "A complete failure. We didn't come out with enough money and ended up losing" +
                        " friends on the way out. A tragedy that we will always remember... and we can't" +
                        " show our face around this world again. We are wanted criminals."; //multiplier 0.5 //half dead
                numberToRemove = usersEntered.size()/2;
                multiplier = 0.5;
                break;
        }

        event.getChannel().sendMessage(heistDescr).queue();

        if(numberToRemove > 0) {
            removeDead(numberToRemove);
        }

        if(usersEntered.isEmpty()){
            event.getChannel().sendMessage("The wind gusts as the meeting room is empty.\nNobody made it out.");
        }
        else {
            for (int i = 0; i < numberOfParticipants; i++) {
                int coinsToAdd = coinsBidded.get(i);
                coinsToAdd *= multiplier;
                usersEntered.get(i).addCoins(coinsToAdd);
                stringToSend = stringToSend + usersEntered.get(i).getName() + ": " + coinsToAdd + "\n";
            }
            stringToSend = stringToSend + "**";
            event.getChannel().sendMessage(stringToSend).queue();
        }
    }

    public static void removeDead(int numberToRemove){
        /*
        numberToRemove will never be greater than the amount of
        people in the bankheist
         */
        Random randObj = new Random();
        int randInt;
        for(int i = 0; i < numberToRemove; i++){
            randInt = randObj.nextInt() % usersEntered.size();
            usersEntered.remove(randInt);
            coinsBidded.remove(randInt);
        }
    }

    public static String helpToString(){
        String retString = "**!bankheist [# of coins]**: Start a new bankheist with other people in the server!\n";

        return retString;
    }


}
