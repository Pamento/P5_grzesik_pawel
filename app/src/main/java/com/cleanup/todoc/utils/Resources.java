package com.cleanup.todoc.utils;

import com.cleanup.todoc.model.Project;

public class Resources {

    /**
     * Array with Projects to prepopulate SQLite database (ROOM)
     */
    public static final Project[] allProjects = new Project[]{
            new Project(1L, "Projet Tartampion", 0xFFEADAD1),
            new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
            new Project(3L, "Projet Circus", 0xFFA3CED2),
    };
}
