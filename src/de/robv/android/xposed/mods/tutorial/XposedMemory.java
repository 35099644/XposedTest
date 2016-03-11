package de.robv.android.xposed.mods.tutorial;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedMemory implements IXposedHookLoadPackage{

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		XposedHelpers.findAndHookMethod(
				"com.echo.monkeytest.MainActivity",
				lpparam.classLoader, "onCreate", Bundle.class,
				new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						// this will be called before the activity was created by the original method
						XposedBridge.log("Enter->beforeHookedMethod:onCreate");
						Activity app = (Activity) param.thisObject;
						long availMem = getAvailMemory(app);
						XposedBridge.log("availMem before onCreate:" + availMem + "KB");
						Toast.makeText(app, "availMem before onCreate:" + availMem + "KB", Toast.LENGTH_SHORT).show();
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						// this will be called after the activity was created by the original method
						XposedBridge.log("Enter->afterHookedMethod:onCreate");
						Activity app = (Activity) param.thisObject;
						long availMem = getAvailMemory(app);
						XposedBridge.log("availMem after onCreate:" + availMem + "KB");
						Toast.makeText(app, "availMem after onCreate:" + availMem + "KB", Toast.LENGTH_SHORT).show();
					}
				});
	}
	
	public long getAvailMemory(Activity app) {
        ActivityManager am = (ActivityManager)app.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem >> 10;
    }

}
