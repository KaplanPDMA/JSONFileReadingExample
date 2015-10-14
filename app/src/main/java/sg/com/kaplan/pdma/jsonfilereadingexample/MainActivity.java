package sg.com.kaplan.pdma.jsonfilereadingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Question> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Question> list = new ArrayList<Question>();

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
                list.add(q);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String text = "";
        for(int i = 0; i < list.size(); i++) {
            Question q = list.get(i);
            text += "Q" + (i+1) + ". " + q.getQuestion() + "\n";
            String[] options = q.getOptions();
            text += "\t" + "a. " + options[0] + "\n";
            text += "\t" + "b. " + options[1] + "\n";
            text += "\t" + "c. " + options[2] + "\n";
            text += "\t" + "d. " + options[3] + "\n\n";

        }

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(text);
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
