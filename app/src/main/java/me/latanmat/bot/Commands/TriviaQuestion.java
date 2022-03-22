package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class TriviaQuestion {
    private String question;
    private String answer;
    private String[] incorrectAnswers;

    public TriviaQuestion(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setUpQuestion(TextChannel channel){
        //send the question
        channel.sendMessage(question).queue();
    }

    public boolean checkAnswer(String userInput){
        if(answer.equalsIgnoreCase(userInput)){
            return true;
        }
        else {
            return false;
        }
    }
}
