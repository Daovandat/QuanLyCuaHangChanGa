package com.example.qlcuahangchanga.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcuahangchanga.DTO.DonMuaDTO;
import com.example.qlcuahangchanga.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DonMuaDAO {
    SQLiteDatabase database;

    public DonMuaDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemDonMua(DonMuaDTO donMuaDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONMUA_MAKHACH, donMuaDTO.getMaKhach());
        contentValues.put(CreateDatabase.TBL_DONMUA_MANV, donMuaDTO.getMaNV());
        contentValues.put(CreateDatabase.TBL_DONMUA_NGAYMUA, donMuaDTO.getNgayMua());
        contentValues.put(CreateDatabase.TBL_DONMUA_TINHTRANG, donMuaDTO.getTinhTrang());
        contentValues.put(CreateDatabase.TBL_DONMUA_TONGTIEN, donMuaDTO.getTongTien());

        long madonmua = database.insert(CreateDatabase.TBL_DONMUA, null, contentValues);

        return madonmua;
    }

    @SuppressLint("Range")
    public List<DonMuaDTO> LayDSDonMua() {
        List<DonMuaDTO> donMuaDTOS = new ArrayList<DonMuaDTO>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_DONMUA;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DonMuaDTO donMuaDTO = new DonMuaDTO();
            donMuaDTO.setMaDonMua(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_MADONMUA)));
            donMuaDTO.setMaKhach(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_MAKHACH)));
            donMuaDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_TONGTIEN)));
            donMuaDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_TINHTRANG)));
            donMuaDTO.setNgayMua(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_NGAYMUA)));
            donMuaDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_MANV)));
            donMuaDTOS.add(donMuaDTO);

            cursor.moveToNext();
        }
        return donMuaDTOS;
    }

    @SuppressLint("Range")
    public List<DonMuaDTO> LayDSDonMuaNgay(String ngaythang) {
        List<DonMuaDTO> donMuaDTOS = new ArrayList<DonMuaDTO>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_DONMUA + " WHERE " + CreateDatabase.TBL_DONMUA_NGAYMUA + " like '" + ngaythang + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DonMuaDTO donMuaDTO = new DonMuaDTO();
            donMuaDTO.setMaDonMua(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_MADONMUA)));
            donMuaDTO.setMaKhach(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_MAKHACH)));
            donMuaDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_TONGTIEN)));
            donMuaDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_TINHTRANG)));
            donMuaDTO.setNgayMua(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_NGAYMUA)));
            donMuaDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_MANV)));
            donMuaDTOS.add(donMuaDTO);

            cursor.moveToNext();
        }
        return donMuaDTOS;
    }

    @SuppressLint("Range")
    public long LayMaDonTheoMaKhach(int makhach, String tinhtrang) {
        String query = "SELECT * FROM " + CreateDatabase.TBL_DONMUA + " WHERE " + CreateDatabase.TBL_DONMUA_MAKHACH + " = '" + makhach + "' AND "
                + CreateDatabase.TBL_DONMUA_TINHTRANG + " = '" + tinhtrang + "' ";
        long mamuahang = 0;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mamuahang = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONMUA_MADONMUA));

            cursor.moveToNext();
        }
        return mamuahang;
    }

    public boolean UpdateTongTienDonMua(int madonmua, String tongtien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONMUA_TONGTIEN, tongtien);
        long ktra = database.update(CreateDatabase.TBL_DONMUA, contentValues,
                CreateDatabase.TBL_DONMUA_MADONMUA + " = " + madonmua, null);
        if (ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean UpdateTThaiDonTheoMaKhach(int makhach, String tinhtrang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONMUA_TINHTRANG, tinhtrang);
        long ktra = database.update(CreateDatabase.TBL_DONMUA, contentValues, CreateDatabase.TBL_DONMUA_MAKHACH +
                " = '" + makhach + "'", null);
        if (ktra != 0) {
            return true;
        } else {
            return false;
        }
    }
}
