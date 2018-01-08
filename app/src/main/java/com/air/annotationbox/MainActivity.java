package com.air.annotationbox;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.air.converter.R;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        SimpleDateFormat sdf = new SimpleDateFormat("dd天HH时mm分", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        tv.setText(sdf.format(60 * 1000L));
        Log.d(TAG, "onCreate: " + Build.SERIAL);
    }
}
