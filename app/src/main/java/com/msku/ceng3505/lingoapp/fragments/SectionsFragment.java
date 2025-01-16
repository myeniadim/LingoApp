package com.msku.ceng3505.lingoapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.activities.SectionActivity;
import com.msku.ceng3505.lingoapp.activities.SectionDetailActivity;
import com.msku.ceng3505.lingoapp.adapters.SectionAdapter;
import com.msku.ceng3505.lingoapp.models.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SectionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionsFragment extends Fragment {

    private RecyclerView rvSections;
    private SectionAdapter adapter;
    private List<Section> sections;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SectionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SectionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SectionsFragment newInstance(String param1, String param2) {
        SectionsFragment fragment = new SectionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSections = view.findViewById(R.id.rvSections);
        rvSections.setLayoutManager(new LinearLayoutManager(requireContext()));

        sections = new ArrayList<>();

        sections.add(new Section("Section 1", "Colors", "Easy", "asdad", "asaafs", null));
        sections.add(new Section("Section 3", "Colors", "Easy", "asdad", "asaafs", null));
        sections.add(new Section("Section 2", "Colors", "Easy", "asdad", "asaafs", null));

        SectionAdapter sectionAdapter = new SectionAdapter(sections);
        rvSections.setAdapter(sectionAdapter);

        sectionAdapter.setOnItemClickListener(section -> {
            Intent intent = new Intent(getActivity(), SectionDetailActivity.class);
            intent.putExtra("section", section); // Serializable nesneyi ekle
            startActivity(intent);
        });

    }

}