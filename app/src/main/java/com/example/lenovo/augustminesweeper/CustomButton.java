package com.example.lenovo.augustminesweeper;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

/**
 * Created by Lenovo on 29-08-2017.
 */

public class CustomButton extends Button {
    boolean flagged;
    boolean visited;
    int noOfBombs;

    public CustomButton(Context context) {
        super(context);
        setBackgroundResource(R.drawable.bg_buttons);
        setTextSize(20);
        noOfBombs = 0;
        visited=false;
        flagged=false;
    }


}

