package com.spacedancer.globalandromathick;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spacedancer.globalandromathick.utilities.GameUtilities;
import com.spacedancer.globalandromathick.utilities.Translation;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
            changeEmail, changePassword, sendEmail, signOut;

    private EditText oldEmail, newEmail, password, newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private String language;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language = GameUtilities.getGameLanguage(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        // setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        btnChangeEmail = (Button) findViewById(R.id.main_change_email_button);
        btnChangePassword = (Button) findViewById(R.id.main_change_password_button);
        btnSendResetEmail = (Button) findViewById(R.id.main_sending_pass_reset_button);
        btnRemoveUser = (Button) findViewById(R.id.main_remove_user_button);
        changeEmail = (Button) findViewById(R.id.main_changeEmail);
        changePassword = (Button) findViewById(R.id.main_changePass);
        sendEmail = (Button) findViewById(R.id.main_send);
        signOut = (Button) findViewById(R.id.main_sign_out);

        oldEmail = (EditText) findViewById(R.id.main_old_email);
        newEmail = (EditText) findViewById(R.id.main_new_email);
        password = (EditText) findViewById(R.id.main_login_password);
        newPassword = (EditText) findViewById(R.id.main_newPassword);

        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.main_login_progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.VISIBLE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newEmail.getText().toString().trim().equals("")) {
                    user.updateEmail(newEmail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this,
                                        Translation.translate(language,
                                        "E-mail adresa je izmijenjena. Molimo prijavite se s novom adresom"),
                                        Toast.LENGTH_LONG).show();
                                    signOut();
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(MainActivity.this,
                                        Translation.translate(language, "Izmjena e-maila nije uspjela!"),
                                        Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                } else if (newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError(Translation.translate(language, "Unesite e-mail"));
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.VISIBLE);
                sendEmail.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError(Translation.translate(language,
                            "Lozinka je prekratka. Unesite barem 6 znakova"));
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this,
                                            Translation.translate(language,
                                                "Lozinka je izmijenjena. Prijavite se s novom lozinkom!"),
                                            Toast.LENGTH_SHORT).show();
                                        signOut();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(MainActivity.this,
                                            Translation.translate(language,"Izmjena lozinke nije uspjela!"),
                                            Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError(Translation.translate(language,"Unesite lozinku"));
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.VISIBLE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.VISIBLE);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!oldEmail.getText().toString().trim().equals("")) {
                    auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this,
                                    Translation.translate(language,
                                    "Poslan je e-mail za poništavanje lozinke!"),
                                    Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(MainActivity.this,
                                    Translation.translate(language,
                                    "Slanje e-maila za poništavanje lozinke nije uspjelo!"),
                                    Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                            }
                        });
                } else {
                    oldEmail.setError(Translation.translate(language,"Unesite e-mail za prijavu"));
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    removeUser();
                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        // Selektiraj item na navigaciji
        BottomNavigationView bnv =(BottomNavigationView)findViewById(R.id.main_navigation);
        bnv.getMenu().findItem(R.id.navigation_home).setChecked(true);

        // odredi što će se dogoditi klikom na navigaciju
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent actMGA = new Intent(MainActivity.this,MainGameActivity.class);
                        startActivity(actMGA);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_leaderboard:
                        Intent actLead = new Intent(MainActivity.this,LeaderboardActivity.class);
                        startActivity(actLead);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_help:
                        GameUtilities.setHelpDialog(MainActivity.this, language);
                        break;
                    case R.id.navigation_settings:
                        Intent actOptions = new Intent(MainActivity.this,OptionsActivity.class);
                        startActivity(actOptions);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_exit:
                        closeApplication();
                        break;

                }
                return false;
            }
        });

    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }


    public void onBackPressed () {
        closeApplication();
    }

    protected void closeApplication() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        String languageToLoad = "";
        if (language.toLowerCase().equals("en")) languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        dialog.setContentView(R.layout.layout_closing);

        ImageButton yesButton = (ImageButton) dialog.findViewById(R.id.closingButtonYes);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });

        ImageButton noButton = (ImageButton) dialog.findViewById(R.id.closingButtonNo);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }




    protected void removeUser() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        String languageToLoad = "";
        if (language.toLowerCase().equals("en")) languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        dialog.setContentView(R.layout.layout_remove_user);

        ImageButton yesButton = (ImageButton) dialog.findViewById(R.id.removeUserButtonYes);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this,
                                            Translation.translate(language,
                                                    "Vaš profil je izbrisan. Kreirajte novi korisnički račun!"),
                                            Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                    finish();
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(MainActivity.this,
                                            Translation.translate(language, "Neuspješno brisanje korisničkog računa!"),
                                            Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

                dialog.dismiss();
            }
        });

        ImageButton noButton = (ImageButton) dialog.findViewById(R.id.removeUserButtonNo);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }




}