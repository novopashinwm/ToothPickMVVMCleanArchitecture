package com.elegion.test.behancer.di;

import com.example.domain.service.ProfileService;
import com.example.domain.service.ProfileServiceImpl;
import com.example.domain.service.ProjectService;
import com.example.domain.service.ProjectServiceImpl;

import toothpick.config.Module;

/**
 * Created by tanchuev on 23.04.2018.
 */


public class ServiceModule extends Module {

    public ServiceModule() {
        bind(ProjectService.class).toInstance(provideProjectService());
        bind(ProfileService.class).toInstance(provideProfileService());

    }

    ProjectService provideProjectService() {
        return new ProjectServiceImpl();
    }

    ProfileService provideProfileService( ) {
        return new ProfileServiceImpl();
    }

}
