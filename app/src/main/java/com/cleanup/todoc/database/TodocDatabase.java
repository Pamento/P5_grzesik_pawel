package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.Resources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Creation of DataBase with two table: Project and Task.
 * The Project table is prepopulate with 3 projects
 * if the table is empty in moment of start of application.
 *
 * Call for this class return an singleton INSTANCE of database.
 */
@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
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

    public static TodocDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "todoc_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Prepopulate the database with the projects if "Project" table is empty.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background. Unlimited add.
                ProjectDao projectDao = INSTANCE.projectDao();
                if (projectDao.getProjects() == null) {
                    Project[] projects = Resources.allProjects;

                    for (Project project : projects) {
                        projectDao.insertProject(project);
                    }
                }
            });
        }
    };
}
