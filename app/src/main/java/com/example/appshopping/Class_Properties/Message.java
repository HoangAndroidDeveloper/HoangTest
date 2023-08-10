package com.example.appshopping.Class_Properties;
import java.util.List;
public class Message {
    private int id;
    private List<TinNhan> tn;
    private User user;
    public Message() {

    }

    public Message(int id, List<TinNhan> tn, User user) {
        this.id = id;
        this.tn = tn;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TinNhan> getTn() {
        return tn;
    }

    public void setTn(List<TinNhan> tn) {
        this.tn = tn;
    }

    public static class TinNhan
    {
        private  String mden, mdi, emoji_di, emoji_den, time_di, time_den;

        public TinNhan() {
        }

        public TinNhan(String mden, String mdi, String emoji_di, String emoji_den, String time_di, String time_den) {
            this.mden = mden;
            this.mdi = mdi;
            this.emoji_di = emoji_di;
            this.emoji_den = emoji_den;
            this.time_di = time_di;
            this.time_den = time_den;
        }

        public String getMden() {
            return mden;
        }

        public void setMden(String mden) {
            this.mden = mden;
        }

        public String getMdi() {
            return mdi;
        }

        public void setMdi(String mdi)
        {
            this.mdi = mdi;
        }

        public String getEmoji_di() {
            return emoji_di;
        }

        public void setEmoji_di(String emoji_di) {
            this.emoji_di = emoji_di;
        }

        public String getEmoji_den() {
            return emoji_den;
        }

        public String getTime_di() {
            return time_di;
        }

        public void setTime_di(String time_di) {
            this.time_di = time_di;
        }

        public String getTime_den() {
            return time_den;
        }

        public void setTime_den(String time_den) {
            this.time_den = time_den;
        }

        public void setEmoji_den(String emoji_den) {
            this.emoji_den = emoji_den;
        }
    }
}
