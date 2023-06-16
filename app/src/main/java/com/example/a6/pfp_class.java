package com.example.a6;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class pfp_class {
    private Drawable imageView;

    public pfp_class(Drawable imageView){
        this.imageView = imageView;
    }

    public Drawable getImageView() {
        return imageView;
    }
}
