package de.robv.android.xposed.mods.tutorial;

import android.view.View;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedHookClick implements IXposedHookLoadPackage{

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.echo.monkeytest"))
            return;
		
		XposedHelpers.findAndHookMethod(
				"android.view.View.OnClickListener",
				lpparam.classLoader, "onClick", View.class,
				new XC_MethodHook() {
					
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->beforeHookedMethod:OnClickListener.onClick");
						View view = (View) param.args[0];
						if (view.getId() == (int) 2131230738) {
							param.setResult(null);
						}
					}
					
					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->afterHookedMethod:OnClickListener.onClick");
					}
				});
	}

}
