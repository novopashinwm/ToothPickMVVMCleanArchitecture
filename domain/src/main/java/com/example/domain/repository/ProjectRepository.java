package com.example.domain.repository;

import com.example.domain.model.project.Project;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by tanchuev on 24.04.2018.
 */

public interface ProjectRepository {
    String SERVER = "SERVER";
    String DB = "DB";

    Single<List<Project>> getProjects();

    void insertProjects(List<Project> projects);

}
