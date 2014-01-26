package me.morley.dcmetrofares;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan on 23/01/14.
 */
public class MetroDBProvider extends ContentProvider {

    private DatabaseHelper mOpenHelper;
    private UriMatcher matcher;

    private static final String AUTHORITY = "me.morley.dcmetrofares.MetroDBProvider";

    private static final int STATIONS = 1;
    private static final int STATION = 2;
    private static final int ROUTES = 3;
    private static final int ORIGINROUTES = 4;
    private static final int ROUTE = 5;
    private static final int LINES = 6;
    private static final int LINE = 7;

    public MetroDBProvider() {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "stations", STATIONS);
        matcher.addURI(AUTHORITY, "stations/*", STATION);
        matcher.addURI(AUTHORITY, "routes", ROUTES);
        matcher.addURI(AUTHORITY, "routes/*", ORIGINROUTES);
        matcher.addURI(AUTHORITY, "routes/*/*", ROUTE);
        matcher.addURI(AUTHORITY, "lines", LINES);
        matcher.addURI(AUTHORITY, "lines/*", LINE);
    }

    public static class DatabaseHelper extends SQLiteAssetHelper {
        private static final String DATABASE_NAME = "metro.db";
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            // calls the super constructor, requesting the default cursor factory.
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
    }

    /**
     *
     * Initializes the provider by creating a new DatabaseHelper. onCreate() is called
     * automatically when Android creates the provider in response to a resolver request from a
     * client.
     */
    @Override
    public boolean onCreate() {

        // Creates a new helper object. Note that the database itself isn't opened until
        // something tries to access it, and it's only created if it doesn't already exist.
        mOpenHelper = new DatabaseHelper(getContext());

        // Assumes that any failures will be reported by a thrown exception.
        return true;
    }

    /**
     * This is called when a client calls {@link android.content.ContentResolver#getType(Uri)}.
     * Returns the MIME data type of the URI given as a parameter.
     *
     * @param uri The URI whose MIME type is desired.
     * @return The MIME type of the URI.
     * @throws IllegalArgumentException if the incoming URI pattern is invalid.
     */
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case STATIONS:
                //TODO: get the right MIME type for these
                return "vnd.android.cursor.dir/station";
            case STATION:
                return "vnd.android.cursor.item/station";
            case ROUTES:
            case ORIGINROUTES:
                return "vnd.android.cursor.dir/route";
            case ROUTE:
                return "vnd.android.cursor.item/route";
            case LINES:
            case LINE:
                return "vnd.android.cursor.dir/lines";
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    /**
     * This method is called when a client calls
     * {@link android.content.ContentResolver#query(Uri, String[], String, String[], String)}.
     * Queries the database and returns a cursor containing the results.
     *
     * @return A cursor containing the results of the query. The cursor exists but is empty if
     * the query returns no results or an exception occurs.
     * @throws IllegalArgumentException if the incoming URI pattern is invalid.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        // Opens the database object in "read" mode, since no writes need to be done.
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (matcher.match(uri)) {
            case STATION:
                if (selection == null && selectionArgs == null) {
                    selection = "slug=?";
                    selectionArgs = new String[] {uri.getLastPathSegment()};
                }
            case STATIONS:
                qb.setTables("stations");
                break;
            case ROUTE:
                //selection = "d_slug=? AND ";
                //selectionArgs = uri.getPathSegments().toArray()
            case ORIGINROUTES:
                if (selection == null && selectionArgs == null) {
                    selection = "o_slug=?";
                    selectionArgs = new String[] {uri.getLastPathSegment()};
                }
            case ROUTES:
                qb.setTables("routes_names");
                break;
            case LINE:
                if (selection == null && selectionArgs == null) {
                    selection = "slug=?";
                    selectionArgs = new String[] {uri.getLastPathSegment()};
                }
            case LINES:
                qb.setTables("slug_lines");
                break;
        }

        Cursor c = null;
        if (db != null) {
            c = qb.query(db, projection, selection, selectionArgs, null, null,
                    sortOrder);
        }

        // Tells the Cursor what URI to watch, so it knows when its source data changes
        if (c != null) {
            c.moveToFirst();
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        throw new IllegalArgumentException("This content provider doesn't support insertion.");
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        throw new IllegalArgumentException("This content provider doesn't support deletion.");
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        throw new IllegalArgumentException("This content provider doesn't support updates.");
    }
}
