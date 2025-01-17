package com.msku.ceng3505.lingoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.helpers.UserProgressFirebaseHelper;
import com.msku.ceng3505.lingoapp.models.Section;
import com.msku.ceng3505.lingoapp.models.UserProgress;

public class SectionResultActivity extends AppCompatActivity {

    private Button backHomeButton;

    private TextView correctAns;
    private TextView wrongAns;
    private TextView resultTV;

    private Section section;

    private UserProgressFirebaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_section_result);

        Intent intent = getIntent();
        section = (Section) intent.getSerializableExtra("section");

        backHomeButton = findViewById(R.id.resultBtnBackHome);

        correctAns = findViewById(R.id.resutCorrectAnswersTV);
        wrongAns = findViewById(R.id.resultWrongAnswersTV);
        resultTV = findViewById(R.id.resultScoreTV);

        helper = new UserProgressFirebaseHelper();

        helper.getUserProgressBySectionId(section.getSectionId(), new UserProgressFirebaseHelper.FirestoreCallback<UserProgress>() {
                    @Override
                    public void onSuccess(UserProgress result) {
                        int correct = Integer.valueOf(result.getCorrect());
                        int total = Integer.valueOf(result.getTotalQuestion());
                        correctAns.setText("Correct Answers: " + String.valueOf(result.getCorrect()));
                        wrongAns.setText("Wrong Answers:" + result.getIncorrect());
                        double percent = ((double) correct / total) * 100;
                        resultTV.setText("Your Score: " + String.format("%.2f", percent) + "%");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.w("Firestore", "Error adding progress", e);
                    }
                });

                backHomeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SectionResultActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
    }
}