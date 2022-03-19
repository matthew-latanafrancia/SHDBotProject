package me.latanmat.bot;

import me.latanmat.bot.Commands.Listener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class Bot {

    public static Connection conn;

    public static void main(String[] args) throws Exception {
        //will make a config file in the future
        JDABuilder builder = JDABuilder.createDefault("INSERT TOKEN HERE");
        builder.setActivity(Activity.playing("Making a Discord Bot"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new Listener());

        builder.build();

        connectToDatabase(args);
    }

    public static void connectToDatabase(String[] args) throws Exception{
        String paramsFile = "readerparams.txt"; //user and pass in readerparams.txt
        if (args.length >= 1) {
            paramsFile = args[0];
        }

        Properties connectprops = new Properties();
        connectprops.load(new FileInputStream(paramsFile));

        try {
            //Get connection
            Class.forName("com.mysql.jdbc.Driver");
            String dburl = connectprops.getProperty("dburl");
            conn = DriverManager.getConnection(dburl, connectprops); //connect to the database
            System.out.printf("Database connection %s established.%n", dburl);

        } catch (SQLException ex) {
            System.out.printf("SQLException: %s%nSQLState: %s%nVendorError: %s%n",
                    ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
        }
    }
}
