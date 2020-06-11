package com.cleanup.todoc;


import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    private volatile TodocDatabase mDatabase;

    private static long PROJECT_ID = 1L;
    private static Project PROJECT_FOR_TESTS = new Project(PROJECT_ID, "Projet Tartampion", 0xFFEADAD1);
    private static Task NEW_TASK_ONE = new Task(1L, PROJECT_ID, "Task one", System.currentTimeMillis());
    private static Task NEW_TASK_TWO = new Task(2L, PROJECT_ID, "Task two", System.currentTimeMillis());
    private static Task NEW_TASK_TREE = new Task(3L, PROJECT_ID, "Task one", System.currentTimeMillis());

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        mDatabase = Room.inMemoryDatabaseBuilder(context,  TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDB() {
        mDatabase.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        mDatabase.projectDao().insertProject(PROJECT_FOR_TESTS);
        Project project = LiveDataTestUtils.getValue(mDatabase.projectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_FOR_TESTS.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getTaskWhenNoTask_shouldFail() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtils.getValue(mDatabase.mTaskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        mDatabase.projectDao().insertProject(PROJECT_FOR_TESTS);
        mDatabase.mTaskDao().insertTask(NEW_TASK_ONE);
        mDatabase.mTaskDao().insertTask(NEW_TASK_TWO);
        mDatabase.mTaskDao().insertTask(NEW_TASK_TREE);

        List<Task> tasks = LiveDataTestUtils.getValue(mDatabase.mTaskDao().getTasks(PROJECT_ID));
        assertEquals(3,tasks.size());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        mDatabase.projectDao().insertProject(PROJECT_FOR_TESTS);
        mDatabase.mTaskDao().insertTask(NEW_TASK_TWO);
        Task taskAdded = LiveDataTestUtils.getValue(mDatabase.mTaskDao().getTasks(PROJECT_ID)).get(0);
        mDatabase.mTaskDao().deleteTask(taskAdded.getId());

        List<Task> tasks = LiveDataTestUtils.getValue(mDatabase.mTaskDao().getTasks(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }
 }
