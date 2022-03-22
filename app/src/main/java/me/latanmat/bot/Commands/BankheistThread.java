package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;

public class BankheistThread implements Runnable{

    private Bankheist bankheist;
    private GuildMessageReceivedEvent event;

    /*
     * The BankheistThread function implements Runnable so the program can make a
     * separate thread to run through the bulk of the bankheist.  This is so the
     * main thread can continue listening to the channels in the server.
     */
    public BankheistThread(){
        this.bankheist = null;
        this.event = null;
    }

    public BankheistThread(GuildMessageReceivedEvent event, Bankheist bankheist) throws SQLException {
        this.bankheist = bankheist;
        this.event = event;
    }

    public void run() {
        /*
         * Once a thread starts, it will start running here where startBankheist is called so that
         * to start the bankheist.  The thread will only terminate once the bankheist ends and the bankheist is
         * set to null in the bankheist thread.
         */
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
