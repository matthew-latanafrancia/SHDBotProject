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
    private String serverID;
    private String name;

    public Litcoin(String userID, String serverID, String name) throws SQLException{
        /*
         * The Litcoin() constructor gets the id of the user and the server
         * so it can either make an account for the user in the database, or so that it can
         * get the account of the user if acpplicable
         */
        this.name = name;
        //This gets information of the users

        PreparedStatement prepStmt = Bot.conn.prepareStatement("select UserID, NumberOfCoins from Litcoin where UserID = ? AND ServerID = ?");
        prepStmt.setString(1, userID); //fill in the blanks in the preparedstatement
        prepStmt.setString(2, serverID);
        ResultSet results = prepStmt.executeQuery();

        if (results.next()) { //If the user already has an account on the server
            this.userID = results.getString(1); //print out the associated name of the ticker
            this.serverID = serverID;
            this.numberOfCoins = results.getInt(2);
            prepStmt.close();

        } else { //If the userID is NOT in the ResultSet.  Makes an account in the database for the user.
            this.userID = userID;
            this.serverID = serverID;
            newUser(userID, serverID);
            prepStmt.close();
        }
    }

    private void newUser(String userID, String serverID) throws SQLException{
        /*
         * The newUser function is called when the bot needs to add a new user into the database.  UserID and ServerID
         * is saved because we want to make sure that we can track different numbers of coins per user just in case they
         * are in separate servers.  This is because we want different coin values if the user is in a different server.
         */
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
        stringToSend = "Litcoins: " + numberOfCoins; //gets the number of coins the user has
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
        //adds coins to the current user
        PreparedStatement prepStmt = Bot.conn.prepareStatement("UPDATE Litcoin SET NumberOfCoins = ? WHERE UserID = ?");
        prepStmt.setInt(1, numberOfCoins+coinsToAdd);
        prepStmt.setString(2, userID);
        if(prepStmt.execute()){
            System.out.println("Number of coins has been updated.");
        }
    }

    public void negateCoins(int coinsToNegate) throws SQLException{
        //negates coins from the current users balance
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

    public void updateCoinCount() throws SQLException {
        PreparedStatement prepStmt = Bot.conn.prepareStatement("select UserID, NumberOfCoins from Litcoin where UserID = ? AND ServerID = ?");
        prepStmt.setString(1, userID); //fill in the blanks in the preparedstatement
        prepStmt.setString(2, serverID);
        ResultSet result = prepStmt.executeQuery();
        if(result.next()) {
            this.numberOfCoins = result.getInt(2);
        }
    }
}
