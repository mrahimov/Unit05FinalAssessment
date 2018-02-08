package productions.darthplagueis.giphyview.database.util;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import productions.darthplagueis.giphyview.database.GifDatabase;
import productions.darthplagueis.giphyview.database.GiphyGif;
import productions.darthplagueis.giphyview.model.datadetails.GiphyData;

/**
 * Created by oleg on 2/5/18.
 */

public class DatabaseInitializer {

    private static final String TAG = "DATABASE_INITIALIZER";
    private static JobServiceResponse jobServiceResponse;
    private static AdapterResponse adapterResponse;

    public static void populateAsync(@NonNull final GifDatabase database, @NonNull final List<GiphyData> dataList) {
        PopulateDatabase task = new PopulateDatabase(database, dataList);
        task.execute();
    }

    public static void populateViewAsync(@NonNull final GifDatabase database, @NonNull final List<GiphyData> dataList) {
        PopulateView task = new PopulateView(database, dataList);
        task.execute();
    }

    public static void removeSpecGif(@NonNull final GifDatabase database, @NonNull final GiphyGif gif) {
        RemoveSpecificGif task = new RemoveSpecificGif(database, gif);
        task.execute();
    }

    public static void removeAllFromDb(@NonNull final GifDatabase database) {
        RemoveAllGifs task = new RemoveAllGifs(database);
        task.execute();
    }

    public static void setJobServiceResponse(JobServiceResponse jobServiceResponse) {
        DatabaseInitializer.jobServiceResponse = jobServiceResponse;
    }

    public static void setAdapterResponse(AdapterResponse adapterResponse) {
        DatabaseInitializer.adapterResponse = adapterResponse;
    }

    private static class PopulateDatabase extends AsyncTask<Void, Void, Void> {

        private GifDatabase database;
        private List<GiphyData> dataList;
        private GiphyGif notificationGif;

        PopulateDatabase(GifDatabase database, List<GiphyData> dataList) {
            this.database = database;
            this.dataList = dataList;
            notificationGif = new GiphyGif();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataListInput(database, dataList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            jobServiceResponse.onPostExecute(true, notificationGif);
        }

        private void dataListInput(GifDatabase database, List<GiphyData> dataList) {
            for (int i = 0; i < dataList.size(); i++) {
                GiphyGif giphyGif = new GiphyGif();

                giphyGif.setUrl(dataList.get(i).getUrl());
                giphyGif.setTitle(dataList.get(i).getTitle());
                giphyGif.setHeightStill(dataList.get(i).getImages().getFixed_height_still().getUrl());
                giphyGif.setFixedStillWidth(dataList.get(i).getImages().getFixed_height_still().getWidth());
                giphyGif.setFixedStillHeight(dataList.get(i).getImages().getFixed_height_still().getHeight());
                giphyGif.setHeightSmallStill(dataList.get(i).getImages().getFixed_height_small_still().getUrl());
                giphyGif.setFixedSmallStillWidth(dataList.get(i).getImages().getFixed_height_small_still().getWidth());
                giphyGif.setFixedSmallStillHeight(dataList.get(i).getImages().getFixed_height_small_still().getHeight());
                giphyGif.setPreviewGif(dataList.get(i).getImages().getPreview_gif().getUrl());

                addGifToDb(database, giphyGif);

                if (i == dataList.size() - 1) {
                    notificationGif.setHeightSmallStill(dataList.get(i).getImages().getFixed_height_small_still().getUrl());
                    notificationGif.setFixedSmallStillWidth(dataList.get(i).getImages().getFixed_height_small_still().getWidth());
                    notificationGif.setFixedSmallStillHeight(dataList.get(i).getImages().getFixed_height_small_still().getHeight());
                }
            }
        }

        private GiphyGif addGifToDb(GifDatabase database, GiphyGif gif) {
            database.giphyGifDao().insertGifs(gif);
            return gif;
        }
    }

    private static class PopulateView extends AsyncTask<Void, Void, Void> {

        private GifDatabase database;
        private List<GiphyData> dataList;

        PopulateView(GifDatabase database, List<GiphyData> dataList) {
            this.database = database;
            this.dataList = dataList;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataListInput(database, dataList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapterResponse.onPostExecute();
        }

        private void dataListInput(GifDatabase database, List<GiphyData> dataList) {
            for (GiphyData gif : dataList) {
                GiphyGif giphyGif = new GiphyGif();

                giphyGif.setUrl(gif.getUrl());
                giphyGif.setTitle(gif.getTitle());
                giphyGif.setHeightStill(gif.getImages().getFixed_height_still().getUrl());
                giphyGif.setFixedStillWidth(gif.getImages().getFixed_height_still().getWidth());
                giphyGif.setFixedStillHeight(gif.getImages().getFixed_height_still().getHeight());
                giphyGif.setHeightSmallStill(gif.getImages().getFixed_height_small_still().getUrl());
                giphyGif.setFixedSmallStillWidth(gif.getImages().getFixed_height_small_still().getWidth());
                giphyGif.setFixedSmallStillHeight(gif.getImages().getFixed_height_small_still().getHeight());
                giphyGif.setPreviewGif(gif.getImages().getPreview_gif().getUrl());

                addGifToDb(database, giphyGif);
            }
        }

        private GiphyGif addGifToDb(GifDatabase database, GiphyGif gif) {
            database.giphyGifDao().insertGifs(gif);
            return gif;
        }
    }

    private static class RemoveSpecificGif extends AsyncTask<Void, Void, Void> {

        private GifDatabase database;
        private GiphyGif gif;

        RemoveSpecificGif(GifDatabase database, GiphyGif gif) {
            this.database = database;
            this.gif = gif;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.giphyGifDao().deleteGif(gif);
            return null;
        }
    }

    private static class RemoveAllGifs extends AsyncTask<Void, Void, Void> {

        private GifDatabase database;

        RemoveAllGifs(GifDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.giphyGifDao().deleteAll();
            return null;
        }
    }

    public interface JobServiceResponse {
        void onPostExecute(boolean success, GiphyGif gif);
    }

    public interface AdapterResponse {
        void onPostExecute();
    }

}
