package cosmic.com.pkwprj.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cosmic.com.pkwprj.R;
import cosmic.com.pkwprj.adapter.ProgressAdapter;
import cosmic.com.pkwprj.model.Office;
import cosmic.com.pkwprj.model.OfficeList;

public class SecondActivity extends AppCompatActivity {

    final static String TAG = "Main";

    ProgressBar progressBar;

    OfficeList officeList;
    ArrayList<Office> list;

    RecyclerView recyclerView;
    String recomTime;

    HashMap<String, Integer> map;
    ArrayList<Drawable> drawables;

//    static Drawable d;

    static int convertedKey;
    TextView tv_office1, tv_office2, tv_office3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        map = new HashMap<>();
        drawables = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar3);

        tv_office1 = findViewById(R.id.tv_office1);
        tv_office2 = findViewById(R.id.tv_office2);
        tv_office3 = findViewById(R.id.tv_office3);


        recyclerView = findViewById(R.id.recyclerView_office);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//                String getTime=getCurrentTime();
        //임시 시간
        String getTime = "1100";
        avaibleTimeCheck(getTime);

        newgetJsonString(recomTime);//조정된 시간 넣어주기
        showOfficeTimeTable(recomTime);

    }

    private void showOfficeTimeTable(String recomTime) {
        for (int i = 0; i < list.size(); i++) {
            Office office = list.get(i);
            String name = office.getName();
            String location = office.getLocation();
            ArrayList<Office.Reservations> reservationList = office.getReservations();

            for (int j = 0; j < reservationList.size(); j++) {
                Office.Reservations reservationStaus = reservationList.get(j);

                String startTime = reservationStaus.getStartTime();

                int compare1 = Integer.valueOf(startTime);
                int compare2 = Integer.valueOf(recomTime);
                if (compare1 >= compare2) {

                    if (compare1 == compare2) {
                        nonShowTopOfficeList(name);
                    }
                    String endTime = reservationStaus.getEndTime();

                    Log.d(TAG, "name->" + name);
                    Log.d(TAG, "location->" + location);
                    Log.d(TAG, "reser start to end=" + startTime + "~" + endTime);

                    int a = processConvert1(startTime);
                    int b = processConvert2(endTime);

                    MakeMapData(a, b, name);
                }
            }
        }

    }

    private void nonShowTopOfficeList(String officename) {
        Log.d(TAG, "현재가능하지않는  오피스이름:" + officename);

        switch (officename) {
            case "대회의실1":
                tv_office1.setVisibility(View.GONE);
                break;
            case "회의실2":
                tv_office2.setVisibility(View.GONE);
                break;

            case "회의실3":
                tv_office3.setVisibility(View.GONE);
                break;
        }

    }


    private void avaibleTimeCheck(String time) {
        //15분 단위 처리
        String hourTime = time.substring(0, 2);
        String minuteTime = time.substring(2);
//        Log.d( TAG,"분단위->"+minuteTime );
        int mTime = Integer.valueOf(minuteTime);

        // 시간 편입시키기
        if (mTime < 29) {
            // x 시 00분에 맞추기
//            Log.i( TAG,"00분에 맞춤" );
            String preMtime = "00";
            recomTime = hourTime.concat(preMtime);
//            Log.d( TAG,"조정된 시간:"+recomTime );

        } else if (30 <= mTime && mTime < 59) {
            // x 시 30분에 맞추기
//            Log.i( TAG,"30분에 맞춤" );
            String preMtime = "30";
            recomTime = hourTime.concat(preMtime);
//            Log.d( TAG,"조정된 시간:"+recomTime );
        }

        //int 변환
        int TimeConvertToInt = Integer.valueOf(time);
        if (TimeConvertToInt < 900) {
//            Log.d( TAG,"아직개업전" );
        } else if (900 <= TimeConvertToInt && TimeConvertToInt <= 1800) {
//            Log.d( TAG,"영업중" );
        } else {
//            Log.d( TAG,"영업마감" );
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        long cTime = System.currentTimeMillis();
        String time = dateFormat.format(cTime);
        Log.d(TAG, "현재시간:" + time);

        return time;

    }

    private List newgetJsonString(String time) {

        list = new ArrayList<>();

        try {
            InputStream is = getAssets().open("MUSINSA.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            officeList = gson.fromJson(json, OfficeList.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        int curPoint = processConvert1(time);
        Log.d(TAG, "커포인트: " + curPoint);// 맞게 가져옴.


        if (officeList != null) {
            for (int i = 0; i < officeList.musinsa.size(); i++) {

                //여기서 두 시간 포인트가 일치하지않으면
                Office office = officeList.musinsa.get(i);
                String name = office.getName();
                String location = office.getLocation();
                ArrayList<Office.Reservations> reservations = office.getReservations();

                //드로블 호출하면 들어올거같다
                Drawable d = new ProgressDrawable(map, name);


                list.add(new Office(name, location, reservations, d)); //여기서 두 시간 포인트가 일치하지않으면 list에 못담
            }
        }

        return list;
    }

    private int processConvert2(String et) {
        int code2 = 0;

        switch (et) {
            case "0900":
                code2 = 0;
                break;
            case "0930":
                code2 = 1;
                break;
            case "1000":
                code2 = 2;
                break;
            case "1030":
                code2 = 3;
                break;
            case "1100":
                code2 = 4;
                break;
            case "1130":
                code2 = 5;
                break;
            case "1200":
                code2 = 6;
                break;
            case "1230":
                code2 = 7;
                break;
            case "1300":
                code2 = 8;
                break;
            case "1330":
                code2 = 9;
                break;
            case "1400":
                code2 = 10;
                break;
            case "1430":
                code2 = 11;
                break;
            case "1500":
                code2 = 12;
                break;
            case "1530":
                code2 = 13;
                break;
            case "1600":
                code2 = 14;
                break;
            case "1630":
                code2 = 15;
                break;
            case "1700":
                code2 = 16;
                break;
            case "1730":
                code2 = 17;
                break;
            case "1800":
                code2 = 18;
                break;
        }
        return code2;

    }

    private int processConvert1(String st) {

        Log.d(TAG, "넘어온st: " + st); //1220이넘어오니 안됨.
        int code1 = 0;

        switch (st) {
            case "0900":
                code1 = 0;
                break;
            case "0930":
                code1 = 1;
                break;
            case "1000":
                code1 = 2;
                break;
            case "1030":
                code1 = 3;
                break;
            case "1100":
                code1 = 4;
                break;
            case "1130":
                code1 = 5;
                break;
            case "1200":
                code1 = 6;
                break;
            case "1230":
                code1 = 7;
                break;
            case "1300":
                code1 = 8;
                break;
            case "1330":
                code1 = 9;
                break;
            case "1400":
                code1 = 10;
                break;
            case "1430":
                code1 = 11;
                break;
            case "1500":
                code1 = 12;
                break;
            case "1530":
                code1 = 13;
                break;
            case "1600":
                code1 = 14;
                break;
            case "1630":
                code1 = 15;
                break;
            case "1700":
                code1 = 16;
                break;
            case "1730":
                code1 = 17;
                break;
        }
        return code1;
    }


    private void MakeMapData(int startPoint, int endPoint, String officeName) {
        Log.d(TAG, "들어온 a&b=>" + officeName + ": " + startPoint + ", " + endPoint);
        String inputKey;
        for (int l = 0; l < list.size(); l++) {
            if (endPoint - startPoint == 1) {
                inputKey = l + officeName;
                map.put(inputKey, startPoint);
            } else if (endPoint - startPoint > 1) {
                for (int i = startPoint; i < endPoint; i++) {
                    inputKey = i + officeName;
                    map.put(inputKey, i);

                }
            }

        }

        showAvailableOffice();
        ProgressAdapter adapter = new ProgressAdapter(this, list);
        recyclerView.setAdapter(adapter);

    }


    private void showAvailableOffice() {

        //예약된시간이 담긴.
        Log.d(TAG, "현재 시간num:: " + recomTime);
        convertedKey = processConvert1(recomTime);
        Log.d(TAG, "변환된시간코드:" + convertedKey);

    }
}
