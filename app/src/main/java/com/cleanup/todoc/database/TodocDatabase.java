package com.cleanup.todoc.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.Resources;

@Database(entities = {Project.class, Task.class},version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    // --- DAO ---
    public abstract ProjectDao projectDao();
    public abstract TaskDao mTaskDao();

    // --- SINGLETON ---
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile TodocDatabase INSTANCE;

    // --- INSTANCE ---
//    private static final int NUMBER_OF_THREADS = 4;
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static TodocDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "todoc_database")
                            .build();
                    INSTANCE.populateInitialData();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Prepopulate the database if the "project" table is empty.
     */
    private void populateInitialData() {
        if (projectDao().count() == 0) {
            runInTransaction(() -> {
                Project[] projects = Resources.allProjects;

                for (Project project : projects) {
                    projectDao().insertProject(project);
                }
            });
        }
    }
}
