package com.udacity.sandwichclub;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.udacity.sandwichclub.adapters.SandwichAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = findViewById(R.id.sandwiches_recycleview);

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);
        String[] sandwichImages = getResources().getStringArray(R.array.sandwich_images);
        SandwichAdapter adapter = new SandwichAdapter(this, sandwiches, sandwichImages);
        final LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        adapter.setOnSandwichItemClickListener(new SandwichAdapter.OnSandwichItemClickListener() {
            @Override
            public void onClick(int position) {
                launchDetailActivity(position, manager, recyclerView);
            }
        });
        // Simplification: Using a ListView instead of a RecyclerView
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }

    private void launchDetailActivity(int position, LinearLayoutManager manager, RecyclerView recyclerView) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = manager.findLastVisibleItemPosition();

        List<Pair<View, String>> pairs = new ArrayList<>();
        for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
            SandwichAdapter.SandwichHolder holderAtPosition = (SandwichAdapter.SandwichHolder) recyclerView.findViewHolderForAdapterPosition(i);
            View itemView = holderAtPosition.sandwichImage;
            pairs.add(Pair.create(itemView, "data_key" + i));
        }
        Pair.create(((SandwichAdapter.SandwichHolder) recyclerView.findViewHolderForAdapterPosition(position)).nameOfSandwich,"name");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, pairs.toArray(new Pair[]{})).toBundle();
            startActivity(intent, bundle);
        } else
            startActivity(intent);

    }
}
