package com.example.qlcuahangchanga.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class CreateDatabase extends SQLiteOpenHelper {
        public static String TBL_NHANVIEN = "NHANVIEN";
        public static String TBL_HANG = "HANG";
        public static String TBL_LOAIHANG = "LOAIHANG";
        public static String TBL_KHACH = "KHACH";
        public static String TBL_DONMUA = "DONMUA";
        public static String TBL_CHITIETDONMUA = "CHITIETDONMUA";
        public static String TBL_QUYEN = "QUYEN";

        //Bảng nhân viên
        public static String TBL_NHANVIEN_MANV = "MANV";
        public static String TBL_NHANVIEN_HOTENNV = "HOTENNV";
        public static String TBL_NHANVIEN_TENDN = "TENDN";
        public static String TBL_NHANVIEN_MATKHAU = "MATKHAU";
        public static String TBL_NHANVIEN_EMAIL = "EMAIL";
        public static String TBL_NHANVIEN_SDT = "SDT";
        public static String TBL_NHANVIEN_GIOITINH = "GIOITINH";
        public static String TBL_NHANVIEN_NGAYSINH = "NGAYSINH";
        public static String TBL_NHANVIEN_MAQUYEN= "MAQUYEN";

        //Bảng quyền
        public static String TBL_QUYEN_MAQUYEN = "MAQUYEN";
        public static String TBL_QUYEN_TENQUYEN = "TENQUYEN";

        //Bảng mặt hàng
        public static String TBL_HANG_MAHANG = "MAHANG";
        public static String TBL_HANG_TENHANG = "TENHANG";
        public static String TBL_HANG_GIATIEN = "GIATIEN";
        public static String TBL_HANG_TINHTRANG = "TINHTRANG";
        public static String TBL_HANG_HINHANH = "HINHANH";
        public static String TBL_HANG_MALOAI = "MALOAI";

        //Bảng loại hàng
        public static String TBL_LOAIHANG_MALOAI = "MALOAI";
        public static String TBL_LOAIHANG_TENLOAI = "TENLOAI";
        public static String TBL_LOAIHANG_HINHANH = "HINHANH";

        //Bảng khách hàng
        public static String TBL_KHACH_MAKHACH = "MAKHACH";
        public static String TBL_KHACH_TENKHACH = "TENKHACH";
        public static String TBL_KHACH_DIACHI = "DIACHI";
        public static String TBL_KHACH_SDT = "SDT";
        public static String TBL_KHACH_TINHTRANG = "TINHTRANG";

        //Bảng đơn đặt
        public static String TBL_DONMUA_MADONMUA = "MADONMUA";
        public static String TBL_DONMUA_MANV = "MANV";
        public static String TBL_DONMUA_NGAYMUA = "NGAYMUA";
        public static String TBL_DONMUA_TINHTRANG = "TINHTRANG";
        public static String TBL_DONMUA_TONGTIEN = "TONGTIEN";
        public static String TBL_DONMUA_MAKHACH = "MAKHACH";

        //Bảng chi tiết đơn đặt
        public static String TBL_CHITIETDONMUA_MADONMUA = "MADONMUA";
        public static String TBL_CHITIETDONMUA_MAHANG = "MAHANG";
        public static String TBL_CHITIETDONMUA_SOLUONG = "SOLUONG";


        public CreateDatabase(Context context) {
            super(context, "OrderDrink", null, 1);
        }

        //thực hiện tạo bảng
        @Override
        public void onCreate(SQLiteDatabase db) {
            String tblNHANVIEN = "CREATE TABLE " +TBL_NHANVIEN+ " ( " +TBL_NHANVIEN_MANV+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +TBL_NHANVIEN_HOTENNV+ " TEXT, " +TBL_NHANVIEN_TENDN+ " TEXT, " +TBL_NHANVIEN_MATKHAU+ " TEXT, " +TBL_NHANVIEN_EMAIL+ " TEXT, "
                    +TBL_NHANVIEN_SDT+ " TEXT, " +TBL_NHANVIEN_GIOITINH+ " TEXT, " +TBL_NHANVIEN_NGAYSINH+ " TEXT , "+TBL_NHANVIEN_MAQUYEN+" INTEGER)";

            String tblQUYEN = "CREATE TABLE " +TBL_QUYEN+ " ( " +TBL_QUYEN_MAQUYEN+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +TBL_QUYEN_TENQUYEN+ " TEXT)" ;

            String tblKHACH = "CREATE TABLE " +TBL_KHACH+ " ( " +TBL_KHACH_MAKHACH+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +TBL_KHACH_TENKHACH+ " TEXT, " +TBL_KHACH_DIACHI+ " TEXT, " +TBL_KHACH_SDT+ " TEXT, " +TBL_KHACH_TINHTRANG+ " TEXT )";

            String tblHANG = "CREATE TABLE " +TBL_HANG+ " ( " +TBL_HANG_MAHANG+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +TBL_HANG_TENHANG+ " TEXT, " +TBL_HANG_GIATIEN+ " TEXT, " +TBL_HANG_TINHTRANG+ " TEXT, "
                    +TBL_HANG_HINHANH+ " BLOB, "+TBL_HANG_MALOAI+ " INTEGER )";

            String tblLOAIHANG = "CREATE TABLE " +TBL_LOAIHANG+ " ( " +TBL_LOAIHANG_MALOAI+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +TBL_LOAIHANG_HINHANH+ " BLOB, " +TBL_LOAIHANG_TENLOAI+ " TEXT)" ;

            String tblDONMUA = "CREATE TABLE " +TBL_DONMUA+ " ( " +TBL_DONMUA_MADONMUA+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +TBL_DONMUA_MAKHACH+ " INTEGER, " +TBL_DONMUA_MANV+ " INTEGER, " +TBL_DONMUA_NGAYMUA+ " TEXT, "+TBL_DONMUA_TONGTIEN+" TEXT,"
                    +TBL_DONMUA_TINHTRANG+ " TEXT )" ;

            String tblCHITIETDONMUA = "CREATE TABLE " +TBL_CHITIETDONMUA+ " ( " +TBL_CHITIETDONMUA_MADONMUA+ " INTEGER, "
                    +TBL_CHITIETDONMUA_MAHANG+ " INTEGER, " +TBL_CHITIETDONMUA_SOLUONG+ " INTEGER, "
                    + " PRIMARY KEY ( " +TBL_CHITIETDONMUA_MADONMUA+ "," +TBL_CHITIETDONMUA_MAHANG+ "))";

            db.execSQL(tblNHANVIEN);
            db.execSQL(tblQUYEN);
            db.execSQL(tblKHACH);
            db.execSQL(tblHANG);
            db.execSQL(tblLOAIHANG);
            db.execSQL(tblDONMUA);
            db.execSQL(tblCHITIETDONMUA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        //mở kết nối csdl
        public SQLiteDatabase open(){
            return this.getWritableDatabase();
        }
}
