package com.example.qlcuahangchanga.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcuahangchanga.DTO.LoaiHangDTO;
import com.example.qlcuahangchanga.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiHangDAO {
    SQLiteDatabase database;
    public LoaiHangDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiHang(LoaiHangDTO loaiHangDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIHANG_TENLOAI,loaiHangDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIHANG_HINHANH,loaiHangDTO.getHinhAnh());
        long ktra = database.insert(CreateDatabase.TBL_LOAIHANG,null,contentValues);

        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaLoaiHang(int maloai){
        long ktra = database.delete(CreateDatabase.TBL_LOAIHANG,CreateDatabase.TBL_LOAIHANG_MALOAI+ " = " +maloai
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaLoaiHang(LoaiHangDTO loaiHangDTO,int maloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIHANG_TENLOAI,loaiHangDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIHANG_HINHANH,loaiHangDTO.getHinhAnh());
        long ktra = database.update(CreateDatabase.TBL_LOAIHANG,contentValues
                ,CreateDatabase.TBL_LOAIHANG_MALOAI+" = "+maloai,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public List<LoaiHangDTO> LayDSLoaiHang(){
        List<LoaiHangDTO> loaiMonDTOList = new ArrayList<LoaiHangDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAIHANG;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiHangDTO loaiHangDTO = new LoaiHangDTO();
            loaiHangDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIHANG_MALOAI)));
            loaiHangDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIHANG_TENLOAI)));
            loaiHangDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAIHANG_HINHANH)));
            loaiMonDTOList.add(loaiHangDTO);

            cursor.moveToNext();
        }
        return loaiMonDTOList;
    }

    @SuppressLint("Range")
    public LoaiHangDTO LayLoaiHangTheoMa(int maloai){
        LoaiHangDTO loaiHangDTO = new LoaiHangDTO();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAIHANG+" WHERE "+CreateDatabase.TBL_LOAIHANG_MALOAI+" = "+maloai;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            loaiHangDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIHANG_MALOAI)));
            loaiHangDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIHANG_TENLOAI)));
            loaiHangDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAIHANG_HINHANH)));

            cursor.moveToNext();
        }
        return loaiHangDTO;
    }
}
