package com.example.ystand.Dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ystand.Dao.po.Role;
import com.example.ystand.Dao.po.Users;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-05-13
 */
public interface UserMapper extends BaseMapper<Users> {

    @Select("SELECT * FROM users " +
            "WHERE account = #{username}")
    Users findUserByName(String username);

    @Select("select role.* from role,user_role where role.id=user_role.role_id and user_role.user_id=#{uid}")
    List<Role> findRolesByUserId(Integer uid);
}
