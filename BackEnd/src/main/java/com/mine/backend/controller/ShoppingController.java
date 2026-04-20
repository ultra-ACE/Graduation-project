package com.mine.backend.controller;

import com.mine.backend.entity.ShoppingCar;
import com.mine.backend.entity.ShoppingCarRequest;
import com.mine.backend.exception.Result;
import com.mine.backend.service.ShoppingCarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {
    @Resource
    private ShoppingCarService shoppingCarService;

    @PostMapping("/addGoodInShoppingCar")
    public Result addGoodInShoppingCar(@RequestBody ShoppingCarRequest shoppingCarRequest){
        List<ShoppingCar> shoppingCars=shoppingCarService.addGoodInShoppingCar(shoppingCarRequest.getGoods(),
                shoppingCarRequest.getGood());
        return Result.success(shoppingCars);
    }

    @PutMapping("/updateGoodInShoppingCar")
    public Result updateShoppingCar(@RequestBody ShoppingCarRequest shoppingCarRequest){
        List<ShoppingCar> shoppingCars=shoppingCarService.updateGoodInShoppingCar(shoppingCarRequest.getGoods(), shoppingCarRequest.getGood());
        return Result.success(shoppingCars);
    }

    @PostMapping("/deleteGoodInShoppingCar")
    public Result deleteGoodInShoppingCar(@RequestBody ShoppingCarRequest shoppingCarRequest){
        List<ShoppingCar> shoppingCars=shoppingCarService.deleteGoodInShoppingCar(shoppingCarRequest.getGoods(),
                shoppingCarRequest.getGood());
        return Result.success(shoppingCars);
    }

    @PostMapping("/deleteGoodsInShoppingCar")
    public Result deleteGoodsInShoppingCar(@RequestBody ShoppingCarRequest shoppingCarRequest){
        List<ShoppingCar> shoppingCars=shoppingCarService.deleteGoods(shoppingCarRequest.getGoods(), shoppingCarRequest.getDeleteGoods());
        return Result.success(shoppingCars);
    }

    @PostMapping("/calculateTotalPrice")
    public Result calculateTotalPrice(@RequestBody List<ShoppingCar> shoppingCars){
        double totalPrice=shoppingCarService.calculateTotalPrice(shoppingCars);
        return Result.success(totalPrice);
    }

    @PostMapping("/afford")
    public Result affords(@RequestBody ShoppingCarRequest shoppingCarRequest){
        List<ShoppingCar> shoppingCars = shoppingCarService.afford(shoppingCarRequest.getGoods(), shoppingCarRequest.getDeleteGoods(),shoppingCarRequest.getUser(), shoppingCarRequest.getAddress());
        return Result.success(shoppingCars);
    }
}
