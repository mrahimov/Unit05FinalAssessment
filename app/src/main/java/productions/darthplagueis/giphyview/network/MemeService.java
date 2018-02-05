package productions.darthplagueis.giphyview.network;

import productions.darthplagueis.giphyview.model.ModelResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by murodjon.rahimov on 2/4/18.
 */

public interface MemeService {

    String endPoint = "v1/gifs/trending";

    @GET(endPoint)
    Call<ModelResponse> getResponse(@Query("api_key") String apiKey, @Query("limit") int limit, @Query("rating") String rating);

}


//   "v1/gifs/trending"
//  Observable<ModelResponse> getResponse (@Query("api_key") String apiKey, @Query("limit") int limit, @Query("rating") String rating);
