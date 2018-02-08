package productions.darthplagueis.giphyview.network;

import android.content.Context;
import android.util.Log;

import java.util.List;

import productions.darthplagueis.giphyview.database.GifDatabase;
import productions.darthplagueis.giphyview.database.util.DatabaseInitializer;
import productions.darthplagueis.giphyview.model.ModelResponse;
import productions.darthplagueis.giphyview.model.datadetails.GiphyData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static productions.darthplagueis.giphyview.BuildConfig.API_KEY;

/**
 * Created by oleg on 2/4/18.
 */

public class GiphyRetrofit {

    private static final String TAG = GiphyRetrofit.class.getSimpleName();

    private Retrofit retrofit;

    private static GiphyRetrofit INSTANCE;

    private GiphyRetrofit() {
        String startPoint = "https://api.giphy.com/";
        retrofit = new Retrofit.Builder()
                .baseUrl(startPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static GiphyRetrofit getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GiphyRetrofit();
        }
        return INSTANCE;
    }

    public MemeService getMemeService() {
        return retrofit.create(MemeService.class);
    }

    public static void makeApiCall(final Context context, final boolean isJobService) {
        MemeService memeService = GiphyRetrofit.getInstance().getMemeService();
        Call<ModelResponse> call = memeService.getResponse(API_KEY, 30, "R");
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                if (response.isSuccessful()) {
                    ModelResponse modelResponse = response.body();
                    List<GiphyData> dataList = modelResponse.getData();
                    if (isJobService) {
                        DatabaseInitializer.populateAsync(GifDatabase.getDatabase(context), dataList);
                    } else {
                        DatabaseInitializer.populateViewAsync(GifDatabase.getDatabase(context), dataList);
                    }
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
