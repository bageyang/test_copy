package com.zj.auction.common.mapper;

import com.zj.auction.common.model.UserConfig;
import java.util.List;

public interface UserConfigMapper {
    int deleteByPrimaryKey(Integer userConfigId);

    int insert(UserConfig record);

    UserConfig selectByPrimaryKey(Integer userConfigId);

    List<UserConfig> selectAll();

    int updateByPrimaryKey(UserConfig record);
}