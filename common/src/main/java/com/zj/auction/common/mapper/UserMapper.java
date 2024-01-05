package com.zj.auction.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.auction.common.dto.UserDTO;
import com.zj.auction.common.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Long userId);

    @Override
    int insert(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int insertSelective(User record);

    List<User> selectAll();

    Integer countByName(@Param("userName") String username);

    int updateByPrimaryKey(User record);

    //    List<Map<String,Object>> getStatistics(@Param("userId") Long userId,@Param("ids") String ids);
//    @MapKey("id")
    List<Map<String, Object>> listMemberIndirect(@Param("dto") UserDTO dto);

    Integer updateAuditRejection(@Param("userId") Long userId, @Param("auditExplain") String auditExplain);

    Integer updateAuditApproval(@Param("userId") Long userId);

    Integer updUserChildByPidStr(@Param("tagId") Long tagId, @Param("userId") Long userId, @Param("pcUserId") Long pcUserId);

    //     ------------------------APP用户----------------------------
    Integer customerAllPage(@Param("userId") Long userId);

    User findByUserName(String tel);

    List<User> findByTel(String tel);

    Page<User> findFirstAndSecondByUserId(@Param("userId") Long userId, @Param("levelNum1") Integer levelNum1, @Param("levelNum2") Integer levelNum2, Pageable pageable);

    Long countUserChildByUserId(@Param("userId") Long userId);

    Long selectUserParent(@Param("userId") Long userId);
}