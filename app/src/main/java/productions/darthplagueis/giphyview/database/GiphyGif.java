package productions.darthplagueis.giphyview.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by oleg on 2/4/18.
 */

@Entity(tableName = "gifs")
public class GiphyGif {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "gif-url")
    private String url;

    @ColumnInfo(name = "gif-title")
    private String title;

    @ColumnInfo(name = "fixed-still")
    private String heightStill;

    @ColumnInfo(name = "still-width")
    private String fixedStillWidth;

    @ColumnInfo(name = "still-height")
    private String fixedStillHeight;

    @ColumnInfo(name = "fixed-small")
    private String heightSmallStill;

    @ColumnInfo(name = "small-width")
    private String fixedSmallStillWidth;

    @ColumnInfo(name = "small-height")
    private String fixedSmallStillHeight;

    @ColumnInfo(name = "gif-preview")
    private String previewGif;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeightStill() {
        return heightStill;
    }

    public void setHeightStill(String heightStill) {
        this.heightStill = heightStill;
    }

    public String getFixedStillWidth() {
        return fixedStillWidth;
    }

    public void setFixedStillWidth(String fixedStillWidth) {
        this.fixedStillWidth = fixedStillWidth;
    }

    public String getFixedStillHeight() {
        return fixedStillHeight;
    }

    public void setFixedStillHeight(String fixedStillHeight) {
        this.fixedStillHeight = fixedStillHeight;
    }

    public String getHeightSmallStill() {
        return heightSmallStill;
    }

    public void setHeightSmallStill(String heightSmallStill) {
        this.heightSmallStill = heightSmallStill;
    }

    public String getFixedSmallStillWidth() {
        return fixedSmallStillWidth;
    }

    public void setFixedSmallStillWidth(String fixedSmallStillWidth) {
        this.fixedSmallStillWidth = fixedSmallStillWidth;
    }

    public String getFixedSmallStillHeight() {
        return fixedSmallStillHeight;
    }

    public void setFixedSmallStillHeight(String fixedSmallStillHeight) {
        this.fixedSmallStillHeight = fixedSmallStillHeight;
    }

    public String getPreviewGif() {
        return previewGif;
    }

    public void setPreviewGif(String previewGif) {
        this.previewGif = previewGif;
    }
}
