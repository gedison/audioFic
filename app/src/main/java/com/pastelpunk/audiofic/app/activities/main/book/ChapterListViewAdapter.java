package com.pastelpunk.audiofic.app.activities.main.book;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pastelpunk.audiofic.app.R;
import com.pastelpunk.audiofic.core.model.Chapter;

import java.util.List;
import java.util.Objects;

public class ChapterListViewAdapter extends RecyclerView.Adapter<ChapterListViewAdapter.ViewHolder> {

    private final ChapterSelectionCallback activity;
    private final List<Chapter> chapters;

    public ChapterListViewAdapter(List<Chapter> chapters, ChapterSelectionCallback activity) {
        this.chapters = chapters;
        this.activity = activity;
    }

    @Override
    public ChapterListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_chapter_list_item, parent, false);
        return new ChapterListViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChapterListViewAdapter.ViewHolder holder, int position) {
        holder.chapter = chapters.get(position);

        String chapterLabel = "Chapter "+ (holder.chapter.getChapterNumber() + 1);
        if(Objects.nonNull(holder.chapter.getName()) && !"null".equals(holder.chapter.getName())){
            chapterLabel += ": " +holder.chapter.getName();
        }

        holder.textViewTitle.setText(chapterLabel);
        holder.textViewDescription.setText(holder.chapter.getDescription());
        holder.view.setOnClickListener(v -> {
            if (Objects.nonNull(activity)) {
                activity.onChapterSelection(holder.chapter);
            }
        });
    }

    public int getItemCount() {
        return chapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView textViewTitle;
        private final TextView textViewDescription;
        private Chapter chapter;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewTitle = view.findViewById(R.id.textViewTitle);
            this.textViewDescription = view.findViewById(R.id.textViewDescription);
        }
    }
}
