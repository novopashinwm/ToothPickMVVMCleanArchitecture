package com.example.data.repository;

import com.example.data.api.BehanceApi;
import com.example.domain.model.user.User;
import com.example.domain.model.user.UserResponse;
import com.example.domain.repository.ProfileRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class ProfileServerRepository implements ProfileRepository {

    @Inject
    BehanceApi mApi;

    @Inject
    public ProfileServerRepository() {
    }

    @Override
    public Single<User> getUser(String username) {
        return mApi.getUserInfo(username).map(new Function<UserResponse, User>() {
            @Override
            public User apply(UserResponse userResponse) throws Exception {
                return userResponse.getUser();
            }
        });
    }

    @Override
    public void insertUser(User user) {
        // empty
    }
}
