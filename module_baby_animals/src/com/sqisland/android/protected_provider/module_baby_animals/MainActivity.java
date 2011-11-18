package com.sqisland.android.protected_provider.module_baby_animals;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
  private static final String MAIN_PACKAGE_NAME
      = "com.sqisland.android.protected_provider.main";
  ComponentName mMainAppComponentName = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    PackageManager pm = getPackageManager();
    mMainAppComponentName = getMainPackageComponentName(pm);

    if (mMainAppComponentName != null) {
      View button = findViewById(R.id.launch_main_button);
      button.setVisibility(View.VISIBLE);
      button.setOnClickListener(this);
    }
  }

  private ComponentName getMainPackageComponentName(PackageManager pm) {
    try {
      pm.getApplicationInfo(MAIN_PACKAGE_NAME, 0);
      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_LAUNCHER);
      List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);
      for (ResolveInfo info : apps) {
        if (info.activityInfo.packageName.equals(MAIN_PACKAGE_NAME)) {
          return new ComponentName(MAIN_PACKAGE_NAME, info.activityInfo.name);
        }
      }
    } catch (NameNotFoundException e) {
    }
  return null;
  }

  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.launch_main_button:
      if (mMainAppComponentName == null) {
        return;
      }
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setComponent(mMainAppComponentName);
      startActivity(intent);
      break;
    }
  }
}