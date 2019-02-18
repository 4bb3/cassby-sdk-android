package com.cassby.cassbysdk.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class CheckItem {

    @PrimaryKey
    @NonNull
    public String uuid = UUID.randomUUID().toString();
    public String uuid_check;
    public Double qty = 1.0;
    public int price = 0;
    public boolean isSynced = false;
    public String name = "";

    public CheckItem () {}

    public CheckItem(Check check, Double qty, int price, String name) {
        this.uuid_check = check.uuid;
        this.qty = qty;
        this.price = price;
        this.name = name;
    }

}
