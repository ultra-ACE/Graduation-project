package com.mine.backend.entity;

import java.util.List;

public class ShoppingCarRequest {

    private List<ShoppingCar> goods;
    private ShoppingCar good;
    private List<ShoppingCar> deleteGoods;
    private String address;
    private User user;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setDeleteGoods(List<ShoppingCar> deleteGoods) {
        this.deleteGoods = deleteGoods;
    }

    public List<ShoppingCar> getDeleteGoods() {
        return deleteGoods;
    }

    public List<ShoppingCar> getGoods() {
        return goods;
    }

    public void setGoods(List<ShoppingCar> goods) {
        this.goods = goods;
    }

    public ShoppingCar getGood() {
        return good;
    }

    public void setGood(ShoppingCar good) {
        this.good = good;
    }
}
