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

public class SectionDetailActivity extends AppCompatActivity {

    private TextView tvId, tvTitle, tvLevel;
    private Button btnStart;
    private UserProgressFirebaseHelper helper;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_section_detail);

        tvId = findViewById(R.id.tvDetailSectionName);
        tvTitle = findViewById(R.id.tvDetailSectionTitle);
        tvLevel = findViewById(R.id.tvDetailSectionDifficulty);

        btnStart = findViewById(R.id.btnDetailStart);

        tvProgress = findViewById(R.id.tvUserProgress);

        Intent intent = getIntent();
        Section section = (Section) intent.getSerializableExtra("section");

        helper = new UserProgressFirebaseHelper();

        helper.getUserProgressBySectionId(section.getSectionId(), new UserProgressFirebaseHelper.FirestoreCallback<UserProgress>() {
            @Override
            public void onSuccess(UserProgress result) {
                int correct = Integer.valueOf(result.getCorrect());
                int total = Integer.valueOf(result.getTotalQuestion());
                double percent = ((double) correct / total) * 100;
                tvProgress.setText("Your Score: " + String.format("%.2f", percent) + "%");
            }

            @Override
            public void onFailure(Exception e) {
                Log.w("Firestore", "Error adding progress", e);
                tvProgress.setText("Your Score: 0%");
            }
        });

        tvId.setText(section.getSectionId().toUpperCase());
        tvTitle.setText("Section Title: " + section.getTitle());
        tvLevel.setText("Section Level: " + section.getDifficultyLevel());



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectionDetailActivity.this, SectionActivity.class);
                intent.putExtra("section", section); // Serializable nesneyi ekle
                startActivity(intent);
            }
        });
    }
}