package productions.darthplagueis.giphyview.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by oleg on 2/4/18.
 */

@Dao
public interface GiphyGifDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGifs(GiphyGif... giphyGifs);

    @Query("SELECT * FROM gifs WHERE id LIKE :id")
    GiphyGif loadGifById(int id);

    @Query("SELECT * FROM gifs")
    LiveData<List<GiphyGif>> loadAllGifs();

    @Query("SELECT COUNT(*) FROM gifs")
    int countGifsSaved();

    @Query("DELETE FROM gifs")
    void deleteAll();

    @Delete
    void deleteGif(GiphyGif... giphyGifs);
}
