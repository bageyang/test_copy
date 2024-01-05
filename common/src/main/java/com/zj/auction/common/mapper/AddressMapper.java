package com.zj.auction.common.mapper;

import com.zj.auction.common.model.Address;
import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(Long addrId);

    int insert(Address record);

    Address selectByPrimaryKey(Long addrId);

    List<Address> selectAll();

    int updateByPrimaryKey(Address record);
}