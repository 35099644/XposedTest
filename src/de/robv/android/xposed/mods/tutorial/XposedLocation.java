package de.robv.android.xposed.mods.tutorial;

import android.location.Location;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class XposedLocation implements IXposedHookZygoteInit{

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		//经度
		XposedHelpers.findAndHookMethod(Location.class, "getLatitude",
				new XC_MethodHook() {
			
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->beforeHookedMethod:Location.getLatitude");
						param.setResult((double)22.659543);
					}
					
					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->afterHookedMethod:Location.getLatitude");
					}
		});
		
		//纬度
		XposedHelpers.findAndHookMethod(Location.class, "getLongitude",
				new XC_MethodHook() {
			
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->beforeHookedMethod:Location.getLongitude");
						param.setResult((double)114.054784);
					}
					
					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						XposedBridge.log("Enter->afterHookedMethod:Location.getLongitude");
					}
		});
		
	}

}
