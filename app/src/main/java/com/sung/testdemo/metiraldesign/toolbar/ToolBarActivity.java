package com.sung.testdemo.metiraldesign.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sung.testdemo.R;

public class ToolBarActivity extends AppCompatActivity implements CheckBox.OnCheckedChangeListener{
    private Toolbar toolbar;
    private CheckBox tittle,subtittle,centertittle,back,logo;
    private CheckBox red,black;
    private CheckBox blue,black2;
    private TextView center_tittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);

        findview();
        setSupportActionBar(toolbar);
        // 返回按键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void findview(){
        toolbar = findViewById(R.id.toolbar);
        center_tittle = findViewById(R.id.tittle);
        tittle = findViewById(R.id.cb_tittle);
        subtittle = findViewById(R.id.cb_subtittle);
        centertittle = findViewById(R.id.cb_centertittle);
        back = findViewById(R.id.cb_back);
        logo = findViewById(R.id.cb_logo);

        red = findViewById(R.id.cb_red);
        black = findViewById(R.id.cb_black);

        blue = findViewById(R.id.cb_blue);
        black2 = findViewById(R.id.cb_black2);


        tittle.setOnCheckedChangeListener(this);
        subtittle.setOnCheckedChangeListener(this);
        centertittle.setOnCheckedChangeListener(this);
        back.setOnCheckedChangeListener(this);
        logo.setOnCheckedChangeListener(this);

        red.setOnCheckedChangeListener(this);
        black.setOnCheckedChangeListener(this);

        blue.setOnCheckedChangeListener(this);
        black2.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 菜单
        getMenuInflater().inflate(R.menu.tool_bar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == tittle){
            getSupportActionBar().setDisplayShowTitleEnabled(isChecked);
        }
        if (buttonView == subtittle){
            getSupportActionBar().setSubtitle(isChecked ? "sub tittle" : "");
        }
        if (buttonView == centertittle){
            center_tittle.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(!isChecked);
        }
        if (buttonView == back){
            getSupportActionBar().setDisplayHomeAsUpEnabled(isChecked);
        }
        if (buttonView == logo){
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(isChecked);
        }
        if (buttonView == red){
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(isChecked ? android.R.color.holo_red_dark : R.color.colorPrimary));
        }
        if (buttonView == black){
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(isChecked ? android.R.color.black : R.color.colorPrimary));
        }
        if (buttonView == blue){
            toolbar.setTitleTextColor(getResources().getColor(isChecked ? android.R.color.holo_blue_light : android.R.color.white));
            toolbar.setSubtitleTextColor(getResources().getColor(isChecked ? android.R.color.holo_blue_light : android.R.color.white));
        }
        if (buttonView == black2){
            toolbar.setTitleTextColor(getResources().getColor(isChecked ? android.R.color.black : android.R.color.white));
            toolbar.setSubtitleTextColor(getResources().getColor(isChecked ? android.R.color.black : android.R.color.white));
        }
    }
}
