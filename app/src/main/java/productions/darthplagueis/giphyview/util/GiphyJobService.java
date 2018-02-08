package productions.darthplagueis.giphyview.util;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by c4q on 2/7/18.
 */

public class GiphyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
