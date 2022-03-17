package me.latanmat.bot;

import me.latanmat.bot.Commands.Listener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {

    public static void main(String[] args){
        JDABuilder builder = JDABuilder.createDefault("OTU0MTQ4NTYwNDgyNDkyNTA2.YjO6GA.TtC1YtDL4kx8N65Vf5MNVCt6OCk");
        builder.setActivity(Activity.playing("Making a Discord Bot"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new Listener());
    }
}
