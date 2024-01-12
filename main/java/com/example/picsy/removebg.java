package com.example.picsy;
import android.os.AsyncTask;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;

public class removebg extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView1;
    private ImageView imageView2;
    private Button generatebtn;
    private String selectedImagePath; // This variable will store the image path

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removebg);

       imageView1 = findViewById(R.id.image_view);
       imageView2 = findViewById(R.id.image_view2);
       generatebtn = findViewById(R.id.generate_btn);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        generatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processImage(selectedImagePath);
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            Uri selectedImage = data.getData();

            // Convert the URI to a file path
            selectedImagePath = getPathFromUri(selectedImage);

            // Use the image path as needed
            // ...

            // Process the image with the remove.bg API
            processImage(selectedImagePath);

            // Set the selected image to imageView1
            imageView1.setImageURI(selectedImage);
        }
    }

    // This method converts the URI to a string representing the file path
    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else {
            return uri.getPath(); // Fallback to URI.getPath() if cursor is null
        }
    }

    // This method processes the image with the remove.bg API
    private class ImageProcessingTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String imagePath = params[0];

            try {
                OkHttpClient client = new OkHttpClient();

                // Replace "INSERT_YOUR_API_KEY_HERE" with your actual API key
                String apiKey = "n8Ceo1twkZ878YYkzpwCuo4n";

                // Create the request body
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image_file", "image.jpg",
                                RequestBody.create(MediaType.parse("image/*"), new File(imagePath)))
                        .addFormDataPart("size", "auto")
                        .build();

                // Create the request
                Request request = new Request.Builder()
                        .url("https://api.remove.bg/v1.0/removebg")
                        .addHeader("X-Api-Key", apiKey)
                        .post(requestBody)
                        .build();

                // Execute the request
                Response response = client.newCall(request).execute();

                // Handle the response as needed
                // ...

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        // Add any post-execution code if needed
    }

    // Modify your existing processImage method to use AsyncTask
    private void processImage(String imagePath) {
        new ImageProcessingTask().execute(imagePath);
    }
}


