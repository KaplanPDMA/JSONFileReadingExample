package sg.com.kaplan.pdma.jsonfilereadingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Question> questions;

    TextView textViewQuestion;
    RadioButton[] radioButtons;
    Question currentQuestion;
    int selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questions = new ArrayList<Question>();

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset("questions.json"));
            JSONArray array = jsonObject.getJSONArray("questions");
            for ( int i = 0; i < array.length(); i++ ) {
                JSONObject questionObject = array.getJSONObject(i);
                String question = questionObject.getString("question");
                String[] options = new String[4];
                options[0] = questionObject.getString("a");
                options[1] = questionObject.getString("b");
                options[2] = questionObject.getString("c");
                options[3] = questionObject.getString("d");

                Question q = new Question(question, options);
                questions.add(q);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);

        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButtons = new RadioButton[4];
        radioButtons[0] = radioButton1;
        radioButtons[1] = radioButton2;
        radioButtons[2] = radioButton3;
        radioButtons[3] = radioButton4;

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioButton1:
                        selectedOption = 0;
                        break;
                    case R.id.radioButton2:
                        selectedOption = 1;
                        break;
                    case R.id.radioButton3:
                        selectedOption = 2;
                        break;
                    case R.id.radioButton4:
                        selectedOption = 3;
                        break;
                }
            }
        });


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOption == -1) {
                    Toast.makeText(getApplicationContext(), "Please select an option first", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Boolean isCorrect = false;
                    if(selectedOption == currentQuestion.getCorrectOption()) {
                        isCorrect = true;
                    }

                    if(isCorrect) {
                        Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                        //can move to the next question by questionNum++ and then displayQuestion()
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect ... please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //start
        int questionNum = 0;
        displayQuestion(questionNum);
    }

    private void displayQuestion(int questionNum) {
        currentQuestion = questions.get(questionNum);
        textViewQuestion.setText(currentQuestion.getQuestion());
        String[] optionsText = currentQuestion.getOptions();
        //We can randomly set one particular option to be the correct option (to use correctOption)
        //and then fill up the other options
        currentQuestion.setCorrectOption(0); //the correct option is the first one.
        for(int i = 0; i < 4; i++) {
            radioButtons[i].setText(optionsText[i]);
        }

        selectedOption = -1;
    }

    //http://stackoverflow.com/questions/13814503/reading-a-json-file-in-android
    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {

            InputStream is = getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
    }
}
