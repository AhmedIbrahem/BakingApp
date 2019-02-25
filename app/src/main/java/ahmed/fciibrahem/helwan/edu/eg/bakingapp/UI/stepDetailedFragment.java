package ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Steps;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.R;

public class stepDetailedFragment extends Fragment   {
    SimpleExoPlayer player;
    SimpleExoPlayerView VedioPlayer;
    Button Brevios, next;
    static Steps step;
    ArrayList<Steps> arrayList;
    int posation;
    TextView StepDetailTextView;
    ImageView imageView;
    private  static long posationPlayer;
    private boolean autoPlay = false;
    private  static int currentWindow;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            root = inflater.inflate(R.layout.detailedfragmentland, container, false);
            StepDetailTextView = root.findViewById(R.id.step_detials_text_view);
            StepDetailTextView.setVisibility(View.GONE);

        } else {


            root = inflater.inflate(R.layout.fragment_step_detailed, container, false);
            StepDetailTextView = root.findViewById(R.id.step_detials_text_view);

            Brevios = root.findViewById(R.id.bervios);
            next = root.findViewById(R.id.next);
            imageView=root.findViewById(R.id.iv_thumbnail);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (posation < arrayList.size() - 1) {
                        posation++;
                        Log.d("posation in if", "onCreateView: " + posation);
                        releasePlayer();
                        if (!arrayList.get(posation).getVideoURL().isEmpty()) {
                            //initializePlayer(arrayList.get(posation).getVideoURL());
                            playStepVideo(posation);
                        } else {
                            VedioPlayer.setVisibility(View.GONE);
                        }
                        StepDetailTextView.setText(arrayList.get(posation).getDescription());

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "this is the last vedio", Toast.LENGTH_SHORT).show();
                    }
                    Brevios.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (posation != 0) {
                                posation--;
                                Log.d("posation in if", "onCreateView: " + posation);
                                releasePlayer();
                                if (!arrayList.get(posation).getVideoURL().isEmpty()) {
                                    //initializePlayer(arrayList.get(posation).getVideoURL());
                                    playStepVideo(posation);
                                } else {
                                    VedioPlayer.setVisibility(View.GONE);
                                }
                                StepDetailTextView.setText(arrayList.get(posation).getDescription());
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "this is the firest vedio", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

                  //  Log.d("oneStep", "onCreateView: " + step.getShortDescription().toString());
                    Log.d("steps", "onCreateView: " + arrayList.size());
                    Log.d("posation", "onCreateView: " + posation);
                }


            });


        }
        if(isTablet(getActivity().getApplicationContext()))
        {
            if(savedInstanceState !=null)
            {
                arrayList=savedInstanceState.getParcelableArrayList("steps");
                step= savedInstanceState.getParcelable("step");
                posation= savedInstanceState.getInt("posation");
                posationPlayer = savedInstanceState.getLong("PLAYBACK_POSITION");
                currentWindow = savedInstanceState.getInt("CURRENT_WINDOW_INDEX");
                autoPlay = savedInstanceState.getBoolean("AUTOPLAY", false);
                if(!arrayList.get(posation).getVideoURL().isEmpty())
                {//initializePlayer(arrayList.get(posation).getVideoURL());}
                     playStepVideo(posation);}
                else
                {
                    VedioPlayer.setVisibility(View.GONE);
                }


            }
            else {

                Log.d("stepDetialdFragment", "istablet");
                if (getArguments() != null) {
                }
            }
        }
        else {

            if(savedInstanceState !=null)
            {
                arrayList=savedInstanceState.getParcelableArrayList("steps");
               step= savedInstanceState.getParcelable("step");
               posation= savedInstanceState.getInt("posation");
                posationPlayer = savedInstanceState.getLong("PLAYBACK_POSITION");
                currentWindow = savedInstanceState.getInt("CURRENT_WINDOW_INDEX");
                autoPlay = savedInstanceState.getBoolean("AUTOPLAY", false);

            }
            else
            {
                Intent GetStepData = getActivity().getIntent();
                step = GetStepData.getParcelableExtra("OneStep");
                arrayList = GetStepData.getParcelableArrayListExtra("steps");
                posation = GetStepData.getIntExtra("AdapterPosation", 0);
                StepDetailTextView.setText(arrayList.get(posation).getDescription());

            }

        }
        VedioPlayer = root.findViewById(R.id.videoPlayer);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!arrayList.get(posation).getVideoURL().isEmpty())
        {
            playStepVideo(posation);
           // initializePlayer(arrayList.get(posation).getVideoURL());

        }
        else
        {
            VedioPlayer.setVisibility(View.GONE);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            posationPlayer = player.getCurrentPosition();
            player.stop();
            releasePlayer();
            player = null;
        }}


    private void initializePlayer(String url) {
        // Create a default TrackSelector
        VedioPlayer.setVisibility(View.VISIBLE);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        player = ExoPlayerFactory.newSimpleInstance(getActivity().getApplicationContext(), trackSelector);
        player.seekTo(currentWindow, posationPlayer);

        //Initialize simpleExoPlayerView
        if(url !=null) {
            VedioPlayer.setPlayer(player);
            player.setPlayWhenReady(true);
            VedioPlayer.requestFocus();
        }
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity().getApplicationContext(), Util.getUserAgent(getActivity().getApplicationContext(), "BakingApp"));

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        Uri videoUri = Uri.parse(url);
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);
        // Prepare the player with the source.
        Log.d("initializePlayer", ": "+url);
            player.prepare(videoSource);

    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);}


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments()!=null) {
            Log.d("stepDetialdFragment", "created not null");
            Log.d("stepDetialdFragment", "not null");
            posation = getArguments().getInt("AdapterPosation");
            arrayList = getArguments().getParcelableArrayList("steps");

        }
        }

        public  void getArs()
        {
            if(getArguments()!=null) {
                Log.d("stepDetialdFragment", " args created not null");
                Log.d("stepDetialdFragment", "args not null");
                posation = getArguments().getInt("AdapterPosation");
                arrayList = getArguments().getParcelableArrayList("steps");
                step = getArguments().getParcelable("OneStep");
                if(!arrayList.get(posation).getVideoURL().isEmpty())
                {
                    playStepVideo(posation);
                }
                //initializePlayer(arrayList.get(posation).getVideoURL());}
                else if (!arrayList.get(posation).getThumbnailURL().isEmpty())
                {
                    Picasso.with(getContext())
                            .load(Uri.parse(arrayList.get(posation).getThumbnailURL()))
                            .error(R.mipmap.ic_launcher).into(imageView);
                    imageView.setVisibility(View.VISIBLE);

                }
                else
                {
                   VedioPlayer.setVisibility(View.GONE);
                }

            }

        }


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        if (VedioPlayer != null)
        {
            //this is because if you rotated the screen while it's playing
            outState.putLong("PLAYBACK_POSITION", posationPlayer);
            outState.putInt("CURRENT_WINDOW_INDEX", currentWindow);
            outState.putBoolean("AUTOPLAY", autoPlay);
        }
        outState.putParcelableArrayList("steps",arrayList);
        outState.putInt("posation",posation);
        outState.putParcelable("step",step);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


    private void releasePlayer()
    {
        if (player != null)
        {
            currentWindow=player.getCurrentWindowIndex();
            posationPlayer=player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
    private void playStepVideo(int index)
    {
        VedioPlayer.setVisibility(View.VISIBLE);
        VedioPlayer.requestFocus();
        String videoUrl = arrayList.get(index).getVideoURL();
        String thumbNailUrl = arrayList.get(index).getThumbnailURL();
        if (!videoUrl.isEmpty())
        {
            initializePlayer(videoUrl);
        }
        else if (!thumbNailUrl.isEmpty())
        {
            Picasso.with(getContext())
                    .load(Uri.parse(thumbNailUrl))
                    .error(R.mipmap.ic_launcher).into(imageView);
            imageView.setVisibility(View.VISIBLE);

        }
        else
        {
            VedioPlayer.setVisibility(View.GONE);
        }
    }


}










