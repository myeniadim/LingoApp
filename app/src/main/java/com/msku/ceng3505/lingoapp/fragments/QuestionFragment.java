package com.msku.ceng3505.lingoapp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.activities.SectionActivity;
import com.msku.ceng3505.lingoapp.activities.SectionDetailActivity;
import com.msku.ceng3505.lingoapp.activities.SectionResultActivity;
import com.msku.ceng3505.lingoapp.models.Question;
import com.msku.ceng3505.lingoapp.models.Section;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION = "arg_question";
    private static final String ARG_POSITION = "arg_position";
    private static final String ARG_TITLE = "arg_title";

    private Question question;
    private int position;
    private String titlee;



    private TextView questionTv;
    private TextView optionTv1;
    private TextView optionTv2;
    private TextView optionTv3;
    private CardView optionCv1;
    private CardView optionCv2;
    private CardView optionCv3;
    private TextView progMessage;
    private CardView progCard;
    private TextView title;

    private Button btnNext;
    private Button btnPrev;

    private TextView pageTv;


    public QuestionFragment() {
        // Required empty public constructor
    }


    public static QuestionFragment newInstance(Question question, int position, String titlee) {
        QuestionFragment fragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, (Serializable) question);
        args.putInt(ARG_POSITION, position);
        args.putString(ARG_TITLE, titlee);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable(ARG_QUESTION);
            position = getArguments().getInt(ARG_POSITION);
            titlee = getArguments().getString(ARG_TITLE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SectionActivity sectionActivity = (SectionActivity) requireActivity();

        questionTv = view.findViewById(R.id.questionText);

        String[] options = question.getOptions();
        String answer = question.getAnswer();

        optionTv1 = view.findViewById(R.id.optionA_text);
        optionTv2 = view.findViewById(R.id.optionB_text);
        optionTv3 = view.findViewById(R.id.optionC_text);

        optionCv1 = view.findViewById(R.id.optionA_card);
        optionCv2 = view.findViewById(R.id.optionB_card);
        optionCv3 = view.findViewById(R.id.optionC_card);

        progMessage = view.findViewById(R.id.progressMessage);
        progCard = view.findViewById(R.id.progressCard);

        pageTv = view.findViewById(R.id.page_indicator);

        title = view.findViewById(R.id.questionTitle);

        btnNext = view.findViewById(R.id.btnNext);
        btnPrev = view.findViewById(R.id.btnPrev);

        title.setText(titlee);

        questionTv.setText(question.getQuestion());

        optionTv1.setText(options[0]);
        optionTv2.setText(options[1]);
        optionTv3.setText(options[2]);

        optionCv1.setOnClickListener(v -> handleOptionClick(optionCv1, optionTv1, answer, optionCv1, optionCv2, optionCv3));
        optionCv2.setOnClickListener(v -> handleOptionClick(optionCv2, optionTv2, answer, optionCv1, optionCv2, optionCv3));
        optionCv3.setOnClickListener(v -> handleOptionClick(optionCv3, optionTv3, answer, optionCv1, optionCv2, optionCv3));

        int totalPages = sectionActivity.getTotalPages();

        int currentPage = position + 1;

        pageTv.setText(currentPage + "/" + totalPages + " Pages");

        if (currentPage != totalPages){
            btnNext.setOnClickListener(v -> {
                ViewPager2 viewPager = sectionActivity.findViewById(R.id.viewPager);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            });
        }else{
            btnNext.setText("Finish");
            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), SectionResultActivity.class);
                startActivity(intent);
            });
        }

        btnPrev.setOnClickListener(v -> {
            ViewPager2 viewPager = sectionActivity.findViewById(R.id.viewPager);
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        });

    }

    private void handleOptionClick(CardView selectedOption, TextView selectedTextView, String correctAnswer, CardView... allOptions) {
        if (correctAnswer.equals(String.valueOf(selectedTextView.getText()))) {
            selectedOption.setCardBackgroundColor(Color.parseColor("#50EB66"));
            progMessage.setText("True!");
        } else {
            selectedOption.setCardBackgroundColor(Color.parseColor("#eb5066"));
            progMessage.setText("False!");
        }
        for (CardView option : allOptions) {
            option.setClickable(false);
        }
        progCard.setVisibility(View.VISIBLE);
    }
}