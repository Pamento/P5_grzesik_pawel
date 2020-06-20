package com.cleanup.todoc.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * ViewModel brings together the project and task repository.
 * and execute via Android Executor the request for database.
 *
 * Also ViewModel keeps the memory of filters apply to RecyclerView with field: sortMethod.
 */
public class MainViewModel extends ViewModel {

    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;
    @NonNull
    public MainActivity.SortMethod sortMethod = MainActivity.SortMethod.NONE;

    public MainViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        mExecutor = executor;
    }

    public LiveData<Project> getProject(long projectId) { return mProjectRepository.getProject(projectId); }
    public LiveData<List<Project>> getProjects() { return mProjectRepository.getProjects(); }
    public LiveData<List<Task>> getTasks() { return mTaskRepository.getTasks(); }
    public void createTask(Task task) {
        mExecutor.execute(()-> mTaskRepository.createTask(task));
    }
    public void deleteTask(long taskId) {
        mExecutor.execute(()-> mTaskRepository.deleteTask(taskId));
    }
}
