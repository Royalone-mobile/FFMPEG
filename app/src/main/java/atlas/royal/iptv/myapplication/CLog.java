package atlas.royal.iptv.myapplication;

import android.os.Environment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CLog
{
    private	static	final	String strLogFileName	= Environment.getExternalStorageDirectory().getPath() + "/ffmpeg.txt";

    private	static File logFile = null;
    private	static DataOutputStream logOutputStream = null;

    public static void init()
    {
        try
        {
            logFile = new File(strLogFileName);
            Logger.error(strLogFileName);
            if (!logFile.exists())
                logFile.createNewFile();

            OutputStream in = new FileOutputStream(logFile);
            logOutputStream = new DataOutputStream(in);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void release()
    {
        try
        {
            logOutputStream.close();
            logOutputStream = null;
            logFile.exists();
            logFile = null;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void log(String strLog)
    {
        if (logOutputStream == null)
            return;

        try
        {
            logOutputStream.writeChars(strLog+"\n");
            ///logOutputStream.writeUTF(strLog + "\n");
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
