package productions.darthplagueis.giphyview.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
    private GiphyGif gif;
    private ImageView giphyGif;
    private TextView textView;
    private Button browserBtn;

    public GiphyViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        giphyGif = itemView.findViewById(R.id.giphy_imageview);
        textView = itemView.findViewById(R.id.giphy_title);
        browserBtn = itemView.findViewById(R.id.startBrowser);
    }

    public GiphyGif getGif() {
        return gif;
    }

    public void onBind(final GiphyGif gif) {
        this.gif = gif;
        Glide.with(context)
                .load(gif.getHeightStill())
                .apply(new RequestOptions().override(Integer.parseInt(gif.getFixedStillWidth()), Integer.parseInt(gif.getFixedStillHeight())))
                .into(giphyGif);

        textView.setText(gif.getTitle());

        browserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = gif.getUrl();
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

    }
}
