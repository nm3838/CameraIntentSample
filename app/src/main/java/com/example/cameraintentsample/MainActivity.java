package com.example.cameraintentsample;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //保存された画像のURI
    private Uri _imageUri;

    ActivityResultLauncher<Intent> _cameraLauncher
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallbackFromCamera());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //画像部分がタップされた時の処理メソッド
    public void onCameraImageClick(View view) {
        //日時データを「yyyyMMddHHmmss」の形式に整形するフォーマットを生成
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //現在の日時を取得
        Date now = new Date(System.currentTimeMillis());
        //取得した日時データを「yyyyMMddHHmmss」形式に整形した文字列を作成
        String nowStr = dateFormat.format(now);
        //ストレージに格納する画像のファイル名を生成。
        //ファイル名の一いせいを確保するためにタイムスタンプの値を利用
        String fileName = "CameraIntentSamplePhoto_"+nowStr+".jpg";

        //ContentValuesオブジェクトを生成
        ContentValues values = new ContentValues();
        //画像ファイル名を設定
        values.put(MediaStore.Images.Media.TITLE, fileName);
        //画像ファイルの種類を設定
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        //ContentResolverオブジェクトを生成
        ContentResolver resolver = getContentResolver();
        //ContentResolverを使ってURIオブジェクトを生成
        _imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //インテントオブジェクトを生成
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Extra情報として_imageUriを設定
        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);
        //アクティビティを起動
        _cameraLauncher.launch(intent);
    }

    private class ActivityResultCallbackFromCamera implements ActivityResultCallback<ActivityResult> {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                ImageView ivCamera = findViewById(R.id.ivCamera);
                ivCamera.setImageURI(_imageUri);
            }
        }
    }
}
