package com.msku.ceng3505.lingoapp.adapters;

import android.location.GnssAntennaInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msku.ceng3505.lingoapp.models.Section;
import com.msku.ceng3505.lingoapp.R;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {

    private List<Section> sectionList;
    private OnItemClickListener listener;

    public SectionAdapter(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionAdapter.SectionViewHolder holder, int position){
        Section section = sectionList.get(position);
        holder.title.setText(String.valueOf(section.getSectionId()).toUpperCase());
        holder.subtitle.setText(section.getTitle());
        holder.level.setText(section.getDifficultyLevel());

        holder.itemView.setOnClickListener(view -> {
            if (listener != null){
                listener.onItemClick(section);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    static class SectionViewHolder extends RecyclerView.ViewHolder{
        TextView title, subtitle, level;

        public SectionViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            subtitle = itemView.findViewById(R.id.item_subtitle);
            level = itemView.findViewById(R.id.item_level);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Section section);
    }
}