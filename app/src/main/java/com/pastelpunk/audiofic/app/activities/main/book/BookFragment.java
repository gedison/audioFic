package com.pastelpunk.audiofic.app.activities.main.book;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pastelpunk.audiofic.app.R;
import com.pastelpunk.audiofic.app.database.book.BookRepository;
import com.pastelpunk.audiofic.app.database.chapter.ChapterRepository;
import com.pastelpunk.audiofic.core.model.Book;

import org.w3c.dom.Text;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class BookFragment extends Fragment {

    private static final String ARGUMENT_SELECTED_BOOK = "argSelectedBook";

    @Inject
    ChapterRepository chapterRepository;

    private Book book;
    private ChapterSelectionCallback activity;
    private RecyclerView recyclerView;
    private Gson gson;

    public BookFragment() {
        gson = new Gson();
    }

    public static BookFragment newInstance(Book selectedBook) {
        BookFragment fragment = new BookFragment();

        Bundle args = new Bundle();
        args.putString(ARGUMENT_SELECTED_BOOK, new Gson().toJson(selectedBook));
        fragment.setArguments(args);

        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(Objects.nonNull(bundle) && Objects.nonNull(bundle.getString(ARGUMENT_SELECTED_BOOK))){
            String json = bundle.getString(ARGUMENT_SELECTED_BOOK);
            book = gson.fromJson(json, Book.class);
        }else{
            throw new IllegalArgumentException("Book fragment requires book to be passed in bundle");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        View listView = view.findViewById(R.id.list);
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            recyclerView = (RecyclerView) listView;
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        }

        TextView title = view.findViewById(R.id.textViewTitle);
        title.setText(book.getName());

        TextView description = view.findViewById(R.id.textViewDescription);
        description.setText(book.getDescription());

        Button button = view.findViewById(R.id.button2);
        button.setOnClickListener(v->{
            activity.onResume(book);
        });

        return view;
    }

    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
        if (context instanceof ChapterSelectionCallback) {
            activity = (ChapterSelectionCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BookSelectionCallback");
        }
    }

    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public void onResume(){
        super.onResume();
        recyclerView.setAdapter(new ChapterListViewAdapter(chapterRepository.getChapterTitles(book.getId()), activity));
    }
}
