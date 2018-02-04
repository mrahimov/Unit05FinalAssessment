package productions.darthplagueis.giphyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import productions.darthplagueis.giphyview.adapter.GiphyAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView giphyRecyclerView = findViewById(R.id.giphy_recyclerview);
        GiphyAdapter giphyAdapter = new GiphyAdapter(giphyDataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        giphyRecyclerView.setAdapter(giphyAdapter);
        giphyRecyclerView.setLayoutManager(linearLayoutManager);
    }

}
