package com.sqisland.android.protected_provider.main;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class ProviderActivity extends ListActivity {
  private static final String FIELD_ID = "_id";
  private static final String FIELD_TITLE = "title";
  private static final String FIELD_DESCRIPTION = "description";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    String[] projection = new String[] {
        FIELD_ID,
        FIELD_TITLE,
        FIELD_DESCRIPTION };
    Cursor cursor = managedQuery(
        getIntent().getData(),
        projection,
        null,
        null,
        null);

    String[] columns = new String[] { FIELD_TITLE, FIELD_DESCRIPTION };
    int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
    SimpleCursorAdapter adapter = new SimpleCursorAdapter(
        this, android.R.layout.simple_list_item_2, cursor, columns, to);
    setListAdapter(adapter);
  }
}