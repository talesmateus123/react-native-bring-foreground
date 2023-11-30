
package com.reactlibrary;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.view.WindowManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import static android.content.Context.POWER_SERVICE;

public class LaunchApplicationModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public LaunchApplicationModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "LaunchApplication";
  }

  @ReactMethod
  public void open(String PackageName) {
    PowerManager.WakeLock screenLock = ((PowerManager) getReactApplicationContext().getSystemService(POWER_SERVICE)).newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
    screenLock.acquire();

    screenLock.release();
    KeyguardManager km = (KeyguardManager) getReactApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
    final KeyguardManager.KeyguardLock kl = km.newKeyguardLock("MyKeyguardLock");
    kl.disableKeyguard();

    Intent dialogIntent = getReactApplicationContext().getPackageManager().getLaunchIntentForPackage(PackageName);

    dialogIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP );
    dialogIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
    dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    getReactApplicationContext().startActivity(dialogIntent);
  }
}