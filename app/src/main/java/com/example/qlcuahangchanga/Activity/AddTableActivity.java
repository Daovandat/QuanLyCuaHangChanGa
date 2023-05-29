package com.example.qlcuahangchanga.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.qlcuahangchanga.DAO.KhachHangDAO;
import com.example.qlcuahangchanga.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddTableActivity extends AppCompatActivity {

    TextInputLayout TXTL_addtable_tenkhach,txtl_addtable_diachi,txtl_addtable_sdt;
    Button BTN_addtable_TaoKhach;
    KhachHangDAO khachHangDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);

        //region Lấy đối tượng trong view
        TXTL_addtable_tenkhach = (TextInputLayout)findViewById(R.id.txtl_addtable_tenkhach);
        txtl_addtable_diachi = (TextInputLayout)findViewById(R.id.txtl_addtable_diachi);
        txtl_addtable_sdt = (TextInputLayout)findViewById(R.id.txtl_addtable_sdt);
        BTN_addtable_TaoKhach  = (Button)findViewById(R.id.btn_addtable_TaoKhach);

        khachHangDAO = new KhachHangDAO(this);
        BTN_addtable_TaoKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTenKhach = TXTL_addtable_tenkhach.getEditText().getText().toString();
                String diachi = txtl_addtable_diachi.getEditText().getText().toString();
                String sdt = txtl_addtable_sdt.getEditText().getText().toString();
                if(sTenKhach != null || sTenKhach.equals("")){
                    boolean ktra = khachHangDAO.ThemKhachHang(sTenKhach,diachi,sdt);
                    //trả về result cho displaytable
                    Intent intent = new Intent();
                    intent.putExtra("ketquathem",ktra);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    //validate dữ liệu
    private boolean validateName(){
        String val = TXTL_addtable_tenkhach.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addtable_tenkhach.setError("Không được để trống");
            return false;
        }else {
            TXTL_addtable_tenkhach.setError(null);
            TXTL_addtable_tenkhach.setErrorEnabled(false);
            return true;
        }
    }
}