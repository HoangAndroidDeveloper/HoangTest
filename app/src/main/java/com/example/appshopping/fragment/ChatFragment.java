package com.example.appshopping.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appshopping.Class_Properties.Message;
import com.example.appshopping.Class_Properties.User;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.example.appshopping.adapter.Message_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    public ChatFragment() {}
    BroadcastReceiver BNotificationMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    RecyclerView rMessage;
    ImageView bt_send;
    EditText inputTn;
    View view;
    Message message ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat,container,false);
        AnhXa();
        Message_Adapter message_adapter = new Message_Adapter(message,getContext());
        getMessage(message_adapter);
        rMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        rMessage.setAdapter(message_adapter);
        rMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                rMessage.setAdapter(message_adapter);
            }
        });

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tn = inputTn.getText().toString();
                if(tn.isEmpty())
                    return;
                putMessage(message_adapter);
                inputTn.setText("");
                Message_Adapter.waitSend = 1;
            }
        });
        return view;
    }

    private void AnhXa()
    {
        rMessage = view.findViewById(R.id.rMessage);
        bt_send = view.findViewById(R.id.bt_send);
        inputTn = view.findViewById(R.id.input_message);
    }
    private void  getMessage(Message_Adapter message_adapter) // get message
    {
        DatabaseReference get = FirebaseDatabase.getInstance()
                .getReference("DsMessage/"+ MainActivity.user.getId());
        get.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                message = snapshot.getValue(Message.class);
                message_adapter.setData(message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void putMessage(Message_Adapter message_adapter) // put message vừa nhập lên firebase
    {

        User user = MainActivity.user;
        DatabaseReference put = FirebaseDatabase.getInstance()
                .getReference("DsMessage/"+user.getId());
        String tn_di = inputTn.getText().toString();
        if(message == null) // check xem đã gửi lần nào chưa, nếu chưa thì message null nên phải khởi tạo
        {
            List<Message.TinNhan> DsTN = new ArrayList<>();
            DsTN.add(new Message.TinNhan("",tn_di,null,null,null,null));
            message = new Message(1,DsTN,user);
            message_adapter.setData(message);
        }
        else
        {
            Message.TinNhan tn = new Message.TinNhan("",tn_di,null,null,null,null);
            message.getTn().add(tn);
            message_adapter.setData(message);
        }
        put.setValue(message).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Message_Adapter.waitSend = 0;
                message_adapter.setData(message);
                String action = "notificationMessage";
                Intent it = new Intent(action);
                it.putExtra("message",tn_di);
                getContext().sendBroadcast(it);
            }
        });
    }
}