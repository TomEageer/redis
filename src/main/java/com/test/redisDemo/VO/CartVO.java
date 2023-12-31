package com.test.redisDemo.VO;

import java.util.List;

public class CartVO {
    /**
     * 购物项
     */
    private List<CartItemVO> cartItems;

    /**
     * 购物车总价格
     */
    private Integer totalAmount;

    /**
     * 总价格
     * @return
     */
    public int getTotalAmount() {
        return cartItems.stream().mapToInt(CartItemVO::getTotalPrice).sum();
    }

    public List<CartItemVO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemVO> cartItems) {
        this.cartItems = cartItems;
    }
}