package com.msku.ceng3505.lingoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.adapters.ViewPagerAdapter;
import com.msku.ceng3505.lingoapp.fragments.QuestionFragment;
import com.msku.ceng3505.lingoapp.fragments.ReadingFragment;
import com.msku.ceng3505.lingoapp.models.Question;
import com.msku.ceng3505.lingoapp.models.Section;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private List<Question> questionList;
    private String reading;
    private Section section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        viewPager = findViewById(R.id.viewPager);

        Intent intent = getIntent();
        section = (Section) intent.getSerializableExtra("section");


        questionList = section.getQuestions();

        Fragment introFragment = new ReadingFragment(reading, section.getReadingHeader(), section.getTitle());

        adapter = new ViewPagerAdapter(this, section.getQuestions(), introFragment, section.getReadingContent(), section.getReadingHeader(), section.getTitle());

        viewPager.setAdapter(adapter);

    }



    public void goToNextQuestion() {
        int currentItem = viewPager.getCurrentItem();

        if (currentItem < questionList.size() - 1) {
            viewPager.setCurrentItem(currentItem + 1, true);
        } else {
            Toast.makeText(this, "Tüm soruları tamamladınız!", Toast.LENGTH_SHORT).show();
        }
    }

    public int getTotalPages() {
        return adapter.getItemCount();
    }

    public int getCurrentPage(){
        return viewPager.getCurrentItem();
    }

}