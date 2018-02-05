package productions.darthplagueis.giphyview.database.util;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import productions.darthplagueis.giphyview.database.GifDatabase;
import productions.darthplagueis.giphyview.database.GiphyGif;

/**
 * Created by oleg on 2/5/18.
 */

public class GifViewModel extends AndroidViewModel {

    private LiveData<List<GiphyGif>> gifsList;

    public GifViewModel(@NonNull Application application) {
        super(application);
        initializeDb();
    }

    public void initializeDb() {
        GifDatabase database = GifDatabase.getDatabase(this.getApplication());
        gifsList = database.giphyGifDao().loadAllGifs();
    }

    public LiveData<List<GiphyGif>> getGifsList() {
        return gifsList;
    }
}
