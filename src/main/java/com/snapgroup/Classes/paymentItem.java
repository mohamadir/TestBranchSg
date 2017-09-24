package com.snapgroup.Classes;

/**
 * Created by snapgroup2 on 19/07/17.
 */

public class paymentItem {
    public String type,price,cardStatus,cardNumber;

    public paymentItem(String type, String price, String cardStatus, String cardNumber) {
        this.type = type;
        this.price = price;
        this.cardStatus = cardStatus;
        this.cardNumber = cardNumber;
    }



}
