package com.msku.ceng3505.lingoapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.msku.ceng3505.lingoapp.fragments.QuestionFragment;
import com.msku.ceng3505.lingoapp.fragments.ReadingFragment;
import com.msku.ceng3505.lingoapp.models.Question;
import com.msku.ceng3505.lingoapp.models.Section;


import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Question> questionList;
    private Fragment introFragment;
    private String reading;
    private String readingHeader;


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,
                            List<Question> questionList,
                            Fragment introFragment, String reading, String readingHeader) {
        super(fragmentActivity);
        this.questionList = questionList;
        this.introFragment = introFragment;
        this.reading = reading;
        this.readingHeader = readingHeader;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return ReadingFragment.newInstance(reading, readingHeader);
        } else {
            int questionIndex = position - 1;
            Question question = questionList.get(questionIndex);
            return QuestionFragment.newInstance(question, position);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size() + 1;
    }
}
