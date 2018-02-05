package productions.darthplagueis.giphyview.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by murodjon.rahimov on 2/4/18.
 */

public class ModelResponse {
  private List<GiphyData> data;
  private ModelPagination paginaton;
  private ModelMeta meta;

  public List<GiphyData> getData() {
    return data;
  }

  public ModelPagination getPaginaton() {
    return paginaton;
  }

  public ModelMeta getMeta() {
    return meta;
  }
}