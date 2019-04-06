package com.pastelpunk.audiofic.app.activities.main.booklist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pastelpunk.audiofic.app.R;
import com.pastelpunk.audiofic.core.model.Book;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class BookListViewAdapter extends RecyclerView.Adapter<BookListViewAdapter.ViewHolder> {

    private final BookSelectionCallback activity;
    private final List<Book> books;

    public BookListViewAdapter(List<Book> books, BookSelectionCallback activity) {
        this.books = books;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.book = books.get(position);
        String bookLabel = holder.book.getName();
        holder.textViewTitle.setText(bookLabel);
        holder.textViewAuthor.setText(holder.book.getAuthor());
        holder.view.setOnClickListener(v -> {
            if (Objects.nonNull(activity)) {
                activity.onBookSelection(holder.book);
            }
        });
    }

    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView textViewTitle;
        private final TextView textViewAuthor;
        private Book book;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewTitle = view.findViewById(R.id.textViewTitle);
            this.textViewAuthor = view.findViewById(R.id.textViewAuthor);
        }
    }
}
