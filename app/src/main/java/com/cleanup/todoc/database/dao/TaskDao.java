package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Class of request for SQLite for Task table in "todoc_database".
 */
@Dao
public interface TaskDao {
    @Query("SELECT * FROM task WHERE projectId = :projectId")
    LiveData<List<Task>> getTasks(long projectId);

    @Insert
    void insertTask(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getTasks();

    @Query("DELETE FROM task WHERE id = :taskId")
    void deleteTask(long taskId);
}
