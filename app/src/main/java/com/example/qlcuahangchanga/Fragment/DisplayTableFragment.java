package com.example.qlcuahangchanga.Fragment;

import static com.example.qlcuahangchanga.Activity.HomeActivity.toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.qlcuahangchanga.Activity.AddTableActivity;
import com.example.qlcuahangchanga.Activity.EditTableActivity;
import com.example.qlcuahangchanga.Activity.HomeActivity;
import com.example.qlcuahangchanga.Adapter.AdapterDisplayTable;
import com.example.qlcuahangchanga.DAO.KhachHangDAO;
import com.example.qlcuahangchanga.DTO.KhachHangDTO;
import com.example.qlcuahangchanga.R;

import java.util.List;

public class DisplayTableFragment extends Fragment {
    GridView GVDisplayTable;
    List<KhachHangDTO> khachHangDTOList;
    KhachHangDAO khachHangDAO;
    AdapterDisplayTable adapterDisplayTable;

    //Dùng activity result để nhận data gửi từ activity addtable
    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ketquathem",false);
                        if(ktra){
                            HienThiDSKhach();
                            Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> resultLauncherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ketquasua",false);
                        if(ktra){
                            HienThiDSKhach();
                            Toast.makeText(getActivity(),"Sửa thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),"Lỗi khi sửa",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaytable_layout,container,false);
        setHasOptionsMenu(true);
        toolbar.setTitle("Quản lý khách");
        GVDisplayTable = (GridView)view.findViewById(R.id.gvDisplayTable);
        khachHangDAO = new KhachHangDAO(getActivity());

        HienThiDSKhach();

        registerForContextMenu(GVDisplayTable);
        return view;
    }

    //tạo ra context menu khi longclick
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //Xử lí cho từng trường hợp trong contextmenu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int makhach = khachHangDTOList.get(vitri).getMaKhach();
        switch(id){
            case R.id.itEdit:
                Intent intent = new Intent(getActivity(), EditTableActivity.class);
                intent.putExtra("makhach",makhach);
                resultLauncherEdit.launch(intent);
                break;

            case R.id.itDelete:
                boolean ktraxoa = khachHangDAO.XoaKhachTheoMa(makhach);
                if(ktraxoa){
                    HienThiDSKhach();
                    Toast.makeText(getActivity(),"Xóa thành công!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"Lỗi khi xóa!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddTable = menu.add(1,R.id.itAddCustomer,1,"Thêm khách hàng");
        itAddTable.setIcon(R.drawable.ic_baseline_add_24);
        itAddTable.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.itAddCustomer:
                Intent iAddTable = new Intent(getActivity(), AddTableActivity.class);
                resultLauncherAdd.launch(iAddTable);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapterDisplayTable.notifyDataSetChanged();
    }

    private void HienThiDSKhach(){
        khachHangDTOList = khachHangDAO.LayTatCaKhachHang();
        adapterDisplayTable = new AdapterDisplayTable(getActivity(),R.layout.custom_layout_displaytable,khachHangDTOList);
        GVDisplayTable.setAdapter(adapterDisplayTable);
        adapterDisplayTable.notifyDataSetChanged();
    }
}
