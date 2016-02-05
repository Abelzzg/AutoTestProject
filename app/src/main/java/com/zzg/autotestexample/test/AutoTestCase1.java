package com.zzg.autotestexample.test;

import com.zzg.robotium.lib.TestCase;
import com.zzg.robotium.lib.NewSolo;


@SuppressWarnings("rawtypes")
public class AutoTestCase1 extends TestCase {
//  	private NewSolo solo;

	public AutoTestCase1() throws ClassNotFoundException {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		solo = new NewSolo(getInstrumentation(),getActivity());
		solo.setup("AutoTestCase1");
  	}
  
   	@Override
   	protected void tearDown() throws Exception {
        solo.newTeardown();
	}
  
	public void testRun() {
        //Wait for activity: 'com.yeepay.materialdesign.MainActivity'
		solo.waitForActivity("MainActivity", 2000);
		//下滑
		solo.pullDown();
		//点击侧滑按钮
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
		//判断是否出现侧滑栏
		solo.verifyEquals("侧滑栏未出现", true, solo.waitForView("nav_view"));
		//验证用户名是否正确
		solo.verifyEquals("用户名","android.studio@android.com",solo.getViewText(solo.getView("com.yeepay.materialdesign:id/textView")));
		//点击头像avators
		solo.clickOnView("imageView");
		//验证是否跳到PrivateInfoActivity
		solo.waitForActivity("PrivateInfoActivity", 2000);
		//如果无法获得activity名称，则选用标志性组件验证
		 solo.verifyTextAppear("PrivateInfoActivity");
		//验证snackbar功能
		solo.clickOnView("fab");
		//点击删除按钮
		solo.clickOnButton("DELETE");
		solo.sleep();
		solo.verifyEquals("snackbar消失",false,solo.searchText("snackbar_text"));
	}
}
