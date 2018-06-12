package example.dokemonster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

public class ImageOLED {
    static {
        System.loadLibrary("imageOLED");
    }

    public native boolean open();

    public native void Control(int[] image);

    public native boolean close();

    private Context context;

    private Bitmap bm;

    ImageOLED(Context ctx) {
        context = ctx;
        boolean ret = open();
        if (!ret)
            Log.d("OLED", "Open fail");
    }


    public boolean UpdateValue(int count) {

        java.io.InputStream stage1 = context.getResources().openRawResource(R.raw.stage1_img);
        java.io.InputStream stage2 = context.getResources().openRawResource(R.raw.stage2_img);
        java.io.InputStream stage3 = context.getResources().openRawResource(R.raw.stage3_img);
        java.io.InputStream clear = context.getResources().openRawResource(R.raw.clear_img);
        switch (count) {
            case 1:
                bm = BitmapFactory.decodeStream(stage1);
                break;
//            case 2:
//                close();
//                break;
            case 4:
                bm = BitmapFactory.decodeStream(stage2);
                break;
//            case 5:
//                close();
//                break;
            case 7:
                bm = BitmapFactory.decodeStream(stage3);
                break;
//            case 8:
//                close();
//                break;
            case 9:
                bm = BitmapFactory.decodeStream(clear);
                break;
        }

        if (count == 1 || count == 4 || count == 7) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            final int[] pixels = new int[width * height];
            bm.getPixels(pixels, 0, width, 0, 0, width, height);

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Control(pixels);
                }
            }, 6000);
        //시간 체크해서 고치셈 고치셈

        }
        return true;
    }
}
