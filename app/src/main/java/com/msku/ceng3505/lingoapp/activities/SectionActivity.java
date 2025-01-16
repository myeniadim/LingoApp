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


        questionList = new ArrayList<>();
        loadSampleQuestions();

        Fragment introFragment = new ReadingFragment(reading, section.getReadingHeader());

        adapter = new ViewPagerAdapter(this, section.getQuestions(), introFragment, section.getReadingContent(), section.getReadingHeader());

        viewPager.setAdapter(adapter);

    }


    private void loadSampleQuestions() {
        String q1 = "Cumhuriyet hangi yıl ilan edildi?";
        String[] opt1 = {"1919", "1920", "1923"};
        questionList.add(new Question("question1", q1, opt1, "1923"));

        String q2 = "Hangisi bir programlama dili değildir?";
        String[] opt2 = {"Java", "Python", "HTML"};
        questionList.add(new Question("question2", q2, opt2, "Java"));

        String q3 = "Hangisi Ege Bölgesi'ndedir?";
        String[] opt3 = {"Ankara", "İzmir", "Kayseri"};
        questionList.add(new Question("question3", q3, opt3, "İzmir"));

        String q4 = "Hangisi Ege Bölgesi'ndedir?";
        String[] opt4 = {"Ankara", "İzmir", "Kayseri"};
        questionList.add(new Question("question4", q4, opt4, "İzmir"));

        section.setQuestions(questionList);
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