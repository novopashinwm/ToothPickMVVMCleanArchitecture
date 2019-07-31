package com.elegion.test.behancer.di;

import com.example.data.repository.ProfileDBRepository;
import com.example.data.repository.ProfileServerRepository;
import com.example.data.repository.ProjectDBRepository;
import com.example.data.repository.ProjectServerRepository;
import com.example.domain.repository.ProfileRepository;
import com.example.domain.repository.ProjectRepository;

import toothpick.config.Module;

/**
 * Created by tanchuev on 23.04.2018.
 */

public class RepositoryModule extends Module {

    public RepositoryModule() {
        bind(ProjectRepository.class).withName(ProjectRepository.SERVER).toInstance(provideProjectServerRepository());
        bind(ProjectRepository.class).withName(ProjectRepository.DB).toInstance(provideProjectDBRepository());
        bind(ProfileRepository.class).withName(ProjectRepository.SERVER).toInstance(provideProfileServerRepository());
        bind(ProfileRepository.class).withName(ProjectRepository.DB).toInstance(provideProfileDBRepository());
    }

    ProjectRepository provideProjectServerRepository() {
        return new ProjectServerRepository();
    }


    ProjectRepository provideProjectDBRepository() {
        return new ProjectDBRepository();
    }


    ProfileRepository provideProfileServerRepository() {
        return new ProfileServerRepository();
    }

    ProfileRepository provideProfileDBRepository() {
        return new ProfileDBRepository();
    }

}
