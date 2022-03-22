package me.latanmat.bot.Commands;

import java.util.Random;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Flipcoin {
    public static boolean flipcoinCommand(GuildMessageReceivedEvent event) {
        /*
        The flipcoinCommand function returns true or false
        Output:
        true: when the user wins the coin flip
        false: when the user loses the coin flip
         */
        Random random = new Random();
        int randomNumber = random.nextInt(2);

        if(randomNumber == 1){
            event.getMessage().reply("Congratulations!  You won!").queue();
            return true;
        }
        else{
            event.getMessage().reply("You lost. Try your luck next time!").queue();
            return false;
        }

    }

    public static String helpToString(){

        String retString = "**!flipcoin [# of coins]**: Bet against the bot and win some coins!\n";

        return retString;
    }
}
