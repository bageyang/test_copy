package com.zj.auction.common.base;
import com.zj.auction.common.exception.ServiceException;

import java.util.function.Function;

/**
 * ************************************************
 * describe: 提供公用的业务实现
 *
 * @author Mr.Chen->MengDaNai
 * @version 1.0
 * @date 2018年11月8日 创建文件
 * @see ************************************************
 */
public abstract class BaseServiceImpl implements BaseService {

    /**
     * describe:公用的方法
     *
     * @param t
     * @param deal
     * @return
     * @throws ServiceException
     */
    @Override
    public <T, R> R base(T t, Function<T, R> deal) throws ServiceException {
        return deal.apply(t);
    }

}
