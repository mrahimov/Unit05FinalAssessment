package productions.darthplagueis.giphyview.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by oleg on 2/5/18.
 */

@Database(entities = {GiphyGif.class}, version = 2)
public abstract class GifDatabase extends RoomDatabase {

    private static volatile GifDatabase INSTANCE;

    public abstract GiphyGifDao giphyGifDao();

    public static synchronized GifDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            String DATABASE_NAME = "gif-database";
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GifDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
