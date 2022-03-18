package me.latanmat.bot;

import me.latanmat.bot.Commands.Listener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

    public static void main(String[] args) throws LoginException {
        //will make a config file in the future
        JDABuilder builder = JDABuilder.createDefault("OTU0MTQ4NTYwNDgyNDkyNTA2.YjO6GA.ZDjHv5MwYfABtAuARA4nfpXyTcs");
        builder.setActivity(Activity.playing("Making a Discord Bot"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new Listener());
        builder.build();
    }
}
