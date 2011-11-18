package com.sqisland.android.protected_provider.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ProviderListActivity extends ListActivity {
  private static final String PERMISSION
      = "com.sqisland.android.protected_provider.ACCESS_DATA";
  private static final String FIELD_LABEL = "label";
  private static final String FIELD_AUTHORITY = "authority";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // Find all the content providers installed on the device, filtered the
    // ones we can read by checking the permission string.
    final List<Map<String, String>> groupData
        = new ArrayList<Map<String, String>>();
    PackageManager packageManager = getPackageManager();
    List<PackageInfo> packs = packageManager.getInstalledPackages(
        PackageManager.GET_PROVIDERS);
    for (PackageInfo pack : packs) {
      ProviderInfo[] providers = pack.providers;
      if (providers != null) {
        for (ProviderInfo provider : providers) {
          String permission = provider.readPermission;
          if (permission != null && permission.equals(PERMISSION)) {
            String label = provider.loadLabel(packageManager).toString();
            Map<String, String> group = new HashMap<String, String>();
            group.put(FIELD_LABEL, label);
            group.put(FIELD_AUTHORITY,  provider.authority);
            groupData.add(group);
          }
        }
      }
    }

    // Show the providers on a list
    SimpleAdapter adapter = new SimpleAdapter(
        this,
        groupData,
        android.R.layout.simple_list_item_2,
        new String[] { FIELD_LABEL, FIELD_AUTHORITY },
        new int[]{ android.R.id.text1, android.R.id.text2 });
    setListAdapter(adapter);

    // When clicking on a provider, show all its items
    getListView().setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(
          AdapterView<?> a, View view, int position, long id) {
        if (position < 0 || position >= groupData.size()) {
          return;
        }
        Map<String, String> group = groupData.get(position);
        String authority = group.get(FIELD_AUTHORITY);
        Intent intent = new Intent();
        intent.setClass(ProviderListActivity.this, ProviderActivity.class);
        intent.setData(Uri.parse("content://" + authority + "/items"));
        startActivity(intent);
      }
    });
  }
}