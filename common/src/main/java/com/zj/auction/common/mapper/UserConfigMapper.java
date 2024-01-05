package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.model.UserConfig;
import java.util.List;

public interface UserConfigMapper extends BaseMapper<UserConfig> {
    int deleteByPrimaryKey(Integer userConfigId);

    int insert(UserConfig record);

    UserConfig selectByPrimaryKey(Integer userConfigId);

    List<UserConfig> selectAll();

    int updateByPrimaryKey(UserConfig record);

    UserConfig selectAllByUserId (Long userId);
}