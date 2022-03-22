package me.latanmat.bot.Commands;

import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;

public class Trivia {
    private Litcoin userThatsPlaying;
    private TextChannel channelPlayingIn;
    private ArrayList<TriviaQuestion> questions = new ArrayList<>();

    public Trivia(TextChannel channelPlayingIn, Litcoin currentUser){
        this.channelPlayingIn = channelPlayingIn;
        this.userThatsPlaying = currentUser;

        ArrayList<String> incorrectAnswers = new ArrayList<>();
        //Question 1
        String questionOne = "Test Question One";
        String answerOne = "Test Answer One";
        //Question 2
        String questionTwo = "Test Question Two";
        String answerTwo = "Test Answer Two";
        //Question 3
        String questionThree = "Test Question Three";
        String answerThree = "Test Answer Three";
        //Question 4
        String questionFour = "Test Question Four";
        String answerFour = "Test Question Four";
        //Question 5
        String questionFive = "Test Question Five";
        String answerFive = "Test Question Five";

        incorrectAnswers.add("Incorrect One");
        incorrectAnswers.add("Incorrect Two");
        incorrectAnswers.add("Incorrect Three");

        TriviaQuestion q1 = new TriviaQuestion(questionOne, answerOne);
        TriviaQuestion q2 = new TriviaQuestion(questionTwo, answerTwo);
        TriviaQuestion q3 = new TriviaQuestion(questionThree, answerThree);
        TriviaQuestion q4 = new TriviaQuestion(questionFour, answerFour);
        TriviaQuestion q5 = new TriviaQuestion(questionFive, answerFive);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
    }

    public TriviaQuestion askQuestion(int i){
            //ask question q1
            //ask question q2
            TriviaQuestion currQuestion = questions.get(i);
            currQuestion.setUpQuestion(channelPlayingIn);
            return currQuestion;
    }
}
