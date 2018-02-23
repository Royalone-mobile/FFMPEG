package atlas.royal.iptv.myapplication;

/**
 * Created by royal on 8/16/2017.
 */

import android.util.Log;

public class Logger {

    private static final String TAG = "FFMpeg";

    public static void i(String format, Object ... args)
    {
        String msg = String.format(format, args);
        Log.i(TAG, msg);
    }

    public static void d(String format, Object ... args)
    {
        String msg = String.format(format, args);
        Log.d(TAG, msg);
    }

    public static void w(String format, Object ... args)
    {
        String msg = String.format(format, args);
        Log.w(TAG, msg);
    }

    public static void e(String format, Object ... args)
    {
        String msg = String.format(format, args);
        Log.e(TAG, msg);
    }

    public static void error(String string){
//        if(string!=null)
//            Log.println(Log.ASSERT,TAG,string);
//        else
//            Log.println(Log.ASSERT,TAG,"null");
    }

    public static void print(String string){
        if(string!=null)
            Log.println(Log.ASSERT,TAG,string);
        else
            Log.println(Log.ASSERT,TAG,"null");
    }

}

