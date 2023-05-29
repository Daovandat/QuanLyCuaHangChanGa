package com.example.qlcuahangchanga.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcuahangchanga.DTO.KhachHangDTO;
import com.example.qlcuahangchanga.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    SQLiteDatabase database;
    public KhachHangDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    //Hàm thêm khách hàng mới
    public boolean ThemKhachHang(String tenkhach,String diachi,String sdt){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_KHACH_TENKHACH,tenkhach);
        contentValues.put(CreateDatabase.TBL_KHACH_DIACHI,diachi);
        contentValues.put(CreateDatabase.TBL_KHACH_SDT,sdt);
        contentValues.put(CreateDatabase.TBL_KHACH_TINHTRANG,"false");

        long ktra = database.insert(CreateDatabase.TBL_KHACH,null,contentValues);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm xóa khách hàng theo mã
    public boolean XoaKhachTheoMa(int makhach){
        long ktra =database.delete(CreateDatabase.TBL_KHACH,CreateDatabase.TBL_KHACH_MAKHACH+" = "+makhach,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Sửa tên bàn
    public boolean CapNhatTenKhach(int makhach, String tenkhach,String diachi,String sdt){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_KHACH_TENKHACH,tenkhach);
        contentValues.put(CreateDatabase.TBL_KHACH_DIACHI,diachi);
        contentValues.put(CreateDatabase.TBL_KHACH_SDT,sdt);
        long ktra = database.update(CreateDatabase.TBL_KHACH,contentValues,CreateDatabase.TBL_KHACH_MAKHACH+ " = '"+makhach+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm lấy ds các bàn ăn đổ vào gridview
    @SuppressLint("Range")
    public List<KhachHangDTO> LayTatCaKhachHang(){
        List<KhachHangDTO> khachHangDTOListh = new ArrayList<KhachHangDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_KHACH;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            khachHangDTO.setMaKhach(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_KHACH_MAKHACH)));
            khachHangDTO.setTenKhach(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACH_TENKHACH)));
            khachHangDTO.setDiachi(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACH_DIACHI)));
            khachHangDTO.setSdt(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACH_SDT)));

            khachHangDTOListh.add(khachHangDTO);
            cursor.moveToNext();
        }
        return khachHangDTOListh;
    }

    @SuppressLint("Range")
    public String LayTinhTrangKhachTheoMa(int makhach){
        String tinhtrang="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_KHACH + " WHERE " +CreateDatabase.TBL_KHACH_MAKHACH+ " = '" +makhach+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_HANG_TINHTRANG));
            cursor.moveToNext();
        }

        return tinhtrang;
    }

    public boolean CapNhatTinhTrangKhach(int makhach, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_KHACH_TINHTRANG,tinhtrang);

        long ktra = database.update(CreateDatabase.TBL_KHACH,contentValues,CreateDatabase.TBL_KHACH_MAKHACH+ " = '"+makhach+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public String LayTenKhachTheoMa(int makhach){
        String tenkhach="";
        String query = "SELECT * FROM "+ CreateDatabase.TBL_KHACH + " WHERE " +CreateDatabase.TBL_KHACH_MAKHACH+ " = '" +makhach+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tenkhach = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACH_TENKHACH));
            cursor.moveToNext();
        }

        return tenkhach;
    }
}
