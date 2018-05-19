package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Trailer> mTrailers;
    private TrailerAdapterOnClickHandler mClickHandler;

    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();

    public TrailerAdapter(Context context, ArrayList<Trailer> trailers,TrailerAdapterOnClickHandler clickHandler){
        mContext = context;
        mTrailers = trailers;
        mClickHandler = clickHandler;
    }

    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.trailer) TextView mTrailer;
        @BindView(R.id.share_button) ImageView shareView;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(mTrailers.get(position));
        }
    }


    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_trailers,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String text = "Trailer " + mTrailers.get(position).getId();
        holder.mTrailer.setText(text);

        holder.shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mTrailers == null)
            return 0;
        return mTrailers.size();
    }

    public void setTrailerData(ArrayList<Trailer> trailers) {
        mTrailers = trailers;
        notifyDataSetChanged();
    }
}