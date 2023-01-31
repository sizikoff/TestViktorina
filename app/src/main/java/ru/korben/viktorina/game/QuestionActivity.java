package ru.korben.viktorina.game;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ru.korben.viktorina.R;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Gson gson = new Gson();
        String jsonFileString = JSONHelper.getJsonFromAssets(getApplicationContext());
        Type listUserType = new TypeToken<List<Question>>() {
        }.getType();
        List<Question> questions = gson.fromJson(jsonFileString, listUserType);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        QuestionAdapter adapter = new QuestionAdapter(this, questions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutFrozen(true);
    }
}
