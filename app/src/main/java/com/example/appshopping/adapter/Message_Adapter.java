package com.example.appshopping.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appshopping.Class_Properties.Message;
import com.example.appshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.MHolder>
{
   Message DsTN;
   public static int vTriMessage, waitSend = 0;
   Context context;

    public Message_Adapter(Message dsTN, Context context) {
        DsTN = dsTN;
        this.context = context;
    }

    public void setData(Message DsTN)
 {
     this.DsTN = DsTN;
     notifyDataSetChanged();
 }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new MHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, @SuppressLint("RecyclerView") int position)
    {
        String [] LEmoji = context.getResources().getStringArray(R.array.LEmoji);
        Message.TinNhan tinNhan = DsTN.getTn().get(position);
        String mDi = tinNhan.getMdi();
        String mDen = tinNhan.getMden();
        String urlEmoji_den = tinNhan.getEmoji_den();
        String urlEmoji_di = tinNhan.getEmoji_di();
        holder.tn_den.setText(mDen);
        holder.tn_di.setText(mDi);
        if(mDen.isEmpty())
            holder.layout_message_den.setVisibility(View.GONE);
        else
        if(mDi.isEmpty())
        {
            holder.layout_message_di.setVisibility(View.GONE);
        }

        if(!mDi.isEmpty())
        {
            holder.layout_message_di.setVisibility(View.VISIBLE);
        }
        if(!mDen.isEmpty())
        {
            holder.layout_message_den.setVisibility(View.VISIBLE);
        }
        if (urlEmoji_den != null)
        {
            holder.card_emoji_den.setVisibility(View.VISIBLE);
            Glide.with(context).load(urlEmoji_den).centerCrop().into(holder.emoji_den);
        }
        if (urlEmoji_di != null)
        {
            holder.card_emoji_di.setVisibility(View.VISIBLE);
            Glide.with(context).load(urlEmoji_di).centerCrop().into(holder.emoji_di);
        }
        if (urlEmoji_den == null)
        {
            holder.card_emoji_den.setVisibility(View.GONE);
        }
        if (urlEmoji_di == null)
        {
            holder.card_emoji_di.setVisibility(View.GONE);
        }

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.emoji_dialog);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView recyclerView = dialog.findViewById(R.id.r_Emoji);
        TextView txt_emoji = dialog.findViewById(R.id.message_dialog);
        EmojiAdapter.interfaceEmoji Click = new EmojiAdapter.interfaceEmoji()
        {
            @Override
            public void click(int position)
            {
                dialog.dismiss();
                Message.TinNhan tn = DsTN.getTn().get(vTriMessage);
                tn.setEmoji_den(LEmoji[position]);
                int idUser = DsTN.getUser().getId();
                DatabaseReference putEmoji = FirebaseDatabase.getInstance().getReference("DsMessage/"+idUser);
                putEmoji.setValue(DsTN);
            }
        };
        EmojiAdapter emojiAdapter = new EmojiAdapter(LEmoji,context,Click);
        recyclerView.setAdapter(emojiAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));

        holder.layout_message_den.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view)
            {
                vTriMessage = position;
                txt_emoji.setText(mDen);
                if(dialog.isShowing())
                    dialog.dismiss();
                else
                    dialog.show();
                return false;
            }
        });
       @DrawableRes int idIcon = R.drawable.check_message_complate;
       holder.icon_send_success.setImageResource(idIcon);
       if(waitSend == 0 && position == DsTN.getTn().size() -1)
           holder.icon_send_success.setImageResource(idIcon);
       else
       if(waitSend == 1 && position == DsTN.getTn().size() - 1)
       {
           holder.icon_send_success.setImageResource(R.drawable.un_check_message);
       }
    }

    @Override
    public int getItemCount() {
        if(DsTN == null)
            return 0;
        return DsTN.getTn().size();
    }

    public static class MHolder extends RecyclerView.ViewHolder
    {
        TextView tn_den, tn_di;
        ImageView emoji_di, emoji_den, icon_send_success;
        CardView  card_emoji_den, card_emoji_di;
        LinearLayout  layout_message_den, layout_message_di;
        public MHolder(@NonNull View itemView)
        {
            super(itemView);
            tn_den = itemView.findViewById(R.id.message_den);
            tn_di = itemView.findViewById(R.id.message_di);
            emoji_di = itemView.findViewById(R.id.emoji_messge_di);
            emoji_den = itemView.findViewById(R.id.emoji_messge_den);
            card_emoji_den = itemView.findViewById(R.id.card_emoji_den);
            card_emoji_di = itemView.findViewById(R.id.card_emoji_di);
            layout_message_den = itemView.findViewById(R.id.layout_message_den);
            layout_message_di = itemView.findViewById(R.id.layout_message_di);
            icon_send_success = itemView.findViewById(R.id.icon_send_success);

        }
    }

}
