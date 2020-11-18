package com.nessy.learnswipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class StartActivity extends AppCompatActivity {
    private TextView authStatTv;
    private Button authBtn;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        authStatTv = findViewById(R.id.authStatTv);
        authBtn = findViewById(R.id.authBtn);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(StartActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //error auth, stop task that requires auth
                authStatTv.setText("Authentication error: "+ errString);
                Toast.makeText(StartActivity.this, "Authentication errors "+ errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //auth succeed, continue tasks that requires auth
                authStatTv.setText("Authentication succeed..!");
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //failed auth, stop tasks that required auth
                authStatTv.setText("Authentication failed..!");
                Toast.makeText(StartActivity.this, "Authentication failed...!", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint authentication")
                .setNegativeButtonText("User App Password")
                .build();

        authBtn.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
    }
}