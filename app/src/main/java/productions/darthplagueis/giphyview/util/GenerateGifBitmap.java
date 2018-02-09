package productions.darthplagueis.giphyview.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by oleg on 2/9/18.
 */

public class GenerateGifBitmap extends AsyncTask<String, Void, Bitmap> {

    private BitmapResponseListener listener;

    public GenerateGifBitmap(BitmapResponseListener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        InputStream in;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            in = connection.getInputStream();
            Bitmap gifBitmap = BitmapFactory.decodeStream(in);
            return gifBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        listener.onPostExecute(bitmap);
    }

    public interface BitmapResponseListener {
        void onPostExecute(Bitmap bitmap);
    }
}
