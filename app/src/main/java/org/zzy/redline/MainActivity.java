package org.zzy.redline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "redline";

    public static final int testConstant = 1;

    private TextView mTvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvName = findViewById(R.id.tv_name);
        testChineseString();
        testLog();
        testThread();
        testForDepth();
        testMessage();
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

    private void testForDepth(){
        for(int i=0;i<3;i++){
            for(int j=0;j<2;j++){
                for(int z=0;z<3;z++){
                    for(int k=0 ;k <4;k++){

                    }
                }
            }
        }
    }

    private void testForEachDepth(){
        int [] arr = new int[]{1,2,3,4,5};
        for (int a :arr) {
            for (int b :arr) {
                for (int c :arr) {
                    for (int d :arr) {

                    }
                }
            }
        }
    }

    private void testForOrEachDepth(){
        int [] arr = new int[]{1,2,3,4,5};
        for (int a :arr) {
            for (int b=1;b<4;b++) {
                for (int c :arr) {
                    for (int d=0;d<5;d++) {

                    }
                }
            }
        }
    }

    private void testMessage(){
        Message  message = new Message();
    }
}
