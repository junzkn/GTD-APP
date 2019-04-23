package com.jun.gtd.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.jun.bubble.BubbleLayout;
import com.jun.gtd.R;

public class BubbleUtils {

    public static BubbleLayout get(Context activity) {
        BubbleLayout bl = new BubbleLayout(activity);
//        bl.setShadowColor(ContextCompat.getColor(activity, R.color.colorAccent));
        return bl;
    }

    public static BubbleLayout getAmber(Activity activity) {
        BubbleLayout bl = new BubbleLayout(activity);
        bl.setBubbleColor(ContextCompat.getColor(activity, R.color.amber_800));
        bl.setShadowColor(ContextCompat.getColor(activity, R.color.overlay_dark_80));
        return bl;
    }
}
