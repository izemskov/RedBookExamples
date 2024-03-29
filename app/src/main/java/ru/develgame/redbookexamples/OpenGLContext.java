package ru.develgame.redbookexamples;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

public class OpenGLContext extends Activity {
    private GlRenderer renderer;
    private GLSurfaceView surface;
    public static final String EXAMPLE_NAME = "ExampleName";
    private GestureDetector gestureDetector;

    private float tx = 0.0f;				//Old coordinate TouchScreenX
    private float ty = 0.0f;				//Old coordinate TouchScreenY

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestureDetector = new GestureDetector(this, new GlAppGestureListener(this));

        surface = new GLSurfaceView(this);
        renderer = new GlRenderer(this);

        Bundle extras = getIntent().getExtras();

        renderer.SetExampleNum(extras.getInt(EXAMPLE_NAME));

        if ((renderer.GetExampleNum() == 5) || (renderer.GetExampleNum() == 7) || (renderer.GetExampleNum() == 6)) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Use double tap", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 50);
            toast.show();
        }

        if (renderer.GetExampleNum() == 4) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Tap and swipe", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 50);
            toast.show();
        }

        surface.setRenderer(renderer);
        setContentView(surface);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }

        int action = event.getAction();

        if (renderer.GetExampleNum() == 4) {
            if ((action == MotionEvent.ACTION_DOWN) | (action == MotionEvent.ACTION_UP)) {
                tx = event.getX();
                ty = event.getY();
            }

            if (action == MotionEvent.ACTION_MOVE) {
                renderer.ChangeLightPosition(0.5f * (event.getX() - tx), 0.5f * (event.getY() - ty));

                tx = event.getX();
                ty = event.getY();
            }
        }

        return super.onTouchEvent(event);
    }

    private class GlAppGestureListener extends GestureDetector.SimpleOnGestureListener {
        private OpenGLContext glApp;

        public GlAppGestureListener(OpenGLContext glApp) {
            this.glApp = glApp;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            if (glApp.renderer.GetExampleNum() == 5) {
                glApp.renderer.SwitchAlpha();
            }
            else if (glApp.renderer.GetExampleNum() == 6) {
                glApp.renderer.SwitchFog();
            }
            else if (glApp.renderer.GetExampleNum() == 7) {
                glApp.renderer.SwitchAA();
            }

            return true;
        }
    }
}
