package productions.darthplagueis.giphyview.network;

import javax.inject.Inject;
import productions.darthplagueis.giphyview.model.ModelResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by murodjon.rahimov on 2/4/18.
 */

public class Presenter {

private MemeService memeService;
private MemeView memeView;

  @Inject
  public Presenter(MemeService memeService) {
    this.memeService = memeService;
  }



  void setView(MemeView memeView) {
  this.memeView = memeView;
}

void loadMemes() {

    //memeService.getResponse("Ox1wPL2NMMyQvD57hmcMJQjWkv8u3nRx", 10, "23")


  //Observable<ModelResponse> friendResponseObservable = networkAPI.getFriendsObservable()
  //  .subscribeOn(Schedulers.newThread())
  //  .observeOn(AndroidSchedulers.mainThread());
  //
  //friendResponseObservable.subscribe(new Observer<FriendResponse>(){
  //  @Override
  //  public void onCompleted(){}
  //
  //  @Override
  //  public void onError(Throwable e){
  //    //handle error
  //  }
  //
  //  @Override
  //  public void onNext(FriendResponse response){
  //    //handle response
  //  }
  //});
    // Retrofit retrofit = new Retrofit.Builder() //
    //.baseUrl("https://api.giphy.com/") //
    //.addConverterFactory(GsonConverterFactory.create())
    //.build();
}
}
