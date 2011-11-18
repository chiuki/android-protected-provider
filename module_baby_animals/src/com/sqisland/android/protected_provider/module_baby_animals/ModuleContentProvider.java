package com.sqisland.android.protected_provider.module_baby_animals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ModuleContentProvider extends ContentProvider {
  private static final String TAG = "sqisland";

  private static final String AUTHORITY = "com.sqisland.android.protected_provider.module_baby_animals.ModuleContentProvider";
  private static final String TABLE_NAME = "items";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
      + "/items");
  private static final String DB_FILENAME = "database.db";

  private static final int ITEMS = 1;
  private static final int ITEM_ID = 2;
  private static final UriMatcher sUriMatcher;
  static {
    sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    sUriMatcher.addURI(AUTHORITY, "items", ITEMS);
    sUriMatcher.addURI(AUTHORITY, "items/#", ITEM_ID);
  }

  private SQLiteDatabase mDb = null;

  @Override
  public boolean onCreate() {
    File databaseFile = new File(getContext().getFilesDir(), DB_FILENAME);
    if (!databaseFile.isFile()) {
      if (!copyDatabaseFile(databaseFile)) {
        return false;
      }
    }
    mDb = SQLiteDatabase.openDatabase(databaseFile.getAbsolutePath(), null,
        SQLiteDatabase.OPEN_READONLY);
    return true;
  }

  private boolean copyDatabaseFile(File databaseFile) {
    try {
      Resources resources = getContext().getResources();
      InputStream input = resources.openRawResource(R.raw.database);
      OutputStream output = new FileOutputStream(databaseFile);
      byte[] buffer = new byte[1024];
      int length;
      while ((length = input.read(buffer)) > 0) {
        output.write(buffer, 0, length);
      }
      output.flush();
      output.close();
      input.close();
    } catch (IOException e) {
      Log.e(TAG, "copyDatabaseFile: " + e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String orderBy) {
    return mDb.query(TABLE_NAME, projection, selection, selectionArgs, null,
        null, orderBy);
  }

  @Override
  public String getType(Uri uri) {
    switch (sUriMatcher.match(uri)) {
    case ITEMS:
      return "vnd.android.cursor.dir";
    case ITEM_ID:
      return "vnd.android.cursor.item";
    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int delete(Uri url, String where, String[] selectionArgs) {
    throw new UnsupportedOperationException();
  }
}
