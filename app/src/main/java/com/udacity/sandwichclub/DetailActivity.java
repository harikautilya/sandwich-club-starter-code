package com.udacity.sandwichclub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.udacity.sandwichclub.adapters.FragmentAdapter;
import com.udacity.sandwichclub.adapters.ImageAdapter;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private List<Sandwich> sandwichesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sandwichesList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_detail);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
        final RecyclerView imagesOfSandwiches = findViewById(R.id.image_iv);
        final ViewPager viewPager = findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int currentPosition = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (currentPosition == DEFAULT_POSITION) {
            closeOnError();
            return;
        }


        populateToolbar(imagesOfSandwiches, currentPosition);
        populateViewPager(viewPager, currentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                imagesOfSandwiches.smoothScrollToPosition(position);
                collapsingToolbarLayout.setTitle(sandwichesList.get(position).getName().getMainName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        supportPostponeEnterTransition();
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(imagesOfSandwiches);

        getSupportActionBar().setTitle(sandwichesList.get(currentPosition).getName().getMainName());

        viewPager.post(new Runnable() {
            @Override
            public void run() {
                supportStartPostponedEnterTransition();
            }
        });




    }

    private void populateViewPager(ViewPager viewPager, int currentPosition) {
        List<Fragment> fragments = new ArrayList<>();

        final String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        for (int i = 0; i < sandwiches.length; i++) {
            String json = sandwiches[i];
            final Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            Bundle bundle = new Bundle();
            bundle.putParcelable("sandwich", sandwich);
            fragments.add(SandwichFragment.instantiate(this, SandwichFragment.class.getName(), bundle));
            sandwichesList.add(sandwich);


        }
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(currentPosition);
        getSupportActionBar().setTitle("");

    }

    @SuppressLint("ClickableViewAccessibility")
    private void populateToolbar(RecyclerView ingredientsIv, int currentPosition) {
        final LinearLayoutManager imageManger = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ingredientsIv.setLayoutManager(imageManger);
        ingredientsIv.setAdapter(new ImageAdapter(this, getResources().getStringArray(R.array.sandwich_images)));
        ingredientsIv.scrollToPosition(currentPosition);
        ingredientsIv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Used to remove the touch of user related activity and still allow scrolling
                return true;
            }
        });
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
