package com.elegion.test.behancer.ui.profile;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.elegion.test.behancer.AppDelegate;
import com.example.data.Storage;
import com.example.data.api.BehanceApi;
import com.elegion.test.behancer.utils.ApiUtils;
import com.elegion.test.behancer.utils.DateUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import toothpick.Toothpick;

public class ProfileViewModel extends ViewModel {
    public static final String TAG = "Debug";
    private Disposable mDisposable;

    @Inject
    BehanceApi mApi;
    @Inject
    Storage mStorage;


    private String mUsername;
    private ObservableField<String> mDisplayedName = new ObservableField<>();
    private ObservableField<String> mProfileCreatedOn = new ObservableField<>();
    private ObservableField<String> mProfileLocation = new ObservableField<>();
    private ObservableField<String> mProfileImageUrl = new ObservableField<>();


    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::getProfile;

    public void getProfile() {
        mDisposable = mApi.getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> {
                    mStorage.insertUser(response);
                    mIsErrorVisible.postValue(false);
                })
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .subscribe(
                        response -> {

                            mDisplayedName.set(response.getUser().getDisplayName());
                            mProfileCreatedOn.set(DateUtils.format(response.getUser().getCreatedOn()));
                            mProfileLocation.set(response.getUser().getLocation());
                            mProfileImageUrl.set(response.getUser().getImage().getPhotoUrl());

                            mIsErrorVisible.postValue(false);
                            Log.d(TAG, "getProfile: " + mIsErrorVisible.getValue());
                        },
                        throwable -> {
                            mIsErrorVisible.setValue(true);
                        });
    }


    @Inject
    public ProfileViewModel(String userName) {
        Toothpick.inject(this, Toothpick.openScope(AppDelegate.class));
        mUsername = userName;
        mOnRefreshListener.onRefresh();
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }


    public ObservableField<String> getDisplayedName() {
        return mDisplayedName;
    }


    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }


    public MutableLiveData<Boolean> getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public ObservableField<String> getProfileCreatedOn() {
        return mProfileCreatedOn;
    }

    public ObservableField<String> getProfileLocation() {
        return mProfileLocation;
    }

    public ObservableField<String> getProfileImageUrl() {
        return mProfileImageUrl;
    }

    @Override
    public void onCleared() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
