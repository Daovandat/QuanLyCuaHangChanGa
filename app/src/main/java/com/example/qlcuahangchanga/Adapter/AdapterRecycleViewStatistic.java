package com.example.qlcuahangchanga.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlcuahangchanga.DAO.KhachHangDAO;
import com.example.qlcuahangchanga.DAO.NhanVienDAO;
import com.example.qlcuahangchanga.DTO.DonMuaDTO;
import com.example.qlcuahangchanga.DTO.NhanVienDTO;
import com.example.qlcuahangchanga.R;

import java.util.List;

public class AdapterRecycleViewStatistic extends RecyclerView.Adapter<AdapterRecycleViewStatistic.ViewHolder> {
    Context context;
    int layout;
    List<DonMuaDTO> donMuaDTOList;
    NhanVienDAO nhanVienDAO;
    KhachHangDAO khachHangDAO;

    public AdapterRecycleViewStatistic(Context context, int layout, List<DonMuaDTO> donMuaDTOList){

        this.context =context;
        this.layout = layout;
        this.donMuaDTOList = donMuaDTOList;
        nhanVienDAO = new NhanVienDAO(context);
        khachHangDAO = new KhachHangDAO(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewStatistic.ViewHolder holder, int position) {
        DonMuaDTO donMuaDTO = donMuaDTOList.get(position);
        holder.txt_customstatistic_MaDon.setText("Mã đơn: "+donMuaDTO.getMaDonMua());
        holder.txt_customstatistic_NgayMua.setText(donMuaDTO.getNgayMua());
        if(donMuaDTO.getTongTien().equals("0"))
        {
            holder.txt_customstatistic_TongTien.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_customstatistic_TongTien.setVisibility(View.VISIBLE);
        }

        if (donMuaDTO.getTinhTrang().equals("true"))
        {
            holder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            holder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(donMuaDTO.getMaNV());
        holder.txt_customstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
        holder.txt_customstatistic_KhachMua.setText(khachHangDAO.LayTenKhachTheoMa(donMuaDTO.getMaKhach()));
    }

    @Override
    public int getItemCount() {
        return donMuaDTOList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayMua, txt_customstatistic_TenNV,
                txt_customstatistic_KhachMua, txt_customstatistic_TongTien,txt_customstatistic_TrangThai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customstatistic_MaDon     = itemView.findViewById(R.id.txt_customstatistic_MaDon);
            txt_customstatistic_NgayMua   = itemView.findViewById(R.id.txt_customstatistic_NgayMua);
            txt_customstatistic_TenNV     = itemView.findViewById(R.id.txt_customstatistic_TenNV);
            txt_customstatistic_KhachMua  = itemView.findViewById(R.id.txt_customstatistic_KhachMua);
            txt_customstatistic_TongTien  = itemView.findViewById(R.id.txt_customstatistic_TongTien);
            txt_customstatistic_TrangThai = itemView.findViewById(R.id.txt_customstatistic_TrangThai);
        }
    }
}

