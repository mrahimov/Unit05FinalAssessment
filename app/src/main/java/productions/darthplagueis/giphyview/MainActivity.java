package productions.darthplagueis.giphyview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import productions.darthplagueis.giphyview.view.GiphyViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static productions.darthplagueis.giphyview.BuildConfig.API_KEY;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN_ACTIVITY";
    private RecyclerView giphyRecyclerView;
    private GiphyAdapter adapter;
    private GifViewModel gifViewModel;
    private boolean updateWithDiff;

    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseInitializer.removeAllFromDb(GifDatabase.getDatabase(getApplicationContext()));

        giphyRecyclerView = findViewById(R.id.giphy_recyclerview);
        adapter = new GiphyAdapter();
        giphyRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        giphyRecyclerView.setLayoutManager(linearLayoutManager);

        gifViewModel = ViewModelProviders.of(this).get(GifViewModel.class);

        onPostExecuteListener();
        setSwipeListener();

        if (isNetworkAvailable()) {
            makeApiCall();
        } else {
            Toast.makeText(MainActivity.this, "Please check your internet connection" +
                    " and try again", Toast.LENGTH_LONG).show();
        }


    }

    private void onPostExecuteListener() {
        DatabaseInitializer.setAsyncResponse(new DatabaseInitializer.AsyncResponse() {
            @Override
            public void onPostExecute() {
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

    private void makeApiCall() {
        MemeService memeService = GiphyRetrofit.getInstance().getMemeService();
        Call<ModelResponse> call = memeService.getResponse(API_KEY, 30, "R");
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                if (response.isSuccessful()) {
                    ModelResponse modelResponse = response.body();
                    List<GiphyData> dataList = modelResponse.getData();
                    DatabaseInitializer.populateAsync(GifDatabase.getDatabase(getApplicationContext()), dataList);
                    Log.d(TAG, "onResponse: listSize = " + dataList.size());
                    Log.d(TAG, "onResponse: testUrl = " + dataList.get(0).getUrl());
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

