package com.kevoroid.andhackernews.ui.storydetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.ui.BaseFragment;

/**
 * Created by kevin on 5/31/17.
 */

public class StoryWebViewFragment extends BaseFragment {

    private ActionBar actionBar;

    public static StoryWebViewFragment newInstance(String title, String url, int commentsCount, String commentList) {
        Bundle args = new Bundle();
        StoryWebViewFragment fragment = new StoryWebViewFragment();
        args.putString("storyTitle", title);
        args.putString("storyUrl", url);
        args.putInt("storyCommentsCount", commentsCount);
        args.putString("storyCommentsList", commentList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        }
        if (actionBar != null) {
            actionBar.setTitle(getArguments() != null ? getArguments().getString("storyTitle") : getResources().getString(R.string.app_name));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.story_webview_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.story_progressbar);
        WebView webView = (WebView) view.findViewById(R.id.story_webview);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.INVISIBLE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }

                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        webView.loadUrl(getArguments() != null ? getArguments().getString("storyUrl") : null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.comments, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.story_menu_comments:
                StoryDetailFragment storyDetailFragment = StoryDetailFragment.newInstance(
                        getArguments() != null ? getArguments().getString("storyTitle") : "No title found!",
                        getArguments() != null ? getArguments().getInt("storyCommentsCount") : 0,
                        getArguments().getString("storyCommentsList", "N/A"));
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.main_activity_fragment_container, storyDetailFragment).addToBackStack(storyDetailFragment.getTag()).commit();
                }
                return true;
            case R.id.story_menu_share:
                shareUrl();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void shareUrl() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getArguments() != null ? getArguments().getString("storyUrl") : null);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share the URL"));
    }
}
