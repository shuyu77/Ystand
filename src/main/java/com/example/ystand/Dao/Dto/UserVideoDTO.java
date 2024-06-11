package com.example.ystand.Dao.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVideoDTO {

    private boolean isLike;

    private boolean isCoin;

    private boolean isCollect;
}
