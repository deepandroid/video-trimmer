package com.deep.videotrimmerexample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.deep.videotrimmerexample.databinding.DialogVideoPickerBinding;


public abstract class VideoPicker extends BottomSheetDialog implements View.OnClickListener {
    protected long lastClickTime = 0;
    DialogVideoPickerBinding mBinder;

    public VideoPicker(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_video_picker, null, false);
        setContentView(mBinder.getRoot());

        mBinder.camera.setOnClickListener(this);
        mBinder.gallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        preventDoubleClick(view);
        dismiss();
        switch (view.getId()) {
            case R.id.camera:
                onCameraClicked();
                break;
            case R.id.gallery:
                onGalleryClicked();
                break;
        }
    }

    private void preventDoubleClick(View view) {
        /*// preventing double, using threshold of 1000 ms*/
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();
    }

    protected abstract void onCameraClicked();

    protected abstract void onGalleryClicked();
}