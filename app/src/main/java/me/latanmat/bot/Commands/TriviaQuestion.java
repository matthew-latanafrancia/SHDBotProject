package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class TriviaQuestion {
    private String question;
    private String answer;
    private String[] incorrectAnswers;

    /*
    The TriviaQuestion will carry the questions and answers to the said
    question.  These connect to the TriviaListener and the Trivia class which
    will turn these questions into part of the trivia game.
     */
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
