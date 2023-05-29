package com.example.qlcuahangchanga.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qlcuahangchanga.Activity.HomeActivity;
import com.example.qlcuahangchanga.Activity.PaymentActivity;
import com.example.qlcuahangchanga.DAO.DonMuaDAO;
import com.example.qlcuahangchanga.DAO.KhachHangDAO;
import com.example.qlcuahangchanga.DTO.DonMuaDTO;
import com.example.qlcuahangchanga.DTO.KhachHangDTO;
import com.example.qlcuahangchanga.Fragment.DisplayCategoryFragment;
import com.example.qlcuahangchanga.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterDisplayTable extends BaseAdapter implements View.OnClickListener {
    Context context;
    int layout;
    List<KhachHangDTO> khachHangDTOList;
    ViewHolder viewHolder;
    KhachHangDAO khachHangDAO;
    DonMuaDAO donMuaDAO;
    FragmentManager fragmentManager;

    public AdapterDisplayTable(Context context, int layout, List<KhachHangDTO> khachHangDTOList){
        this.context = context;
        this.layout = layout;
        this.khachHangDTOList = khachHangDTOList;
        khachHangDAO = new KhachHangDAO(context);
        donMuaDAO = new DonMuaDAO(context);
        fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return khachHangDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return khachHangDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return khachHangDTOList.get(position).getMaKhach();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout,parent,false);

            viewHolder.imgKhachHang    = (ImageView) view.findViewById(R.id.img_customtable_KhachHang);
            viewHolder.imgMuaHang      = (ImageView) view.findViewById(R.id.img_customtable_MuaHang);
            viewHolder.imgThanhToan    = (ImageView) view.findViewById(R.id.img_customtable_ThanhToan);
            viewHolder.imgAnNut        = (ImageView) view.findViewById(R.id.img_customtable_AnNut);
            viewHolder.txtTenKhachHang = (TextView)view.findViewById(R.id.txt_customtable_TenKhachHang);
            viewHolder.txt_customtable_diachi = (TextView)view.findViewById(R.id.txt_customtable_diachi);
            viewHolder.txt_customtable_sdt = (TextView)view.findViewById(R.id.txt_customtable_sdt);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(khachHangDTOList.get(position).isDuocChon()){
            HienThiButton();
        }else {
            AnButton();
        }

        KhachHangDTO khachHangDTO = khachHangDTOList.get(position);

        String kttinhtrang = khachHangDAO.LayTinhTrangKhachTheoMa(khachHangDTO.getMaKhach());
        //đổi hình theo tình trạng
        if(kttinhtrang.equals("true")){
            viewHolder.imgKhachHang.setImageResource(R.drawable.ic_baseline_event_seat_40);
        }else {
            viewHolder.imgKhachHang.setImageResource(R.drawable.ic_baseline_event_seat_40);
        }

        viewHolder.txtTenKhachHang.setText(khachHangDTO.getTenKhach());
        viewHolder.txt_customtable_diachi.setText(khachHangDTO.getDiachi());
        viewHolder.txt_customtable_sdt.setText(khachHangDTO.getSdt());

        viewHolder.imgKhachHang.setTag(position);

        //sự kiện click
        viewHolder.imgKhachHang.setOnClickListener(this);
        viewHolder.imgMuaHang.setOnClickListener(this);
        viewHolder.imgThanhToan.setOnClickListener(this);
        viewHolder.imgAnNut.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag();

        int vitri1 = (int) viewHolder.imgKhachHang.getTag();

        int makhach = khachHangDTOList.get(vitri1).getMaKhach();
        String tenkhach = khachHangDTOList.get(vitri1).getTenKhach();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaymua= dateFormat.format(calendar.getTime());

        switch (id){
            case R.id.img_customtable_KhachHang:
                int vitri = (int)v.getTag();
                khachHangDTOList.get(vitri).setDuocChon(true);
                HienThiButton();
                break;

            case R.id.img_customtable_AnNut:
                AnButton();
                break;

            case R.id.img_customtable_MuaHang:
                Intent getIHome = ((HomeActivity)context).getIntent();
                int manv = getIHome.getIntExtra("manv",0);
                String tinhtrang = khachHangDAO.LayTinhTrangKhachTheoMa(makhach);

                if(tinhtrang.equals("false")){
                    //Thêm bảng mua hàng và update tình trạng khách
                    DonMuaDTO donMuaDTO = new DonMuaDTO();
                    donMuaDTO.setMaKhach(makhach);
                    donMuaDTO.setMaNV(manv);
                    donMuaDTO.setNgayMua(ngaymua);
                    donMuaDTO.setTinhTrang("false");
                    donMuaDTO.setTongTien("0");

                    long ktra = donMuaDAO.ThemDonMua(donMuaDTO);
                    khachHangDAO.CapNhatTinhTrangKhach(makhach,"true");
                    if(ktra == 0){ Toast.makeText(context,("Thêm thành công!"),Toast.LENGTH_SHORT).show(); }
                }
                //chuyển qua trang category
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();

                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("makhach",makhach);
                displayCategoryFragment.setArguments(bDataCategory);

                transaction.replace(R.id.contentView,displayCategoryFragment).addToBackStack("hienthikhachmua");
                transaction.commit();
                break;

            case R.id.img_customtable_ThanhToan:
                //chuyển dữ liệu qua trang thanh toán
                Intent iThanhToan = new Intent(context, PaymentActivity.class);
                iThanhToan.putExtra("makhach",makhach);
                iThanhToan.putExtra("tenkhach",tenkhach);
                iThanhToan.putExtra("ngaymua",ngaymua);
                context.startActivity(iThanhToan);
                break;
        }
    }

    private void HienThiButton(){
        viewHolder.imgMuaHang.setVisibility(View.VISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolder.imgAnNut.setVisibility(View.VISIBLE);
    }
    private void AnButton(){
        viewHolder.imgMuaHang.setVisibility(View.INVISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolder.imgAnNut.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder{
        ImageView imgKhachHang, imgMuaHang, imgThanhToan, imgAnNut;
        TextView txtTenKhachHang,txt_customtable_diachi,txt_customtable_sdt;
    }
}
