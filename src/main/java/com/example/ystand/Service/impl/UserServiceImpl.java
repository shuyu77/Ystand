package com.example.ystand.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ystand.Dao.Dto.UserDTO;
import com.example.ystand.Dao.mapper.UserMapper;
import com.example.ystand.Dao.po.Users;
import com.example.ystand.Service.IUserService;
import com.example.ystand.Utils.ConvertBeanUtils;
import com.example.ystand.Utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements IUserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public Users getByAccount(String name) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", name);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean insertUser(UserDTO userDTO) {
        if(userDTO.getAvatar().isEmpty()) return false;

        Users users = ConvertBeanUtils.convert(userDTO, Users.class);
        String filename = HttpUtils.postMultipartFile(userDTO.getAvatar());
        users.setAvatar("http://8.138.112.13:8180/Y-stand/" + filename);
        return userMapper.insert(users) > 0;
    }

    @Override
    public boolean deleteUser(Integer id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        Users users = ConvertBeanUtils.convert(userDTO, Users.class);
        users.setId(userDTO.getId());
        if(!userDTO.getAvatar().isEmpty()) {
            String filename = HttpUtils.postMultipartFile(userDTO.getAvatar());
            users.setAvatar("http://8.138.112.13:8180/Y-stand/" + filename);
        }
        return userMapper.updateById(users) > 0;
    }
}
