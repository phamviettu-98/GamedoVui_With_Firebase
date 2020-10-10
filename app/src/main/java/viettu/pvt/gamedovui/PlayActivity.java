package viettu.pvt.gamedovui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    ImageView btnback;
    TextView tvLife, tvTime, tvCauHoi, tvDiemHt, tvDiemmax;
    TextView tvCauA, tvCauB, tvCauC, tvCauD;
    Button btnCauTiepTheo;
    private int life = 2;
    String name="";
    private int dem = 0;
    private int diemmax = 0 ;
    CountDownTimer Timer;
    private long time = 180000;
    private long mTimeRemaining;
    boolean run = true;
    Cauhoi cauhoiCurrent;
    ArrayList<Integer> caudahoi = new ArrayList<>();
    ArrayList<Cauhoi> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor  editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        btnback = findViewById(R.id.back);
        tvLife = findViewById(R.id.life);
        tvTime = findViewById(R.id.time);
        tvCauHoi = findViewById(R.id.cauhoi);
        tvDiemHt = findViewById(R.id.diemht);
        tvDiemmax = findViewById(R.id.diemmax);
        tvCauA = findViewById(R.id.dapanA);
        tvCauB = findViewById(R.id.dapanB);
        tvCauC = findViewById(R.id.dapanC);
        tvCauD = findViewById(R.id.dapanD);
        btnCauTiepTheo = findViewById(R.id.btntiep);
         sharedPreferences = getSharedPreferences("diemso", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getDiembyName();
      diemmax =  sharedPreferences.getInt("diemmax",0);


        countTimer();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogExit();
            }
        });
        addListener();
        initCauhoi();

    }

    public void countTimer(){
         Timer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeRemaining = millisUntilFinished;
                int second =(int) (millisUntilFinished /1000)/60;
                int mini = (int) (millisUntilFinished /1000)%60;
                String mininues="";
                if (mini < 10)  mininues ="0"+mini;
                else mininues= mini+"";
                tvTime.setText("0"+second+":"+mininues);
            }

            @Override
            public void onFinish() {
                tvTime.setText("00:00");
                showDiemSo();
            }

        }.start();

    }

    private void addListener(){

        tvCauA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cauhoiCurrent!=null){
                    if(cauhoiCurrent.getDapan().equals(cauhoiCurrent.getA())){
                        resetLevel(true);
                    }
                    else resetLevel(false);
                }
            }
        });
        tvCauB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cauhoiCurrent!=null){
                    if(cauhoiCurrent.getDapan().equals(cauhoiCurrent.getB())){
                        resetLevel(true);
                    }
                    else resetLevel(false);
                }
            }
        });
        tvCauC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cauhoiCurrent!=null){
                    if(cauhoiCurrent.getDapan().equals(cauhoiCurrent.getC())){
                        resetLevel(true);
                    }
                    else resetLevel(false);
                }
            }
        });
        tvCauD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cauhoiCurrent!=null){
                    if(cauhoiCurrent.getDapan().equals(cauhoiCurrent.getD())){
                        resetLevel(true);
                    }
                    else resetLevel(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Timer == null){
             Timer = new CountDownTimer(mTimeRemaining, 1000) {
                 @Override
                 public void onTick(long millisUntilFinished) {
                     mTimeRemaining = millisUntilFinished;
                     int second =(int) (millisUntilFinished /1000)/60;
                     int mini = (int) (millisUntilFinished /1000)%60;
                     String mininues="";
                     if (mini < 10)  mininues ="0"+mini;
                     else mininues= mini+"";
                     tvTime.setText("0"+second+":"+mininues);
                 }

                 @Override
                 public void onFinish() {
                   tvTime.setText("00:00");
                     showDiemSo();
                 }
             }.start();
        }
    }





    @Override
    public void onBackPressed() {
        showDialogExit();
    }
    private void showDiemSo(){
        final Dialog dia = new Dialog(this);
        dia.setContentView(R.layout.finish_time_life);
        TextView tvDiem = dia.findViewById(R.id.diemso);
        Button btnok = dia.findViewById(R.id.end_ok);

        tvDiem.setText(dem+ " ");
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("diemmax", diemmax);
                editor.commit();
                updateDiem();
                finish();
            }
        });
        dia.show();
    }
    private void showDialogExit() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_exit);
        Button ok = dialog.findViewById(R.id.dialog_ok);
        Button cancel = dialog.findViewById(R.id.dilog_cancel);
        Timer.cancel();
        Timer = null;
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("diemmax", diemmax);
                editor.commit();
                updateDiem();
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (Timer == null){
                    Timer = new CountDownTimer(mTimeRemaining, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mTimeRemaining = millisUntilFinished;
                            int second =(int) (millisUntilFinished /1000)/60;
                            int mini = (int) (millisUntilFinished /1000)%60;
                            String mininues="";
                            if (mini < 10)  mininues ="0"+mini;
                            else mininues= mini+"";
                            tvTime.setText("0"+second+":"+mininues);
                        }

                        @Override
                        public void onFinish() {
                            tvTime.setText("00:00");
                            showDiemSo();
                        }
                    }.start();
                }
            }
        });
        dialog.show();
    }
    private void resetLevel(boolean dung){
        if(dung){
            dem++;
            if ( dem > diemmax) {
                diemmax = dem;
                tvDiemmax.setText(diemmax+"");
            }
            tvDiemHt.setText(""+dem);
            setClicked(false);
            showGiaithich();
        }
        else{
            life--;
            tvLife.setText(life+"");
            if (life == 0) {
                setClicked(false);
                showDiemSo();
            }
        }


    }
    private  void updateDiem(){
        Intent intent = getIntent();
         name = intent.getStringExtra("name");
        Map<String, Object> user1 = new HashMap<>();
        user1.put("name",name);
        user1.put("diem", diemmax);
        db.collection("User").document(name)
                .update(user1);
    }
    private  void getDiembyName(){
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful()){
                            for (QueryDocumentSnapshot dc : task.getResult()) {

                                String ten = (String) dc.get("name");
                                if (ten.equals(name)){
                                    Long diem1 = (Long) dc.get("diem");
                                    int diem2 = diem1.intValue();
                                    diemmax = diem2;
                                    tvDiemmax.setText(diem2+"");

                                    break;
                                }
                            }

                        }
                    }
                });
    }



    private void initCauhoi(){
        db.collection("CauHoi")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot dc: task.getResult()){
                                String cauhoi = (String) dc.get("cauhoi");
                                String a = (String) dc.get("a");
                                String b = (String) dc.get("b");
                                String c = (String) dc.get("c");
                                String d = (String) dc.get("d");
                                String dapan = (String) dc.get("dapan");
                                String giaidap = (String) dc.get("giaidap");
                                Cauhoi ch = new Cauhoi(cauhoi,a,b,c,d,dapan,giaidap);
                                list.add(ch);
                            }

                            playGame();
                        }
                        else Log.d("hihi", "onComplete: fail");
                    }
                });
    }

    public void playGame(){
        setClicked(true);
        Random random = new Random();
        int x;
        while(true){
            x = random.nextInt(60);
            if(caudahoi.contains(x)){
                continue;
            }
            Cauhoi c = list.get(x);
            caudahoi.add(x);
            cauhoiCurrent= c;
            tvCauHoi.setText(c.getCauhoi());
            tvCauA.setText(c.getA());
            tvCauB.setText(c.getB());
            tvCauC.setText(c.getC());
            tvCauD.setText(c.getD());
            break;
        }
    }
    private void showGiaithich(){
        Cauhoi c = cauhoiCurrent;

        btnCauTiepTheo.setVisibility(View.VISIBLE);

        String giaithich = "Đáp án là: ";
        switch (c.getDapan()){
            case "a": {
                giaithich = c.getA() + "\n";
                break;
            }
            case "b": {
                giaithich = c.getB() + "\n";
                break;
            }
            case "c": {
                giaithich = c.getC() + "\n";
                break;
            }
            case "d": {
                giaithich = c.getD() + "\n";
                break;
            }
        }
        giaithich += c.getGiaidap();

        tvCauHoi.setText(giaithich);

        btnCauTiepTheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCauTiepTheo.setVisibility(View.GONE);

                playGame();
            }
        });
    }
    private void setClicked(boolean b){
        if(b == true){
            tvCauA.setClickable(true);
            tvCauB.setClickable(true);
            tvCauC.setClickable(true);
            tvCauD.setClickable(true);
        }
        else {
            tvCauA.setClickable(false);
            tvCauB.setClickable(false);
            tvCauC.setClickable(false);
            tvCauD.setClickable(false);
        }
    }
}