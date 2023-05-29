package com.example.qlcuahangchanga.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlcuahangchanga.DAO.HangDAO;
import com.example.qlcuahangchanga.DTO.HangDTO;
import com.example.qlcuahangchanga.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button BTN_addmenu_ThemHang;
    RelativeLayout layout_trangthaihang;
    ImageView IMG_addmenu_ThemHinh, IMG_addmenu_back;
    TextView TXT_addmenu_title;
    TextInputLayout TXTL_addmenu_TenHang,TXTL_addmenu_GiaTien,TXTL_addmenu_LoaiHang;
    RadioGroup RG_addmenu_TinhTrang;
    RadioButton RD_addmenu_ConHang, RD_addmenu_HetHang;
    HangDAO hangDAO;
    String tenloai, sTenHang,sGiaTien,sTinhTrang;
    Bitmap bitmapold;
    int maloai;
    int mahang = 0;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addmenu_ThemHinh.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        //region Lấy đối tượng view
        IMG_addmenu_ThemHinh  = (ImageView)findViewById(R.id.img_addmenu_ThemHinh);
        IMG_addmenu_ThemHinh.setTag(R.drawable.grocery2);
        IMG_addmenu_back      = (ImageView)findViewById(R.id.img_addmenu_back);
        TXTL_addmenu_TenHang  = (TextInputLayout)findViewById(R.id.txtl_addmenu_TenHang);
        TXTL_addmenu_GiaTien  = (TextInputLayout)findViewById(R.id.txtl_addmenu_GiaTien);
        TXTL_addmenu_LoaiHang = (TextInputLayout)findViewById(R.id.txtl_addmenu_LoaiHang);
        BTN_addmenu_ThemHang  = (Button)findViewById(R.id.btn_addmenu_ThemHang);
        TXT_addmenu_title     = (TextView)findViewById(R.id.txt_addmenu_title);
        layout_trangthaihang  = (RelativeLayout)findViewById(R.id.layout_trangthaihang);
        RG_addmenu_TinhTrang  = (RadioGroup)findViewById(R.id.rg_addmenu_TinhTrang);
        RD_addmenu_ConHang    = (RadioButton)findViewById(R.id.rd_addmenu_ConHang);
        RD_addmenu_HetHang    = (RadioButton)findViewById(R.id.rd_addmenu_HetHang);
        //endregion

        Intent intent = getIntent();
        maloai = intent.getIntExtra("maLoai",-1);
        tenloai = intent.getStringExtra("tenLoai");
        hangDAO = new HangDAO(this);  //khởi tạo đối tượng dao kết nối csdl
        TXTL_addmenu_LoaiHang.getEditText().setText(tenloai);

        BitmapDrawable olddrawable = (BitmapDrawable)IMG_addmenu_ThemHinh.getDrawable();
        bitmapold = olddrawable.getBitmap();

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        mahang = getIntent().getIntExtra("mahang",0);
        if(mahang != 0){
            TXT_addmenu_title.setText("Sửa danh sách hàng");
            HangDTO hangDTO = hangDAO.LayHangTheoMa(mahang);

            TXTL_addmenu_TenHang.getEditText().setText(hangDTO.getTenHang());
            TXTL_addmenu_GiaTien.getEditText().setText(hangDTO.getGiaTien());

            byte[] menuimage = hangDTO.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            IMG_addmenu_ThemHinh.setImageBitmap(bitmap);

            layout_trangthaihang.setVisibility(View.VISIBLE);
            String tinhtrang = hangDTO.getTinhTrang();
            if(tinhtrang.equals("true")){
                RD_addmenu_ConHang.setChecked(true);
            }else {
                RD_addmenu_HetHang.setChecked(true);
            }

            BTN_addmenu_ThemHang.setText("Sửa hàng");
        }

        //endregion

        IMG_addmenu_ThemHinh.setOnClickListener(this);

        BTN_addmenu_ThemHang.setOnClickListener(this);
        IMG_addmenu_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        switch (id){
            case R.id.img_addmenu_ThemHinh:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,"Nhập hình ảnh"));
                break;

            case R.id.img_addmenu_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;

            case R.id.btn_addmenu_ThemHang:
                //ktra validation
                if(!validateImage() | !validateName() | !validatePrice()){
                    return;
                }

                sTenHang = TXTL_addmenu_TenHang.getEditText().getText().toString();
                sGiaTien = TXTL_addmenu_GiaTien.getEditText().getText().toString();
                switch (RG_addmenu_TinhTrang.getCheckedRadioButtonId()){
                    case R.id.rd_addmenu_ConHang: sTinhTrang = "true";   break;
                    case R.id.rd_addmenu_HetHang: sTinhTrang = "false";  break;
                }

                HangDTO hangDTO = new HangDTO();
                hangDTO.setMaLoai(maloai);
                hangDTO.setTenHang(sTenHang);
                hangDTO.setGiaTien(sGiaTien);
                hangDTO.setTinhTrang(sTinhTrang);
                hangDTO.setHinhAnh(imageViewtoByte(IMG_addmenu_ThemHinh));
                if(mahang!= 0){
                    ktra = hangDAO.SuaHang(hangDTO,mahang);
                    chucnang = "suahang";
                }else {
                    ktra = hangDAO.ThemHang(hangDTO);
                    chucnang = "themhang";
                }

                //Thêm, sửa hàng dựa theo obj loaihangDTO
                Intent intent = new Intent();
                intent.putExtra("ktra",ktra);
                intent.putExtra("chucnang",chucnang);
                setResult(RESULT_OK,intent);
                finish();

                break;
        }
    }

    //Chuyển ảnh bitmap về mảng byte lưu vào csdl
    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //region Validate field
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)IMG_addmenu_ThemHinh.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = TXTL_addmenu_TenHang.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addmenu_TenHang.setError("Không được để trống!");
            return false;
        }else {
            TXTL_addmenu_TenHang.setError(null);
            TXTL_addmenu_TenHang.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePrice(){
        String val = TXTL_addmenu_GiaTien.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addmenu_GiaTien.setError("Giá tiền không hợp lệ");
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_addmenu_GiaTien.setError("Giá tiền không hợp lệ");
            return false;
        }else {
            TXTL_addmenu_GiaTien.setError(null);
            TXTL_addmenu_GiaTien.setErrorEnabled(false);
            return true;
        }
    }
}
//endregion

