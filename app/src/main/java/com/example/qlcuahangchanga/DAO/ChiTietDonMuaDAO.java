package com.example.qlcuahangchanga.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcuahangchanga.DTO.ChiTietDonMuaDTO;
import com.example.qlcuahangchanga.Database.CreateDatabase;

public class ChiTietDonMuaDAO {
    SQLiteDatabase database;
    public ChiTietDonMuaDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean KiemTraHangTonTai(int madonmua, int mahang){
        String query = "SELECT * FROM " +CreateDatabase.TBL_CHITIETDONMUA+ " WHERE " +CreateDatabase.TBL_CHITIETDONMUA_MAHANG+
                " = " +mahang+ " AND " +CreateDatabase.TBL_CHITIETDONMUA_MADONMUA+ " = "+madonmua;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public int LaySLHangTheoMaDon(int madonmua, int mahang){
        int soluong = 0;
        String query = "SELECT * FROM " +CreateDatabase.TBL_CHITIETDONMUA+ " WHERE " +CreateDatabase.TBL_CHITIETDONMUA_MAHANG+
                " = " +mahang+ " AND " +CreateDatabase.TBL_CHITIETDONMUA_MADONMUA+ " = "+madonmua;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_CHITIETDONMUA_SOLUONG));
            cursor.moveToNext();
        }
        return soluong;
    }

    public boolean CapNhatSL(ChiTietDonMuaDTO chiTietDonMuaDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONMUA_SOLUONG, chiTietDonMuaDTO.getSoLuong());

        long ktra = database.update(CreateDatabase.TBL_CHITIETDONMUA,contentValues,CreateDatabase.TBL_CHITIETDONMUA_MADONMUA+ " = "
                +chiTietDonMuaDTO.getMaDonMua()+ " AND " +CreateDatabase.TBL_CHITIETDONMUA_MAHANG+ " = "
                +chiTietDonMuaDTO.getMaHang(),null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ThemChiTietDonMua(ChiTietDonMuaDTO chiTietDonMuaDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONMUA_SOLUONG,chiTietDonMuaDTO.getSoLuong());
        contentValues.put(CreateDatabase.TBL_CHITIETDONMUA_MADONMUA,chiTietDonMuaDTO.getMaDonMua());
        contentValues.put(CreateDatabase.TBL_CHITIETDONMUA_MAHANG,chiTietDonMuaDTO.getMaHang());

        long ktra = database.insert(CreateDatabase.TBL_CHITIETDONMUA,null,contentValues);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

}
