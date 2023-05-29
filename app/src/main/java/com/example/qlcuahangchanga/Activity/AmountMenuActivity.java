package com.example.qlcuahangchanga.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.qlcuahangchanga.DAO.ChiTietDonMuaDAO;
import com.example.qlcuahangchanga.DAO.DonMuaDAO;
import com.example.qlcuahangchanga.DTO.ChiTietDonMuaDTO;
import com.example.qlcuahangchanga.R;
import com.google.android.material.textfield.TextInputLayout;

public class AmountMenuActivity extends AppCompatActivity {

    TextInputLayout TXTL_amountmenu_SoLuong;
    Button BTN_amountmenu_DongY;
    int makhach, mahang;
    DonMuaDAO donMuaDAO;
    ChiTietDonMuaDAO chiTietDonMuaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_menu);

        //Lấy đối tượng view
        TXTL_amountmenu_SoLuong = (TextInputLayout)findViewById(R.id.txtl_amountmenu_SoLuong);
        BTN_amountmenu_DongY    = (Button)findViewById(R.id.btn_amountmenu_DongY);

        //khởi tạo kết nối csdl
        donMuaDAO = new DonMuaDAO(this);
        chiTietDonMuaDAO = new ChiTietDonMuaDAO(this);

        //Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        makhach = intent.getIntExtra("makhach",0);
        mahang = intent.getIntExtra("mahang",0);

        BTN_amountmenu_DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAmount()){
                    return;
                }

                int madonmua = (int) donMuaDAO.LayMaDonTheoMaKhach(makhach,"false");
                boolean ktra = chiTietDonMuaDAO.KiemTraHangTonTai(madonmua,mahang);
                if(ktra){
                    //update số lượng hàng đã chọn
                    int sluongcu = chiTietDonMuaDAO.LaySLHangTheoMaDon(madonmua,mahang);
                    int sluongmoi = Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString());
                    int tongsl = sluongcu + sluongmoi;

                    ChiTietDonMuaDTO chiTietDonMuaDTO = new ChiTietDonMuaDTO();
                    chiTietDonMuaDTO.setMaHang(mahang);
                    chiTietDonMuaDTO.setMaDonMua(madonmua);
                    chiTietDonMuaDTO.setSoLuong(tongsl);

                    boolean ktracapnhat = chiTietDonMuaDAO.CapNhatSL(chiTietDonMuaDTO);
                    if(ktracapnhat){
                        Toast.makeText(getApplicationContext(),("Thêm thành công!"),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),("Thêm thất bại!"),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //thêm số lượng hàng nếu chưa chọn hàng này
                    int sluong = Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString());
                    ChiTietDonMuaDTO chiTietDonMuaDTO = new ChiTietDonMuaDTO();
                    chiTietDonMuaDTO.setMaHang(mahang);
                    chiTietDonMuaDTO.setMaDonMua(madonmua);
                    chiTietDonMuaDTO.setSoLuong(sluong);

                    boolean ktracapnhat = chiTietDonMuaDAO.ThemChiTietDonMua(chiTietDonMuaDTO);
                    if(ktracapnhat){
                        Toast.makeText(getApplicationContext(),("Thêm thành công!"),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),("Thêm thất bại!"),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //validate số lượng
    private boolean validateAmount(){
        String val = TXTL_amountmenu_SoLuong.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_amountmenu_SoLuong.setError("Không được để trống!");
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_amountmenu_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        }else {
            TXTL_amountmenu_SoLuong.setError(null);
            TXTL_amountmenu_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}