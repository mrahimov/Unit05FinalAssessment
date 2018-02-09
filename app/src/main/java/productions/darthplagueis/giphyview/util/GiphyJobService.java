package productions.darthplagueis.giphyview.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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
            public void onPostExecute(final boolean success, GiphyGif gif) {

                GenerateGifBitmap task = new GenerateGifBitmap(new GenerateGifBitmap.BitmapResponseListener() {
                    @Override
                    public void onPostExecute(Bitmap bitmap) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        int NOTIFICATION_ID = 66;
                        String NOTIFICATION_CHANNEL = "Giphy_Channel";
                        int requestID = (int) System.currentTimeMillis();
                        int flag = PendingIntent.FLAG_CANCEL_CURRENT;

                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestID, intent, flag);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL)
                                .setSmallIcon(R.drawable.notification_icon)
                                .setContentTitle("Daily Dose of Giphy")
                                .setContentText("Get Ya Gifs Here!")
                                .setContentIntent(pendingIntent)
                                .setLargeIcon(bitmap)
                                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                                .setAutoCancel(true);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(NOTIFICATION_ID, builder.build());

                        jobFinished(jobParameters, !success);
                        Log.d("JobService", "onStartJob: Executed");
                    }
                });
                task.execute(gif.getHeightSmallStill());
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
