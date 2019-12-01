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
    public static int convertedKey;
    TextView tv_office1, tv_office2, tv_office3;
    TextView tv_move;


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

        tv_move =findViewById(R.id.tv_move);

        recyclerView = findViewById(R.id.recyclerView_office);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String getTime = getCurrentTime();

//        String getTime = "1739";     //test시간
        avaibleTimeCheck(getTime);

//        list=secondPresenter.newgetJsonString(adjustTime);//조정된 시간 넣어주기
        newgetJsonString();//조정된 시간 넣어주기
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

                if (compare1 >= compare2||compare1==1700&&compare2<1800) {

                    if (compare1 == compare2 ) {
                        nonShowTopOfficeList(name);
                    }else if(compare1==1700&&compare2==1730){
                        nonShowTopOfficeList(name);
                    }
                    String endTime = reservationStaus.getEndTime();

                    int a = secondPresenter.processConvert1(startTime);
                    int b = secondPresenter.processConvert2(endTime);
                    MakeMapData(a, b, name);
                }
                else if(compare2>=1800){
                    tv_office1.setVisibility(View.GONE);
                    tv_office2.setVisibility(View.GONE);
                    tv_office3.setVisibility(View.GONE);
                }
            }
        }

    }

    private void nonShowTopOfficeList(String officename) {

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

        String hourTime = time.substring(0, 2);
        String minuteTime = time.substring(2);
        int mTime = Integer.valueOf(minuteTime);

        if (mTime < 30) {
            String preMtime = "00";
            adjustTime = hourTime.concat(preMtime);

        } else if (30 <= mTime && mTime <= 59) {
            String preMtime = "30";
            adjustTime = hourTime.concat(preMtime);

        }

        int TimeConvertToInt = Integer.valueOf(time);
        if (TimeConvertToInt < 900) {
            Log.d( TAG,"아직개업전" );
        } else if (900 <= TimeConvertToInt && TimeConvertToInt < 1800) {
            Log.d( TAG,"영업중" );
        } else {
            Log.d( TAG,"영업마감" );
        }

    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        long cTime = System.currentTimeMillis();
        String time = dateFormat.format(cTime);

        return time;

    }

    private List newgetJsonString() {

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
                list.add(new Office(name, location, reservations, d));
            }
        }

        return list;
    }


    private void MakeMapData(int startPoint, int endPoint, String officeName) {
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

        convertedKey = secondPresenter.processConvert1(adjustTime);

    }

    private void showList() {

        ProgressAdapter adapter = new ProgressAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}
