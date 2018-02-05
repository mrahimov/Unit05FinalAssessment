package productions.darthplagueis.giphyview.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import productions.darthplagueis.giphyview.R;
import productions.darthplagueis.giphyview.database.GiphyGif;
import productions.darthplagueis.giphyview.model.datadetails.GiphyData;

/**
 * Created by c4q on 2/4/18.
 */

public class GiphyViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ImageView giphyGif;
    private TextView textView;

    public GiphyViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        giphyGif = itemView.findViewById(R.id.giphy_imageview);
        textView = itemView.findViewById(R.id.textview);
    }

    public void onBind(GiphyGif gif) {
        Glide.with(context)
                .load(gif.getHeightStill())
                .apply(new RequestOptions().override(Integer.parseInt(gif.getFixedStillWidth()), Integer.parseInt(gif.getFixedStillHeight())))
                .into(giphyGif);

        String id = String.valueOf(gif.getId());
        textView.setText(id);
    }
}
