package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    User selectByPrimaryKey(Long userId);

    List<User> selectAll();

    Integer countByName(@Param("userName") String username);

    int updateByPrimaryKey(User record);

    List<Map<String,Object>> getStatistics(@Param("userId") Long userId,@Param("ids") String ids);
}