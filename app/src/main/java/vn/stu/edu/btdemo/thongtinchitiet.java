package vn.stu.edu.btdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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

public class thongtinchitiet extends AppCompatActivity {
    EditText txt_idsua,txt_tensua,txt_giasua,txt_phanloaisua,txt_motasua;
    Button btn_sua,btn_backsua;
    ImageButton btn_uploadsua;
    ImageView imghinhsua;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    final int REQUEST_CODE_GALLERY = 999;
    final String DB_PATH_SUFFIX = "/databases/";
    final String DB_NAME = "dbdemo.sqlite";
    sanpham sp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinchitiet);
        addcontrols();
        addevents();
        Intent chitiet=getIntent();
         sp2= (sanpham) chitiet.getSerializableExtra("chitiet");
        byte[] img=sp2.getAnh();

        Bitmap bitmap= BitmapFactory.decodeByteArray(img,0, img.length);
        imghinhsua.setImageBitmap(bitmap);
        txt_idsua.setText(String.valueOf(sp2.getId()));
        txt_tensua.setText(sp2.getTen().toString());
        txt_giasua.setText(String.valueOf(sp2.getGia()));
        txt_motasua.setText(sp2.getMota().toString());

    }

    private void addevents() {
        btn_backsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(thongtinchitiet.this,trangchu.class);
                startActivity(intent);
            }
        });
        btn_uploadsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        thongtinchitiet.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ten=txt_tensua.getText().toString();
                    int gia=Integer.parseInt(txt_giasua.getText().toString());
                    String mota=txt_motasua.getText().toString();

                    sanpham sp4=new sanpham(sp2.getId(),ten,gia,"phanloai",mota,imageViewTOByte(imghinhsua) );
                    //Toast.makeText(thongtinchitiet.this, "đã sửa sản phẩm", Toast.LENGTH_SHORT).show();
                    ghidulieu(sp4);
                    //Intent intent = new Intent(thongtinchitiet.this, trangchu.class);
                   // startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void addcontrols() {

        txt_idsua=findViewById(R.id.txt_idsua);
        txt_tensua=findViewById(R.id.txt_tensua);
        txt_giasua=findViewById(R.id.txt_giasua);
        txt_motasua=findViewById(R.id.txt_motasua);
        imghinhsua=findViewById(R.id.imghinhsua);
        btn_uploadsua=findViewById(R.id.btn_uploadsua);
        btn_sua=findViewById(R.id.btn_sua);
        btn_backsua=findViewById(R.id.btn_backsua);
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
                imghinhsua.setImageBitmap(bitmap);
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
    private void ghidulieu(sanpham sp1) {
        SQLiteDatabase database = openOrCreateDatabase(
                DB_NAME,
                MODE_PRIVATE,
                null
        );
        ContentValues row = new ContentValues();
        row.put("ten", sp1.getTen().toString());
        row.put("gia",sp1.getGia());
        row.put("phanloai", sp1.getPhanloai());
        row.put("mota", sp1.getMota());
        row.put("hinhanh", sp1.getAnh());
        int cs=database.update("sampham",row, "id=?",new String[]{sp1.getId()+""});
        Toast.makeText(getApplicationContext(),cs+"111",Toast.LENGTH_LONG).show();


    }
}