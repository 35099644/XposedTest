package de.robv.android.xposed.mods.tutorial;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class XposedHook implements IXposedHookZygoteInit {

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		// 设定hook目标类和方法
		XposedHelpers.findAndHookMethod(Toast.class, "show",
				new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						// 获取Toast对象
						Toast t = (Toast) param.thisObject;
						try {
							// 获取唯一的TextView，即Toast文本
							View view = t.getView();
							List<TextView> list = new ArrayList<TextView>();
							if (view instanceof TextView) {
								list.add((TextView) view);
							} else if (view instanceof ViewGroup) {
								finaAllTextView(list, (ViewGroup) view);
							}
							if (list.size() != 1) {
								throw new RuntimeException(
										"number of TextViews in toast is not 1");
							}
							TextView text = list.get(0);
							// 获取文本内容
							CharSequence toastMsg = text.getText();
							XposedBridge.log("XposedHookToast:" + toastMsg);
							if (!toastMsg.toString().startsWith("availMem")) {
								t.setText("fengyan caught");
							}

						} catch (RuntimeException e) {
							XposedBridge.log(e);
						}
					}
				});
	}

	// 获取对象中的所有TextView
	private void finaAllTextView(List<TextView> addTo, ViewGroup view) {
		int count = view.getChildCount();
		for (int i = 0; i < count; ++i) {
			View child = view.getChildAt(i);
			if (child instanceof TextView) {
				addTo.add((TextView) child);
			} else if (child instanceof ViewGroup) {
				finaAllTextView(addTo, (ViewGroup) child);
			}
		}
	}

}
