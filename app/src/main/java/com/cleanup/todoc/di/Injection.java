package com.cleanup.todoc.di;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.ui.ViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {
    public static TaskRepository provideItemDataSource(Context context) {
        TodocDatabase database = TodocDatabase.getDatabase(context);
        return new TaskRepository(database.mTaskDao());
    }

    public static ProjectRepository provideUserDataSource(Context context) {
        TodocDatabase database = TodocDatabase.getDatabase(context);
        return new ProjectRepository(database.projectDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskRepository dataSourceTask = provideItemDataSource(context);
        ProjectRepository dataSourceProject = provideUserDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceProject, dataSourceTask, executor);
    }
}
