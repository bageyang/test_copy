package com.zj.auction.payment.boot;

import com.zj.auction.payment.util.ThreadUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@Configuration
public class WisdomRunner implements CommandLineRunner {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public WisdomRunner(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void run(String... args) {
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(" select * from zj_system_cnf ", new HashMap<>());

        ThreadUtils.getPool().execute(() -> {
            try {
                Class<?> clazz = Class.forName("com.zj.auction.common.constant.SystemConfig");
                Object obj = clazz.newInstance();
                List<Method> ms = Arrays.asList(clazz.getMethods());
                ms.forEach(c ->
                        list.stream().filter(m -> c.getName().equalsIgnoreCase("set" + m.get("key_name").toString())).forEach(m -> {
                            try {
                                c.invoke(obj, m.get("key_value"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        })
                );
                log.info("初始化系统参数成功");
            } catch (Exception e) {
                e.printStackTrace();
                log.error("初始化系统参数异常");
            }
        });

        //qingchu redis
        /*RedisUtil.del(PublicStaticCariable.REDIS_CLASS_KEY,PublicStaticCariable.REDIS_EDITION_KEY,
                PublicStaticCariable.REDIS_GRADE_KEY, PublicStaticCariable.REDIS_SCHOOL_KEY, PublicStaticCariable.REDIS_SUBJECT_KEY,
                PublicStaticCariable.REDIS_PAY_RULE_TYPE_KEY);*/


    }
}
