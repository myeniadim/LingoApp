package com.msku.ceng3505.lingoapp.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.helpers.FlashcardsFirebaseHelper;
import com.msku.ceng3505.lingoapp.models.Vocabulary;
import com.msku.ceng3505.lingoapp.adapters.VocabularyAdapter;

import java.util.ArrayList;
import java.util.List;


public class FlashcardsFragment extends Fragment {

    private RecyclerView rvVocabs;
    private VocabularyAdapter adapter;
    private List<Vocabulary> vocabs;
    private FlashcardsFirebaseHelper helper;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FlashcardsFragment() {
    }


    public static FlashcardsFragment newInstance(String param1, String param2) {
        FlashcardsFragment fragment = new FlashcardsFragment();
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
        return inflater.inflate(R.layout.fragment_flashcards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvVocabs = view.findViewById(R.id.rvWords);
        rvVocabs.setLayoutManager(new LinearLayoutManager(requireContext()));
        helper = new FlashcardsFirebaseHelper();

        vocabs = new ArrayList<>();


        helper.getAllVocabularies(new FlashcardsFirebaseHelper.FirestoreCallback<List<Vocabulary>>() {
            @Override
            public void onSuccess(List<Vocabulary> result) {
                vocabs.clear();
                vocabs.addAll(result);
                if (adapter == null) {
                    adapter = new VocabularyAdapter(vocabs);
                    rvVocabs.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(requireContext(), "Failed to load vocabularies: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Vocabulary vocabToDelete = vocabs.get(position);


                helper.deleteVocabulary(vocabToDelete.getDocId(), new FlashcardsFirebaseHelper.FirestoreCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        vocabs.remove(position);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(requireContext(), "Vocabulary deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        adapter.notifyItemChanged(position);
                        Toast.makeText(requireContext(), "Failed to delete vocabulary: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        itemTouchHelper.attachToRecyclerView(rvVocabs);

        ImageButton button = view.findViewById(R.id.imageButton);
        button.setOnClickListener(v -> showAddVocabDialog());



    }

    @SuppressLint("NotifyDataSetChanged")
    private void showAddVocabDialog(){
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.add_word_dialog);

        EditText etEnglish = dialog.findViewById(R.id.etEnglish);
        EditText etTurkish = dialog.findViewById(R.id.etTurkish);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(view -> {
            String english = etEnglish.getText().toString().trim();
            String turkish = etTurkish.getText().toString().trim();

            if (!english.isEmpty() && !turkish.isEmpty()) {

                Vocabulary newVocab = new Vocabulary(english, turkish, false, null);

                helper.addVocabulary(newVocab, new FlashcardsFirebaseHelper.FirestoreCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        helper.updateVocabulary(newVocab.getDocId(), newVocab, new FlashcardsFirebaseHelper.FirestoreCallback<Void>() {
                            @Override
                            public void onSuccess(Void result) {
                                vocabs.add(newVocab);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Log.d("TAG", "Error while updating: " + e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.err.println("Error fetching vocabulary: " + e.getMessage());
                    }
                });

                dialog.dismiss();
            }
        });
        dialog.show();
    }
}