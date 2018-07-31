package com.shout.android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shout.android.core.BluetoothClient;
import com.shout.android.core.ConnectionListener;

public class LoginActivity extends AppCompatActivity implements ConnectionListener{
    public static final String USERNAME = "com.shout.android.USERNAME";

    TextView numPeopleShouting;
    BluetoothClient bluetoothClient = BluetoothClient.getINSTANCE();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_login);
        bluetoothClient.registerConnectionListener(this);
        bluetoothClient.initialize(this,LoginActivity.this);
        // Set up the login form.
        EditText editText = findViewById(R.id.usernameInput);
        editText.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                joinChat();
                return true;
            }
            return false;
        });
        numPeopleShouting = findViewById(R.id.numPeopleShouting);
        Button mEmailSignInButton = findViewById(R.id.join_button);
        mEmailSignInButton.setOnClickListener(view -> joinChat());

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start Bridgefy
            bluetoothClient.startScanning(LoginActivity.this);

        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Location permissions needed to start peers discovery.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void createNotificationChannel() {
        String channel_name = getString(R.string.channel_name);
        String channel_description = getString(R.string.channel_description);
        String CHANNEL_ID = getString(R.string.CHANNEL_ID);
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channel_name;
            String description = channel_description;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static final int MY_REQUEST_CODE = 0xe110; // Or whatever number you want
// ensure it's unique compared to other activity request codes you use

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE)
            BluetoothClient.getINSTANCE().disconnect();
    }

    private void joinChat() {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = findViewById(R.id.usernameInput);
        String username = editText.getText().toString();
        if (username.length() > 0) {
            intent.putExtra(USERNAME, username);
            startActivityForResult(intent, MY_REQUEST_CODE);
        }

    }


    @Override
    public void deviceConnected(String username, long timestamp, String userID) {

    }

    @Override
    public void deviceLost(String username, long timestamp, String userID) {

    }

    @Override
    public void connectedDeviceCountChanged(int count) {
        numPeopleShouting.setText(""+count);
    }
}

