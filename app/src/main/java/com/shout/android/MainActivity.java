package com.shout.android;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;


import com.shout.android.core.BluetoothClient;
import com.shout.android.core.ConnectionListener;

import java.util.Date;
// This is my change
public class MainActivity extends AppCompatActivity implements ConnectionListener {

    private final BluetoothClient bluetoothClient =
            BluetoothClient.getINSTANCE();
    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Create your menu...

        this.menu = menu;
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        bluetoothClient.initialize(this,MainActivity.this);
        EditText editText = findViewById(R.id.editText);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                ChatMessage cm = new ChatMessage(v.getText().toString(), Build.MODEL,new Date().getTime());
                bluetoothClient.sendMessage(cm);
                return true;
            }
            return false;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start Bridgefy
            bluetoothClient.startScanning(MainActivity.this);

        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Location permissions needed to start peers discovery.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void deviceConnected() {

    }

    @Override
    public void deviceLost() {

    }

    @Override
    public void connectedDeviceCountChanged(int count) {

    }
}
