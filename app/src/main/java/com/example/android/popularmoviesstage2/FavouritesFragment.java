package com.example.android.popularmoviesstage2;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ScrollView;

import com.example.android.popularmoviesstage2.data.FavouritesContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.favourites_not_found)
    ScrollView notFound;
    @BindView(R.id.grid_view_favourites) GridView gridView;

    private FavouritesAdapter favAdapter;
    private static final int CURSOR_LOADER_ID = 1;

    public FavouritesFragment(){

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Cursor c = getActivity().getContentResolver().query(FavouritesContract.FavouriteEntry.CONTENT_URI,
                        new String[]{FavouritesContract.FavouriteEntry._ID},
                        null,
                        null,
                        null);

        notFound.setVisibility(View.INVISIBLE);
        gridView.setVisibility(View.VISIBLE);

        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_favourites, container, false);
        ButterKnife.bind(this,v);

        // initialize our FlavorAdapter
        favAdapter = new FavouritesAdapter(getActivity(), null, 0, CURSOR_LOADER_ID);
        // set mGridView adapter to our CursorAdapter
        gridView.setAdapter(favAdapter);
        return v;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                FavouritesContract.FavouriteEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data == null || data.getCount() == 0){
            gridView.setVisibility(View.INVISIBLE);
            notFound.setVisibility(View.VISIBLE);
            favAdapter.swapCursor(null);
            favAdapter.notifyDataSetChanged();
        }else{
            notFound.setVisibility(View.INVISIBLE);
            gridView.setVisibility(View.VISIBLE);
            favAdapter.swapCursor(data);
            favAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favAdapter.swapCursor(null);
        favAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
        super.onResume();
    }
}
