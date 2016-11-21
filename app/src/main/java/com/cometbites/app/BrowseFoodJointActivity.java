package com.cometbites.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cometbites.R;
import com.cometbites.adapter.BrowseFoodJointAdapter;
import com.cometbites.model.FoodJoint;

public class BrowseFoodJointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsefoodjoint);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        setupRecyclerView();
    }

    private void setupRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        BrowseFoodJointAdapter adapter = new BrowseFoodJointAdapter(this, FoodJoint.getData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

//    public void goToItems(View view) {
//        // Do something in response to button
//        Intent intent = new Intent(this, ListItemsActivity.class);
//        startActivity(intent);
//    }
}
