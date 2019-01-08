package info.atiar.questionnaire;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.UUID;

import bp.DrawingView;
import bp.Info;
import bp.SP;


//Question 10


public class Page11q10 extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "drawing p11q10";
    private DrawingView mDrawingView;
    private ImageButton currPaint, drawButton, eraseButton, newButton, nextButton;
    private float smallBrush, mediumBrush, largeBrush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page11q10);

        //Checking the permission for writing external storage when sdk is >23
        isStoragePermissionGranted();

        mDrawingView = (DrawingView)findViewById(R.id.drawing);
        // Getting the initial paint color.
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        // 0th child is white color, so selecting first child to give black as initial color.
        currPaint = (ImageButton)paintLayout.getChildAt(1);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.pallet_pressed));
        drawButton = (ImageButton) findViewById(R.id.buttonBrush);
        drawButton.setOnClickListener(this);
        eraseButton = (ImageButton) findViewById(R.id.buttonErase);
        eraseButton.setOnClickListener(this);
        newButton = (ImageButton) findViewById(R.id.buttonNew);
        newButton.setOnClickListener(this);
        nextButton = (ImageButton) findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(this);

        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        // Set the initial brush size
        mDrawingView.setBrushSize(smallBrush);

    }

    /**
     * Method is called when color is clicked from pallet.
     * @param view ImageButton on which click took place.
     */
    public void paintClicked(View view){
        if (view != currPaint){
            // Update the color
            ImageButton imageButton = (ImageButton) view;
            String colorTag = imageButton.getTag().toString();
            mDrawingView.setColor(colorTag);
            // Swap the backgrounds for last active and currently active image button.
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.pallet_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.pallet));
            currPaint = (ImageButton)view;
            mDrawingView.setErase(false);
            mDrawingView.setBrushSize(mDrawingView.getLastBrushSize());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.buttonBrush:
                // Show brush size chooser dialog
                showBrushSizeChooserDialog();
                break;
            case R.id.buttonErase:
                // Show eraser size chooser dialog
                showEraserSizeChooserDialog();
                break;
            case R.id.buttonNew:
                // Show new painting alert dialog
                showNewPaintingAlertDialog();
                break;
            case R.id.nextBtn:
                // Show save painting confirmation dialog.
                showSavePaintingConfirmationDialog();
                break;
        }
    }

    private void showBrushSizeChooserDialog(){
        final Dialog brushDialog = new Dialog(this);
        brushDialog.setContentView(R.layout.dialog_brush_size);
        brushDialog.setTitle("Brush size:");
        ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
        smallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setBrushSize(smallBrush);
                mDrawingView.setLastBrushSize(smallBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setBrushSize(mediumBrush);
                mDrawingView.setLastBrushSize(mediumBrush);
                brushDialog.dismiss();
            }
        });

        ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
        largeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDrawingView.setBrushSize(largeBrush);
                mDrawingView.setLastBrushSize(largeBrush);
                brushDialog.dismiss();
            }
        });
        mDrawingView.setErase(false);
        brushDialog.show();
    }

    private void showEraserSizeChooserDialog(){
        final Dialog brushDialog = new Dialog(this);
        brushDialog.setTitle("Eraser size:");
        brushDialog.setContentView(R.layout.dialog_brush_size);
        ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
        smallBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setBrushSize(smallBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setBrushSize(mediumBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
        largeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setBrushSize(largeBrush);
                brushDialog.dismiss();
            }
        });
        brushDialog.show();
    }

    private void showNewPaintingAlertDialog(){
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New drawing");
        newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mDrawingView.startNew();
                dialog.dismiss();
            }
        });
        newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        newDialog.show();
    }

    private void showSavePaintingConfirmationDialog(){
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing and continnue next page?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                //save drawing
                mDrawingView.setDrawingCacheEnabled(true);
                mDrawingView.buildDrawingCache();
                Bitmap bmp = Bitmap.createBitmap(mDrawingView.getDrawingCache());
                String path = SP.storeImage(getApplicationContext(), bmp, Info.fileNam_page11q10_image);
                SP.setPreference(getApplicationContext(),Info.key_page11q10_image,path);
                next();

                // Destroy the current cache.
                mDrawingView.destroyDrawingCache();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    public  void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }




    public void next() {
        ////going to Question 11, its named q12
        Intent intent = new Intent(this,Page12q12Preview.class);
        startActivity(intent);
    }
}
