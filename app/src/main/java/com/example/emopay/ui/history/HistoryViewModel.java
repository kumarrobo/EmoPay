package com.example.emopay.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public HistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is History fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}