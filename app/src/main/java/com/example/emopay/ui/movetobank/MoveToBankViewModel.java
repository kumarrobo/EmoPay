package com.example.emopay.ui.movetobank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoveToBankViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MoveToBankViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}