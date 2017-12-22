package fr.wildcodeschool.kelian.winstate.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import fr.wildcodeschool.kelian.winstate.R;

public class PhotoActivity extends AppCompatActivity {

    public static int GALLERY_INTENT = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageButton mImage;

    private Uri mUri = null;
    private Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ImageButton phonePhoto = findViewById(R.id.phone_photo);

        phonePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
    }

}
