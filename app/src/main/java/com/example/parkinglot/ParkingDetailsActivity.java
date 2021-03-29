package com.example.parkinglot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parkinglot.pojo.Parking;
import com.example.parkinglot.utils.KenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ParkingDetailsActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Parking parking;
    private TextView name;
    private TextView vacancy;   //空位?
    private TextView priceCaps;     //价格上限
    private TextView rates;     //利率
    private TextView address;
    private TextView distance;      //距离
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_details);
        actionBar = getSupportActionBar();
        actionBar.setTitle("停车场详情");

        name = findViewById(R.id.parking_details_name);
        vacancy = findViewById(R.id.parking_details_vacancy);
        priceCaps = findViewById(R.id.parking_details_priceCaps);
        rates = findViewById(R.id.parking_details_rates);
        address = findViewById(R.id.parking_details_address);
        distance = findViewById(R.id.parking_details_distance);
        imageView = findViewById(R.id.parking_details_img);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id",0);
        getParkingLot(id);


    }


    private void getParkingLot(int id){
      new Thread(new Runnable() {
          @Override
          public void run() {
              String url = "http://124.93.196.45:10002/userinfo/parklot/"+id;
              try {
                  String json = KenUtils.getJson(url);
                  JSONObject jsonObject = new JSONObject(json);
                  JSONObject object = jsonObject.getJSONObject("data");
                  int id = object.getInt("id");
                  String parkName = object.getString("parkName");     //停车场名称
                  String vacancy = object.getString("vacancy");       //空位
                  String priceCaps = object.getString("priceCaps");   //价格上限
                  String imgUrl = "http://124.93.196.45:10002"+object.getString("imgUrl");         //图片url地址
                  String rates = object.getString("rates");           //利率
                  String address = object.getString("address");       //停车厂地点
                  String distance = object.getString("distance");     //距离
                  String allPark = object.getString("allPark");       //停车位总数

                  parking = new Parking(id,parkName,vacancy,priceCaps,imgUrl,rates,address,distance,allPark);

                  Message message = new Message();
                  message.what  =1;
                  handler.sendMessage(message);

              } catch (IOException | JSONException e) {
                  e.printStackTrace();
              }
          }
      }).start();
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    Glide.with(ParkingDetailsActivity.this).load(parking.getImgUrl()).into(imageView);
                    name.setText(parking.getParkName());
                    vacancy.setText("剩余车位: "+parking.getVacancy());
                    address.setText("地址："+parking.getAddress());
                    rates.setText("费率："+parking.getRates());
                    distance.setText("距离："+parking.getDistance());
                    priceCaps.setText("当日最高收费: "+parking.getPriceCaps());

                    break;
                default:
                    break;
            }
        }
    };



}