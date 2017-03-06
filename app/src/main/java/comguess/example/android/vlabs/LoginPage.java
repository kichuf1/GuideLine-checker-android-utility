package comguess.example.android.vlabs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import comguess.example.android.vlabs.activity.MainActivity;

public class LoginPage extends AppCompatActivity {
    EditText name,password;
    Button login;
    Database_Helper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        db = new Database_Helper(this);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Cursor res = db.getCredentials(name.getText().toString().trim());
                String check = res.getString(0).toString();
                String pas = password.getText().toString().trim();
                if(check.equals(pas)){
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("name",name.getText().toString().trim());
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginPage.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                }

            }

        });

    }

}
