package com.zzg.autotestexample.test;

import com.zzg.robotium.lib.TestCase;
import com.zzg.robotium.lib.NewSolo;


@SuppressWarnings("rawtypes")
public class AutoTestCase3 extends TestCase {
    private NewSolo solo;

    public AutoTestCase3() throws ClassNotFoundException {
        super();
    }

    @Override
    protected void setUp() throws Exception {
		solo = new NewSolo(getInstrumentation(),getActivity());
		solo.setup("AutoTestCase3");
    }

    @Override
    protected void tearDown() throws Exception {
        solo.newTeardown();
    }

    public void testRun() {
        //Wait for activity: 'com.yeepay.materialdesign.MainActivity'
        solo.waitForActivity("MainActivity", 2000);
        //上滑
        solo.pullDown();
        //点击侧滑按钮
        solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //判断是否出现侧滑栏
        solo.verifyEquals("侧滑栏未出现", true, solo.waitForView("nav_view"));
        //点击gallery按钮，让程序崩溃
        solo.clickOnView(solo.getText("Gallery"));
    }
}
