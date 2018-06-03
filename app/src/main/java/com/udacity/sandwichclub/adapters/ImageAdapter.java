package com.udacity.sandwichclub.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private Context context;
    private String[] images;

    public ImageAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder(LayoutInflater.from(context).inflate(R.layout.item_sandwich_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.sandwichImage.setTransitionName("data_key" + position);
        }
        holder.bind(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        ImageView sandwichImage;


        public ImageHolder(View itemView) {
            super(itemView);
            sandwichImage = itemView.findViewById(R.id.sandwich_image_toolbar);
        }

        public void bind(String image) {
            Picasso.with(context)
                    .load(image)
                    .into(sandwichImage);
        }
    }
}
