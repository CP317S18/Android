package com.shout.android;

import android.app.AlertDialog;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shout.android.core.BluetoothClient;
import com.shout.android.core.ConnectionListener;

import java.util.Date;
// This is my change
public class MainActivity extends AppCompatActivity implements ConnectionListener {

    private final BluetoothClient bluetoothClient =
            BluetoothClient.getINSTANCE();
    private TextView numPeopleShouting;
    private DrawerLayout mDrawerLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(BluetoothClient.getINSTANCE().getForegroundBackgroundListener());
        BluetoothClient.getINSTANCE().registerConnectionListener(this);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.img_shape);

        myToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.setUsername) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Set Username");

                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
                builder.setView(input);

                builder.setPositiveButton("OK", (dialog, which) -> {
                    BluetoothClient.getINSTANCE().setUsername(input.getText().toString());
                    setUserInNavigation(input.getText().toString());
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
                return true;
            }
            return false;
        });
        //Listener for navigation events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem->  {
            // set item as selected to persist highlight
            menuItem.setChecked(true);
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers();
            switch (menuItem.getItemId()){
                case R.id.nav_username:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Set Username");

                    final EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
                    builder.setView(input);

                    builder.setPositiveButton("OK", (dialog, which) -> {
                        BluetoothClient.getINSTANCE().setUsername(input.getText().toString());
                        setUserInNavigation(input.getText().toString());
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                    builder.show();
                    break;
                case R.id.nav_notification:
                    break;
                case R.id.nav_bugs:
                    break;
                case R.id.nav_about:
                    break;
                default:
                    return false;
            }
            actionbar.invalidateOptionsMenu();
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            return true;
        });


        bluetoothClient.initialize(this,MainActivity.this);
        EditText editText = findViewById(R.id.editText);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                ChatMessage cm = new ChatMessage(v.getText().toString(), BluetoothClient.getINSTANCE().getUsername(), new Date().getTime(), BluetoothClient.getINSTANCE().getUserID());
                bluetoothClient.sendMessage(cm);
                v.setText("");
                return true;
            }
            return false;
        });
        numPeopleShouting = findViewById(R.id.numPeopleShouting);
        initUsername();
    }

    private void initUsername() {

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.USERNAME);
        if (message != null) {
            BluetoothClient.getINSTANCE().setUsername(message);
            BluetoothClient.getINSTANCE().connect();
            setUserInNavigation(message);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Username");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            BluetoothClient.getINSTANCE().setUsername(input.getText().toString());
            BluetoothClient.getINSTANCE().connect();
            setUserInNavigation(input.getText().toString());
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            initUsername();
        });

        builder.show();
    }

    private void setUserInNavigation(String Name){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textUserName);
        navUsername.setText(Name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
    public void deviceConnected(String username, long timestamp, String userID) {

    }

    @Override
    public void deviceLost(String username, long timestamp, String userID) {

    }

    @Override
    public void connectedDeviceCountChanged(int count) {
        if (count == 1) {
            numPeopleShouting.setText(getString(R.string.person_shouting_template));
        } else {
            numPeopleShouting.setText(getString(R.string.people_shouting_template, count));
        }
    }
}
