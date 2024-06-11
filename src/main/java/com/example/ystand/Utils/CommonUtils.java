package com.example.ystand.Utils;

import com.example.ystand.Dao.po.Users;

public class CommonUtils {

    private static Users users = new Users();

    public static void setUsers(Users user) {
        users = user;
    }

    public static Users getUsers() {
        return users;
    }

    public static void setAvatar(String avatar){
        users.setAvatar(avatar);
    }
}
