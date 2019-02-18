package com.cassby.cassbysdk.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class Payment {

    @PrimaryKey
    @NonNull
    public String uuid = UUID.randomUUID().toString();
    public String uuid_check;
    public int amount = 0;
    public int cash = 0;
    public String error_details;
    public int id = 0;
    public boolean isSynced = false;
    public String method = "card";
    public String status = "success";
    public String pne_error_code = "";

    public Payment() {}

    public Payment(Check check) {
        this.uuid_check = check.uuid;

        int total = 0;

        for (int i = 0; i < check.items.size(); i++) {
            total = total +  (int) ((double) check.items.get(i).price * check.items.get(i).qty);
        }

        this.amount = total;
    }

}
