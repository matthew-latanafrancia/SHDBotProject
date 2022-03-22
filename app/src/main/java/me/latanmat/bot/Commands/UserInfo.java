package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class UserInfo {
    public static void userInfoCommand(GuildMessageReceivedEvent event, Litcoin currUser){
        /*
         * Gets the user and prints out all of the information such as
         * userID and their profile picture into an embed.  This is then queued
         * and sent to the user in the same channel that they messaged
         * in.
         */
        User usertoLookAt = event.getAuthor();
        String userID = usertoLookAt.getId();
        String userAvatar = usertoLookAt.getAvatarUrl();
        String userName = usertoLookAt.getName();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(userName);
        embed.setImage(userAvatar);
        embed.addField("Discord ID:", userID, false);
        embed.addField("Number of coins:", Integer.toString(currUser.getNumberOfCoins()), false);
        embed.setColor(Color.CYAN);
        embed.setFooter("Bot created by Dez", event.getGuild().getOwner().getUser().getAvatarUrl());

        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }

    public static String helpToString(){
        String retString = "**!userinfo**: time to look at some of that information.\n";
        return retString;
    }
}
