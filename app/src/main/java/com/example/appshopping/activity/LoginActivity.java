package com.example.appshopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;

public class LoginActivity extends AppCompatActivity {
    Button bt_login;
    TextView startActivity_register;
    EditText email, password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.custom_loading_dialog);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        // kiểm tra xem user đã login chưa
         if(user1 != null) // kiểm tra xem đã login chưa
         {
             startActivity(new Intent(this,MainActivity.class));
             finish();
         }
         else {

             AnhXa();
             startRegister(); // chuyển xang activity Register
             bt_login.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     HideKeyBoard();
                     String user = email.getText().toString();
                     String pass = password.getText().toString();
                     if (user.isEmpty()) {
                         email.setError("Email rỗng");
                     }
                     if (pass.isEmpty()) {
                         password.setError("Password rỗng");
                         return;
                     }

                     if (!user.isEmpty()) {

                         dialog.show();
                         auth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isComplete())
                                 {
                                     dialog.dismiss();
                                     Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                     intent.putExtra("email",user);
                                     startActivity(intent);
                                     finish();

                                 } else {
                                     dialog.dismiss();
                                     Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();

                                 }
                             }
                         });
                     }
                 }
             });
         }
    }

    private void AnhXa()
    {
     bt_login = findViewById(R.id.bt_login);
     startActivity_register = findViewById(R.id.startActivity_register);
     email = findViewById(R.id.email_login);
     password = findViewById(R.id.password_login);
     auth = FirebaseAuth.getInstance();
    }
    public void HideKeyBoard()
    {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }
    public void startRegister()
    {
        startActivity_register.setOnClickListener(v -> {
            Intent it = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(it);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}