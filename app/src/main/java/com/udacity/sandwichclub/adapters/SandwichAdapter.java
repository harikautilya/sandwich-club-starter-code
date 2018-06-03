package com.udacity.sandwichclub.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.SandwichHolder> {

    private String[] images;
    private String[] name;
    private Context context;
    private OnSandwichItemClickListener onSandwichItemClickListener;

    public SandwichAdapter(Context context, String[] name, String[] images) {
        this.images = images;
        this.name = name;
        this.context = context;
        if (images.length != name.length)
            throw new IllegalStateException("The sandwichImage and name are not of equal size");
    }

    @NonNull
    @Override
    public SandwichHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SandwichHolder(LayoutInflater.from(context).inflate(R.layout.item_sandwich, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichHolder holder, final int position) {
        holder.bind(images[position], name[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSandwichItemClickListener != null)
                    onSandwichItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public void setOnSandwichItemClickListener(OnSandwichItemClickListener onSandwichItemClickListener) {
        this.onSandwichItemClickListener = onSandwichItemClickListener;
    }

   public class SandwichHolder extends RecyclerView.ViewHolder {

        public ImageView sandwichImage;
        public TextView nameOfSandwich;


        public SandwichHolder(View itemView) {
            super(itemView);
            sandwichImage = itemView.findViewById(R.id.sandwich_image_iv);
            nameOfSandwich = itemView.findViewById(R.id.sandwich_name_tv);
        }

        public void bind(String image, String name) {
            nameOfSandwich.setText(name);
            Picasso.with(context)
                    .load(image)
                    .into(sandwichImage);
        }
    }

    public interface OnSandwichItemClickListener {
        void onClick(int position);
    }
}
