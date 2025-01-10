package com.msku.ceng3505.lingoapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.models.Vocabulary;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder>{

    private List<Vocabulary> vocabList;

    public VocabularyAdapter(List<Vocabulary> vocabList){
        this.vocabList = vocabList;
    }

    @NonNull
    @Override
    public VocabularyAdapter.VocabularyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vocabulary, parent, false);
        return new VocabularyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyAdapter.VocabularyViewHolder holder, int position) {
        Vocabulary vocabulary = vocabList.get(position);
        holder.tvEnglish.setText(vocabulary.getEnglishVocab());
        holder.tvTurkish.setText(vocabulary.getTurkishVocab());
    }

    @Override
    public int getItemCount() {
        return vocabList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setVocabList(List<Vocabulary> newVocabList){
        this.vocabList = newVocabList;
        notifyDataSetChanged();
    }

    static class VocabularyViewHolder extends RecyclerView.ViewHolder{
        TextView tvEnglish, tvTurkish;

        public VocabularyViewHolder(@NonNull View itemView){
            super(itemView);
            tvEnglish = itemView.findViewById(R.id.tvEnglishWord);
            tvTurkish = itemView.findViewById(R.id.tvTurkishWord);
        }
    }
}
