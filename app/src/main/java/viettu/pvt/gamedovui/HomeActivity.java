package viettu.pvt.gamedovui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    Button btnPlay, btnRank;
    ImageView imgAvatar;
    FirebaseUser user;
    TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      btnPlay = findViewById(R.id.play);
      btnRank = findViewById(R.id.rank);

      tvName = findViewById(R.id.name_play);
      Intent intent = getIntent();
      final String name = intent.getStringExtra("name");

      btnPlay.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(HomeActivity.this, PlayActivity.class);
              intent.putExtra("name", name);
              startActivity(intent);
          }
      });
      btnRank.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(HomeActivity.this, RankActivity.class);

              startActivity(intent);
          }
      });
    }
}