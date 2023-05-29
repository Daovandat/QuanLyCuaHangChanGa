package com.example.qlcuahangchanga.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcuahangchanga.DTO.ThanhToanDTO;
import com.example.qlcuahangchanga.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class ThanhToanDAO {
    SQLiteDatabase database;
    public ThanhToanDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    @SuppressLint("Range")
    public List<ThanhToanDTO> LayDSHangTheoMaDon(int madonmua){
        List<ThanhToanDTO> thanhToanDTOS = new ArrayList<ThanhToanDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_CHITIETDONMUA+" ctdm,"+CreateDatabase.TBL_HANG+" hang WHERE "
                +"ctdm."+CreateDatabase.TBL_CHITIETDONMUA_MAHANG+" = hang."+CreateDatabase.TBL_HANG_MAHANG+" AND "
                +CreateDatabase.TBL_CHITIETDONMUA_MADONMUA+" = '"+madonmua+"'";

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhToanDTO thanhToanDTO = new ThanhToanDTO();
            thanhToanDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_CHITIETDONMUA_SOLUONG)));
            thanhToanDTO.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_HANG_GIATIEN)));
            thanhToanDTO.setTenHang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_HANG_TENHANG)));
            thanhToanDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_HANG_HINHANH)));
            thanhToanDTOS.add(thanhToanDTO);

            cursor.moveToNext();
        }

        return thanhToanDTOS;
    }
}
