package com.cometbites.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cometbites.R;
import com.cometbites.adapter.ListItemsAdapter;
import com.cometbites.model.Item;

public class ListItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerItemView = (RecyclerView) findViewById(R.id.recyclerItemView);
        ListItemsAdapter adapter1 = new ListItemsAdapter(this, Item.getData());
        recyclerItemView.setAdapter(adapter1);

        LinearLayoutManager mLinearLayoutManagerVertical1 = new LinearLayoutManager(this); // (Context context, int spanCount)
        mLinearLayoutManagerVertical1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerItemView.setLayoutManager(mLinearLayoutManagerVertical1);

        recyclerItemView.setItemAnimator(new DefaultItemAnimator());
    }
}
