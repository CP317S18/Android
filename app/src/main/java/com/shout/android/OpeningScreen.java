package com.shout.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.bridgefy.sdk.client.BridgefyUtils;

public class OpeningScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

        Button getStarted = findViewById(R.id.opening_button);
        getStarted.setOnClickListener(view -> {
            BridgefyUtils.enableBluetooth(this);
            finish();
        });
    }

}
