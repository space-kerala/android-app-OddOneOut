package com.example.root.oddoneout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

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

    ArrayList<Scene> list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition);

        list = new ArrayList<Scene>();


        //Preparing a root container
        rootContainer = (ViewGroup) findViewById(R.id.rootContainer);


        //referencing scenes created in xml
        scene1 = Scene.getSceneForLayout(rootContainer, R.layout.scene1_layout, this);
        scene2 = Scene.getSceneForLayout(rootContainer, R.layout.scene2_layout, this);
        scene3 = Scene.getSceneForLayout(rootContainer, R.layout.scene3_layout, this);

        list.add(scene1);
        list.add(scene2);
        list.add(scene3);

        transition = new AutoTransition();
        transition.setDuration(500);
        transition.setInterpolator(new LinearInterpolator());

        setCurrentScene();
        //entering scene1
        scene1.enter();

        rightVoice = MediaPlayer.create(this, R.raw.correct);
        wrongVoice = MediaPlayer.create(this, R.raw.wrong);


    }


    @Override
    public void onClick(View view) {
        //Toast.makeText(MainActivity.this,view.getRootView().toString(),Toast.LENGTH_LONG).show();

        switch (view.getId()) {
            case R.id.imageButton_tux:
               if(currentScene==1) {

                    changeToScene(scene2);
                }
                else {
                    wrongVoice.start();
                }
                               
                break;

            case R.id.imageButton_plant:
                if(currentScene==2) {

                    changeToScene(scene3);
                }
                else {
                    wrongVoice.start();
                }
                break;

            case R.id.imageButton_inback:

                backToScene();
                break;
            default:
                wrongVoice.start();

        }


    }

    private void setCurrentScene() {
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


    private void changeToScene(final Scene scene) {


        rightVoice.start();
        rightVoice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                TransitionManager.go(scene, transition);
            }

        });

    }

    private void backToScene() {


        int index= currentScene-2;

        Log.d("tagnnnn",Integer.toString(index));
        TransitionManager.go(list.get(index),transition);


    }

}
