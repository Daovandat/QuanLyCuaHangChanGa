package com.example.qlcuahangchanga.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlcuahangchanga.DTO.HangDTO;
import com.example.qlcuahangchanga.R;

import java.util.List;

public class AdapterDisplayMenu extends BaseAdapter {
    Context context;
    int layout;
    List<HangDTO> hangDTOList;
    Viewholder viewholder;

    //constructor
    public AdapterDisplayMenu(Context context, int layout, List<HangDTO> hangDTOList){
        this.context = context;
        this.layout = layout;
        this.hangDTOList = hangDTOList;
    }

    @Override
    public int getCount() {
        return hangDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return hangDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return hangDTOList.get(position).getMaHang();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewholder.img_custommenu_HinhHang  = (ImageView)view.findViewById(R.id.img_custommenu_HinhHang);
            viewholder.txt_custommenu_TenHang   = (TextView) view.findViewById(R.id.txt_custommenu_TenHang);
            viewholder.txt_custommenu_TinhTrang = (TextView)view.findViewById(R.id.txt_custommenu_TinhTrang);
            viewholder.txt_custommenu_GiaTien   = (TextView)view.findViewById(R.id.txt_custommenu_GiaTien);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        HangDTO hangDTO = hangDTOList.get(position);
        viewholder.txt_custommenu_TenHang.setText(hangDTO.getTenHang());
        viewholder.txt_custommenu_GiaTien.setText(hangDTO.getGiaTien()+" VNĐ");

        //hiển thị tình trạng của hàng
        if(hangDTO.getTinhTrang().equals("true")){
            viewholder.txt_custommenu_TinhTrang.setText("Còn hàng");
        }else{
            viewholder.txt_custommenu_TinhTrang.setText("Hết hàng");
        }

        //lấy hình ảnh
        if(hangDTO.getHinhAnh() != null){
            byte[] menuimage = hangDTO.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewholder.img_custommenu_HinhHang.setImageBitmap(bitmap);
        }else {
            viewholder.img_custommenu_HinhHang.setImageResource(R.drawable.goi);
        }

        return view;
    }

    //tạo viewholer lưu trữ component
    public class Viewholder{
        ImageView img_custommenu_HinhHang;
        TextView txt_custommenu_TenHang, txt_custommenu_GiaTien,txt_custommenu_TinhTrang;

    }
}
