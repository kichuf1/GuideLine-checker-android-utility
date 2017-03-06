package comguess.example.android.vlabs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.os.Vibrator;
import android.widget.Toast;

import comguess.example.android.vlabs.activity.SimpleTabsActivity;

public class Authentication extends AppCompatActivity {
    EditText key;
    Database_Helper db;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        key = (EditText) findViewById(R.id.key);
        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        String check = key.getText().toString().trim();
        if(username==null){
            Toast.makeText(Authentication.this,"Please login to continue",Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(Authentication.this,HomePage.class);
            startActivity(intent1);
        }
        db = new Database_Helper(this);
        key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String check = key.getText().toString().trim();
                Cursor res = db.getCredentials(username);
                res.moveToNext();
                String aut = res.getString(2);
                if(check.length()>=4){
                    if(aut.equals(check)){
                        Intent intent = new Intent(Authentication.this, SimpleTabsActivity.class);
                        startActivity(intent);
                    }else{
                        key.setError("Key Mismatch");
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 400 milliseconds
                        v.vibrate(400);
                    }
                }


            }


            });
    }
}
