package com.pastelpunk.audiofic.app.activities.main.downloadpopup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.pastelpunk.audiofic.app.R;
import com.pastelpunk.audiofic.app.activities.main.downloadpopup.model.AddBookEvent;
import com.pastelpunk.audiofic.app.activities.main.downloadpopup.model.SupportedType;

import dagger.android.AndroidInjection;

public class AddBookDialogFragment extends DialogFragment {

    private AddBookCallback activity;
    private EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root =  inflater.inflate(R.layout.fragment_book_download_dialog, null);

        editText = root.findViewById(R.id.url);

        builder.setView(root)
                .setPositiveButton("Download", (dialog, id) -> {
                    String url = editText.getText().toString();
                    Log.d(AddBookDialogFragment.class.toString(), url);
                    activity.onBookChosen(new AddBookEvent(SupportedType.AO3, url));
                }).setNegativeButton("Cancel", (dialog, id) -> AddBookDialogFragment.this.getDialog().cancel());

        return builder.create();
    }

    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
        if (context instanceof AddBookCallback) {
            activity = (AddBookCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddBookCallback");
        }
    }
}
