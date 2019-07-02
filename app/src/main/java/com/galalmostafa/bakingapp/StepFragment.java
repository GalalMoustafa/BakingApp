package com.galalmostafa.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.galalmostafa.bakingapp.Models.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    View rootView;
    @BindView(R.id.imgStepView)ImageView stepImg;
    @BindView(R.id.step_detail)TextView stepDetails;
    @BindView(R.id.next_btn)Button nextBtn;
    @BindView(R.id.prev_btn)Button prevBtn;
    @BindView(R.id.videoView)SimpleExoPlayerView mPlayer;
    SimpleExoPlayer player;
    private int counter;
    private Steps stepModel;
    private int newCounter = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.step_details_fragment, container, false);
        ButterKnife.bind(this,rootView);
        if (savedInstanceState == null){
            Bundle bundle = this.getArguments();
            if (bundle != null){
                counter = bundle.getInt("count", 0);
            }

        }else {
            counter = savedInstanceState.getInt("counter");
        }
        stepModel = ((RecipeActivity)getActivity()).recipe.getSteps().get(counter);
        Initialize();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter",counter);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void Initialize(){
        seeImgOrVideo();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNext();
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrevious();
            }
        });
        stepDetails.setText(stepModel.getDescription());
    }

    private void setNext(){
        if (counter == ((RecipeActivity)getActivity()).recipe.getSteps().size()-1 ){
            Toast.makeText(getContext(),"This the Last Step !", Toast.LENGTH_SHORT).show();
        }else {
            newCounter = counter+1;
            setNewFragment(newCounter);
        }
    }

    private void setPrevious(){
        if (counter == 0){
            Toast.makeText(getContext(),"This the First Step !", Toast.LENGTH_SHORT).show();
        }else {
            newCounter = counter-1;
            setNewFragment((counter-1));
        }
    }

    private void setNewFragment(int newCounter){
        StepFragment stepFragment = new StepFragment();
        Bundle b = new Bundle();
        b.putInt("count", newCounter);
        stepFragment.setArguments(b);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (((RecipeActivity)getActivity()).isTwoPane){
            fragmentTransaction.replace(R.id.details_container, stepFragment, "detailsFragment");
        }else {
            fragmentTransaction.replace(R.id.container, stepFragment, "recipeFragment");
        }
        getActivity().getSupportFragmentManager().popBackStack();
        fragmentTransaction.commit();
    }

    private void seeImgOrVideo(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (!stepModel.getVideoURL().isEmpty()){
                prevBtn.setVisibility(View.GONE);
                nextBtn.setVisibility(View.GONE);
                stepDetails.setVisibility(View.GONE);
                stepImg.setVisibility(View.GONE);
                setPlayer();
            } else {
                mPlayer.setVisibility(View.GONE);
                if (stepModel.getThumbnailURL().isEmpty()){
                    stepImg.setVisibility(View.GONE);
                }
            }
        }else {
            if (stepModel.getThumbnailURL().isEmpty()){
                stepImg.setVisibility(View.GONE);
            }else {
                Picasso.get().load(stepModel.getThumbnailURL()).into(stepImg);
            }
            if(stepModel.getVideoURL().isEmpty()){
                mPlayer.setVisibility(View.GONE);
            }else {
                setPlayer();
            }
        }
    }

    private void setPlayer(){
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mPlayer.setPlayer(player);
            player.setPlayWhenReady(true);
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(stepModel.getVideoURL()),
                    new DefaultHttpDataSourceFactory("userAgent"),
                    new DefaultExtractorsFactory(),
                    null, null);
            player.prepare(mediaSource, true, false);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
