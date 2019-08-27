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

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language = GameUtilities.getGameLanguage(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainGameActivity.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.login_login_email);
        inputPassword = (EditText) findViewById(R.id.login_login_password);
        progressBar = (ProgressBar) findViewById(R.id.login_login_progressBar);
        btnSignup = (Button) findViewById(R.id.login_btn_signup);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        btnReset = (Button) findViewById(R.id.login_login_btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actMain = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(actMain);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actMain = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(actMain);
                finish();
                overridePendingTransition(0, 0);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

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

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    inputPassword.setError(getString(R.string.auth_minimum_password));
                                } else {
                                    Toast.makeText(LoginActivity.this, getString(R.string.auth_auth_failed), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Intent intent = new Intent(LoginActivity.this, MainGameActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(0, 0);
                            }
                        }
                    });
            }
        });



        // Selektiraj item na navigaciji
        BottomNavigationView bnv =(BottomNavigationView)findViewById(R.id.login_navigation);
        bnv.getMenu().findItem(R.id.navigation_home).setChecked(true);

        // odredi što će se dogoditi klikom na navigaciju
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_settings:
                        Intent actOptions = new Intent(LoginActivity.this, OptionsActivity.class);
                        startActivity(actOptions);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_help:
                        GameUtilities.setHelpDialog(LoginActivity.this, language);
                        break;
                    case R.id.navigation_exit:
                        closeApplication();
                        break;
                }
                return false;
            }
        });
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
                LoginActivity.this.finish();
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
