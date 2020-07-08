package org.zzy.redline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "redline";

    //检测常量名不是大写的情况
    public static final int testConstant = 5;

    private TextView mTvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_main);
        mTvName = findViewById(R.id.tv_name);
        testChineseString();
        testLog();
        testThread();
        testForDepth();
        testMessage();
        testPrintStackTrace();
        testParse();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        registerReceiver(systemReceiver,filter);
    }

    private BroadcastReceiver systemReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

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
        Person person = new Person("zzy",30);
        Person personOne = new Person();
    }


    private void testPrintStackTrace(){
        try {
            exceptionMethod();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void exceptionMethod() throws Throwable{
        
    }
    
    private void testTodo(){
        // TODO: 2020/6/29 测试todo
        int i = 1;
    }

    private void testParse(){
        ImageView ivColor = findViewById(R.id.color);
        ivColor.setBackgroundColor(Color.parseColor("0x112323"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(systemReceiver);
    }

    public enum ColorEnum{
        RED("红色",1),GREEN("绿色",2),BLANK("白色",3);

        private String name;
        private int index;

        ColorEnum(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
