package com.example.qlcuahangchanga.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcuahangchanga.DTO.HangDTO;
import com.example.qlcuahangchanga.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class HangDAO {
    SQLiteDatabase database;
    public HangDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemHang(HangDTO hangDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_HANG_MALOAI,hangDTO.getMaLoai());
        contentValues.put(CreateDatabase.TBL_HANG_TENHANG,hangDTO.getTenHang());
        contentValues.put(CreateDatabase.TBL_HANG_GIATIEN,hangDTO.getGiaTien());
        contentValues.put(CreateDatabase.TBL_HANG_HINHANH,hangDTO.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_HANG_TINHTRANG,"true");

        long ktra = database.insert(CreateDatabase.TBL_HANG,null,contentValues);

        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaHang(int mahang){
        long ktra = database.delete(CreateDatabase.TBL_HANG,CreateDatabase.TBL_HANG_MAHANG+ " = " +mahang
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaHang(HangDTO hangDTO,int mahang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_HANG_MALOAI,hangDTO.getMaLoai());
        contentValues.put(CreateDatabase.TBL_HANG_TENHANG,hangDTO.getTenHang());
        contentValues.put(CreateDatabase.TBL_HANG_GIATIEN,hangDTO.getGiaTien());
        contentValues.put(CreateDatabase.TBL_HANG_HINHANH,hangDTO.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_HANG_TINHTRANG,hangDTO.getTinhTrang());

        long ktra = database.update(CreateDatabase.TBL_HANG,contentValues,
                CreateDatabase.TBL_HANG_MAHANG+" = "+mahang,null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public List<HangDTO> LayDSHangTheoLoai(int maloai){
        List<HangDTO> hangDTOList = new ArrayList<HangDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_HANG+ " WHERE " +CreateDatabase.TBL_HANG_MALOAI+ " = '" +maloai+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            HangDTO hangDTO = new HangDTO();
            hangDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_HANG_HINHANH)));
            hangDTO.setTenHang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_HANG_TENHANG)));
            hangDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_HANG_MALOAI)));
            hangDTO.setMaHang(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_HANG_MAHANG)));
            hangDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_HANG_GIATIEN)));
            hangDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_HANG_TINHTRANG)));
            hangDTOList.add(hangDTO);

            cursor.moveToNext();
        }
        return hangDTOList;
    }

    @SuppressLint("Range")
    public HangDTO LayHangTheoMa(int mahang){
        HangDTO hangDTO = new HangDTO();
        String query = "SELECT * FROM "+CreateDatabase.TBL_HANG+" WHERE "+CreateDatabase.TBL_HANG_MAHANG+" = "+mahang;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            hangDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_HANG_HINHANH)));
            hangDTO.setTenHang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_HANG_TENHANG)));
            hangDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_HANG_MALOAI)));
            hangDTO.setMaHang(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_HANG_MAHANG)));
            hangDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_HANG_GIATIEN)));
            hangDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_HANG_TINHTRANG)));

            cursor.moveToNext();
        }
        return hangDTO;
    }
}
