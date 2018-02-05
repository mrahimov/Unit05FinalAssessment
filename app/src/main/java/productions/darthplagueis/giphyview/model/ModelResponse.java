package productions.darthplagueis.giphyview.model;

import java.util.List;

import productions.darthplagueis.giphyview.model.datadetails.GiphyData;

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