package fr.wildcodeschool.kelian.winstate;

import android.app.ProgressDialog;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

import fr.wildcodeschool.kelian.winstate.Controllers.AuthController;


public class MainActivity extends AppCompatActivity {

    private final String TAG = String.format("Florian's Coffe : %s", getClass().getSimpleName());
    private EditText mEmail, mPassword;
    private Button mSignin;
    private ProgressDialog mProgressDialog;
    private AuthController mAuthController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthController = AuthController.getInstance();

        mSignin = findViewById(R.id.button_signin);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = mEmail.getText().toString().trim();
                String getPassword = mPassword.getText().toString().trim();
                if (getEmail.isEmpty() || getPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.empties_fields, Toast.LENGTH_SHORT).show();
                } else {
                    mProgressDialog = new ProgressDialog(MainActivity.this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage(getString(R.string.registartion));
                    mProgressDialog.show();
                    mAuthController.callSignIn(MainActivity.this, getEmail, getPassword, mProgressDialog);
                }
            }
        });
    }
}
