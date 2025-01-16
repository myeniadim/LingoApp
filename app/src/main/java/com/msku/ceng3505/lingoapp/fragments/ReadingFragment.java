package com.msku.ceng3505.lingoapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.annotations.Nullable;
import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.activities.SectionActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_READING = "arg_reading";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String reading;
    private String mParam2;

    private TextView pageTv;

    public ReadingFragment(String reading) {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadingFragment newInstance(String reading) {
        ReadingFragment fragment = new ReadingFragment(reading);
        Bundle args = new Bundle();
        args.putString(ARG_READING, reading);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reading = getArguments().getString(ARG_READING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.content);
        Button btnNext = view.findViewById(R.id.btnNext);

        pageTv = view.findViewById(R.id.page_indicator);

        SectionActivity sectionActivity = (SectionActivity) requireActivity();

        int totalPages = sectionActivity.getTotalPages();
        int currentPage = 1;

        pageTv.setText(currentPage + "/" + totalPages + " Pages");

        textView.setText(reading);

        btnNext.setOnClickListener(v -> {
            ViewPager2 viewPager = sectionActivity.findViewById(R.id.viewPager);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        });
    }

}