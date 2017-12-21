package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.Observable;
import java.util.Observer;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.wildcodeschool.kelian.winstate.Controllers.AuthController;
import fr.wildcodeschool.kelian.winstate.Models.UserModel;
import fr.wildcodeschool.kelian.winstate.R;

public class ModifUserActivity extends AppCompatActivity implements Observer {

    private final String TAG = String.format("WinState : %s", getClass().getSimpleName());
    private CircleImageView avatarImage;
    private EditText editName, editPseudo;
    private Spinner editGender;
    private AuthController mAuthController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_user);


        mAuthController = AuthController.getInstance();
        mAuthController.addObserver(this);

        avatarImage = findViewById(R.id.avatarImage);
        editName = findViewById(R.id.edittext_name);
        editPseudo = findViewById(R.id.edittext_pseudo);
        editGender = findViewById(R.id.spinnerGender);
        Button buttonResetPassword = findViewById(R.id.reset_pwd);
        Button buttonChangeInfo = findViewById(R.id.button_submit_modification);

        mAuthController.loadModel();

        avatarImage.setOnClickListener(v ->
                CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(ModifUserActivity.this));

        buttonChangeInfo.setOnClickListener(v -> {
            final UserModel newUserModel = mAuthController.getUserModel();
            newUserModel.setName(editName.getText().toString());
            newUserModel.setPseudonyme(editPseudo.getText().toString());
            newUserModel.setGender(editGender.getSelectedItem().toString());

            avatarImage.setDrawingCacheEnabled(true);
            avatarImage.buildDrawingCache();

            Bitmap bitmap = avatarImage.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mAuthController.getStorageRef().putBytes(data);
            uploadTask.addOnFailureListener(exception ->
                    Log.d(TAG, "onFailure: " + exception.getLocalizedMessage()))
                    .addOnSuccessListener(taskSnapshot -> {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        newUserModel.setPhotoUrl(downloadUrl.toString());
                        mAuthController.setUserModel(newUserModel);
                    });
        });

        buttonResetPassword.setOnClickListener(v ->
                FirebaseAuth.getInstance().sendPasswordResetEmail(mAuthController.getUser().getEmail())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ModifUserActivity.this, getString(R.string.send_email), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ModifUserActivity.this, getString(R.string.failure), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                avatarImage.setImageDrawable(Drawable.createFromPath(imageUri.getPath()));
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof AuthController){
            mAuthController = (AuthController) observable;
            if (!mAuthController.getUserModel().getPhotoUrl().equals("")) {
                Glide.with(ModifUserActivity.this)
                        .load(mAuthController.getUserModel().getPhotoUrl())
                        .into(avatarImage);
            }
            editName.setText(mAuthController.getUserModel().getName());
            editPseudo.setText(mAuthController.getUserModel().getPseudonyme());
            String spinnerSelect = mAuthController.getUserModel().getGender();
            switch (spinnerSelect){
                default:
                    editGender.setSelected(false);
                    break;
                case "Homme":
                    editGender.setSelection(0);
                    break;
                case "Femme":
                    editGender.setSelection(1);
                    break;
                case "Enfant":
                    editGender.setSelection(2);
                    break;
                case "Renne de Santa Nora":
                    editGender.setSelection(3);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuthController.addObserver(this);
    }
}
