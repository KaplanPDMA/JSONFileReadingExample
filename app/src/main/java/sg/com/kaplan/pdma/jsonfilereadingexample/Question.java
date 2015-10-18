package sg.com.kaplan.pdma.jsonfilereadingexample;

/**
 * Created by wku on 10/14/2015.
 */

public class Question {
    String question;
    String[] options;
    int correctOption;

    public Question(String question, String[] options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }
}
