package com.cassby.cassbysdk.Database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.cassby.cassbysdk.Database.Dao.CheckDao;
import com.cassby.cassbysdk.Database.Dao.CheckItemDao;
import com.cassby.cassbysdk.Database.Dao.PaymentDao;
import com.cassby.cassbysdk.Entities.Check;
import com.cassby.cassbysdk.Entities.CheckItem;
import com.cassby.cassbysdk.Entities.Payment;

@android.arch.persistence.room.Database(entities = {Check.class, CheckItem.class, Payment.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract CheckDao checkDao();
    public abstract CheckItemDao checkItemDao();
    public abstract PaymentDao paymentDao();
}
