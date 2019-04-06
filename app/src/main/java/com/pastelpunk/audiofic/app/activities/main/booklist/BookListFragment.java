package com.pastelpunk.audiofic.app.activities.main.booklist;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastelpunk.audiofic.app.R;
import com.pastelpunk.audiofic.app.database.book.BookRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class BookListFragment extends Fragment {

    @Inject
    BookRepository bookRepository;

    private BookSelectionCallback activity;
    private RecyclerView recyclerView;

    public BookListFragment() {}

    public static BookListFragment newInstance() {
        return new BookListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        View listView = view.findViewById(R.id.list);
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            recyclerView = (RecyclerView) listView;
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
        if (context instanceof BookSelectionCallback) {
            activity = (BookSelectionCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BookSelectionCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onResume(){
        super.onResume();
        recyclerView.setAdapter(new BookListViewAdapter(bookRepository.getAllBooks(), activity));
    }

    public void resetList(){
        recyclerView.setAdapter(new BookListViewAdapter(bookRepository.getAllBooks(), activity));
    }
}
