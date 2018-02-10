package productions.darthplagueis.giphyview.view;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import productions.darthplagueis.giphyview.MainActivity;
import productions.darthplagueis.giphyview.R;
import productions.darthplagueis.giphyview.database.GiphyGif;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by c4q on 2/4/18.
 */

public class GiphyViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private GiphyGif gif;
    private ImageView giphyGif;
    private TextView textView;
    private Button browserBtn, downloadBtn;

    public GiphyViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        giphyGif = itemView.findViewById(R.id.giphy_imageview);
        textView = itemView.findViewById(R.id.giphy_title);
        browserBtn = itemView.findViewById(R.id.start_browser);
        downloadBtn = itemView.findViewById(R.id.download_gif);
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

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("gif-download");
                intent.putExtra("gif-extras", new String[]{gif.getHeightStill(), gif.getTitle(), String.valueOf(gif.getId())});
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

//                        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
//                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(gif.getHeightStill()));
//                        String downloadTitle = String.valueOf(gif.getId()) + ".mp4";
//                        request.setTitle(gif.getTitle())
//                                .setDescription("Downloading Gif")
//                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadTitle);
//                        downloadManager.enqueue(request);


            }
        });
    }
}
