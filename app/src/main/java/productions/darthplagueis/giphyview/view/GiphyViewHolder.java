package productions.darthplagueis.giphyview.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import productions.darthplagueis.giphyview.R;

/**
 * Created by c4q on 2/4/18.
 */

public class GiphyViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    public ImageView giphyGif;

    public GiphyViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        giphyGif = itemView.findViewById(R.id.giphy_imageview);
    }

    public void onBind(GiphyData giphyData){


    }
}
