package com.zj.auction.common.mapper;

import com.zj.auction.common.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    User selectByPrimaryKey(Long userId);

    List<User> selectAll();

    Integer countByName(@Param("userName") String username);

    int updateByPrimaryKey(User record);
}