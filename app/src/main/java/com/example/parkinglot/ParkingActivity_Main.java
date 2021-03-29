package com.example.parkinglot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parkinglot.adpter.ParkingListViewAdapter;
import com.example.parkinglot.pojo.Parking;
import com.example.parkinglot.utils.KenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParkingActivity_Main extends AppCompatActivity {

    private ListView listView_parking;
    private ParkingListViewAdapter adapter;
    private Button test;
    private int pageNum = 1;
    private List<Parking>parkingList = new ArrayList<>();
    private ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.parking_menu:
                Intent intent = new Intent(ParkingActivity_Main.this,ParkingRecordActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        listView_parking = findViewById(R.id.parking_listView_main);
        test = findViewById(R.id.main_findmore);


        actionBar.setTitle("停车场功能模块");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParkingList(pageNum);
                pageNum++;
            }
        });

        listView_parking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ParkingActivity_Main.this,ParkingDetailsActivity.class);
                intent.putExtra("id",parkingList.get(i).getId());
                startActivity(intent);
            }
        });

        getParkingList(pageNum);
        pageNum++;


    }


    public void getParkingList(int pageNum){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://124.93.196.45:10002/userinfo/parklot/list?pageNum="+pageNum+"&pageSize=5";
                    String json = KenUtils.getJson(url);
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int id = object.getInt("id");
                        String parkName = object.getString("parkName");     //停车场名称
                        String vacancy = object.getString("vacancy");       //空位
                        String priceCaps = object.getString("priceCaps");   //价格上限
                        String imgUrl = object.getString("imgUrl");         //图片url地址
                        String rates = object.getString("rates");           //利率
                        String address = object.getString("address");       //停车厂地点
                        String distance = object.getString("distance");     //距离
                        String allPark = object.getString("allPark");       //停车位总数
                        parkingList.add(new Parking(id,parkName,vacancy,priceCaps,imgUrl,rates,address,distance,allPark));
                    }

                    //根据距离进行排序
                    parkingList.sort(new Comparator<Parking>() {
                        @Override
                        public int compare(Parking parking, Parking t1) {
                            int a =  Integer.parseInt(parking.getDistance());
                            int b =  Integer.parseInt(t1.getDistance());
                            return a-b;
                        }
                    });


                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    /**
     * 1：发请求，渲染listView
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    adapter = new ParkingListViewAdapter(ParkingActivity_Main.this,R.layout.parking_item,parkingList);
                    listView_parking.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };


}