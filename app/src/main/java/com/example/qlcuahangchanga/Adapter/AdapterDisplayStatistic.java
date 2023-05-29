package com.example.qlcuahangchanga.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qlcuahangchanga.DAO.KhachHangDAO;
import com.example.qlcuahangchanga.DAO.NhanVienDAO;
import com.example.qlcuahangchanga.DTO.DonMuaDTO;
import com.example.qlcuahangchanga.DTO.NhanVienDTO;
import com.example.qlcuahangchanga.R;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {
    Context context;
    int layout;
    List<DonMuaDTO> donMuaDTOS;
    ViewHolder viewHolder;
    NhanVienDAO nhanVienDAO;
    KhachHangDAO khachHangDAO;

    public AdapterDisplayStatistic(Context context, int layout, List<DonMuaDTO> donMuaDTOS){
        this.context = context;
        this.layout = layout;
        this.donMuaDTOS = donMuaDTOS;
        nhanVienDAO = new NhanVienDAO(context);
        khachHangDAO = new KhachHangDAO(context);
    }

    @Override
    public int getCount() {
        return donMuaDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return donMuaDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return donMuaDTOS.get(position).getMaDonMua();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_MaDon     = (TextView)view.findViewById(R.id.txt_customstatistic_MaDon);
            viewHolder.txt_customstatistic_NgayMua   = (TextView)view.findViewById(R.id.txt_customstatistic_NgayMua);
            viewHolder.txt_customstatistic_TenNV     = (TextView)view.findViewById(R.id.txt_customstatistic_TenNV);
            viewHolder.txt_customstatistic_TongTien  = (TextView)view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = (TextView)view.findViewById(R.id.txt_customstatistic_TrangThai);
            viewHolder.txt_customstatistic_KhachMua  = (TextView)view.findViewById(R.id.txt_customstatistic_KhachMua);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DonMuaDTO donMuaDTO = donMuaDTOS.get(position);

        viewHolder.txt_customstatistic_MaDon.setText("Mã đơn: "+donMuaDTO.getMaDonMua());
        viewHolder.txt_customstatistic_NgayMua.setText(donMuaDTO.getNgayMua());
        viewHolder.txt_customstatistic_TongTien.setText(donMuaDTO.getTongTien()+" VNĐ");
        if (donMuaDTO.getTinhTrang().equals("true"))
        {
            viewHolder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            viewHolder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(donMuaDTO.getMaNV());
        viewHolder.txt_customstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
        viewHolder.txt_customstatistic_KhachMua.setText(khachHangDAO.LayTenKhachTheoMa(donMuaDTO.getMaKhach()));

        return view;
    }
    public class ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayMua, txt_customstatistic_TenNV
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_KhachMua;

    }
}
