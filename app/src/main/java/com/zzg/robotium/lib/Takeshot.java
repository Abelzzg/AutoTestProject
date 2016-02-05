package com.zzg.robotium.lib;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Takeshot {

    private static final String TAG ="ROBOT" ;

    // 获取指定Activity的截屏，保存到png文件
    private static Bitmap takeScreenShot(Activity activity) {
    	 // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("TAG", "" + statusBarHeight);

     // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        
		int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
		// 去掉状态栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    // 保存到目录
    private static boolean savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
            	if(strFileName.endsWith(".png")){
            		b.compress(Bitmap.CompressFormat.PNG, 90, fos);
            	}
            	else{
            		b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            	}
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 程序入口
    public static boolean shoot(Activity a, String absolutepath) {
        Log.e(TAG,"截图"+a.getLocalClassName());
    	return Takeshot.savePic(Takeshot.takeScreenShot(a), absolutepath);
    }
}

