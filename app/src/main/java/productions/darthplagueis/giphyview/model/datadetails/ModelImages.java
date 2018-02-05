package productions.darthplagueis.giphyview.model.datadetails;

import productions.darthplagueis.giphyview.model.datadetails.imagedetails.ModelFHSmallStill;
import productions.darthplagueis.giphyview.model.datadetails.imagedetails.ModelFHStill;
import productions.darthplagueis.giphyview.model.datadetails.imagedetails.ModelPreview;

/**
 * Created by murodjon.rahimov on 2/4/18.
 */

public class ModelImages {

    private ModelFHStill fixed_height;
    private ModelFHSmallStill fixed_height_small_still;
    private ModelPreview preview_gif;

    public ModelFHStill getFixed_height_still() {
        return fixed_height;
    }

    public ModelFHSmallStill getFixed_height_small_still() {
        return fixed_height_small_still;
    }

    public ModelPreview getPreview_gif() {
        return preview_gif;
    }
}
