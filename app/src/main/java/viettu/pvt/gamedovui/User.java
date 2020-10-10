package viettu.pvt.gamedovui;

import java.util.Comparator;

public class User {


    String name;
    int diem;

    public User(String name, int diem) {
        this.name = name;
        this.diem = diem;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public static Comparator<User> userComparator = new Comparator<User>() {
        @Override
        public int compare(User user, User t1) {
            return t1.diem - user.diem;
        }
    };
}
