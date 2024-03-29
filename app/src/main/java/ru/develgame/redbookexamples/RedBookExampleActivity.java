package ru.develgame.redbookexamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RedBookExampleActivity extends Activity {
    private Intent intent;

    public void textOnClick1(View v) {
        intent.putExtra(OpenGLContext.EXAMPLE_NAME, 0);
        startActivity(intent);
    }

    public void textOnClick2(View v) {
        intent.putExtra(OpenGLContext.EXAMPLE_NAME, 1);
        startActivity(intent);
    }

    public void textOnClick3(View v) {
        intent.putExtra(OpenGLContext.EXAMPLE_NAME, 2);
        startActivity(intent);
    }

    public void textOnClick4(View v) {
        intent.putExtra(OpenGLContext.EXAMPLE_NAME, 3);
        startActivity(intent);
    }

    public void textOnClick5(View v) {
        intent.putExtra(OpenGLContext.EXAMPLE_NAME, 4);
        startActivity(intent);
    }

    public void textOnClick6(View v) {
        intent.putExtra(OpenGLContext.EXAMPLE_NAME, 5);
        startActivity(intent);
    }

    public void textOnClick7(View v) {
        intent.putExtra(OpenGLContext.EXAMPLE_NAME, 6);
        startActivity(intent);
    }

    public void textOnClick8(View v) {
        intent.putExtra(OpenGLContext.EXAMPLE_NAME, 7);
        startActivity(intent);
    }

    public void textOnClick9(View v) {
        intent.putExtra(OpenGLContext.EXAMPLE_NAME, 8);
        startActivity(intent);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = new Intent();
        intent.setClass(this, OpenGLContext.class);

        setContentView(R.layout.main);
    }
}
