package productions.darthplagueis.giphyview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static productions.darthplagueis.giphyview.BuildConfig.API_KEY;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN_ACTIVITY";
    private GiphyAdapter adapter;
    private GifViewModel gifViewModel;

    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseInitializer.removeAllFromDb(GifDatabase.getDatabase(getApplicationContext()));

        RecyclerView giphyRecyclerView = findViewById(R.id.giphy_recyclerview);
        adapter = new GiphyAdapter();
        giphyRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        giphyRecyclerView.setLayoutManager(linearLayoutManager);


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

        gifViewModel = ViewModelProviders.of(this).get(GifViewModel.class);
        gifViewModel.initializeDb();
        gifViewModel.getGifsList().observeForever(new Observer<List<GiphyGif>>() {
            @Override
            public void onChanged(@Nullable List<GiphyGif> gifList) {
                updateRecyclerView(gifList);
            }
        });


    }

    private void updateRecyclerView(List<GiphyGif> gifList) {
        adapter.passListToAdapter(gifList);
    }
}

