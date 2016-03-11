package de.robv.android.xposed.mods.tutorial;

import android.app.Activity;
import android.view.View;
import android.view.accessibility.AccessibilityNodeProvider;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedCalendar implements IXposedHookLoadPackage{

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.echo.monkeytest"))
            return;
		
		XposedHelpers.findAndHookMethod(
				"java.util.Calendar",
				lpparam.classLoader, "get", int.class,
				new XC_MethodHook() {
					
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->beforeHookedMethod:Calendar.get");
						param.setResult((int)6);
					}
					
					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->afterHookedMethod:Calendar.get");
					}
				});
		
		XposedHelpers.findAndHookMethod(
				"com.echo.monkeytest.MainActivity",
				lpparam.classLoader, "showProgress", Activity.class,
				new XC_MethodHook() {
					
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->beforeHookedMethod:MainActivity.showProgress");
						param.setResult(null);
					}
					
					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->afterHookedMethod:MainActivity.showProgress");
					}
				});
		
		XposedHelpers.findAndHookMethod("android.view.View", lpparam.classLoader, "performClick",
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->beforeHookedMethod:View.performClick");
						View node = (View)param.thisObject;
						AccessibilityNodeProvider nodeProvider = node.getAccessibilityNodeProvider();
						if(nodeProvider.findAccessibilityNodeInfosByText("WebViewTest", (int)-1).size()!=0) {
							param.setResult(false);
						}
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->afterHookedMethod:View.performClick");
						View node = (View)param.thisObject;
			            XposedBridge.log("NodeInfo:"+node.toString());
					}
				});
		
	}

}
