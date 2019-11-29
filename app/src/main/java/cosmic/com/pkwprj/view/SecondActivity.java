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
import cosmic.com.pkwprj.contract.SecondContract;
import cosmic.com.pkwprj.model.Office;
import cosmic.com.pkwprj.model.OfficeList;
import cosmic.com.pkwprj.presenter.SecondPresenter;

public class SecondActivity extends AppCompatActivity implements SecondContract.View {

    final static String TAG = "Main";

    ProgressBar progressBar;

    OfficeList officeList;
    ArrayList<Office> list;

    RecyclerView recyclerView;
    String adjustTime;

    HashMap<String, Integer> map;
    ArrayList<Drawable> drawables;

    SecondPresenter secondPresenter;
    static int convertedKey;
    TextView tv_office1, tv_office2, tv_office3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        secondPresenter = new SecondPresenter(this);

        map = new HashMap<>();
        drawables = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar3);

        tv_office1 = findViewById(R.id.tv_office1);
        tv_office2 = findViewById(R.id.tv_office2);
        tv_office3 = findViewById(R.id.tv_office3);


        recyclerView = findViewById(R.id.recyclerView_office);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        String getTime = getCurrentTime();
        //임시 시간
        String getTime = "1100";
        avaibleTimeCheck(getTime);

        newgetJsonString(adjustTime);//조정된 시간 넣어주기
        showOfficeTimeTable(adjustTime);

    }

    private void showOfficeTimeTable(String adjustTime) {
        for (int i = 0; i < list.size(); i++) {
            Office office = list.get(i);
            String name = office.getName();
            String location = office.getLocation();
            ArrayList<Office.Reservations> reservationList = office.getReservations();

            for (int j = 0; j < reservationList.size(); j++) {
                Office.Reservations reservationStaus = reservationList.get(j);

                String startTime = reservationStaus.getStartTime();

                int compare1 = Integer.valueOf(startTime);
                int compare2 = Integer.valueOf(adjustTime);
                if (compare1 >= compare2) {

                    if (compare1 == compare2) {
                        nonShowTopOfficeList(name);
                    }
                    String endTime = reservationStaus.getEndTime();

                    Log.d(TAG, "name->" + name);
                    Log.d(TAG, "location->" + location);
                    Log.d(TAG, "reser start to end=" + startTime + "~" + endTime);

//                    int a = processConvert1(startTime);
//                    int b = processConvert2(endTime);
                    int a = secondPresenter.processConvert1(startTime);
                    int b = secondPresenter.processConvert2(endTime);
                    MakeMapData(a, b, name);
                }
            }
        }

    }

    private void nonShowTopOfficeList(String officename) { //이안에서 현재 시간 아닌거 필터링해주면
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
            adjustTime = hourTime.concat(preMtime);
//            Log.d( TAG,"조정된 시간:"+adjustTime );

        } else if (30 <= mTime && mTime < 59) {
            // x 시 30분에 맞추기
//            Log.i( TAG,"30분에 맞춤" );
            String preMtime = "30";
            adjustTime = hourTime.concat(preMtime);
//            Log.d( TAG,"조정된 시간:"+adjustTime );
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

        if (officeList != null) {
            for (int i = 0; i < officeList.getMusinsa().size(); i++) {

                Office office = officeList.getMusinsa().get(i);
                String name = office.getName();
                String location = office.getLocation();
                ArrayList<Office.Reservations> reservations = office.getReservations();
                Drawable d = new ProgressDrawable(map, name);
                list.add(new Office(name, location, reservations, d)); //여기서 두 시간 포인트가 일치하지않으면 list에 못담
            }
        }

        return list;
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
        showAvailableOfficeCode();
        showList();
    }


    private void showAvailableOfficeCode() {

        Log.d(TAG, "현재 시간num:: " + adjustTime);
        convertedKey = secondPresenter.processConvert1(adjustTime);
        Log.d(TAG, "변환된시간코드:" + convertedKey);
    }

    private void showList() {
        ProgressAdapter adapter = new ProgressAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}
