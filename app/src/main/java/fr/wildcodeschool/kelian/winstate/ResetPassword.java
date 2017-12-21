package fr.wildcodeschool.kelian.winstate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        mAuth = FirebaseAuth.getInstance();

        final EditText resetEmail = findViewById(R.id.et_reset_email);
        Button resetPassword = findViewById(R.id.btn_reset_password);
        final TextView gotoLogin = findViewById(R.id.tv_reset_goto_login);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResetPassword.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = resetEmail.getText().toString().trim();

                if (email.equals("")) {
                    Toast.makeText(getApplication(), R.string.please_enter_email,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassword.this,
                                            R.string.instructions_sent_by_email,
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ResetPassword.this,
                                            R.string.password_initialization_failed, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
}
