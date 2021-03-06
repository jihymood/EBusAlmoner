package com.xpro.ebusalmoner.datasource;

import org.xutils.DbManager;
import org.xutils.x;

import android.annotation.SuppressLint;

public class DatabaseOpenHelper {
    private DbManager.DaoConfig daoConfig;
    private static DbManager db;
    private final String DB_NAME = "almoner.db";
    private final int VERSION = 2;

    private DatabaseOpenHelper() {
        daoConfig = new DbManager.DaoConfig().setDbName(DB_NAME)
                .setDbVersion(VERSION)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                        // 开启WAL, 对写入加速提升巨大(作者原话)
                    }
                }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion,
                                          int newVersion) {
                        // 数据库升级操作
                    }
                });
        db = x.getDb(daoConfig);
    }

    public static DbManager getInstance() {
        if (db == null) {
            DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper();
        }
        return db;
    }
}
