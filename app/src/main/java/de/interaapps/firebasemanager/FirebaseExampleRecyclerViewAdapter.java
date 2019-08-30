package de.interaapps.firebasemanager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FirebaseExampleRecyclerViewAdapter extends RecyclerView.Adapter<FirebaseExampleRecyclerViewAdapter.ViewHolder> {

    private List<String> mName;
    private List<Drawable> mImage;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    FirebaseExampleRecyclerViewAdapter(Context context, List<String> data, List<Drawable> image) {
        this.mInflater = LayoutInflater.from(context);
        this.mName = data;
        this.mImage = image;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_item_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        holder.name.setText(mName.get(position));
        holder.image.setImageDrawable(mImage.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mName.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatImageView image;
        AppCompatTextView name;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recycler_image);
            name = itemView.findViewById(R.id.recycler_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
