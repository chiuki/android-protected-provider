Sample `ContentProvider` with `android:protectionLevel="signature"` and code to read from it.

## module

`module_baby_animals` implements a `ContentProvider` protected by a permission with `protectedLevel="signature"`. Here is the relevant part from its [AndroidManifest.xml](https://github.com/chiuki/android-protected-provider/blob/master/module_baby_animals/AndroidManifest.xml):

    <permission android:name="com.sqisland.android.protected_provider.ACCESS_DATA"
      android:protectionLevel="signature" />

    <provider
      android:authorities="com.sqisland.android.protected_provider.module_baby_animals.ModuleContentProvider"
      android:name=".ModuleContentProvider"
      android:label="@string/app_label"
      android:exported="true"
      android:permission="com.sqisland.android.protected_provider.ACCESS_DATA" />

Points of note:

1. The `<provider>` needs to set `android:exported="true"` so it is visible to other apps.
1. The `<provider>` goes inside the `<application>` block but the `<permission>` goes outside.

## main 

The `main` app goes through all the packages installed on the device and displays the ones matching the specified permissions string. In its [AndroidManifest.xml](https://github.com/chiuki/android-protected-provider/blob/master/main/AndroidManifest.xml), it uses the permission declared in `module_baby_animals`:

    <uses-permission android:name="com.sqisland.android.protected_provider.ACCESS_DATA" />

To test the signature protection, compile the `main` app with matching and non-matching signature. When the signature does not match, you will get a `java.lang.SecurityException`.
