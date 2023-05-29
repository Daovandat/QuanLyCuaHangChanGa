package com.example.qlcuahangchanga.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.qlcuahangchanga.DAO.KhachHangDAO;
import com.example.qlcuahangchanga.R;
import com.google.android.material.textfield.TextInputLayout;

public class EditTableActivity extends AppCompatActivity {
    TextInputLayout TXTL_editcustomer_tenkhach,txtl_editcustomer_diachi,txtl_editcustomer_sdt;
    Button BTN_editcustomer_SuaKhach;
    KhachHangDAO khachHangDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table);

        //thuộc tính view
        TXTL_editcustomer_tenkhach = (TextInputLayout) findViewById(R.id.txtl_editcustomer_tenkhach);
        txtl_editcustomer_diachi = (TextInputLayout) findViewById(R.id.txtl_editcustomer_diachi);
        txtl_editcustomer_sdt = (TextInputLayout) findViewById(R.id.txtl_editcustomer_sdt);
        BTN_editcustomer_SuaKhach  = (Button) findViewById(R.id.btn_editcustomer_SuaKhach);

        //khởi tạo dao mở kết nối csdl
        khachHangDAO = new KhachHangDAO(this);
        int makhach = getIntent().getIntExtra("makhach", 0); //lấy makhach

        BTN_editcustomer_SuaKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenkhach = TXTL_editcustomer_tenkhach.getEditText().getText().toString();
                String diachi = txtl_editcustomer_diachi.getEditText().getText().toString();
                String sdt = txtl_editcustomer_sdt.getEditText().getText().toString();

                if (tenkhach != null || tenkhach.equals("")) {
                    boolean ktra = khachHangDAO.CapNhatTenKhach(makhach, tenkhach,diachi,sdt);
                    Intent intent = new Intent();
                    intent.putExtra("ketquasua", ktra);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}