package com.cleanup.todoc.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getItems(long projectId);

    @Insert
    long insertItem(Task task);

    @Update
    int updateItem(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteItem(long taskId);

    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    Cursor getItemsWithCursor(long projectId);
}
