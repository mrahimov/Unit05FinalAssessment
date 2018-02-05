package productions.darthplagueis.giphyview.model;

/**
 * Created by murodjon.rahimov on 2/4/18.
 */

public class ModelImages {
  private String fixed_height;
  private String preview_gif;

  public ModelImages(String fixed_height, String preview_gif) {
    this.fixed_height = fixed_height;
    this.preview_gif = preview_gif;
  }

  public String getFixed_height() {
    return fixed_height;
  }

  public void setFixed_height(String fixed_height) {
    this.fixed_height = fixed_height;
  }

  public String getPreview_gif() {
    return preview_gif;
  }

  public void setPreview_gif(String preview_gif) {
    this.preview_gif = preview_gif;
  }
}
