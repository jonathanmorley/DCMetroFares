package me.morley.dcmetrofares;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Arrays;

import me.morley.dcmetrofares.metro.*;

/**
 * A fragment representing a single Station detail screen.
 * This fragment is either contained in a {@link StationListActivity}
 * in two-pane mode (on tablets) or a {@link StationDetailActivity}
 * on handsets.
 */
public class StationDetailFragment extends Fragment
        implements LoaderCallbacks<Cursor> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "me.morley.dcmetrofares.station_detail";

    SimpleCursorAdapter detailAdapter;
    SimpleCursorAdapter linesAdapter;
    SimpleCursorAdapter routesAdapter;

    /**
     * The dummy content this fragment is presenting.
     */
    //private Station station;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StationDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the station specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // For the cursor adapter, specify which columns go into which views
            String[] fromColumns = {"wp_name","parking"};
            int[] toViews = {R.id.station_detail_name, R.id.station_detail_subtitle}; // The TextView in simple_list_item_1

            // Create an empty adapter we will use to display the loaded data.
            // We pass null for the cursor, then update it in onLoadFinished()
            detailAdapter = new SimpleCursorAdapter(getActivity(),
                    R.layout.fragment_station_detail, null,
                    fromColumns, toViews, 0);

            fromColumns = new String[] {"d_wp_name","off_peak_fare"};
            toViews = new int[] {android.R.id.text1,android.R.id.text2};
            routesAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_2, null,
                    fromColumns, toViews, 0);

            fromColumns = new String[] {"line_code"};
            toViews = new int[] {android.R.id.text1};
            linesAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, null,
                    fromColumns, toViews, 0);

            // Prepare the loader.  Either re-connect with an existing one,
            // or start a new one.

            Bundle bundle = new Bundle();
            bundle.putString("slug", getArguments().getString(ARG_ITEM_ID));

            getLoaderManager().initLoader(1, bundle, this);
            getLoaderManager().initLoader(2, bundle, this);
            getLoaderManager().initLoader(3, bundle, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_station_detail, container, false);

        if (rootView != null) {
            ((ListView) rootView.findViewById(R.id.routes_list)).setAdapter(routesAdapter);
            ((GridView) rootView.findViewById(R.id.lines_grid)).setAdapter(linesAdapter);
        }

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String slug = bundle.getString("slug");
        switch(i) {
            case 1:
                return new CursorLoader(this.getActivity(),
                        Uri.parse("content://me.morley.dcmetrofares.MetroDBProvider/stations/" + slug),
                        new String[] {"_id", "wp_name", "latitude", "longitude", "parking"},
                        null,
                        null,
                        null);
            case 2:
                return new CursorLoader(this.getActivity(),
                        Uri.parse("content://me.morley.dcmetrofares.MetroDBProvider/routes/" + slug),
                        null,
                        null,
                        null,
                        null);
            case 3:
                return new CursorLoader(this.getActivity(),
                        Uri.parse("content://me.morley.dcmetrofares.MetroDBProvider/lines/" + slug),
                        null,
                        null,
                        null,
                        null);
            default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        switch(cursorLoader.getId()) {
            case 1:
                detailAdapter.swapCursor(cursor);
                detailAdapter.bindView(getView(),getActivity(),cursor);
                break;
            case 2:
                routesAdapter.swapCursor(cursor);
                routesAdapter.bindView(getView(),getActivity(),cursor);
                break;
            case 3:
                linesAdapter.swapCursor(cursor);
                Log.d("LoadFinished", "Count: " + cursor.getCount());
                linesAdapter.bindView(getView(),getActivity(),cursor);
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        switch(cursorLoader.getId()) {
            case 1:
                detailAdapter.swapCursor(null);
                break;
            case 2:
                routesAdapter.swapCursor(null);
                break;
            case 3:
                linesAdapter.swapCursor(null);
                break;
        }
    }
}
