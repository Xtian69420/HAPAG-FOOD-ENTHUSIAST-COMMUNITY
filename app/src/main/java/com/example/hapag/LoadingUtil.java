package com.example.hapag;
import android.view.View;
import android.widget.ProgressBar;

public class LoadingUtil {

    public static void showLoading(ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public static void hideLoading(ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}

// copy this code to other .xml file to implement it to other activity
/*
<ProgressBar
    android:id="@+id/progressBar"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:visibility="gone"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />

 */
// before set content view : ProgressBar progressBar;
// after set content view: progressBar = findViewById(R.id.progressBar);

// event triggered:

/*
LoadingUtil.showLoading(progressBar);
--------
new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingUtil.hideLoading(progressBar);
                        // actions
                    }
                }, 2000);
 */

