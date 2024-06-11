package com.example.ystand.Service.impl;

import com.example.ystand.Dao.mapper.UserMapper;
import com.example.ystand.Dao.po.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLoginService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userMapper.findUserByName(username);//从数据库中查询用户
        if(user==null){
            throw new UsernameNotFoundException("帐户不存在");
        }
        user.setRoles(userMapper.findRolesByUserId(user.getId()));//从数据库中查询到该用户的所有角色（含权限）
        // 将角色转换为Spring Security的GrantedAuthority列表
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getAccount(),new BCryptPasswordEncoder().encode(user.getPassword()),authorities);
    }
}
