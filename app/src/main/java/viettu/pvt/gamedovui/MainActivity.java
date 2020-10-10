package viettu.pvt.gamedovui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   EditText edName;
   Button btnPlay;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       edName = findViewById(R.id.namePlayer);
       btnPlay= findViewById(R.id.playclick);
        final SharedPreferences sharedPreferences = getSharedPreferences("Play", MODE_PRIVATE);
        String name = sharedPreferences.getString("name",null);
        if ( name != null){
            Intent intent= new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = edName.getText().toString().trim();
                if ( !a.equals("")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name",a);
                    editor.commit();
                    Map<String, Object> user = new HashMap<>();
                    user.put("name",a);
                    user.put("diem", 0);
                    db.collection("User").document(a).set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("loi", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("loi",  "Error writing document", e);
                                }
                            });

                    Intent intent= new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("name", a);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Phai dien thong tin", Toast.LENGTH_LONG).show();
                }
            }
        });

//        Map<String, Object> cauhoi = new HashMap<>();
//        cauhoi.put("cauhoi", "Quả tròn to, da xanh xanh. Ruột trong đỏ chót mát lành ai ơi. Hạt đen be bé thủi thui. Ăn vào hết háo, đoán vui xem nào? Là quả gì?");
//        cauhoi.put("a", "Quả dừa" );
//        cauhoi.put("b", "Quả Thanh Long");
//        cauhoi.put("c", "Quả Dưa Hấu");
//        cauhoi.put("d", "Quả Cam");
//        cauhoi.put("dapan", "Quả Dưa Hấu");
//        cauhoi.put("giaidap", "Quả Dưa Hấu vỏ xanh ruột đỏ , hạt đen");
//        db.collection("CauHoi")
//                .add(cauhoi)
//               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                   @Override
//                   public void onSuccess(DocumentReference documentReference) {
//                       Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_LONG).show();
//                   }
//               })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("aa","Error"+e.getMessage());
//                        Toast.makeText(MainActivity.this,"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });

    }






}