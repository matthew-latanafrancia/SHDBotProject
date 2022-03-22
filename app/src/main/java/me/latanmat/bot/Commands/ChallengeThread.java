package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ChallengeThread implements Runnable{

    private GuildMessageReceivedEvent event;
    private Litcoin idOfOpponent;
    private Litcoin  idOfUser;

    public ChallengeThread(GuildMessageReceivedEvent event, Litcoin idOfOpponent, Litcoin idOfUser){
            this.event = event;
            this.idOfOpponent = idOfOpponent;
            this.idOfUser = idOfUser;
    }

    public void run(){
        event.getChannel().sendMessage("You challenged " + idOfOpponent.getName()).queue();
    }
}
