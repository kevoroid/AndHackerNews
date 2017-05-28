package com.kevoroid.andhackernews.ui;

import android.support.v4.app.Fragment;

import com.kevoroid.andhackernews.AndHackerNewsApplication;

/**
 * Created by kevin on 5/27/17.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        AndHackerNewsApplication.getRequestQueue().cancelAll(this.getClass());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
