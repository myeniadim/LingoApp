package com.msku.ceng3505.lingoapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.activities.SectionDetailActivity;
import com.msku.ceng3505.lingoapp.adapters.SectionAdapter;
import com.msku.ceng3505.lingoapp.helpers.FlashcardsFirebaseHelper;
import com.msku.ceng3505.lingoapp.helpers.SectionsFirebaseHelper;
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
    private SectionsFirebaseHelper helper;

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
        return inflater.inflate(R.layout.fragment_sections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSections = view.findViewById(R.id.rvSections);
        rvSections.setLayoutManager(new LinearLayoutManager(requireContext()));

        helper = new SectionsFirebaseHelper();

        sections = new ArrayList<>();

        helper.getAllSections(new FlashcardsFirebaseHelper.FirestoreCallback<List<Section>>() {
            @Override
            public void onSuccess(List<Section> result) {
                sections.clear();
                sections.addAll(result);
                if (adapter == null){
                    adapter = new SectionAdapter(sections);
                    rvSections.setAdapter(adapter);
                    adapter.setOnItemClickListener(section -> {
                        Log.d("MERT", section.getTitle());
                        Intent intent = new Intent(getActivity(), SectionDetailActivity.class);
                        intent.putExtra("section", section);
                        startActivity(intent);
                    });
                }else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(requireContext(), "Failed to load sections: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}