package com.shishic.cb.view;

import android.content.Context;
import android.util.AttributeSet;

public class NfProgressBar extends  android.support.v7.widget.AppCompatImageView {
    NfLogoMaterialProgressDrawable nf;

    public NfProgressBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public NfProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        nf = new NfLogoMaterialProgressDrawable(context, this);
        nf.setAlpha(255);
        this.setImageDrawable(nf);
        this.setMaxHeight(30);
        this.setMaxWidth(30);
        this.setMinimumHeight(30);
        this.setMinimumWidth(30);
        //this.setBackgroundResource(R.drawable.nf_progressbar_bg);
    }

    public void start() {
        nf.start();
    }

    @Override
    public void setVisibility(int visibility) {
        if (VISIBLE == visibility) {
            nf.start();
        } else {
            nf.stop();
        }
        super.setVisibility(visibility);
    }

    public void stop() {
        nf.stop();
    }


}
