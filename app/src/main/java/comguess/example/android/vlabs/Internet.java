package comguess.example.android.vlabs;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by kishore on 9/2/17.
 */

public class Internet extends AppCompatActivity {
    ConnectivityManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login_page);
        manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkConnection();

    }
    public void checkConnection(){
        //For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();


       if(is3g)
        {
            Toast.makeText(Internet.this,"3G net available",Toast.LENGTH_LONG).show();
        }
        else if(isWifi)
        {
            Toast.makeText(Internet.this,"WIFY available",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please make sure your Network Connection is ON ", Toast.LENGTH_LONG).show();
        }
    }
}
