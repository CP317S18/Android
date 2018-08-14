package com.shout.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class SettingsAboutActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.settings_about);

        Button terms = findViewById(R.id.termsofservice);
        Button sources = findViewById(R.id.sources);
        Button team = findViewById(R.id.theteam);

        terms.setOnClickListener(view -> goToTerms());
        sources.setOnClickListener(view -> goToSources());
        team.setOnClickListener(view -> goToTeam());
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
