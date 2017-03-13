package com.androidprojects.androidcheatsheet.Samples;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidprojects.androidcheatsheet.R;
import com.androidprojects.androidcheatsheet.SettingsActivity;

import java.util.concurrent.TimeUnit;

public class IntentActivity extends AppCompatActivity implements LocationListener {

    Button btnUri, btnShare, btnMap, btnAlarm, btnPref;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        //memory leaks sample
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                TimeUnit.MINUTES.toMillis(5), 100, this);

        btnUri = (Button) findViewById(R.id.btn_Uri);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnShare = (Button) findViewById(R.id.btn_share);
        btnAlarm = (Button) findViewById(R.id.btn_alarm);

        //alarm sample
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlert();
            }
        });

        btnUri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlAsString = "http://www.udacity.com";
                openWebPage(urlAsString);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textThatYouWantToShare =
                        "Sharing the coolest thing I've learned so far";
                shareText(textThatYouWantToShare);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressString = "1600 Amphitheatre Parkway, CA";

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("geo")
                        .path("0,0")
                        .query(addressString);
                Uri addressUri = builder.build();

                showMap(addressUri);

            }
        });


    }

    @Override
    protected void onDestroy() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
        super.onDestroy();
    }

    private void startAlert() {

        EditText etTime=(EditText)findViewById(R.id.et_time);
        int time = Integer.parseInt(etTime.getText().toString());
        Intent i=new Intent(this,MyBroadcast.class);
        PendingIntent intent = PendingIntent.getBroadcast(this.getApplicationContext(),7868790,i,0);
        AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(time*1000),intent);
    }

    private void openWebPage(String urlAsString) {

        Uri webpage = Uri.parse(urlAsString);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showMap(Uri geoLocation) {

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void shareText(String textToShare) {

        String mimeType = "text/plain";
        String title = "Learning How to Share";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id == R.id.settings){
            Intent intent=new Intent(IntentActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}