package com.mine.backend.service;

import com.mine.backend.entity.Good;
import com.mine.backend.entity.Order;
import com.mine.backend.entity.ShoppingCar;
import com.mine.backend.entity.User;
import com.mine.backend.enums.ResultCodeEnum;
import com.mine.backend.exception.CustomException;
import com.mine.backend.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCarService {
    @Resource
    private OrderService orderService;
    @Resource
    private GoodService goodService;
    public List<ShoppingCar> addGoodInShoppingCar(List<ShoppingCar> goods, ShoppingCar good){
        Good dbgood=goodService.selectById(good.getGoodId());
        for(ShoppingCar g:goods){
            if(Objects.equals(g.getGoodId(), good.getGoodId())){
                if (dbgood.getNum()<=g.getNum())
                    return goods;
                g.setNum(g.getNum()+1);
                return goods;
            }
        }
        goods.add(good);
        return goods;
    }

    public List<ShoppingCar> deleteGoodInShoppingCar(List<ShoppingCar> goods, ShoppingCar good){
        Iterator<ShoppingCar> iterator=goods.iterator();
        while (iterator.hasNext()){
            ShoppingCar g=iterator.next();
            if(Objects.equals(g.getGoodId(), good.getGoodId())){
                iterator.remove();
            }
        }
        return goods;
    }

    public List<ShoppingCar> updateGoodInShoppingCar(List<ShoppingCar> goods, ShoppingCar good){
        for(ShoppingCar g:goods){
            if(Objects.equals(g.getGoodId(), good.getGoodId())){
                g.setNum(good.getNum());
            }
        }
        return goods;
    }

    public List<ShoppingCar> deleteGoods(List<ShoppingCar> goods,List<ShoppingCar> deleteGoods){
        Iterator<ShoppingCar> iterator=goods.iterator();
        while (iterator.hasNext()){
            ShoppingCar g=iterator.next();
            for(ShoppingCar d:deleteGoods){
                if(Objects.equals(g.getGoodId(), d.getGoodId())){
                    iterator.remove();
                }
            }
        }
        return goods;
    }

    public double calculateTotalPrice(List<ShoppingCar> goods){
        double totalPrice=0;
        for(ShoppingCar g:goods){
            totalPrice+=g.getPrice()*g.getNum();
        }
        return totalPrice;
    }


    public List<ShoppingCar> afford(List<ShoppingCar> goods,List<ShoppingCar> deleteGoods, User user, String address){
        Iterator<ShoppingCar> iterator=deleteGoods.iterator();
        while (iterator.hasNext()){
            ShoppingCar g=iterator.next();
            Good good=goodService.selectById(g.getGoodId());
            if(good.getNum()<g.getNum()){
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
            }
            LocalDate date = LocalDate.now();
            Order order=new Order();
            order.setGoodId(g.getGoodId());
            order.setGoodName(good.getGoodName());
            order.setPrice(good.getPrice());
            order.setNum(g.getNum());
            order.setUserId(user.getId());
            order.setUserName(user.getUsername());
            order.setImg(good.getImg());
            order.setAddress(address);
            order.setDate(String.valueOf(date));
            orderService.addOrder(order);
            good.setNum(good.getNum()-g.getNum());
            good.setSales(good.getSales()+g.getNum());
            goodService.sale(good);
        }
        return deleteGoods(goods, deleteGoods);
    }
}
