package vn.stu.edu.btdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import vn.stu.edu.btdemo.model.sanpham;

public class them extends AppCompatActivity {
    EditText txt_ten, txt_gia,txt_mota;
    Button  btn_them,btn_back;
    ImageButton btn_upload;
    ImageView imgsanpham;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private ImageView ivImage;
    private String userChoosenTask;
    final int REQUEST_CODE_GALLERY = 999;
    final String DB_PATH_SUFFIX = "/databases/";
    final String DB_NAME = "dbdemo.sqlite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them);
        addcontrols();
        addevents();
    }

    private void addcontrols() {
        txt_ten=findViewById(R.id.txt_ten);
        txt_gia=findViewById(R.id.txt_gia);
        txt_mota=findViewById(R.id.txt_mota);
        imgsanpham=findViewById(R.id.imgsanpham);
        btn_upload=findViewById(R.id.btn_upload);
        btn_them=findViewById(R.id.btn_them);
        btn_back=findViewById(R.id.btn_back);
    }

    private void addevents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(them.this,trangchu.class);
                startActivity(intent);
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=txt_ten.getText().toString();
                int gia=Integer.parseInt(txt_gia.getText().toString());
                String mota=txt_mota.getText().toString();

            sanpham sp1=new sanpham(1,ten,gia,"phanloai",mota,imageViewTOByte(imgsanpham) );
                Toast.makeText(them.this, "đã thêm sản phẩm", Toast.LENGTH_SHORT).show();
                ghidulieu(sp1);

            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        them.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });



    }

    private void ghidulieu(sanpham sp1) {
        SQLiteDatabase database = openOrCreateDatabase(
                DB_NAME,
                MODE_PRIVATE,
                null
        );
        ContentValues row = new ContentValues();

        row.put("ten",sp1.getTen());
        row.put("gia",sp1.getGia());
        row.put("phanloai",sp1.getPhanloai());
        row.put("mota",sp1.getMota());
        row.put("hinhanh",sp1.getAnh());
        long insertedID= database.insert(
                "sampham",
                null,
                row
        );
        database.close();


    }

    public static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_GALLERY){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgsanpham.setImageBitmap(bitmap);
            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent =  new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else{
                Toast.makeText(getApplicationContext(), "Đã từ chối quyền", Toast.LENGTH_LONG).show();
            }
        }
    }
    private byte[] imageViewTOByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}