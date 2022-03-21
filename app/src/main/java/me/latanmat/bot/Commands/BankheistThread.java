package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;

public class BankheistThread implements Runnable{

    private Bankheist bankheist;

    public BankheistThread(GuildMessageReceivedEvent event) throws SQLException {
        bankheist = new Bankheist(event);
    }

    public void run(){

    }

    public Bankheist getBankheist() {
        return bankheist;
    }
}
