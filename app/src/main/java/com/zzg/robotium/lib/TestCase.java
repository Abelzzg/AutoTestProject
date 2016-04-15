package com.zzg.robotium.lib;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;


@SuppressWarnings({"rawtypes", "deprecation", "unchecked"})
public class TestCase extends ActivityInstrumentationTestCase2 {

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.zzg.materialdesign.MainActivity";
    private static final String TAG = "ROBOT";
    public static NewSolo solo;

    private static Class<?> launcherActivityClass;

    //static {
    //    try {
    //        launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
    //    } catch (ClassNotFoundException e) {
   //         throw new RuntimeException(e);
   //     }
   // }

    public TestCase() throws ClassNotFoundException {
        super(Class.forName(InputDataStore.getInstance().getInput_TargetActivty()));
    }

//    @Override
//    protected void setUp() throws Exception {
//        solo = new NewSolo(getInstrumentation(), getActivity());
//        solo.setup(getClassName());
//    }
//
//    /**
//     * 获取子类的类名
//     */
//    public String getClassName() {
//        Log.e(TAG, "子类名称" + this.getClass().getSimpleName());
//        return this.getClass().getSimpleName();
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        solo.newTeardown();
//    }

    @Override
    public void runTest() throws Throwable {
        int retryTimes = InputDataStore.getInstance().getInput_Retrytime();
        while (retryTimes > 0) {
            Log.e("ROBOT", "运行第" + retryTimes + "次");
            try {
                super.runTest();
                break;
            } catch (Throwable e) {
                if (retryTimes > 1) {
                    retryTimes--;
                    continue;
                }
                throw e;
            }
        }
    }
}
