package com.shout.android;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class TheTeamActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_team);


        ImageButton back = findViewById(R.id.teamBack);
        back.setOnClickListener((view) -> NavUtils.navigateUpFromSameTask(this));
    }
}