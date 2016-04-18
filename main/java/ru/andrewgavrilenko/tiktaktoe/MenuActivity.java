package ru.andrewgavrilenko.tiktaktoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Андрей on 13.04.2016.
 */
public class MenuActivity extends Activity {
    Intent intentMenu = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);


    }

    public void onClick1(View view) {
        intentMenu.putExtra("bot", 1);
        setResult(RESULT_OK, intentMenu);
        finish();
    }

    public void onClick2(View view) {
        intentMenu.putExtra("bot", 2);
        setResult(RESULT_OK, intentMenu);
        finish();
    }
}
