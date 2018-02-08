package productions.darthplagueis.giphyview.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import productions.darthplagueis.giphyview.MainActivity;
import productions.darthplagueis.giphyview.R;
import productions.darthplagueis.giphyview.database.util.DatabaseInitializer;
import productions.darthplagueis.giphyview.network.GiphyRetrofit;

/**
 * Created by c4q on 2/7/18.
 */

public class GiphyJobService extends JobService {
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        GiphyRetrofit.getInstance().makeApiCall(getApplicationContext());
        DatabaseInitializer.setAsyncResponse(new DatabaseInitializer.AsyncResponse() {
            @Override
            public void onPostExecute(boolean success) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                int NOTIFICATION_ID = 555;
                String NOTIFICATION_CHANNEL = "Come View Funny Gifs!";

                int requestID = (int) System.currentTimeMillis();
                int flags = PendingIntent.FLAG_CANCEL_CURRENT;

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestID, intent, flags);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification")
                        .setContentText("Come View Funny Gifs!")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, builder.build());

                jobFinished(jobParameters, !success);
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
