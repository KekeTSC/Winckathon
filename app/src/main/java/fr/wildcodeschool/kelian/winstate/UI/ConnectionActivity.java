package fr.wildcodeschool.kelian.winstate.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.wildcodeschool.kelian.winstate.Controllers.AuthController;
import fr.wildcodeschool.kelian.winstate.R;


public class ConnectionActivity extends AppCompatActivity {

    private final String TAG = String.format("WinState : %s", getClass().getSimpleName());
    private EditText mEmail, mPassword;
    private ProgressDialog mProgressDialog;
    private AuthController mAuthController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        mAuthController = AuthController.getInstance();

        if (mAuthController.isThereCurrentUser()){
            startActivity(new Intent(ConnectionActivity.this, WinLinkActivity.class));
        }

        Button buttonSignIn = findViewById(R.id.button_signin);
        Button buttonCreateAccount = findViewById(R.id.button_create_account);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        TextView resetPassword = findViewById(R.id.tv_login_reset_password);


        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = mEmail.getText().toString().trim();
                String getPassword = mPassword.getText().toString().trim();
                if (getEmail.isEmpty() || getPassword.isEmpty()) {
                    Toast.makeText(ConnectionActivity.this, R.string.empties_fields, Toast.LENGTH_SHORT).show();
                } else {
                    mProgressDialog = new ProgressDialog(ConnectionActivity.this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage(getString(R.string.registartion));
                    mProgressDialog.show();
                    mAuthController.callSignIn(ConnectionActivity.this, getEmail, getPassword, mProgressDialog);
                }
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(ConnectionActivity.this, ResetPassword.class);
                startActivity(intentLogin);
            }
        });


        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = mEmail.getText().toString().trim();
                Intent intent = new Intent(ConnectionActivity.this, CreateAccountActivity.class);
                intent.putExtra("email", getEmail);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}