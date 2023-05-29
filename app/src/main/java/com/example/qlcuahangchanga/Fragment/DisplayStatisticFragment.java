package com.example.qlcuahangchanga.Fragment;

import static com.example.qlcuahangchanga.Activity.HomeActivity.toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.qlcuahangchanga.Activity.DetailStatisticActivity;
import com.example.qlcuahangchanga.Activity.HomeActivity;
import com.example.qlcuahangchanga.Adapter.AdapterDisplayStatistic;
import com.example.qlcuahangchanga.DAO.DonMuaDAO;
import com.example.qlcuahangchanga.DTO.DonMuaDTO;
import com.example.qlcuahangchanga.R;

import java.util.List;

public class DisplayStatisticFragment extends Fragment {
    ListView lvStatistic;
    List<DonMuaDTO> donMuaDTOS;
    DonMuaDAO donMuaDAO;
    AdapterDisplayStatistic adapterDisplayStatistic;
    FragmentManager fragmentManager;
    int madon, manv, makhach;
    String ngaymua, tongtien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaystatistic_layout,container,false);
        toolbar.setTitle("Quản lý thống kê");
        setHasOptionsMenu(true);

        lvStatistic = (ListView)view.findViewById(R.id.lvStatistic);
        donMuaDAO = new DonMuaDAO(getActivity());

        donMuaDTOS = donMuaDAO.LayDSDonMua();
        adapterDisplayStatistic = new AdapterDisplayStatistic(getActivity(),R.layout.custom_layout_displaystatistic,donMuaDTOS);
        lvStatistic.setAdapter(adapterDisplayStatistic);
        adapterDisplayStatistic.notifyDataSetChanged();

        lvStatistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madon    = donMuaDTOS.get(position).getMaDonMua();
                manv     = donMuaDTOS.get(position).getMaNV();
                makhach  = donMuaDTOS.get(position).getMaKhach();
                ngaymua  = donMuaDTOS.get(position).getNgayMua();
                tongtien = donMuaDTOS.get(position).getTongTien();

                Intent intent = new Intent(getActivity(), DetailStatisticActivity.class);
                intent.putExtra("madon",madon);
                intent.putExtra("manv",manv);
                intent.putExtra("makhach",makhach);
                intent.putExtra("ngaymua",ngaymua);
                intent.putExtra("tongtien",tongtien);
                startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }
}

