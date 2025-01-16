package com.msku.ceng3505.lingoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.models.Section;

public class SectionDetailActivity extends AppCompatActivity {

    private TextView tvId, tvTitle, tvLevel;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_section_detail);

        tvId = findViewById(R.id.tvDetailSectionName);
        tvTitle = findViewById(R.id.tvDetailSectionTitle);
        tvLevel = findViewById(R.id.tvDetailSectionDifficulty);

        btnStart = findViewById(R.id.btnDetailStart);

        Intent intent = getIntent();
        Section section = (Section) intent.getSerializableExtra("section");

        tvId.setText(section.getSectionId());
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