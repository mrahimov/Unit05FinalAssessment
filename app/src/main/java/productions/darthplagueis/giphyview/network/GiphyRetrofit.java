package productions.darthplagueis.giphyview.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oleg on 2/4/18.
 */

public class GiphyRetrofit {

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
}
