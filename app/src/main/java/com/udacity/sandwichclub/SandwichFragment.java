package com.udacity.sandwichclub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.sandwichclub.model.Sandwich;

public class SandwichFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_sandwich, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Sandwich sandwich = (Sandwich) getArguments().getParcelable("sandwich");
        if (sandwich == null) {

        } else {

            ((TextView) view.findViewById(R.id.name)).setText(sandwich.getName().getMainName());
            ((TextView) view.findViewById(R.id.description)).setText(sandwich.getDescription());
            ((TextView) view.findViewById(R.id.place)).setText(sandwich.getPlaceOfOrigin());
            ((TextView) view.findViewById(R.id.ingredients)).setText(TextUtils.join(",", sandwich.getIngredients()));
            ((TextView) view.findViewById(R.id.also_know_as)).setText(TextUtils.join(", ", sandwich.getName().getAlsoKnownAs()));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
