package atlas.royal.iptv.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    FFmpeg ffmpeg;
    @Bind(R.id.output_layer)
    LinearLayout outputLayout;
    @Bind(R.id.btn_start)
    Button btnStart;
    @Bind(R.id.edit_input)
    EditText editInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ffmpeg = FFmpeg.getInstance(getApplicationContext());

        CLog.init();
        loadFFMpegBinary();

        final String videoPath = Environment.getExternalStorageDirectory() + "/Watermark/" + "test.flv";

        final String imgPath  = Environment.getExternalStorageDirectory() + "/Watermark/" + "test.png";

        final String outPutPath  = Environment.getExternalStorageDirectory() + "/Watermark/" + "output.mp4";

//        String mycommand
//                = "ffmpeg -i " +   videoPath
//                + " -i " + imgPath +
//                " -filter_complex 'overlay=10:10' "
//                + outPutPath;

        final String mycommand
                = "ffmpeg -i " +   videoPath
//                + " -i " + imgPath +
                +  " -vcodec mpeg4 -s 320x240 -r 10 -y "
                + outPutPath;

        editInput.setText(mycommand);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd =  editInput.getText().toString();
//                String[] command = cmd.split(" ");
                String[] command = {"-i", videoPath, "-i", imgPath, "-filter_complex", "[1:v]scale=600:400 [ovrl], [0:v][ovrl]overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2"
                        , "-r", "50", "-vcodec", "mpeg4", "-b:v", "450k", "-b:a", "48000", "-ac", "2", "-ar", "22050", outPutPath};
//                String[] command = {"-y", "-i", videoPath, "-s", "1600x1200", "-r", "25", "-vcodec", "mpeg4", "-b:v", "150k", "-b:a", "48000", "-ac", "2", "-ar", "22050", outPutPath};
                if (command.length != 0) {
                    execFFmpegBinary(command);
                } else {
                    Toast.makeText(MainActivity.this,"empty command", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadFFMpegBinary() {
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {

                }
            });
        } catch (FFmpegNotSupportedException e) {
        }
    }

    private void execFFmpegBinary(final String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    addTextViewToLayout("FAILED with output : "+s);


                    Logger.print("FAILED with output : "+s);
                }

                @Override
                public void onSuccess(String s) {
                    addTextViewToLayout("SUCCESS with output : "+s);

                }

                @Override
                public void onProgress(String s) {
                    addTextViewToLayout("Started command : ffmpeg "+command);
                    addTextViewToLayout("progress : "+s);
                    Logger.print("Started command : ffmpeg "+command.toString());
                    Logger.print("progress : "+s);

                    CLog.log("progress : "+s);
                }

                @Override
                public void onStart() {
                    outputLayout.removeAllViews();

                    Logger.print("onStart");
                }

                @Override
                public void onFinish() {
                    Logger.print("onFinish");
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // do nothing for now
        }
    }

    private void addTextViewToLayout(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        outputLayout.addView(textView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CLog.release();
    }
}
