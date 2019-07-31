package com.elegion.test.behancer.ui.projects;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.BuildConfig;
import com.example.data.Storage;
import com.example.data.api.BehanceApi;
import com.example.domain.model.project.ProjectResponse;
import com.example.domain.model.project.RichProject;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import toothpick.Toothpick;

/**
 * @author Azret Magometov
 */
public class ProjectsViewModel extends ViewModel {

    private Disposable mDisposable;

    @Inject
    Storage mStorage;

    @Inject
    BehanceApi mApi;

    ProjectsAdapter.OnItemClickListener mOnItemClickListener;
    private LiveData<PagedList<RichProject>> mProjects;
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::updateProjects;

    @Inject
    public ProjectsViewModel(ProjectsAdapter.OnItemClickListener onItemClickListener) {
        Toothpick.inject(this, Toothpick.openScope(AppDelegate.class));
        mOnItemClickListener = onItemClickListener;
        mProjects = mStorage.getProjectsPaged();
        updateProjects();

    }

    private void updateProjects() {
        mDisposable = mApi.getProjects(com.example.data.BuildConfig.API_QUERY)
                .map(ProjectResponse::getProjects)
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .doOnSuccess(response -> mIsErrorVisible.postValue(false))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> mStorage.insertProjects(response),
                        throwable -> {
                            boolean value = mProjects.getValue() == null || mProjects.getValue().size() == 0;
                            mIsErrorVisible.postValue(value);
                        });

    }

    @Override
    public void onCleared() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public ProjectsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public LiveData<PagedList<RichProject>> getProjects() {
        return mProjects;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}