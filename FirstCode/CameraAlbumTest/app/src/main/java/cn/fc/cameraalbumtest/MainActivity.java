package cn.fc.cameraalbumtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    private ImageView picture;

    private Uri imageUri;

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takePhoto = (Button)findViewById(R.id.take_photo);
        picture = (ImageView)findViewById(R.id.picture);
        Button chooseFromAlbum = (Button)findViewById(R.id.choose_from_album);

        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_PERMISSION_STORAGE = 2;
                    String[] permissions = {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    };

                    for (String str : permissions) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this, str) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{str}, REQUEST_CODE_PERMISSION_STORAGE);

                        } else {
                            openAlbum();
                        }
                    }
                }
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 大坑，动态注册权限
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_PERMISSION_STORAGE = 1;
                    String[] permissions = {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    };

                    for (String str : permissions) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this, str) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{str}, REQUEST_CODE_PERMISSION_STORAGE);

                        }
                    }
                }
                // 创建一个文件用于存储拍照后的图片
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(MainActivity.this, "cn.fc.cameraalbumtest.fileProvider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }

                // 启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try{
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(requestCode == RESULT_OK){
                    // 判断手机的系统版本号
                   if (Build.VERSION.SDK_INT >= 19){
                       // 4.4以上的版本用这个版本处理图片
                       handleImageOnKitKat(data);
                   } else {
                       // 4.4以下系统用这个版本处理图片
                   }
                }
                break;
            default:
                break;
        }
    }

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        Log.d(TAG, "get----------intent");
        intent.setType("Image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case 2:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                } else {
                    Toast.makeText(MainActivity.this, "You denied the permission", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    public void handleImageOnKitKat(Intent data){
        Log.d(TAG, "get image data---------------->" + data);
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)){
            // 如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];   // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            } else if("content".equalsIgnoreCase(uri.getScheme())){
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())){
                imagePath = uri.getPath();
            }
            Log.d(TAG, "get image path---------------->" + imagePath);
            displayImage(imagePath);
        }
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor= getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    };

    private void displayImage(String path){
        if (path != null){
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Log.d(TAG, "show img------------------>" + path);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(MainActivity.this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
