package com.zj.auction.common.base;
import com.zj.auction.common.exception.ServiceException;

import java.util.function.*;

/**
 * ************************************************
 * describe:公用的业务接口
 *
 * @author Mr.Chen->MengDaNai
 * @version 1.0
 * @date 2018年11月8日 创建文件
 * @see ************************************************
 */
public interface BaseService {

    /**
     * ************************************************
     * describe:公用的方法
     *
     * @param t
     * @param deal 接收T对象，返回R对象
     * @return r
     * @throws ServiceException 异常类
     * @author MengDaNai
     * @date 2019年3月19日 创建文件
     * ************************************************
     */
    <T, R> R base(T t, Function<T, R> deal) throws ServiceException;

    /**
     * ************************************************
     * describe:公用的大方法
     *
     * @param t
     * @param u
     * @param deal 接收T,U对象，返回R对象
     * @return r
     * @throws ServiceException 异常类
     * @author MengDaNai
     * @date 2019年3月19日 创建文件
     * ************************************************
     */
    default <T, U, R> R base(T t, U u, BiFunction<T, U, R> deal) throws ServiceException {
        return deal.apply(t, u);
    }

    /**
     * ************************************************
     * describe:公用的生产数据
     *
     * @param supplier 生产一个t
     * @return t
     * @throws ServiceException
     * @author MengDaNai
     * @date 2019年3月19日 创建文件
     * ************************************************
     */
    default <T> T base(Supplier<T> supplier) throws ServiceException {
        return supplier.get();
    }

    /**
     * ************************************************
     * describe:公用的消费数据
     *
     * @param t
     * @param consumer 消费一个t
     * @throws ServiceException 异常类
     * @author MengDaNai
     * @date 2019年3月19日 创建文件
     * ************************************************
     */
    default <T> void base(T t, Consumer<T> consumer) throws ServiceException {
        consumer.accept(t);
    }

    /**
     * ************************************************
     * describe:公用的谓词数据校验
     *
     * @param t
     * @param check 接收一个t返回boolean
     * @return boolean
     * @throws ServiceException 异常类
     * @author MengDaNai
     * @date 2019年3月19日 创建文件
     * ************************************************
     */
    default <T> boolean baseCheck(T t, Predicate<T> check) throws ServiceException {
        return check.test(t);
    }

    /**
     * ************************************************
     * describe:公用的方法
     *
     * @param t
     * @param deal 接收T对象，返回R对象
     * @return r
     * @throws Exception 异常
     * @author MengDaNai
     * @date 2019年3月19日 创建文件
     * ************************************************
     */
    default <T, R> R baseException(T t, FunctionCustomize<T, R> deal) throws Exception {
        return deal.apply(t);
    }

}
