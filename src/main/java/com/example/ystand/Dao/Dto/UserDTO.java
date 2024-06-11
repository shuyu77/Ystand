package com.example.ystand.Dao.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    private String account;

    private String password;

    private String nickname;

    private String birth;

    private String sex;

    private String phone;

    private MultipartFile avatar;

    private String tag;
}
