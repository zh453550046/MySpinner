package com.example.zhouwn.myspinner;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;


public class MainActivity extends Activity {

    private MySelectBox selectBox;
    private static final String[] str = {
            "第一项",
            "第二项",
            "第三项",
            "第四项",
            "第五项",
            "第六项",
            "第七项"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectBox = (MySelectBox) findViewById(R.id.select_box);
        selectBox.setResource(Arrays.asList(str), R.layout.pop_content_lv_item);
        selectBox.setTitle("快选", 15);
        selectBox.setOnPopItemClickListener(new MySelectBox.OnPopItemClickListener() {
            @Override
            public void onPopItemClick(int position) {
                Toast.makeText(MainActivity.this, "第" + (position + 1) + "项", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
