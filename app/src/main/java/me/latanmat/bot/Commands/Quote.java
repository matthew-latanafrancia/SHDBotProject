package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static me.latanmat.bot.Bot.conn;

public class Quote {
    public static void addQuote(String quote) throws SQLException {
        PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO Quotes (`QuoteValue`) VALUES (?)");
        prepStmt.setString(1, quote);
        prepStmt.execute();
    }

    public static void getQuote(int id, GuildMessageReceivedEvent event) throws SQLException{
        PreparedStatement prepStmt = conn.prepareStatement("SELECT QuoteValue from Quotes where QuoteID = ?");
        prepStmt.setInt(1, id);
        ResultSet result = prepStmt.executeQuery();

        if(result.next()){
            event.getChannel().sendMessage(result.getString(1)).queue();
        }
        else{
            event.getChannel().sendMessage("Quote not found.").queue();
        }
    }

    public static String helpToString(){
        String retString = "**!quote [any amount of text]**: Add a quote to the database!\n" +
                "**!getquote [quoteID]**: Get a quote from the database!\n";
        return retString;
    }
}
