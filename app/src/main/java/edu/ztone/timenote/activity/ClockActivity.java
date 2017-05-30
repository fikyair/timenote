package edu.ztone.timenote.activity;


import com.example.timenote.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class ClockActivity extends Activity {
	private MediaPlayer mediaPlayer;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clock_layout);
        mediaPlayer = MediaPlayer.create(this, R.raw.naozhong);
        //mediaPlayer.setLooping(true);
        mediaPlayer.start();
        
        Log.i("text", "5");
        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        new AlertDialog.Builder(ClockActivity.this).setTitle("闹钟").setMessage("日记时间到！！")
        .setPositiveButton("关闭闹铃", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	mediaPlayer.stop();
                ClockActivity.this.finish();
            }
        }).show();
        Log.i("text", "6");
    }
}
