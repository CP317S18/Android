package com.shout.android;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;

public class SettingsAboutActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_about);
        Button terms = findViewById(R.id.termsofservice);
        Button sources = findViewById(R.id.sources);
        Button team = findViewById(R.id.theteam);
        terms.setOnClickListener(view -> goToTerms());
        sources.setOnClickListener(view -> goToSources());
        team.setOnClickListener(view -> goToTeam());
        ImageButton back = findViewById(R.id.aboutBack);
        back.setOnClickListener((view) -> NavUtils.navigateUpFromSameTask(this));

    }
    private void goToTerms(){
        Intent intent = new Intent(this,TermsOfServicesActivity.class);
        startActivity(intent);
    }
    private void goToSources(){
        Intent intent = new Intent(this,SourcesActivity.class);
        startActivity(intent);
    }
    private void goToTeam(){
        Intent intent = new Intent(this,TheTeamActivity.class);
        startActivity(intent);
    }
}