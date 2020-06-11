package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Comparator;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "task", foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "projectId"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;
    // Suppress warning because setName is called in constructor
    //@SuppressWarnings("NullableProblems")
    @NonNull
    private String name;
    private long creationTimestamp;
    private long projectId;

    public Task(long id, long projectId, @NonNull String name, long creationTimestamp) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }
//    public Task(long id, long projectId, @NonNull String name, long creationTimestamp) {
//        this.setId(id);
//        this.setProjectId(projectId);
//        this.setName(name);
//        this.setCreationTimestamp(creationTimestamp);
//    }
//
//    private void setId(long id) {
//        this.id = id;
//    }
//
//    private void setProjectId(long projectId) {
//        this.projectId = projectId;
//    }
//
//    @Nullable
//    public Project getProject() {
//        return Project.getProjectById(projectId);
//    }
//
//    @NonNull
//    public String getName() {
//        return name;
//    }
//
//    private void setName(@NonNull String name) {
//        this.name = name;
//    }
//
//    private void setCreationTimestamp(long creationTimestamp) {
//        this.creationTimestamp = creationTimestamp;
//    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
