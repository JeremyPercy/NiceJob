package nl.jeremypercy.nicejob.util;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Loading {

    public static void hide(ConstraintLayout mainContainer, ConstraintLayout progressContainer) {
        mainContainer.setVisibility(View.GONE);
        progressContainer.setVisibility(View.VISIBLE);
    }

    public static void show(ConstraintLayout mainContainer, ConstraintLayout progressContainer) {
        mainContainer.setVisibility(View.VISIBLE);
        progressContainer.setVisibility(View.GONE);
    }
}
