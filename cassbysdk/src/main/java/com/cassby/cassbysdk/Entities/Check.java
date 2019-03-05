package com.cassby.cassbysdk.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.cassby.cassbysdk.Helpers.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Check {

    @PrimaryKey
    @NonNull
    public String uuid = UUID.randomUUID().toString();
    public int total;
    public String dt_created = DateTime.getCurrentDayAndTime();
    public boolean isSynced = false;
    @Ignore
    public List<CheckItem> items = new ArrayList<CheckItem>();
    @Ignore
    public Payment payment;
    public int id_branch;

    public Check(int id_branch) {
        this.id_branch = id_branch;
    }
    public void addCheckItem(String name, int price, double qty) {
        this.items.add(new CheckItem(this, qty, price,  name));
    }

    public void addPayment() {
        this.payment = new Payment(this);
        this.total = payment.amount;
    }

}
