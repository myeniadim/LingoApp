package com.msku.ceng3505.lingoapp.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.msku.ceng3505.lingoapp.R;
import com.msku.ceng3505.lingoapp.helpers.FlashcardsFirebaseHelper;
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
        FlashcardsFirebaseHelper helper = new FlashcardsFirebaseHelper();

        updateFavoriteIcon(holder.ivFav, vocabulary.getFavorite());

        holder.ivFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vocabulary.setFavorite(!vocabulary.getFavorite());
                helper.updateVocabulary(vocabulary.getDocId(), vocabulary, new FlashcardsFirebaseHelper.FirestoreCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        updateFavoriteIcon(holder.ivFav, vocabulary.getFavorite());
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.d("TAG", "Error while updating: " + e.getMessage());
                    }
                });

            }
        });
    }

    private void updateFavoriteIcon(ImageView imageView, boolean isFavorite){
        if (isFavorite){
            imageView.setImageResource(R.drawable.baseline_star_24);
        }else{
            imageView.setImageResource(R.drawable.baseline_star_outline_24);
        }
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
        ImageView ivFav;

        public VocabularyViewHolder(@NonNull View itemView){
            super(itemView);
            tvEnglish = itemView.findViewById(R.id.tvEnglishWord);
            tvTurkish = itemView.findViewById(R.id.tvTurkishWord);
            ivFav = itemView.findViewById(R.id.ivFavorite);
        }
    }
}
