package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.concurrent.Executor;

public class MainActivityViewModel extends ViewModel {
    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;
    private LiveData<Project> mProjects;

    public MainActivityViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        mExecutor = executor;
    }

    public void init(long projectID) {
        if (mProjects != null) {
            return;
        }
        mProjects = mProjectRepository.getProjects();
    }

    public LiveData<Project> getProjects() { return mProjects; }



}
