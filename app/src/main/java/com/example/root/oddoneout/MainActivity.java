package com.example.root.oddoneout;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.transition.AutoTransition;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

//import android.transition.TransitionInflater;

//import android.transition.Scene;
//import android.transition.Transition;
//import android.transition.TransitionInflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup rootContainer;
    private Scene scene1, scene2, scene3;
    private TransitionManager transitionMgr;
    private Transition transition;

    private MediaPlayer rightVoice, wrongVoice;
    private int currentScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition);

        //Preparing a root container
        rootContainer = (ViewGroup) findViewById(R.id.rootContainer);


        // transitionMgr = TransitionInflater.from(this).inflateTransition(R.transition.transition);


        //referencing scenes created in xml
        scene1 = Scene.getSceneForLayout(rootContainer, R.layout.scene1_layout, this);
        scene2 = Scene.getSceneForLayout(rootContainer, R.layout.scene2_layout, this);
        scene3 = Scene.getSceneForLayout(rootContainer, R.layout.scene3_layout, this);



        transition = new AutoTransition();
        transition.setDuration(500);
        transition.setInterpolator(new LinearInterpolator());

        setCurrentScene();

        //Entering scene
        scene1.enter();


        rightVoice = MediaPlayer.create(this, R.raw.correct);
        wrongVoice = MediaPlayer.create(this, R.raw.wrong);


    }


    @Override
    public void onClick(View view) {
        //Toast.makeText(MainActivity.this,view.getRootView().toString(),Toast.LENGTH_LONG).show();

        switch (view.getId()) {
            case R.id.imageButton_tux:

                if (currentScene == 1) {
                    rightVoice.start();
                    rightVoice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            //TransitionManager.go(scene2, transitionMgr);
                            TransitionManager.go(scene2, transition);

                        }
                    });

                }
                break;


            case R.id.imageButton_plant:

                if (currentScene == 2) {
                    rightVoice.start();
                    rightVoice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            //TransitionManager.go(scene3, transitionMgr);
                            TransitionManager.go(scene3, transition);
                        }

                    });
                }
                break;

            case R.id.back_id2:

                //TransitionManager.go(scene1, transitionMgr);
                TransitionManager.go(scene1, transition);
                break;

            case R.id.back_id3:

                TransitionManager.go(scene2, transition);
                break;
            default:
                wrongVoice.start();

        }


    }

private void setCurrentScene(){
    scene1.setEnterAction(new Runnable() {
        @Override
        public void run() {
            currentScene = 1;
        }
    });
    scene2.setEnterAction(new Runnable() {
        @Override
        public void run() {
            currentScene = 2;
        }
    });
    scene3.setEnterAction(new Runnable() {
        @Override
        public void run() {
            currentScene = 3;
        }
    });

}
}
