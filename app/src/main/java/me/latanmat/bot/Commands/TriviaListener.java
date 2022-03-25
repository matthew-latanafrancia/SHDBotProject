package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class TriviaListener extends ListenerAdapter{
    public String prefix = "!";
    private boolean isTriviaGoing = false;
    private Trivia triv = null;
    private TriviaQuestion currQuestion = null;
    private String currUserID;

    /*
     * The TriviaListener is a separate listener function
     * for the trivia game since it would be hard to do it in its own
     * function.
     */

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        try {
            Litcoin currentUser = new Litcoin(event.getAuthor().getId(), event.getGuild().getId(), event.getAuthor().getName());
            if (!isTriviaGoing) {
                if (args[0].startsWith(prefix)) {
                    if (args[0].equalsIgnoreCase(prefix + "trivia")) {
                        event.getChannel().sendMessage("You just started a trivia game! Good luck!").queue();
                        triv = new Trivia(event.getChannel(), currentUser);
                        currQuestion = triv.askQuestion(0);
                        isTriviaGoing = true;
                        currUserID = currentUser.getUserID();
                    }
                }
            } else if (isTriviaGoing && currUserID.equals(currentUser.getUserID())) {
                if (currQuestion.checkAnswer(event.getMessage().getContentRaw())) {
                    event.getChannel().sendMessage("Your answer is correct!").queue();
                    isTriviaGoing = false;
                } else {
                    event.getChannel().sendMessage("Your answer is wrong.").queue();
                    isTriviaGoing = false;
                }
            }
        }catch (SQLException ex){
            System.out.printf("SQLException: %s%nSQLState: %s%nVendorError: %s%n",
                    ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
        }
    }
}
