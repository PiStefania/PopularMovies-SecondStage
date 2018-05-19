package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Review> mReviews;
    private ReviewAdapterOnClickHandler mClickHandler;

    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();

    public ReviewAdapter(Context context, ArrayList<Review> reviews,ReviewAdapterOnClickHandler clickHandler){
        mContext = context;
        mReviews = reviews;
        mClickHandler = clickHandler;
    }

    public interface ReviewAdapterOnClickHandler {
        void onClick(Review review);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.content) TextView mContent;
        @BindView(R.id.author) TextView mAuthor;
        @BindView(R.id.no_review) TextView mNoReview;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(mReviews.get(position));
        }
    }


    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_reviews,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String author = mReviews.get(position).getAuthor();
        String content = mReviews.get(position).getContent();
        String noReview = Integer.toString(mReviews.get(position).getId()) + ". ";
        holder.mAuthor.setText(Html.fromHtml("by <u>" + author + "</u>"));
        holder.mContent.setText(content);
        holder.mNoReview.setText(noReview);
    }

    @Override
    public int getItemCount() {
        if(mReviews == null)
            return 0;
        return mReviews.size();
    }

    public void setTrailerData(ArrayList<Review> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }
}