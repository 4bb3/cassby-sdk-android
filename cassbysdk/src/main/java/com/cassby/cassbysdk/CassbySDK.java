package com.cassby.cassbysdk;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.cassby.cassbysdk.Activity.PostActivity;
import com.cassby.cassbysdk.Activity.PostActivityApi;
import com.cassby.cassbysdk.Database.Database;
import com.cassby.cassbysdk.Entities.Check;
import com.cassby.cassbysdk.Entities.CheckItem;
import com.cassby.cassbysdk.Entities.Payment;
import com.cassby.cassbysdk.NetworkModel.ActivityResponse;
import com.cassby.cassbysdk.NetworkModel.ActivityRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CassbySDK {

    private static Database db;
    private Check check;
    private static CassbySDK shared = null;
    private String token = "";
    private long syncPeriod = 10;

    private CassbySDK() {};

    public static CassbySDK getInstance() {
        if (shared == null) {
            shared = new CassbySDK();
        }
        return shared;
    }

    public void launch(Context context, String token) {
        db = Room.databaseBuilder(context, Database.class, "database-name")
                .fallbackToDestructiveMigration()
                .build();
        this.token = token;

        Observable.interval(syncPeriod, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        loadAndSendToServer();
                    }
                });
    }

    public void loadAndSendToServer() {

        loadChecks().subscribe(new Consumer<List<Check>>() {
            @Override
            public void accept(final List<Check> checks) throws Exception {
                for (int i = 0; i < checks.size(); i++) {

                    final String uuid = checks.get(i).uuid;

                    loadCheckItems(uuid).subscribe(new Consumer<List<CheckItem>>() {
                        @Override
                        public void accept(final List<CheckItem> checkItems) throws Exception {
                            loadPayments(uuid).subscribe(new Consumer<List<Payment>>() {
                                @Override
                                public void accept(List<Payment> payments) throws Exception {
                                    ActivityRoot activityRoot = new ActivityRoot();
                                    activityRoot.activity.check_item.addAll(checkItems) ;
                                    activityRoot.activity.check.addAll(checks);
                                    activityRoot.activity.payment.addAll(payments);

                                    Retrofit retrofit = new PostActivity().getRetrofit();
                                    PostActivityApi api = retrofit.create(PostActivityApi.class);

                                    if (activityRoot.activity.check.isEmpty() == false && activityRoot.activity.check_item.isEmpty() == false && activityRoot.activity.payment.isEmpty() == false) {
                                        api.postActivity(token, activityRoot)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ActivityResponse>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(ActivityResponse activityResponse) {
                                                        for (int i = 0; i < activityResponse.check.size(); i++) {

                                                            String uuid = activityResponse.check.get(i).uuid;

                                                            deleteCheck(uuid);

                                                            for (int j = 0; j < activityResponse.check_item.size(); j++) {

                                                                if (activityResponse.check_item.get(j).uuid_check.equals(uuid)) {
                                                                    deleteCheckItem(uuid);
                                                                }
                                                            }

                                                            for (int k = 0; k < activityResponse.check_item.size(); k++) {
                                                                if (activityResponse.payment.get(k).uuid_check.equals(uuid)) {
                                                                    deletePayment(uuid);
                                                                }
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        Log.d("TAG", e.getLocalizedMessage());
                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }


    public void initCheck(int id_branch, String pneId) {
        this.check = new Check(id_branch, pneId);
    }

    public void addToCheck(String name, int price, Double qty) {
        this.check.addCheckItem(name, price, qty);
    }

    public void commit() {
        this.check.addPayment();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.paymentDao().insertPayment(check.payment);
                db.checkDao().insert(check);

                for (int i = 0; i < check.items.size(); i++) {
                    db.checkItemDao().insertCheckItem(check.items.get(i));
                }
                check = null;
            }
        });
    }

    public void deleteCheck(final String uuid) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.checkDao().deleteByUuid(uuid);
            }
        });
    }

    public void deleteCheckItem(final String uuid) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.checkItemDao().deleteByUuid(uuid);
            }
        });
    }

    public void deletePayment(final String uuid) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.paymentDao().deleteByUuid(uuid);
            }
        });
    }

    public Maybe<List<Check>> loadChecks() {
        return db.checkDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Maybe<List<CheckItem>> loadCheckItems(String uuid) {
        return db.checkItemDao().getByUuid(uuid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Maybe<List<Payment>> loadPayments(String uuid) {
        return db.paymentDao().getByUuid(uuid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }

}
