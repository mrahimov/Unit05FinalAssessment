package productions.darthplagueis.giphyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import dagger.Module;
import java.util.List;
import javax.inject.Inject;
import productions.darthplagueis.giphyview.model.GiphyData;
import productions.darthplagueis.giphyview.model.ModelResponse;
import productions.darthplagueis.giphyview.network.MemeService;
import productions.darthplagueis.giphyview.network.MemeView;
import productions.darthplagueis.giphyview.network.Presenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Inject Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         Retrofit retrofit = new Retrofit.Builder() //
        .baseUrl("https://api.giphy.com/") //
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        MemeService memeService = retrofit.create(MemeService.class);

        Call<ModelResponse> call = memeService.getRespone("Ox1wPL2NMMyQvD57hmcMJQjWkv8u3nRx", 30, "G");
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                ModelResponse modelResponse = response.body();
                List<GiphyData> data = modelResponse.getData();

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {

            }

        });
    }



}

