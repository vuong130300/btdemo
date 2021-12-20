package vn.stu.edu.btdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class about extends AppCompatActivity implements OnMapReadyCallback {
    ImageButton btn_call;
    TextView textphone;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);
        addcontrols();
        addevents();
    }

    

    private void addevents() {
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    makePhonecall();
                }
                else {
                    requestPermissions();
                }
            }
        });
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                about.this,
                Manifest.permission.CALL_PHONE
        )) {
            Toast.makeText(about.this, "Vui lòng cấp quyền gọi", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(
                    about.this,
                    new String[]{
                            Manifest.permission.CALL_PHONE
                    },
                    1303
            );
        }
    }

    private void makePhonecall() {
        String phonenumber = textphone.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumber));
        startActivity(intent);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(
                about.this,
                Manifest.permission.CALL_PHONE
        );
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void addcontrols() {
        btn_call = findViewById(R.id.btn_call);

        textphone=findViewById(R.id.textphone);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1303) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhonecall();
            } else {
                Toast.makeText(about.this, "Bạn đã từ chối cấp quyền", Toast.LENGTH_LONG).show();
            }

        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng stu = new LatLng(10.737990732005878, 106.67781410326936);
        mMap.addMarker(new MarkerOptions().position(stu).title("ĐHCN SÀI GÒN"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stu, 18));
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
                Intent intent1 = new Intent(about.this, about.class);
                startActivity(intent1);
                break;

            case R.id.exit:
                Intent intent2 = new Intent(about.this, trangchu.class);
                startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}



