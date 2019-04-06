package com.pastelpunk.audiofic.app.activities.main.downloadpopup;

import com.pastelpunk.audiofic.app.activities.main.downloadpopup.model.AddBookEvent;

public interface AddBookCallback {
    void onBookChosen(AddBookEvent event);
}
