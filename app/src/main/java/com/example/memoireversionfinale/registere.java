package com.example.memoireversionfinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.memoireversionfinale.viemodele.userdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class registere extends AppCompatActivity {
    private EditText inputemail, inputpassword, retypePassword, fullName, address, contact;
    private Spinner gender, bloodgroup, division;
    private ProgressDialog pd;
    private FirebaseAuth mAuth;
    private DatabaseReference db_ref, donor_ref;
    private FirebaseDatabase db_User;
    private boolean isUpdate = false;
    private CheckBox isDonor;
    Button buttonReg;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://memoire-version-finale-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registere);
        mAuth = FirebaseAuth.getInstance();
        db_User = FirebaseDatabase.getInstance();
        db_ref = db_User.getReference("users");
        donor_ref = db_User.getReference("donors");
        mAuth = FirebaseAuth.getInstance();
        inputemail = findViewById(R.id.input_userEmail);
        inputpassword = findViewById(R.id.input_password);
        retypePassword = findViewById(R.id.input_password_confirm);
        fullName = findViewById(R.id.input_fullName);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.inputAddress);
        division = findViewById(R.id.inputDivision);
        bloodgroup = findViewById(R.id.inputBloodGroup);
        contact = findViewById(R.id.inputMobile);
        isDonor = findViewById(R.id.checkbox);
        buttonReg = findViewById(R.id.button_register);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputemail.getText().toString();
                final String password = inputpassword.getText().toString();
                final String ConfirmPassword = retypePassword.getText().toString();
                final String Name = fullName.getText().toString();
                final int Gender = gender.getSelectedItemPosition();
                final String Contact = contact.getText().toString();
                final int BloodGroup = bloodgroup.getSelectedItemPosition();
                final String Address = address.getText().toString();
                final int Division = division.getSelectedItemPosition();
                final String blood = bloodgroup.getSelectedItem().toString();
                final String div   = division.getSelectedItem().toString();

                try {

                    if (Name.length() <= 2) {
                        ShowError("Name");
                        fullName.requestFocusFromTouch();
                    } else if (Contact.length() < 11) {
                        ShowError("Contact Number");
                        contact.requestFocusFromTouch();
                    } else if (Address.length() <= 2) {
                        ShowError("Address");
                        address.requestFocusFromTouch();
                    } else {
                        if (!isUpdate) {
                            if (email.length() == 0) {
                                ShowError("Email ID");
                                inputemail.requestFocusFromTouch();
                            } else if (password.length() <= 5) {
                                ShowError("Password");
                                inputpassword.requestFocusFromTouch();
                            } else if (password.compareTo(ConfirmPassword) != 0) {
                                Toast.makeText(registere.this, "Password did not match!", Toast.LENGTH_LONG)
                                        .show();
                                retypePassword.requestFocusFromTouch();
                            } else {
                                registerUser( Name,  contact,  address, BloodGroup,  email, password);
                            }

                        }
                        //pd.dismiss();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void registerUser(String name, EditText contact, EditText address, int bloodGroup, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(registere.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final String email = inputemail.getText().toString();
                        final String password = inputpassword.getText().toString();
                        final String ConfirmPassword = retypePassword.getText().toString();
                        final String Name = fullName.getText().toString();
                        final int Gender = gender.getSelectedItemPosition();
                        final int BloodGroup = bloodgroup.getSelectedItemPosition();
                        final String Address = address.getText().toString();
                        final String Contact = address.getText().toString();
                        final int Division = division.getSelectedItemPosition();
                        final String blood = bloodgroup.getSelectedItem().toString();
                        final String div   = division.getSelectedItem().toString();
                        if (!task.isSuccessful()) {
                            Toast.makeText(registere.this, "L'enregistrement a échoué, essayez a nouveau.", Toast.LENGTH_LONG)
                                    .show();
                            Log.v("error", task.getException().getMessage());
                        } else {
                            Toast.makeText(registere.this, "registere suucces",Toast.LENGTH_LONG).show();
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            firebaseUser.sendEmailVerification();

                            String id = mAuth.getCurrentUser().getUid();
                            db_ref.child(id).child("Name").setValue(Name);
                            db_ref.child(id).child("Gender").setValue(Gender);
                            db_ref.child(id).child("BloodGroup").setValue(BloodGroup);
                            donor_ref.child(div).child(blood).child(id).child("Contact").setValue(Contact);
                            donor_ref.child(div).child(blood).child(id).child("Address").setValue(Address);
                            db_ref.child(id).child("Division").setValue(Division);

                            if(isDonor.isChecked())
                            {
                                donor_ref.child(div).child(blood).child(id).child("UID").setValue(id).toString();
                                donor_ref.child(div).child(blood).child(id).child("LastDonate").setValue("Don't donate yet!");
                                donor_ref.child(div).child(blood).child(id).child("TotalDonate").setValue(0);
                                donor_ref.child(div).child(blood).child(id).child("Name").setValue(Name);
                                donor_ref.child(div).child(blood).child(id).child("Contact").setValue(Contact);
                                donor_ref.child(div).child(blood).child(id).child("Address").setValue(Address);


                            }

                            Toast.makeText(getApplicationContext(), "Votre compte a ete creé!", Toast.LENGTH_LONG)
                                    .show();
                            Intent intent = new Intent(getApplicationContext(), log__in.class);
                            startActivity(intent);


                        }
                        //pd.dismiss();

                    }

                });
    }

    private void ShowError(String error) {

        Toast.makeText(registere.this, "Please, Enter a valid "+error,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
