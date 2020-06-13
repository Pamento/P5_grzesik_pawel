package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {
    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;
    private LiveData<List<Project>> mProjects;

    public MainViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        mExecutor = executor;
    }

    public void init() {
        if (mProjects != null) {
            return;
        }
        mProjects = mProjectRepository.getProjects();
    }

    public LiveData<List<Project>> getProjects() { return mProjects; }
    public LiveData<List<Task>> getTasks() { return mTaskRepository.getTasks(); }
    public void createTask(Task task) {
        mExecutor.execute(()->{
            mTaskRepository.createTask(task);
        });
    }
    public void deleteTask(long taskId) {
        mExecutor.execute(()->{
            mTaskRepository.deleteTask(taskId);
        });
    }
}
