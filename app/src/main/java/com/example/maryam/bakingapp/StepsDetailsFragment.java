package com.example.maryam.bakingapp;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maryam.bakingapp.Utils.AppUtils;
import com.example.maryam.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsDetailsFragment extends Fragment {

    //   private Step step;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private TextView shortDesc, Desc;
    private ImageView thumbnail;
    private long position;
    private Step steps;
    private boolean playIfready = true;

    public StepsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_details, container, false);
        shortDesc = (TextView) view.findViewById(R.id.shortDescription_tv);
        Desc = (TextView) view.findViewById(R.id.steps_description_tv);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail_imgv);
        mSimpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.steps_exoplayer);
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(AppUtils.EXO_POSITION);
            playIfready = savedInstanceState.getBoolean(AppUtils.PLAY_IF_READY);
        }
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey(AppUtils.SEND_STEP)) {
            steps = bundle.getParcelable(AppUtils.SEND_STEP);
        }
        if (!steps.getVideoURL().isEmpty()) {
            String mimeType = AppUtils.getMimeType(getContext(), Uri.parse(steps.getVideoURL()));
            if (mimeType.startsWith(AppUtils.MIME_IMAGE)) {
                steps.swapVideoWithThumb();
            }
        }
        if (!steps.getThumbnailURL().isEmpty()) {
            String mimeType = AppUtils.getMimeType(getContext(), Uri.parse(steps.getThumbnailURL()));
            if (mimeType.startsWith(AppUtils.MIME_VIDEO)) {
                steps.swapVideoWithThumb();
            }
        }

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        shortDesc.setText(steps.getShortDescription());
        Desc.setText(steps.getDescription());
        if (!TextUtils.isEmpty(steps.getVideoURL())) {
            initializerPlayer(Uri.parse(steps.getVideoURL()));
        } else if (!steps.getThumbnailURL().isEmpty()) {

            thumbnail.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(steps.getThumbnailURL()).into(thumbnail);
        }

    }

    public void initializerPlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            mSimpleExoPlayerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);
            mSimpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            checkForLandScape();
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (position > 0)
                mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(playIfready);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            playIfready = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    public void checkForLandScape() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Desc.setVisibility(View.GONE);
            shortDesc.setVisibility(View.GONE);
            mSimpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppUtils.PLAY_IF_READY, playIfready);
        outState.putLong(AppUtils.EXO_POSITION, position);
    }

}
