package com.example.cameraintentsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //親クラスの同名メソッドの呼び出し
        super.onActivityResult(requestCode, resultCode, data);
        //カメラアプリからの連携からの戻り値でかつ撮影成功の場合
        if(requestCode == 200 && resultCode == RESULT_OK) {
            //撮影された画像のビットマップデータを取得
            Bitmap bitmap = data.getParcelableExtra("data");
            //画像を表示するImageViewを取得
            ImageView ivCamera = findViewById(R.id.ivCamera);
            //撮影された画像をImageViewに設定
            ivCamera.setImageBitmap(bitmap);
        }
    }

    //画像部分がタップされた時の処理メソッド
    public void onCameraImageClick(View view) {
        //インテントオブジェクトを生成
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //アクティビティを起動
        startActivityForResult(intent, 200);
    }
}