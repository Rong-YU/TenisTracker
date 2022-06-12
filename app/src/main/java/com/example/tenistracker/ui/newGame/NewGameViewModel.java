package com.example.tenistracker.ui.newGame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewGameViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NewGameViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment ga");
    }

    public LiveData<String> getText() {
        return mText;
    }
}