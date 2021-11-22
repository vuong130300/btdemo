package vn.stu.edu.btdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import vn.stu.edu.btdemo.adapter.listviewadapter;
import vn.stu.edu.btdemo.model.sanpham;

public class trangchu extends AppCompatActivity {
    final String DB_PATH_SUFFIX = "/databases/";
    final String DB_NAME = "dbdemo.sqlite";
    ListView lv;
    ArrayList<sanpham> ds;
    listviewadapter lvadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        copyDbFromAssets();
        addControls();
        addEvents();
        docDssanpham();
    }

    private void addEvents() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(trangchu.this,thongtinchitiet.class);
                intent.putExtra("chitiet",ds.get(position));
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(trangchu.this);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sanpham sp = ds.get(position);
                        SQLiteDatabase database = openOrCreateDatabase(
                                DB_NAME,
                                MODE_PRIVATE,
                                null
                        );
                        int deletedRowCount = database.delete(
                                "sampham",
                                "id=?",
                                new String[]{sp.getId() + ""}
                        );
                        Toast.makeText(trangchu.this, "đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                        docDssanpham();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });

    }


    private void docDssanpham() {
        SQLiteDatabase database = openOrCreateDatabase(
                DB_NAME,
                MODE_PRIVATE,
                null
        );
        Cursor cursor = database.rawQuery("select * from sampham", null);
//        Cursor cursor = database.query(
//                "sampham",
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );

        ds.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            int gia = cursor.getInt(2);
            String phanloai = cursor.getString(3);
            String mota = cursor.getString(4);
            byte[] hinhanh = cursor.getBlob(5);
            sanpham sp = new sanpham(id, ten, gia, phanloai, mota, hinhanh);
            ds.add(sp);
        }
        lvadapter = new listviewadapter(trangchu.this, ds, R.layout.listview);
        lv.setAdapter(lvadapter);
        lvadapter.notifyDataSetChanged();
        cursor.close();
        database.close();

    }

    private void addControls() {
        lv = findViewById(R.id.lvtrangchu);
        ds = new ArrayList<>();


    }

    private void copyDbFromAssets() {
        File dbFile = getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            try {
                File dbDir = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
                if (!dbDir.exists()) {
                    dbDir.mkdir();
                }

                InputStream is = getAssets().open(DB_NAME);
                String outputFilePath = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DB_NAME;
                OutputStream os = new FileOutputStream(outputFilePath);
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent1 = new Intent(trangchu.this, about.class);
                startActivity(intent1);
                break;
            case R.id.btnAdd:
                Intent intent2 = new Intent(trangchu.this, them.class);
                startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }
}