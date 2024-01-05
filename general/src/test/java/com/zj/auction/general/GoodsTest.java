package com.zj.auction.general;

import com.zj.auction.common.model.Goods;
import com.zj.auction.common.model.GoodsCategory;
import com.zj.auction.common.query.GoodsQuery;
import com.zj.auction.general.pc.service.GoodsManagerService;
import com.zj.auction.general.pc.service.StockManagerService;
import io.jsonwebtoken.lang.Collections;
import jodd.util.CollectionUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsTest {
    @Autowired
    private GoodsManagerService goodsManagerService;
    @Autowired
    private StockManagerService stockManagerService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void goodsAddTest(){
        Goods goods = new Goods();
        goods.setAddUserId(1);
        goods.setGoodsName("测试压测02");
        goods.setAuctionAreaId(12L);
        goods.setContent("商品内容02");
        goods.setAuctionStatus(0);
        goods.setBucklePointRatio(new BigDecimal("0.011"));
        goods.setPrice(new BigDecimal("3000"));
        goods.setOldPrice(new BigDecimal("4000"));
        goods.setDeliveryMoney(new BigDecimal("4000"));
        goods.setHandNum(100);
        goods.setSellTotal(40000);
        goods.setImgUrl("https://duoqio20180105.oss-cn-beijing.aliyuncs.com/fy/2022/05/04/22/02/b06897fa-53f3-41e2-9dfd-117450be3add.jpg");
        goodsManagerService.addGoods(goods);
        System.out.println(goods.getId());
    }

    @Test
    public void updateGoods(){
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setGoodsName("测试");
        List<Goods> goods = goodsManagerService.listGoods(goodsQuery);
        if(!Collections.isEmpty(goods)){
            Goods goods1 = goods.get(0);
            goods1.setGoodsName("测试修改01");
            goodsManagerService.updateGoods(goods1);
        }
    }

    @Test
    public void setRedisTemplate(){
        redisTemplate.opsForValue().set("qq",555,60, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get("qq"));


    }

    @Test
    public void addGoodsCategory(){
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setName("酒类");
        goodsCategory.setLevel(1);
        goodsCategory.setPid(0L);
        goodsCategory.setSortIndex(1);
        goodsManagerService.addGoodsCategory(goodsCategory);
        GoodsCategory goodsCategory2 = new GoodsCategory();
        goodsCategory2.setName("红色诗篇酒");
        goodsCategory2.setLevel(2);
        goodsCategory2.setPid(goodsCategory.getId());
        goodsCategory2.setSortIndex(1);
        goodsManagerService.addGoodsCategory(goodsCategory2);
        GoodsCategory goodsCategory3 = new GoodsCategory();
        goodsCategory3.setName("健康酒");
        goodsCategory3.setLevel(2);
        goodsCategory3.setPid(goodsCategory.getId());
        goodsCategory3.setSortIndex(1);
        goodsManagerService.addGoodsCategory(goodsCategory3);
    }

    @Test
    public void addStockAndTransfer2Auction(){
        stockManagerService.addAndTransfer2Auction(16800L,50000,202L);
    }

    @Test
    public void addTestAllData(){
        Goods goods = new Goods();
        goods.setAddUserId(1);
        goods.setGoodsName("测试压测03");
        goods.setAuctionAreaId(12L);
        goods.setContent("商品内容02");
        goods.setAuctionStatus(0);
        goods.setBucklePointRatio(new BigDecimal("0.011"));
        goods.setPrice(new BigDecimal("3000"));
        goods.setOldPrice(new BigDecimal("4000"));
        goods.setDeliveryMoney(new BigDecimal("4000"));
        goods.setHandNum(100);
        goods.setSellTotal(40000);
        goods.setImgUrl("https://duoqio20180105.oss-cn-beijing.aliyuncs.com/fy/2022/05/04/22/02/b06897fa-53f3-41e2-9dfd-117450be3add.jpg");
        goodsManagerService.addGoods(goods);
        stockManagerService.addAndTransfer2Auction(goods.getId(),1000,202L);
    }

    @Test
    public void a(){
        Object test_redis = redisTemplate.opsForValue().get("test_redis");
        System.out.println(test_redis);
    }
}
