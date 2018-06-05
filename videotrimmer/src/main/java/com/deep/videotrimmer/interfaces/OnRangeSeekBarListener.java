package com.deep.videotrimmer.interfaces;

import com.deep.videotrimmer.view.RangeSeekBarView;

/**
 * Created by Deep Patel
 * (Sr. Android Developer)
 * on 6/4/2018
 */
public interface OnRangeSeekBarListener {
    void onCreate(RangeSeekBarView rangeSeekBarView, int index, float value);

    void onSeek(RangeSeekBarView rangeSeekBarView, int index, float value);

    void onSeekStart(RangeSeekBarView rangeSeekBarView, int index, float value);

    void onSeekStop(RangeSeekBarView rangeSeekBarView, int index, float value);
}
