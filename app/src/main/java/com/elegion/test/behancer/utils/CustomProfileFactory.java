package com.elegion.test.behancer.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elegion.test.behancer.ui.profile.ProfileViewModel;

public class CustomProfileFactory extends ViewModelProvider.NewInstanceFactory {


    private String mUserName;

    public CustomProfileFactory( String userName) {
        mUserName = userName;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileViewModel(mUserName);
    }
}
