package productions.darthplagueis.giphyview.util;

import android.support.v7.util.DiffUtil;

import java.util.List;

import productions.darthplagueis.giphyview.database.GiphyGif;

/**
 * Created by oleg on 2/5/18.
 */

public class DiffUtility extends DiffUtil.Callback {

    private List<GiphyGif> oldGifsList;
    private List<GiphyGif> newGifsList;

    public DiffUtility(List<GiphyGif> oldGifsList, List<GiphyGif> newGifsList) {
        this.oldGifsList = oldGifsList;
        this.newGifsList = newGifsList;
    }

    @Override
    public int getOldListSize() {
        return oldGifsList != null ? oldGifsList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newGifsList != null ? newGifsList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldGifsList.get(oldItemPosition).getId() == newGifsList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        GiphyGif oldGif = oldGifsList.get(oldItemPosition);
        GiphyGif newGif = newGifsList.get(newItemPosition);
        return oldGif.getHeightStill().contentEquals(newGif.getHeightStill());
    }
}