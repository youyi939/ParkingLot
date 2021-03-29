package com.example.parkinglot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.ClosedSubscriberGroupInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parkinglot.adpter.ParkingListViewAdapter;
import com.example.parkinglot.adpter.ParkingRecordAdapter;
import com.example.parkinglot.pojo.ParkingRecord;
import com.example.parkinglot.utils.KenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ParkingRecordActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private EditText editText;
    private Button find;
    private Button find_more;
    private ListView listView;
    private ParkingRecordAdapter adapter;
    private int pageNum = 1;
    private List<ParkingRecord> recordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_record);
        actionBar = getSupportActionBar();
        actionBar.setTitle("停车记录");
        find = findViewById(R.id.record_find);
        editText = findViewById(R.id.record_edit);
        listView = findViewById(R.id.record_listView);
        find_more = findViewById(R.id.record_btn);
        
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(editText.getText())){
                    Toast.makeText(ParkingRecordActivity.this,"输入不得为空",Toast.LENGTH_SHORT).show();
                    Log.i("Ken", "onClick: 输入不得为空！"+editText.getText());
                }else {
                    recordList.clear();
                    String time = editText.getText().toString();
                    String url = "http://124.93.196.45:10002/userinfo/parkrecord/list?pageNum=1&entryTime="+time;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String json = null;
                            try {
                                json = KenUtils.getJson(url);
                                JSONObject jsonObject = new JSONObject(json);
                                JSONArray jsonArray = jsonObject.getJSONArray("rows");
                                for (int i = 0; i < jsonArray.length() ; i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String outTime = object.getString("outTime");
                                    String entryTime = object.getString("entryTime");
                                    String plateNumber = object.getString("plateNumber");
                                    String monetary = object.getString("monetary");
                                    String parkName = object.getString("parkName");
                                    recordList.add(new ParkingRecord(outTime,entryTime,plateNumber,monetary,parkName));
                                }

                                Message message = new Message();
                                message.what = 2;
                                handler.sendMessage(message);
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }


            }
        });

        find_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://124.93.196.45:10002/userinfo/parkrecord/list?pageNum="+pageNum+"&pageSize=5";
                getNetWork(url);
                pageNum++;
            }
        });


        String url = "http://124.93.196.45:10002/userinfo/parkrecord/list?pageNum="+pageNum+"&pageSize=5";
        getNetWork(url);
        pageNum++;

    }

    private void getNetWork(String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtils.getJson(url);
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String outTime = object.getString("outTime");
                        String entryTime = object.getString("entryTime");
                        String plateNumber = object.getString("plateNumber");
                        String monetary = object.getString("monetary");
                        String parkName = object.getString("parkName");
                        recordList.add(new ParkingRecord(outTime,entryTime,plateNumber,monetary,parkName));
                    }
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                } catch (SocketException e1){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ParkingRecordActivity.this,"网络开小差了哦，请检查网络连接",Toast.LENGTH_LONG).show();
                        }
                    });

                    Log.i("Ken", "run: 网络开小差了");
                    e1.printStackTrace();
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
                    adapter = new ParkingRecordAdapter(ParkingRecordActivity.this,R.layout.parking_record,recordList);
                    listView.setAdapter(adapter);
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };



}





