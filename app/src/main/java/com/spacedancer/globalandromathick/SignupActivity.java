package com.spacedancer.globalandromathick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.spacedancer.globalandromathick.utilities.GameUtilities;
import com.spacedancer.globalandromathick.utilities.Translation;

import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        language = GameUtilities.getGameLanguage(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.signup_sign_in_button);
        btnSignUp = (Button) findViewById(R.id.signup_btn_login);
        inputEmail = (EditText) findViewById(R.id.signup_login_email);
        inputPassword = (EditText) findViewById(R.id.signup_login_password);
        progressBar = (ProgressBar) findViewById(R.id.signup_progressBar);
        btnResetPassword = (Button) findViewById(R.id.signup_btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actMain = new Intent(SignupActivity.this, ResetPasswordActivity.class);
                startActivity(actMain);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actMain = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(actMain);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                        Translation.translate(language, "Unesite e-mail za prijavu"),
                        Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                        Translation.translate(language, "Unesite lozinku"), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(),
                        Translation.translate(language,"Lozinka je prekratka. Unesite barem 6 znakova"),
                        Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this,
                                        Translation.translate(language,"Autentikacija nije uspjela.") + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent actMain = new Intent(SignupActivity.this, MainGameActivity.class);
                                    startActivity(actMain);
                                    finish();
                                    overridePendingTransition(0, 0);
                                }
                            }
                        });

            }
        });

        // Selektiraj item na navigaciji
        BottomNavigationView bnv =(BottomNavigationView)findViewById(R.id.signup_navigation);
        bnv.getMenu().findItem(R.id.navigation_home).setChecked(true);

        // odredi što će se dogoditi klikom na navigaciju
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent actMGA = new Intent(SignupActivity.this,LoginActivity.class);
                        startActivity(actMGA);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_settings:
                        Intent actOptions = new Intent(SignupActivity.this, OptionsActivity.class);
                        startActivity(actOptions);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_help:
                        GameUtilities.setHelpDialog(SignupActivity.this, language);
                        break;
                    case R.id.navigation_exit:
                        closeApplication();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
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
                SignupActivity.this.finish();
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
}
