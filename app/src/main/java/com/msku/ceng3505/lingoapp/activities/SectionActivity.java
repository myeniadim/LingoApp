package com.msku.ceng3505.lingoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.msku.ceng3505.lingoapp.helpers.UserProgressFirebaseHelper;
import com.msku.ceng3505.lingoapp.models.Question;
import com.msku.ceng3505.lingoapp.models.Section;
import com.msku.ceng3505.lingoapp.models.UserProgress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private List<Question> questionList;
    private String reading;
    private Section section;
    private UserProgressFirebaseHelper helper;
    private UserProgress userProgress;

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

        helper = new UserProgressFirebaseHelper();




    }


    public int getTotalPages() {
        return adapter.getItemCount();
    }

    public int getCurrentPage(){
        return viewPager.getCurrentItem();
    }

    public void increaseCorrect() {
        helper.getUserProgressBySectionId(section.getSectionId(), new UserProgressFirebaseHelper.FirestoreCallback<UserProgress>() {
            @Override
            public void onSuccess(UserProgress result) {
                // Correct değerini artır
                result.setCorrect(result.getCorrect() + 1);

                // Güncellenmiş UserProgress nesnesini Firestore'a kaydet
                helper.updateUserProgressBySectionId(section.getSectionId(), result, new UserProgressFirebaseHelper.FirestoreCallback<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Firestore", "Correct value updated successfully: " + result.toString());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.w("Firestore", "Error updating progress: " + e.getMessage(), e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                if (e.getMessage().contains("No progress found")) {
                    // Eğer kullanıcı için bir ilerleme kaydı yoksa, yeni bir tane oluştur
                    UserProgress newUserProgress = new UserProgress(section.getSectionId(), 1, 0, getTotalPages());
                    helper.addUserProgress(newUserProgress, new UserProgressFirebaseHelper.FirestoreCallback<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("Firestore", "Progress added successfully!");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.w("Firestore", "Error adding progress", e);
                        }
                    });
                } else {
                    Log.e("Firestore", "Error fetching progress: " + e.getMessage(), e);
                }
            }
        });
    }

    public void increaseIncorrect(){
        helper.getUserProgressBySectionId(section.getSectionId(), new UserProgressFirebaseHelper.FirestoreCallback<UserProgress>() {
            @Override
            public void onSuccess(UserProgress result) {

                result.setIncorrect(result.getIncorrect() + 1);

                helper.updateUserProgressBySectionId(section.getSectionId(), result, new UserProgressFirebaseHelper.FirestoreCallback<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Firestore", "Correct value updated successfully: " + result.toString());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.w("Firestore", "Error updating progress: " + e.getMessage(), e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                if (e.getMessage().contains("No progress found")) {

                    UserProgress newUserProgress = new UserProgress(section.getSectionId(), 1, 0, getTotalPages());
                    helper.addUserProgress(newUserProgress, new UserProgressFirebaseHelper.FirestoreCallback<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("Firestore", "Progress added successfully!");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.w("Firestore", "Error adding progress", e);
                        }
                    });
                } else {
                    Log.e("Firestore", "Error fetching progress: " + e.getMessage(), e);
                }
            }
        });
    }

    public void changeActivity(){
        Intent intent = new Intent(SectionActivity.this, SectionResultActivity.class);
        intent.putExtra("section", section);
        startActivity(intent);
    }

}