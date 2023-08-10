package com.example.appshopping.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;


import com.example.appshopping.Class_Properties.User;
import com.example.appshopping.Function_class;

import com.example.appshopping.R;


public class RegisterActivity extends AppCompatActivity {

    EditText iFName, iEmail,iLName, iPassword, iDayOfBirth;
    AppCompatButton btRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                User user = checkInfo();
              if(user != null)
              {
                  Intent it = new Intent(RegisterActivity.this, VerificationActivity.class);
                  Bundle bundle = new Bundle();
                  bundle.putSerializable("User",user);
                  it.putExtras(bundle);
                  it.putExtra("email", iEmail.getText().toString());
                  startActivity(it);
              }
            }
        });
    }

    private void AnhXa() {
        iFName = findViewById(R.id.firstName_register);
        iLName = findViewById(R.id.lastName_register);
        iEmail = findViewById(R.id.email_register);
        iPassword = findViewById(R.id.password_register);
        iDayOfBirth = findViewById(R.id.DayOfBirthRegister);
        btRegister = findViewById(R.id.bt_register);
    }
    private User checkInfo() // kiểm tra xem người dùng nhập đầy đủ thông tin chưa
    {
        String email = iEmail.getText().toString();
        String pass = iPassword.getText().toString();
        String fName = iFName.getText().toString(), lName = iLName.getText().toString()
                , DayOfBirth = iDayOfBirth.getText().toString();
        if(pass.isEmpty() ||  email.isEmpty() || DayOfBirth.isEmpty() || fName.isEmpty() || lName.isEmpty())
        {
                if (DayOfBirth.isEmpty()) {
                    iDayOfBirth.setError("Day of birth trống");
                }
                if (fName.isEmpty()) {
                    iLName.setError("Last name trống");
                }
                if (lName.isEmpty()) {
                    iFName.setError("First name trống");
                }
                if (pass.isEmpty()) {
                    iPassword.setError("Password rỗng");
                }
                if (email.isEmpty()) {
                    iEmail.setError("Email rỗng");
                }
                return null;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            iEmail.setError("Email không hợp lệ");
            return null;
        }
        String name = fName + lName;
        return new User(Function_class.createID(),name, email,pass,DayOfBirth,null);
        }


    }