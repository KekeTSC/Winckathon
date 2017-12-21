package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import fr.wildcodeschool.kelian.winstate.Controllers.AuthController;
import fr.wildcodeschool.kelian.winstate.R;

public class CreateAccountActivity extends AppCompatActivity {

    private final String TAG = String.format("WinState : %s", getClass().getSimpleName());
    private EditText mEditMail, mEditPassword, mEditPasswordConfirm;
    private String mEmailText, mPasswordText;
    private AuthController mAuthController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Intent intent = getIntent();

        mAuthController = AuthController.getInstance();

        mEditMail = findViewById(R.id.input_email);
        mEditPassword = findViewById(R.id.input_password);
        mEditPasswordConfirm = findViewById(R.id.input_password_confirmation);
        Button buttonCreateAccount = findViewById(R.id.button_create_account);

        mEditMail.setText(intent.getStringExtra("email"));

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    mEmailText = mEditMail.getText().toString().trim();
                    mPasswordText = mEditPassword.getText().toString().trim();

                    mAuthController.createAccount(CreateAccountActivity.this, mEmailText, mPasswordText);
                }
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEditMail.getText().toString();
        if (email.isEmpty()) {
            mEditMail.setText("");
            mEditPassword.setText("");
            mEditPasswordConfirm.setText("");
            mEditMail.setError(getString(R.string.please_fill_email));
            valid = false;
        } else {
            mEditMail.setError(null);
        }

        String password = mEditPassword.getText().toString();
        if (password.isEmpty()) {
            mEditPassword.setText("");
            mEditPasswordConfirm.setText("");
            mEditPassword.setError(getString(R.string.please_fill_password));
            valid = false;
        } else {
            mEditPassword.setError(null);
        }
        String passwordConfirm = mEditPasswordConfirm.getText().toString();
        if (passwordConfirm.isEmpty()) {
            mEditPassword.setText("");
            mEditPasswordConfirm.setText("");
            mEditPasswordConfirm.setError(getString(R.string.please_fill_password_confirm));
            valid = false;
        } else {
            mEditPasswordConfirm.setError(null);
        }
        if (!password.equals(passwordConfirm)) {
            mEditPassword.setText("");
            mEditPasswordConfirm.setText("");
            mEditPassword.setError("Les mots de passes doivent être les mêmes.");
            mEditPasswordConfirm.setError("Les mots de passes doivent être les mêmes.");
            valid = false;
        } else {
            mEditPassword.setError(null);
            mEditPasswordConfirm.setError(null);
        }
        return valid;
    }
}
