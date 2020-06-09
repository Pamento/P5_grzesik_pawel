package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {
    private final TaskDao mTaskDao;

    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks(long projectId) {
        return mTaskDao.getTasks(projectId);
    }

    public void createTask(Task task) {
        mTaskDao.insertTask(task);
    }

    public void deleteTask(long taskId) {
        mTaskDao.deleteTask(taskId);
    }
}
