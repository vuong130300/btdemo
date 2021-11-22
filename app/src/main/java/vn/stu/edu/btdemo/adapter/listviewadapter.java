package vn.stu.edu.btdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.stu.edu.btdemo.R;
import vn.stu.edu.btdemo.model.sanpham;

public class listviewadapter extends BaseAdapter {
    public listviewadapter(Context context, ArrayList<sanpham> sanPhams, int layout) {
        this.context = context;
        this.sanPhams = sanPhams;
        this.layout = layout;
    }

    Context context;
    private ArrayList<sanpham> sanPhams;
    int layout;
    @Override
    public int getCount() {
        return sanPhams.size();
    }

    @Override
    public Object getItem(int position) {
        return sanPhams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        TextView txtName = convertView.findViewById(R.id.tvTenSP);
        TextView txtGia = convertView.findViewById(R.id.tvGia);
        ImageView imgHinh = convertView.findViewById(R.id.imgSanPham);
        sanpham sp = sanPhams.get(position);
        txtName.setText(sp.getTen());
        txtGia.setText(String.valueOf(sp.getGia()));

        byte[] img = sp.getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgHinh.setImageBitmap(bitmap);
        return convertView;
    }
}
