package productions.darthplagueis.giphyview.util;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import productions.darthplagueis.giphyview.MainActivity;
import productions.darthplagueis.giphyview.R;
import productions.darthplagueis.giphyview.database.GiphyGif;
import productions.darthplagueis.giphyview.database.util.DatabaseInitializer;
import productions.darthplagueis.giphyview.network.GiphyRetrofit;

/**
 * Created by c4q on 2/7/18.
 */

public class GiphyJobService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        GiphyRetrofit.makeApiCall(getApplicationContext(), true);
        DatabaseInitializer.setJobServiceResponse(new DatabaseInitializer.JobServiceResponse() {
            @Override
            public void onPostExecute(boolean success, GiphyGif gif) {
                GenerateBitmap task = new GenerateBitmap(getApplicationContext());
                task.execute(gif.getHeightSmallStill());
                jobFinished(jobParameters, !success);
                Log.d("JobService", "onStartJob: Executed");
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


    private class GenerateBitmap extends AsyncTask<String, Void, Bitmap> {

        private Context context;

        GenerateBitmap(Context context) {
            this.context = context;
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
            if (context != null) {
                Intent intent = new Intent(context, MainActivity.class);
                int NOTIFICATION_ID = 66;
                String NOTIFICATION_CHANNEL = "Giphy_Channel";
                int requestID = (int) System.currentTimeMillis();
                int flag = PendingIntent.FLAG_CANCEL_CURRENT;

                PendingIntent pendingIntent = PendingIntent.getActivity(context, requestID, intent, flag);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("GiphyView")
                        .setContentText("Get Ya Gifs Here!")
                        .setContentIntent(pendingIntent)
                        .setLargeIcon(bitmap)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                        .setAutoCancel(true);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }

        }
    }
}
