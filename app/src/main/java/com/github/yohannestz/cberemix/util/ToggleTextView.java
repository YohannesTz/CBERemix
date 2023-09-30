package com.github.yohannestz.cberemix.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.github.yohannestz.cberemix.R;

public class ToggleTextView extends AppCompatTextView {
    private boolean isOn;
    private String textOn;
    private String textOff;

    public ToggleTextView(Context context) {
        super(context);
        initialize(null);
    }

    public ToggleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public ToggleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        isOn = false;
        updateText();

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ToggleTextView);
            textOn = typedArray.getString(R.styleable.ToggleTextView_textOn);
            textOff = typedArray.getString(R.styleable.ToggleTextView_textOff);
            typedArray.recycle();
        }

        setOnClickListener(v -> toggle());
    }

    public void toggle() {
        isOn = !isOn;
        updateText();
    }

    private void updateText() {
        setText(isOn ? (textOn != null ? textOn : "") : (textOff != null ? textOff : ""));
    }

    public boolean isOn() {
        return isOn;
    }

    public boolean isOff() {
        return !isOn;
    }

    public void setTextOn(String textOn) {
        this.textOn = textOn;
        updateText();
    }

    public void setTextOff(String textOff) {
        this.textOff = textOff;
        updateText();
    }
}