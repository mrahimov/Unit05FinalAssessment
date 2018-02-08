package productions.darthplagueis.giphyview;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import productions.darthplagueis.giphyview.adapter.GiphyAdapter;
import productions.darthplagueis.giphyview.database.GifDatabase;
import productions.darthplagueis.giphyview.database.GiphyGif;
import productions.darthplagueis.giphyview.database.util.DatabaseInitializer;
import productions.darthplagueis.giphyview.database.util.GifViewModel;
import productions.darthplagueis.giphyview.model.datadetails.GiphyData;
import productions.darthplagueis.giphyview.model.ModelResponse;
import productions.darthplagueis.giphyview.network.GiphyRetrofit;
import productions.darthplagueis.giphyview.network.MemeService;
import productions.darthplagueis.giphyview.network.Presenter;
import productions.darthplagueis.giphyview.util.GiphyJobService;
import productions.darthplagueis.giphyview.view.GiphyViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static productions.darthplagueis.giphyview.BuildConfig.API_KEY;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN_ACTIVITY";
    private SharedPreferences preferences;
    private RecyclerView giphyRecyclerView;
    private GiphyAdapter adapter;
    private GifViewModel gifViewModel;
    private boolean hasExecutedInitialCall;
    private boolean updateWithDiff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        hasExecutedInitialCall = preferences.getBoolean("has_executed", false);

        //DatabaseInitializer.removeAllFromDb(GifDatabase.getDatabase(getApplicationContext()));

        giphyRecyclerView = findViewById(R.id.giphy_recyclerview);
        adapter = new GiphyAdapter();
        giphyRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        giphyRecyclerView.setLayoutManager(linearLayoutManager);

        gifViewModel = ViewModelProviders.of(this).get(GifViewModel.class);

        if (!hasExecutedInitialCall) {
            if (isNetworkAvailable()) {
                createJobService();
                GiphyRetrofit.makeApiCall(getApplicationContext(), false);
            } else {
                Toast.makeText(MainActivity.this, "Please check your internet connection" +
                        " and try again", Toast.LENGTH_LONG).show();
            }
        } else {
            showGifsInUi();
        }

        onPostExecuteListener();
        setSwipeListener();
    }

    private void createJobService(){
        long ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L;
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(new JobInfo.Builder(1, new ComponentName(this, GiphyJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(ONE_DAY_INTERVAL)
                .build());
    }

    private void onPostExecuteListener() {
        DatabaseInitializer.setAdapterResponse(new DatabaseInitializer.AdapterResponse() {
            @Override
            public void onPostExecute() {
                hasExecutedInitialCall = true;
                preferences.edit().putBoolean("has_executed", hasExecutedInitialCall).apply();
                updateWithDiff = true;
                showGifsInUi();
            }
        });
    }

    private void showGifsInUi() {
        gifViewModel.initializeDb();
        gifViewModel.getGifsList().observe(this, new Observer<List<GiphyGif>>() {
            @Override
            public void onChanged(@Nullable List<GiphyGif> gifList) {
                updateRecyclerView(gifList);
                if (updateWithDiff) {
                    adapter.updateWithDifference(gifList);
                    updateWithDiff = false;
                }
            }
        });
    }

    private void updateRecyclerView(List<GiphyGif> gifList) {
        adapter.passListToAdapter(gifList);
    }

    private void setSwipeListener() {
        ItemTouchHelper.SimpleCallback itemCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                GiphyGif gif = ((GiphyViewHolder) viewHolder).getGif();
                DatabaseInitializer.removeSpecGif(GifDatabase.getDatabase(getApplicationContext()), gif);
                Toast.makeText(MainActivity.this, "Gif Removed", Toast.LENGTH_LONG).show();
                int position = viewHolder.getAdapterPosition();
                adapter.removeGif(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemCallBack);
        itemTouchHelper.attachToRecyclerView(giphyRecyclerView);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}

