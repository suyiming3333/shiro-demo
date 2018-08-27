package com.sym.shiroweb.dao;

import com.sym.shiroweb.vo.User;

public interface UserDao {
    User getUserByUsername(String userName);
}
