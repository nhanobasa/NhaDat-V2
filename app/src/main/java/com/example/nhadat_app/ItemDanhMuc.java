package com.example.nhadat_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.nhadat_app.Adapter.ListAdapter;
import com.example.nhadat_app.Model.TinDang;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ItemDanhMuc extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp;
    private ImageButton btnBack;
    private EditText txt1, txt2;
    private Button btnLoc;
    private ListAdapter adapter;
    private RecyclerView re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_danh_muc);

        setID();
        setListener();
        receiverDate();
    }

    private void setID(){
        sp=findViewById(R.id.sp_itemkhuvuc);
        btnBack=findViewById(R.id.btnbackitem);
        txt1=findViewById(R.id.txt_gia1);
        txt2=findViewById(R.id.txt_gia);
        btnLoc=findViewById(R.id.btn_filter);
        re=findViewById(R.id.recycle_item);
    }

    private void setListener(){
        btnBack.setOnClickListener(this);
        btnLoc.setOnClickListener(this);
    }

    private void receiverDate(){
        Intent a=getIntent();
        try {
            String s=a.getStringExtra("dmuc");
            if(s.equalsIgnoreCase("muaban")==true){
                getData("Mua bán");
            }
            else if(s.equalsIgnoreCase("chothue")==true){
                getData("Cho thuê");
            }
            else if(s.equalsIgnoreCase("duan")==true){
                getData("Dự án");
            }

        }catch (NullPointerException e){}
    }

    private void getData(String s){

        ParseQuery<ParseObject> query=ParseQuery.getQuery("postin");
        query.whereEqualTo("danhmuc", s);
        query.whereEqualTo("tinhtrang", "duyệt");
        query.findInBackground(((objects, e) -> {
            if(e==null){
                viewData(objects);
            }
        }));
    }

    private void viewData(List<ParseObject> objects){
        ArrayList<TinDang> tinDangs=new ArrayList<>();
        for (ParseObject as:objects){
            tinDangs.add(new com.example.nhadat_app.Model.TinDang(as.getString("name"),
                    as.getString("danhmuc"), as.getString("tinh"),
                    as.getString("huyen"), as.getString("xa"),
                    Integer.parseInt(as.getString("dientich")),
                    Long.parseLong(as.getString("gia")), as.getString("phaply"),
                    as.getString("huongnha"), as.getString("tittle"),
                    as.getString("mota"), as.getInt("luotxem"),
                    Uri.parse(as.getString("img1")), Uri.parse(as.getString("img2")),
                    as.getString("timeUp")));
        }
        re.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ListAdapter(tinDangs, this);
        re.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void filterData(List<ParseObject> objects, String tinh, String gia1, String gia2){
        ArrayList<TinDang> td=new ArrayList<>();
        for (ParseObject as:objects){
            if(as.getString("tinhtrang").equalsIgnoreCase("duyệt")==true &&
            Long.parseLong(as.getString("gia"))>=Long.parseLong(gia1) &&
            Long.parseLong(as.getString("gia"))<=Long.parseLong(gia2) &&
            as.getString("tinh").equalsIgnoreCase(tinh)==true){
                td.add(new com.example.nhadat_app.Model.TinDang(as.getString("name"),
                        as.getString("danhmuc"), as.getString("tinh"),
                        as.getString("huyen"), as.getString("xa"),
                        Integer.parseInt(as.getString("dientich")),
                        Long.parseLong(as.getString("gia")), as.getString("phaply"),
                        as.getString("huongnha"), as.getString("tittle"),
                        as.getString("mota"), as.getInt("luotxem"),
                        Uri.parse(as.getString("img1")), Uri.parse(as.getString("img2")),
                        as.getString("timeUp")));
            }
        }
        re.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ListAdapter(td, this);
        re.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnbackitem:{
                finish();
                break;
            }
            case R.id.btn_filter:{
                String khuvuc=sp.getSelectedItem().toString();
                String gia1=txt1.getText().toString();
                String gia2=txt2.getText().toString();

                ParseQuery<ParseObject> query=ParseQuery.getQuery("postin");
                query.whereEqualTo("tinhtrang", "duyệt");
                query.findInBackground(((objects, e) -> {
                    if(e==null){
                        filterData(objects, khuvuc, gia1, gia2);
                    }
                }));
                break;
            }
        }
    }
}