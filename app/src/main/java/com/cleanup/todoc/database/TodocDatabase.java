package com.cleanup.todoc.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class TodocDatabase extends RoomDatabase {

    // --- DAO ---
    abstract ProjectDao projectDao();

    // --- SINGLETON ---
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile TodocDatabase INSTANCE;

    // --- INSTANCE ---
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TodocDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "todoc_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
