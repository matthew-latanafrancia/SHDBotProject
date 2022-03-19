package me.latanmat.bot.Commands;

import me.latanmat.bot.Bot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static me.latanmat.bot.Bot.conn;

public class Litcoin {
    /*
    !litcoin and !currency-help
     */
    private int numberOfCoins;
    private String userID;

    public Litcoin(String userID, String serverID) throws SQLException{
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

    public void litcoinCommand(GuildMessageReceivedEvent event){
        String stringToSend;
        stringToSend = "Litcoins: " + numberOfCoins;
        //event.getChannel().sendMessage(stringToSend).queue();
        event.getMessage().reply(stringToSend).queue();
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

    }
}
