package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * ProjectRepository connects the Project table from database with ViewModel and UI (MainActivity).
 */
public class ProjectRepository {
    private final ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    public LiveData<Project> getProject(long projectId) {
        return mProjectDao.getProject(projectId);
    }

    public LiveData<List<Project>> getProjects() {
        return mProjectDao.getProjects();
    }
}
