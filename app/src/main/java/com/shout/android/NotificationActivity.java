package com.shout.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE);
        Log.e("testing", "Created view");
        Switch showNotifications = findViewById(R.id.showNotificationsSwitch);
        showNotifications.setChecked(prefs.getBoolean(getString(R.string.pref_file_key_show_notifications), true));
        showNotifications.setOnCheckedChangeListener((view, isChecked) -> {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE).edit();
            editor.putBoolean(getString(R.string.pref_file_key_show_notifications), isChecked);
            editor.commit();
        });

        Switch messageSound = findViewById(R.id.messageSoundSwitch);
        messageSound.setChecked(prefs.getBoolean(getString(R.string.pref_file_key_message_sound), true));
        messageSound.setOnCheckedChangeListener((view, isChecked) -> {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE).edit();
            editor.putBoolean(getString(R.string.pref_file_key_message_sound), isChecked);
            editor.commit();
        });

        Switch vibrate = findViewById(R.id.vibrateSwitch);
        vibrate.setChecked(prefs.getBoolean(getString(R.string.pref_file_vibrate), true));
        vibrate.setOnCheckedChangeListener((view, isChecked) -> {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE).edit();
            editor.putBoolean(getString(R.string.pref_file_vibrate), isChecked);
            editor.commit();
        });

        ImageButton back = findViewById(R.id.notificationBackBtn);
        back.setOnClickListener((view) -> NavUtils.navigateUpFromSameTask(this));

        TextView reset = findViewById(R.id.resetNotifications);
        reset.setOnClickListener((view) -> {
            showNotifications.setChecked(true);
            messageSound.setChecked(true);
            vibrate.setChecked(true);
        });
        prefs.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> Log.e("testing", key));
    }
}