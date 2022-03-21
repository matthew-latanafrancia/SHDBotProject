package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;

public class BankheistThread implements Runnable{

    private Bankheist bankheist;
    private GuildMessageReceivedEvent event;

    public BankheistThread(){
        this.bankheist = null;
        this.event = null;
    }

    public BankheistThread(GuildMessageReceivedEvent event, Bankheist bankheist) throws SQLException {
        this.bankheist = bankheist;
        this.event = event;
    }

    public void run() {
        try {
            bankheist.startBankheist(event);
            this.bankheist = null;
        }
        catch(SQLException ex){
            System.out.printf("SQLException: %s%nSQLState: %s%nVendorError: %s%n",
                    ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
        }
    }

    public Bankheist getBankheist() {
        return bankheist;
    }
}
