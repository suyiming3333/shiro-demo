package com.sym.shiroweb.dao.impl;

import com.sym.shiroweb.dao.UserDao;
import com.sym.shiroweb.vo.User;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("userDaoImpl")
public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByUsername(String userName) {
        String sql = "select username,password from shiro_user where username = ?";
        List<User> list = jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User resultUser = new User();
                resultUser.setUsername(resultSet.getString("username"));
                resultUser.setPassword(resultSet.getString("password"));
                return resultUser;
            }
        });
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }
}
