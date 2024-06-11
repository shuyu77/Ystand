package com.example.ystand.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ystand.Dao.Dto.UserDTO;
import com.example.ystand.Dao.po.Users;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
public interface IUserService extends IService<Users> {

    Users getByAccount(String name);

    boolean insertUser(UserDTO userDTO);

    boolean deleteUser(Integer id);

    boolean updateUser(UserDTO userDTO);
}
