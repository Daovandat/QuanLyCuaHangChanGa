package com.example.qlcuahangchanga.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.qlcuahangchanga.Adapter.AdapterDisplayPayment;
import com.example.qlcuahangchanga.DAO.KhachHangDAO;
import com.example.qlcuahangchanga.DAO.NhanVienDAO;
import com.example.qlcuahangchanga.DAO.ThanhToanDAO;
import com.example.qlcuahangchanga.DTO.NhanVienDTO;
import com.example.qlcuahangchanga.DTO.ThanhToanDTO;
import com.example.qlcuahangchanga.R;

import java.util.List;

public class DetailStatisticActivity extends AppCompatActivity {

    ImageView img_detailstatistic_backbtn;
    TextView txt_detailstatistic_MaDon,txt_detailstatistic_NgayMua,txt_detailstatistic_TenKhach
            ,txt_detailstatistic_TenNV,txt_detailstatistic_TongTien;
    GridView gvDetailStatistic;
    int madon, manv, makhach;
    String ngaymua, tongtien;
    NhanVienDAO nhanVienDAO;
    KhachHangDAO khachHangDAO;
    List<ThanhToanDTO> thanhToanDTOList;
    ThanhToanDAO thanhToanDAO;
    AdapterDisplayPayment adapterDisplayPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_statistic);

        //Lấy thông tin từ display statistic
        Intent intent = getIntent();
        madon    = intent.getIntExtra("madon",0);
        manv     = intent.getIntExtra("manv",0);
        makhach  = intent.getIntExtra("makhach",0);
        ngaymua  = intent.getStringExtra("ngaymua");
        tongtien = intent.getStringExtra("tongtien");

        //region Thuộc tính bên view
        img_detailstatistic_backbtn  = (ImageView)findViewById(R.id.img_detailstatistic_backbtn);
        txt_detailstatistic_MaDon    = (TextView)findViewById(R.id.txt_detailstatistic_MaDon);
        txt_detailstatistic_NgayMua  = (TextView)findViewById(R.id.txt_detailstatistic_NgayMua);
        txt_detailstatistic_TenKhach = (TextView)findViewById(R.id.txt_detailstatistic_TenKhach);
        txt_detailstatistic_TenNV    = (TextView)findViewById(R.id.txt_detailstatistic_TenNV);
        txt_detailstatistic_TongTien = (TextView)findViewById(R.id.txt_detailstatistic_TongTien);
        gvDetailStatistic            = (GridView)findViewById(R.id.gvDetailStatistic);
        //endregion

        //khởi tạo lớp dao mở kết nối csdl
        nhanVienDAO  = new NhanVienDAO(this);
        khachHangDAO = new KhachHangDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);

        //chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (madon !=0){
            txt_detailstatistic_MaDon.setText("Mã đơn: "+madon);
            txt_detailstatistic_NgayMua.setText(ngaymua);
            txt_detailstatistic_TongTien.setText(tongtien+" VNĐ");

            NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(manv);
            txt_detailstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
            txt_detailstatistic_TenKhach.setText(khachHangDAO.LayTenKhachTheoMa(makhach));

            HienThiDSCTDD();
        }

        img_detailstatistic_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void HienThiDSCTDD(){
        thanhToanDTOList = thanhToanDAO.LayDSHangTheoMaDon(madon);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,thanhToanDTOList);
        gvDetailStatistic.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}