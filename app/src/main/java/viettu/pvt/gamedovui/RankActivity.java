package viettu.pvt.gamedovui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class RankActivity extends AppCompatActivity {
    ImageView view;

    RecyclerView Bangxephang;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<User> usersList = new ArrayList<>();
    XepHangAdapter xephangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        Bangxephang = findViewById(R.id.recycle);
        view = findViewById(R.id.quayve);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        xephangAdapter = new XepHangAdapter(usersList);
        Bangxephang.setAdapter(xephangAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        Bangxephang.setLayoutManager(linearLayoutManager);
        getAllUser();
    }
    private  void getAllUser(){
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful()){
                            for (QueryDocumentSnapshot dc : task.getResult()) {

                                String name = (String) dc.get("name");

                                Long diem1 = (Long) dc.get("diem");
                                 int diem = diem1.intValue();
                                User u = new User(name,diem);
                                usersList.add(u);
                            }
                            Collections.sort(usersList,User.userComparator);
                            xephangAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }
}