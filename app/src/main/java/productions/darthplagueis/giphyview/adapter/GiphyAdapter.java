package productions.darthplagueis.giphyview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import productions.darthplagueis.giphyview.R;
import productions.darthplagueis.giphyview.view.GiphyViewHolder;

/**
 * Created by c4q on 2/4/18.
 */

public class GiphyAdapter extends RecyclerView.Adapter<GiphyViewHolder> {

    List<GiphyData> giphyDataList;

    public GiphyAdapter(List<GiphyData> giphyDataList) {

        this.giphyDataList = giphyDataList;
    }

    @Override
    public GiphyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.giphy_item_view, parent, false);
        return new GiphyViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(GiphyViewHolder holder, int position) {
        GiphyData giphyData = giphyDataList.get(position);
        holder.onBind(giphyData);
    }

    @Override
    public int getItemCount() {
        return giphyDataList.size();
    }
}
