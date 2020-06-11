package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class},version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    // --- DAO ---
    public abstract ProjectDao projectDao();
    public abstract TaskDao mTaskDao();

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

    private static Callback populateDatabaseWithProjects() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1L);
                contentValues.put("name","Projet Tartampion");
                contentValues.put("color", 0xFFEADAD1);
//                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
//                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
//                new Project(3L, "Projet Circus", 0xFFA3CED2),
                db.insert("Project", OnConflictStrategy.IGNORE,contentValues);
            }
        };
    }

    /**
     * Other way to prepopulate the database;
     */
//    private void populateInitialData() {
//        if (cheese().count() == 0) {
//            runInTransaction(new Runnable() {
//                @Override
//                public void run() {
//                    Project project = new Project();
//                    for (int i = 0; i < Cheese.CHEESES.length; i++) {
//                        cheese.name = Cheese.CHEESES[i];
//                        cheese().insert(cheese);
//                    }
//                }
//            });
//        }
//    }
}
