package com.example.memoireversionfinale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin extends AppCompatActivity {
     Button entrer;
     EditText code1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        String code = "L3app";
        entrer = findViewById(R.id.resetPassbtn);
        code1 = findViewById(R.id.code);
        entrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = code1.getText().toString();
                if(email.equals(code)){
                      gotoUrl("https://l.facebook.com/l.php?u=https%3A%2F%2Fconsole.firebase.google.com%2Fproject%2Fmemoire-version-finale%2Fauthentication%2Fusers%3Ffbclid%3DIwAR1s-t-7IZScHshiuhj4IzmIhRs77WXuO-WgqVcIIKr9U92Ef166O-PLHxo&h=AT2wm-WIoB57v1j6pOf8FFpNv6ZA7NYeoF0FGfkuml7ayGMS3ZsQg_adSHAPPRgQqOmkkymzQSZiBuINhQlhdGgo6NjFDurgKe-FB7Awze36K-kbvIrr0QEqNcYL5gFoIEPuCQ");
                }else{
                    Toast.makeText(getApplicationContext(), "le code est incorrect !", Toast.LENGTH_LONG)
                            .show();
                }
            }

            private void gotoUrl(String s) {
               Uri uri = Uri.parse(s);
               startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
    }
}