package com.zj.auction.common.util;
import com.zj.auction.common.model.User;
import com.zj.auction.common.vo.UserVO;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @author Mao Qi
 * @version V1.0
 * @date 2019年6月25日
 * @describe NettyBeanUtils
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    private static String[] USER_IGNORE = new String[]{"password", "passWord", "payPassword", "payPassWord","PcPassword","salt","pcSalt", "extend", "roles"};
    private static List<String> covers;

    /**
     * @describe 拷贝用户信息
     * @author Mao Qi
     * @date 2019/8/1 19:36
     */
    public static User copy(User userInfoTbl) {
        if (userInfoTbl == null) {
            return null;
        }
        User target = new User();
        copyProperties(userInfoTbl, target, USER_IGNORE);
        return target;
    }

    /**
     * @describe 拷贝用户信息
     * @author Mao Qi
     * @date 2019/8/1 19:36
     */
    public static UserVO copyUserSimple(User userInfoTbl) {
        if (userInfoTbl == null) {
            return null;
        }
        UserVO target = new UserVO();
        copyProperties(userInfoTbl, target, USER_IGNORE);
        return target;
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


}
