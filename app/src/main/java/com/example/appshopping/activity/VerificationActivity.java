package com.example.appshopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.appshopping.Class_Properties.User;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class VerificationActivity extends AppCompatActivity {
TextView countDown, bt_sendCode, EmailReceiver;
PinView pinView;
Button bt_XacNhan;
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        AnhXa();
       String email = getIntent().getStringExtra("email");
       User user = (User) getIntent().getExtras().getSerializable("User");
        EmailReceiver.setText(email);
        pinView.setAnimationEnable(true);
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // kiểm tra xem đã nhập mã pin chưa nếu nhập rồi thì cho nút xác nhận sáng lên
                if(charSequence.length() == 6)
                {
                        bt_XacNhan.setEnabled(true);
                        bt_XacNhan.setBackgroundColor(Color.RED);
                        bt_XacNhan.setTextColor(Color.WHITE);
                }
                else
                {
                    if(bt_XacNhan.isEnabled())
                    {
                        bt_XacNhan.setEnabled(false);
                        bt_XacNhan.setBackgroundColor(getColor(R.color.color_bt));
                        bt_XacNhan.setTextColor(getColor(R.color.color_textMua));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        String code =  VerificationEmail(email);
        CountDownTimer countDownTimer = new CountDownTimer(61000,1000) {
            @Override
            public void onTick(long l) {
                countDown.setText("Gửi lại mã sau: "+(l/1000)+"s");
            }

            @Override
            public void onFinish() {
             bt_sendCode.setVisibility(View.VISIBLE);
             countDown.setVisibility(View.INVISIBLE);
            }
        }.start();
        bt_XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(code.equals(Objects.requireNonNull(pinView.getText()).toString()))
              {
                  // kiểm tra xem nhập code đúng chưa nếu đúng thì put user lên firebase
                  FirebaseAuth auth = FirebaseAuth.getInstance();
                  auth.createUserWithEmailAndPassword(email,user.getPassword())
                          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isComplete())
                          {
                              DatabaseReference reference = FirebaseDatabase.getInstance()
                                      .getReference("DsUser/"+user.getId());
                              reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isComplete()) {
                                          Toast.makeText(VerificationActivity.this, "Đăng ký tài khoản thành công"
                                                  , Toast.LENGTH_SHORT).show();
                                          startActivity(new Intent(VerificationActivity.this, LoginActivity.class));
                                          finishAffinity(); // đóng tất cả các activity  trước activity Login
                                          auth.signOut();
                                      }
                                  }
                              });

                          }
                      }
                  });
              }
            }
        });

    }

    private void AnhXa() {
        countDown = findViewById(R.id.countDown);
        bt_sendCode = findViewById(R.id.sendTo);
        pinView = findViewById(R.id.PinView);
        bt_XacNhan = findViewById(R.id.bt_xacNhanPinView);
        EmailReceiver = findViewById(R.id.emailReceiver);
    }
    public String VerificationEmail(String email) // gửi code về email
    {
        Random random = new Random();
        String code = String.valueOf(random.nextInt(899999)+100000);
        String EmailSender = "trinhviethoang307@gmail.com";
        String password = "lqzywirpusvnurio", host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.auth","true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailSender,password);
            }
        });
        Message mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.addRecipients(Message.RecipientType.TO,  InternetAddress.parse(email));
            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setSubject("Mã code xác thực ứng dụng App được gửi từ Hoàng Android Developer");
            mimeMessage.setText("Verification Code: "+code);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {

                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return code;
    }

}