package comguess.example.android.vlabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CheckBox extends AppCompatActivity {
    Button default_btn,flatbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_button_check_box);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        default_btn = (Button)findViewById(R.id.default_btn);
        flatbutton = (Button)findViewById(R.id.flatbutton);

    }

    public void alertBox(View v) {
        // Perform action on click
        if (v.getId()==R.id.default_btn) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Properties of Default Button")
                    .setMessage("Button text: 14pt Roboto medium\n" +
                            "Button height: 36dp\n" +
                            "Button text left and right padding: 16dp\n" +
                            "Corner radius: 2dp")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }else if(v.getId()==R.id.flatbutton){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Properties of Flat Button")
                    .setMessage("Flat Button\n" +
                            "Height: 36dp\n" +
                            "Minimum width: 88dp\n" +
                            "Touch target height: 48dp\n" +
                            "Corner radius: 2dp\n" +
                            "Horizontal margin: 8dp\n" +
                            "Horizontal padding: 8dp ")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

}
