package comguess.example.android.vlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    Database_Helper mydb;
    Button submit;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String pass = "pass";
    SharedPreferences sharedpreferences;
    EditText name, userpass,pin;        Drawable dr;
    boolean v_name=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__sign_up);
        mydb = new Database_Helper(this);
        submit = (Button)findViewById(R.id.submit);
        submit.setVisibility(View.INVISIBLE);
        name = (EditText)findViewById(R.id.name);
        userpass = (EditText)findViewById(R.id.password);
        pin = (EditText)findViewById(R.id.pin);
        mydb.demoData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        dr = getResources().getDrawable(R.drawable.tickmark);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                name.setCompoundDrawables(null,null,null,null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name.setCompoundDrawables(null,null,null,null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                v_name = true;
                name.setCompoundDrawables(null,null,null,null);
            }
        });
        userpass.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
            // your code here....

               String valid = name.getText().toString().trim();
                Cursor res = mydb.getCredentials(valid);
                if(res.getCount()==0){

                    //add an error icon to yur drawable files
                    dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
                    name.setCompoundDrawables(null,null,dr,null);
                    name.setError("Valid Name",null);


                }else

                    name.setError("UserName already Exists");
            return false;
        }
    });
        userpass.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(userpass.getText().toString().trim().length()>4)
                checkVisibility();
            }
        });
        //checkVisibility();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nam = name.getText().toString().trim();
                String pas = userpass.getText().toString().trim();
                String auth = pin.getText().toString().trim();
                if(nam.equals("")){
                    name.setError("Required");
                }if(pas.equals("")){
                    userpass.setError("Required");
                }if(auth.equals("")){
                    pin.setError("Should be minimum of 4 numbers");
                }
                else if(nam!=null && pas!=null && auth!=null && auth.length()>3){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name,name.getText().toString() );
                    editor.putString(pass, userpass.getText().toString());
                    editor.commit();
                    boolean flag = mydb.insertValues(nam,pas,auth);
                    if(flag){
                        Toast.makeText(SignUp.this,"Registration Successfull",Toast.LENGTH_LONG).show();
                        Toast.makeText(SignUp.this,"Thanks",Toast.LENGTH_LONG).show();
                        Intent loginpage = new Intent(view.getContext(),HomePage.class);
                        startActivity(loginpage);
                    }else{
                        name.setText("");
                        userpass.setText("");
                        Toast.makeText(SignUp.this,"Registration UnSuccessfull",Toast.LENGTH_LONG).show();
                        Toast.makeText(SignUp.this,"Try Different username",Toast.LENGTH_LONG).show();

                    }

                }


            }
        });
    }
    public void checkVisibility(){
        System.out.print("in method ***************************");
        String nam = name.getText().toString().trim();
        String pas = userpass.getText().toString().trim();
        String auth = pin.getText().toString().trim();
        if(nam.length()==0 && pas.length()==0){

        }else{
            submit.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Created by vleadvlabs on 27/2/17.
     */

    static class MyBounceInterpolator implements android.view.animation.Interpolator {
        double mAmplitude = 1;
        double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }
}
