package comguess.example.android.vlabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import comguess.example.android.vlabs.activity.MainActivity;
import io.fabric.sdk.android.Fabric;

public class HomePage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = " mAZOQhTtt2rhnc9P1J8hrSmZZ";
    private static final String TWITTER_SECRET = " LOZ3agSNe24zfffIa2jV6JRNTCg0ECxfAaubJAd9endBglQw7u";
    private TwitterLoginButton loginButton;
    private LoginButton floginButton;
    private static int RC_SIGN_IN = 0;
    private static String TAG = "MAIN_ACTIVITY";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button Userlogin,signup;
    CallbackManager callbackManager;
    SharedPreferences settings;
    private static final String PREFRENCES_NAME = "myprefrences";
    Button guest;
    EditText name,password;
    Database_Helper db;
    TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        guest = (Button) findViewById(R.id.guest);
        getSupportActionBar();
        name = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        db = new Database_Helper(this);
        forgotPassword = ((TextView)findViewById(R.id.forgotpassword));
               forgotPassword .setMovementMethod(LinkMovementMethod.getInstance());
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        db.demoData();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //initializing buttons
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        floginButton = (LoginButton) findViewById(R.id.connectWithFacebookbButton);
        Userlogin = (Button)findViewById(R.id.login);
        signup = (Button)findViewById(R.id.signUp);
       //sharedpreferenced data


//        settings = getSharedPreferences(PREFRENCES_NAME,
//                Context.MODE_PRIVATE);
//        String name = settings.getString("Name", "");
//        String division = settings.getString("pass", "");
//        if(name!=null|| name.equals("")){
//
//            Intent intent = new Intent(this.getApplicationContext(), SignUp.class);
//            startActivity(intent);
//        }

        //render in to signup page
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUp.class);
                startActivity(intent);
            }
        });
        guest.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        //userLogin button is used to render next page with valid username and password
        Userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Userlogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){

                        String get = name.getText().toString();
                        Cursor res = db.getCredentials(get);
                        res.moveToNext();
                        //System.out.println(res.getString(1)+"*****&&&&^^^^^%%$%%&&&&");
                        //Toast.makeText(HomePage.this,res.toString(),Toast.LENGTH_LONG).show();
                        if(res.getCount()==0){
                            Toast.makeText(HomePage.this,"Register to Login",Toast.LENGTH_LONG).show();
                            name.setText("");
                            password.setText("");
                        }else{
                            String check = res.getString(1);
                            String pas = password.getText().toString().trim();
                            if(check.equals(pas)){
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                intent.putExtra("name",name.getText().toString().trim());
                                startActivity(intent);
                            }else{
                                Toast.makeText(HomePage.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                            }
                        }


                    }

                });
            }
        });


        floginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "comguess.example.android.vlabs",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin:success" + result);
                handleTwitterSession(result.data);
                Toast.makeText(HomePage.this, "welcome to user : "+result.data.getUserName(), Toast.LENGTH_LONG).show();
                Intent i= new Intent(HomePage.this,LoginPage.class);
                startActivity(i);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);
//                updateUI(null);
                Toast.makeText(HomePage.this, "failed the login ", Toast.LENGTH_LONG).show();

            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                    Log.d("AUTH", "user logged in: " + user.getEmail());
                else
                    Log.d("AUTH", "user logged out.");
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                    Intent i = new Intent(this,LoginPage.class);
                    startActivity(i);
                } else

                    Log.d(TAG, "Google Login Failed");

            }else {
                loginButton.onActivityResult(requestCode, resultCode, data);
            }
    }
    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(HomePage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("AUTH", "signInWithCredential:oncomplete: " + task.isSuccessful());
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed.");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Intent search = new Intent(this,SearchWithScrollable.class);
            startActivity(search);
        } else if (id == R.id.undo) {
            Intent undo = new Intent(this,Undo.class);
            startActivity(undo);
        }else if(id==R.id.checkbox){
            Intent checkbox = new Intent(this,CheckBox.class);
            startActivity(checkbox);
        }else if(id==R.id.wifi) {
            Intent internetCheck = new Intent(this,Internet.class);
            startActivity(internetCheck);
        }else if(id==R.id.scanner) {
            Intent internetCheck = new Intent(this,BarcodeScanner.class);
            startActivity(internetCheck);
        }else if(id==R.id.VoiceSearch) {
            Intent voice = new Intent(this,VoiceRecognition.class);
            startActivity(voice);
        }else if(id==R.id.DateTimePicker) {
            Intent internetCheck = new Intent(this,DateTimePicker.class);
            startActivity(internetCheck);
        }else if(id==R.id.Gps) {
            Intent internetCheck = new Intent(this,Gpsfinder.class);
            startActivity(internetCheck);
        }else if(id==R.id.carousel) {
            Intent carouse = new Intent(this,Carousel.class);
            startActivity(carouse);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}