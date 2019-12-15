package com.sung.testdemo.main;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sung.testdemo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main1Activity extends AppCompatActivity {
    private static final String TAG = Main1Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText text = findViewById(R.id.text);
        final Button go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
    }

    private void go(){
        Intent goTo = new Intent();
        goTo.setClass(Main1Activity.this,Main2Activity.class);
        startActivity(goTo);
    }

    private void test(EditText text){
        String trim = text.getText().toString().trim();
        int value = Integer.parseInt(trim);
        String binaryString = Integer.toBinaryString(value);
        appearNumber(binaryString,"1");
    }

    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        Log.e(Main1Activity.class.getSimpleName(), "appearNumber: "+srcText );
        Log.e(Main1Activity.class.getSimpleName(), "appearNumber: count =="+count );
        return count;
    }













    //overwrite


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.e(TAG, "onCreate" );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume" );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy" );
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent" );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState" );
    }

}
