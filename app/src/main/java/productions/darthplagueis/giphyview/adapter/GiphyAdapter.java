package productions.darthplagueis.giphyview.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import productions.darthplagueis.giphyview.R;
import productions.darthplagueis.giphyview.database.GiphyGif;
import productions.darthplagueis.giphyview.model.datadetails.GiphyData;
import productions.darthplagueis.giphyview.util.DiffUtility;
import productions.darthplagueis.giphyview.view.GiphyViewHolder;

/**
 * Created by c4q on 2/4/18.
 */

public class GiphyAdapter extends RecyclerView.Adapter<GiphyViewHolder> {

    private List<GiphyGif> giphyDataList;

    public GiphyAdapter() {
        giphyDataList = new ArrayList<>();
    }

    @Override
    public GiphyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.giphy_item_view, parent, false);
        return new GiphyViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(GiphyViewHolder holder, int position) {
        holder.onBind(giphyDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return giphyDataList.size();
    }

    public void passListToAdapter(List<GiphyGif> newList) {
        giphyDataList.addAll(newList);
        notifyItemRangeInserted(getItemCount(), giphyDataList.size() - 1);
    }

    public void removeGif(int position) {
        giphyDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateWithDifference(List<GiphyGif> newList) {
        DiffUtility diffUtility = new DiffUtility(giphyDataList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtility);
        giphyDataList.clear();
        giphyDataList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }
}
