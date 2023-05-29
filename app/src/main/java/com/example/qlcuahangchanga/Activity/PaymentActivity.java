package com.example.qlcuahangchanga.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlcuahangchanga.Adapter.AdapterDisplayPayment;
import com.example.qlcuahangchanga.DAO.DonMuaDAO;
import com.example.qlcuahangchanga.DAO.KhachHangDAO;
import com.example.qlcuahangchanga.DAO.ThanhToanDAO;
import com.example.qlcuahangchanga.DTO.ThanhToanDTO;
import com.example.qlcuahangchanga.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView IMG_payment_backbtn;
    TextView TXT_payment_TenKhach, TXT_payment_NgayMua, TXT_payment_TongTien;
    Button BTN_payment_ThanhToan;
    GridView gvDisplayPayment;
    DonMuaDAO donMuaDAO;
    KhachHangDAO khachHangDAO;
    ThanhToanDAO thanhToanDAO;
    List<ThanhToanDTO> thanhToanDTOS;
    AdapterDisplayPayment adapterDisplayPayment;
    long tongtien = 0;
    int makhach, madonmua;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //region thuộc tính view
        gvDisplayPayment      = (GridView)findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn   = (ImageView)findViewById(R.id.img_payment_backbtn);
        TXT_payment_TenKhach  = (TextView)findViewById(R.id.txt_payment_TenKhach);
        TXT_payment_NgayMua   = (TextView)findViewById(R.id.txt_payment_NgayMua);
        TXT_payment_TongTien  = (TextView)findViewById(R.id.txt_payment_TongTien);
        BTN_payment_ThanhToan = (Button)findViewById(R.id.btn_payment_ThanhToan);
        //endregion

        //khởi tạo kết nối csdl
        donMuaDAO    = new DonMuaDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);
        khachHangDAO = new KhachHangDAO(this);

        fragmentManager = getSupportFragmentManager();

        //lấy data từ mã khách đc chọn
        Intent intent = getIntent();
        makhach = intent.getIntExtra("makhach",0);
        String tenkhach = intent.getStringExtra("tenkhach");
        String ngaymua = intent.getStringExtra("ngaymua");

        TXT_payment_TenKhach.setText(tenkhach);
        TXT_payment_NgayMua.setText(ngaymua);

        //ktra mã khách tồn tại thì hiển thị
        if(makhach !=0 ){
            HienThiThanhToan();

            for (int i=0;i<thanhToanDTOS.size();i++){
                int soluong = thanhToanDTOS.get(i).getSoLuong();
                int giatien = thanhToanDTOS.get(i).getGiaTien();

                tongtien += (soluong * giatien);
            }
            TXT_payment_TongTien.setText(String.valueOf(tongtien) +" VNĐ");
        }

        BTN_payment_ThanhToan.setOnClickListener(this);
        IMG_payment_backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_ThanhToan:
                boolean ktrakhach    = khachHangDAO.CapNhatTinhTrangKhach(makhach,"false");
                boolean ktradonmua   = donMuaDAO.UpdateTThaiDonTheoMaKhach(makhach,"true");
                boolean ktratongtien = donMuaDAO.UpdateTongTienDonMua(madonmua,String.valueOf(tongtien));
                if(ktrakhach && ktradonmua && ktratongtien){
                    HienThiThanhToan();
                    TXT_payment_TongTien.setText("0 VNĐ");
                    Toast.makeText(getApplicationContext(),"Thanh toán thành công!",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getApplicationContext(),"Lỗi thanh toán!",Toast.LENGTH_SHORT);
                }
                break;

            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }

    //hiển thị data lên gridview
    private void HienThiThanhToan(){
        madonmua = (int) donMuaDAO.LayMaDonTheoMaKhach(makhach,"false");
        thanhToanDTOS = thanhToanDAO.LayDSHangTheoMaDon(madonmua);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,thanhToanDTOS);
        gvDisplayPayment.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}