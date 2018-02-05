package productions.darthplagueis.giphyview.model;

/**
 * Created by murodjon.rahimov on 2/4/18.
 */

public class GiphyData {

  private String url;
  private ModelUser user;
  private ModelImages images;

  public GiphyData(String url, ModelUser user, ModelImages images) {
    this.url = url;
    this.user = user;
    this.images = images;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public ModelUser getUser() {
    return user;
  }

  public void setUser(ModelUser user) {
    this.user = user;
  }

  public ModelImages getImages() {
    return images;
  }

  public void setImages(ModelImages images) {
    this.images = images;
  }
}