package org.zzy.redline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "redline";

    private TextView mTvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvName = findViewById(R.id.tv_name);
        testChineseString();
        testLog();
        testThread();
    }

    private void testChineseString(){
        mTvName.setText("你好");
    }

    private void testLog(){
        Log.d(TAG,"test log");
    }

    private void testThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"new thread");
            }
        });
    }
}
