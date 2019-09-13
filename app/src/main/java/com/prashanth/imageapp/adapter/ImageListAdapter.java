package com.prashanth.imageapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.prashanth.imageapp.R;
import com.prashanth.imageapp.model.ImageDetails;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private Context context;

    public ImageListAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<ImageDetails> imageDetailsList = new ArrayList<>();

    @NotNull
    @Override
    public ImageListAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String photoUrl = imageDetailsList.get(position).getAssets().getHugeThumb().getUrl();
        int width = imageDetailsList.get(position).getAssets().getHugeThumb().getWidth();
        int height = imageDetailsList.get(position).getAssets().getHugeThumb().getHeight();

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(holder.layout);
        constraintSet.setDimensionRatio(holder.photo.getId(), width + ":" + height);
        constraintSet.applyTo(holder.layout);

        Glide.with(context.getApplicationContext())
                .load(photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return imageDetailsList == null ? 0 : imageDetailsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint_layout)
        ConstraintLayout layout;

        @BindView(R.id.shutterstock_photo)
        ImageView photo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void update(ArrayList<ImageDetails> imageDetails) {
        this.imageDetailsList = imageDetails;
        notifyDataSetChanged();
    }

}
