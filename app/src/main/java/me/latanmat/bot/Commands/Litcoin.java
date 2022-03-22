package me.latanmat.bot.Commands;

import me.latanmat.bot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import static me.latanmat.bot.Bot.conn;

public class Litcoin {
    /*
    !litcoin and !currency-help
     */
    private int numberOfCoins;
    private String userID;
    private String name;

    public Litcoin(String userID, String serverID, String name) throws SQLException{
        this.name = name;
        //This gets information of the users

        PreparedStatement prepStmt = Bot.conn.prepareStatement("select UserID, NumberOfCoins from Litcoin where UserID = ? AND ServerID = ?");
        prepStmt.setString(1, userID); //fill in the blanks in the preparedstatement
        prepStmt.setString(2, serverID);
        ResultSet results = prepStmt.executeQuery();

        if (results.next()) {
            this.userID = results.getString(1); //print out the associated name of the ticker
            this.numberOfCoins = results.getInt(2);
            prepStmt.close();

        } else { //If the userID is NOT in the ResultSet
            newUser(userID, serverID);
            prepStmt.close();
        }
    }

    private void newUser(String userID, String serverID) throws SQLException{
        PreparedStatement prepStmt = Bot.conn.prepareStatement("INSERT INTO Litcoin (`UserID`, `ServerID`, `NumberOfCoins`) VALUES (?, ?, ?)");
        prepStmt.setString(1, userID);
        prepStmt.setString(2, serverID);
        prepStmt.setInt(3, 0);
        if (prepStmt.execute()){
            System.out.println("New user made!");
        }
        prepStmt.close();
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void litcoinCommand(GuildMessageReceivedEvent event){
        String stringToSend;
        stringToSend = "Litcoins: " + numberOfCoins;
        //event.getChannel().sendMessage(stringToSend).queue();
        event.getMessage().reply(stringToSend).queue();
    }

    public void currencyHelp(GuildMessageReceivedEvent event){
        //make an embed to tell you about currency
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Litcoin Information");
        embed.setDescription("Litcoin is the currency that LittenBot uses.  With " +
                "Litcoin, you can play games on this server that makes you wager your " +
                "currency.  Depending on how much currency you have, you can buy items " +
                "or ranks in the shop using !shop.  To check how many coins you have, use !litcoin.");
        embed.setColor(Color.CYAN);
        embed.setFooter("Bot created by Dez", event.getGuild().getOwner().getUser().getAvatarUrl());
        event.getChannel().sendMessageEmbeds(embed.build()).queue();

    }

    public void addCoins(int coinsToAdd) throws SQLException{
        PreparedStatement prepStmt = Bot.conn.prepareStatement("UPDATE Litcoin SET NumberOfCoins = ? WHERE UserID = ?");
        prepStmt.setInt(1, numberOfCoins+coinsToAdd);
        prepStmt.setString(2, userID);
        if(prepStmt.execute()){
            System.out.println("Number of coins has been updated.");
        }
    }

    public void negateCoins(int coinsToNegate) throws SQLException{
        PreparedStatement prepStmt = Bot.conn.prepareStatement("UPDATE Litcoin SET NumberOfCoins = ? WHERE UserID = ?");
        prepStmt.setInt(1, numberOfCoins-coinsToNegate);
        prepStmt.setString(2, userID);
        if(prepStmt.execute()){
            System.out.println("Number of coins has been updated.");
        }
    }
    public static String helpToString(){

        String retString = "**!currency-help**: Get help in learning our currency system!\n" +
                "**!litcoin**: Check how many coins you have in this server.\n";

        return retString;
    }
}
