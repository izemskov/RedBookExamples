package ru.develgame.redbookexamples;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class GlRenderer implements Renderer {
    private Context context;
    private float zRot = 0.0f;
    private int exampleNum;
    private boolean alphaType = true;
    private boolean aaEnable = true;
    private int typeFog = 0;
    private boolean changeLight = false;

    private NewSphere mSphere;
    private Cube mcube;

    private float matSpecular[] = {1.0f, 1.0f, 1.0f, 1.0f};
    private float matShininess[] = {100.0f};
    private float lightPosition[] = {1.0f, 1.0f, 1.0f, 0.0f};
    private float whiteLight[] = {1.0f, 1.0f, 1.0f, 1.0f};

    private final float MAXZ = 8.0f;
    private final float MINZ = -8.0f;
    private final float ZINC = 0.4f;
    private float solidZ = 8.0f;
    private float transparentZ = -8.0f;

    private final static float[][] cubeVertexCoords = new float[][] {
            new float[] { // front
                    -1, -1, 1,
                    -1, 1, 1,
                    -1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,
                    1, -1, 1,
                    1, -1, 1,
                    -1, -1, 1
            },
            new float[] { // back
                    -1, -1, -1,
                    -1, 1, -1,
                    -1, 1, -1,
                    1, 1, -1,
                    1, 1, -1,
                    1, -1, -1,
                    1, -1, -1,
                    -1, -1, -1
            },
            new float[] { // left
                    -1, -1, 1,
                    -1, 1, 1,
                    -1, 1, 1,
                    -1, 1, -1,
                    -1, 1, -1,
                    -1, -1, -1,
                    -1, -1, -1,
                    -1, -1, 1
            },
            new float[] { // right
                    1, -1, 1,
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, -1,
                    1, 1, -1,
                    1, -1, -1,
                    1, -1, -1,
                    1, -1, 1
            },
    };

    public void SetExampleNum (int AExampleNum) {
        exampleNum = AExampleNum;
    }

    public int GetExampleNum () {
        return exampleNum;
    }

    public void SwitchAlpha () {
        alphaType = !(alphaType);
    }

    public void SwitchAA () {
        aaEnable = !(aaEnable);
    }

    public void SwitchFog() {
        typeFog++;
        if (typeFog >= 3) {
            typeFog = 0;
        }
    }

    public void ChangeLightPosition (float aX, float aY) {
        lightPosition[0] = lightPosition[0] + aX;
        lightPosition[1] = lightPosition[1] + aY;

        changeLight = true;
    }

    public static FloatBuffer makeFloatBuffer(float[] arr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }

    public static ShortBuffer makeShortBuffer(short[] arr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*2);
        bb.order(ByteOrder.nativeOrder());
        ShortBuffer fb = bb.asShortBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }

    protected void Example1 (GL10 gl) {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        float vVertCoord[];
        vVertCoord = new float[12];
        FloatBuffer vertexBfr;

        vVertCoord[0] = -0.75f; vVertCoord[1] = -0.75f; vVertCoord[2] = 0.0f;
        vVertCoord[3] = 0.75f; vVertCoord[4] = -0.75f; vVertCoord[5] = 0.0f;
        vVertCoord[6] = 0.75f; vVertCoord[7] = 0.75f; vVertCoord[8] = 0.0f;
        vVertCoord[9] = -0.75f; vVertCoord[10] = 0.75f; vVertCoord[11] = 0.0f;

        vertexBfr = makeFloatBuffer(vVertCoord);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBfr);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

    protected void Example2 (GL10 gl) {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glRotatef(zRot, 0, 0, 1);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        float vVertCoord[];
        vVertCoord = new float[12];
        FloatBuffer vertexBfr;

        vVertCoord[0] = -0.50f; vVertCoord[1] = -0.50f; vVertCoord[2] = 0.0f;
        vVertCoord[3] = 0.50f; vVertCoord[4] = -0.50f; vVertCoord[5] = 0.0f;
        vVertCoord[6] = 0.50f; vVertCoord[7] = 0.50f; vVertCoord[8] = 0.0f;
        vVertCoord[9] = -0.50f; vVertCoord[10] = 0.50f; vVertCoord[11] = 0.0f;

        vertexBfr = makeFloatBuffer(vVertCoord);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBfr);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        // update rotations
        zRot += 2.0f;
    }

    protected void Example3 (GL10 gl) {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glFrustumf(-1.0f, 1.0f, -1.0f, 1.0f, 1.5f, 20.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU.gluLookAt(gl, 0.0f, 0.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

        gl.glScalef(1.0f, 2.0f, 1.0f);

        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        FloatBuffer[] cubeVertexBfr = new FloatBuffer[4];
        for (int i = 0; i < 4; i++)	{
            cubeVertexBfr[i] = makeFloatBuffer(cubeVertexCoords[i]);
        }

        for (int i = 0; i < 4; i++) // draw each face
        {
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVertexBfr[i]);
            gl.glDrawArrays(GL10.GL_LINES, 0, 8);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    protected void Example4 (GL10 gl) {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        float vVertCoord[];
        vVertCoord = new float[9];
        float vColor[];
        vColor = new float[12];
        FloatBuffer vertexBfr;
        FloatBuffer colorBfr;

        vVertCoord[0] = -0.50f; vVertCoord[1] = -0.50f; vVertCoord[2] = 0.0f;
        vVertCoord[3] = 0.50f; vVertCoord[4] = -0.50f; vVertCoord[5] = 0.0f;
        vVertCoord[6] = 0.50f; vVertCoord[7] = 0.50f; vVertCoord[8] = 0.0f;

        vColor[0] = 1.0f; vColor[1] = 0.0f; vColor[2] = 0.0f; vColor[3] = 1.0f;
        vColor[4] = 0.0f; vColor[5] = 1.0f; vColor[6] = 0.0f; vColor[7] = 1.0f;
        vColor[8] = 0.0f; vColor[9] = 0.0f; vColor[10] = 1.0f; vColor[11] = 1.0f;

        vertexBfr = makeFloatBuffer(vVertCoord);
        colorBfr = makeFloatBuffer(vColor);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBfr);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBfr);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    protected void Example5 (GL10 gl) {
        if (changeLight) {
            FloatBuffer lightPositionBfr;
            lightPositionBfr = makeFloatBuffer(lightPosition);

            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBfr);
        }

        mSphere.Draw(gl);
    }

    protected void Example6 (GL10 gl) {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        GLU.gluOrtho2D(gl, 0.0f, 1.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        float vVertCoord[];
        vVertCoord = new float[18];
        float vColor[];
        vColor = new float[24];
        FloatBuffer vertexBfr;
        FloatBuffer colorBfr;

        if (alphaType) {
            vVertCoord[0] = 0.1f; vVertCoord[1] = 0.9f; vVertCoord[2] = 0.0f;
            vVertCoord[3] = 0.1f; vVertCoord[4] = 0.1f; vVertCoord[5] = 0.0f;
            vVertCoord[6] = 0.7f; vVertCoord[7] = 0.5f; vVertCoord[8] = 0.0f;

            vVertCoord[9] = 0.9f; vVertCoord[10] = 0.9f; vVertCoord[11] = 0.0f;
            vVertCoord[12] = 0.3f; vVertCoord[13] = 0.5f; vVertCoord[14] = 0.0f;
            vVertCoord[15] = 0.9f; vVertCoord[16] = 0.1f; vVertCoord[17] = 0.0f;

            vColor[0] = 1.0f; vColor[1] = 1.0f; vColor[2] = 0.0f; vColor[3] = 0.75f;
            vColor[4] = 1.0f; vColor[5] = 1.0f; vColor[6] = 0.0f; vColor[7] = 0.75f;
            vColor[8] = 1.0f; vColor[9] = 1.0f; vColor[10] = 0.0f; vColor[11] = 0.75f;

            vColor[12] = 0.0f; vColor[13] = 1.0f; vColor[14] = 1.0f; vColor[15] = 0.75f;
            vColor[16] = 0.0f; vColor[17] = 1.0f; vColor[18] = 1.0f; vColor[19] = 0.75f;
            vColor[20] = 0.0f; vColor[21] = 1.0f; vColor[22] = 1.0f; vColor[23] = 0.75f;
        }
        else {
            vVertCoord[9] = 0.1f; vVertCoord[10] = 0.9f; vVertCoord[11] = 0.0f;
            vVertCoord[12] = 0.1f; vVertCoord[13] = 0.1f; vVertCoord[14] = 0.0f;
            vVertCoord[15] = 0.7f; vVertCoord[16] = 0.5f; vVertCoord[17] = 0.0f;

            vVertCoord[0] = 0.9f; vVertCoord[1] = 0.9f; vVertCoord[2] = 0.0f;
            vVertCoord[3] = 0.3f; vVertCoord[4] = 0.5f; vVertCoord[5] = 0.0f;
            vVertCoord[6] = 0.9f; vVertCoord[7] = 0.1f; vVertCoord[8] = 0.0f;

            vColor[12] = 1.0f; vColor[13] = 1.0f; vColor[14] = 0.0f; vColor[15] = 0.75f;
            vColor[16] = 1.0f; vColor[17] = 1.0f; vColor[18] = 0.0f; vColor[19] = 0.75f;
            vColor[20] = 1.0f; vColor[21] = 1.0f; vColor[22] = 0.0f; vColor[23] = 0.75f;

            vColor[0] = 0.0f; vColor[1] = 1.0f; vColor[2] = 1.0f; vColor[3] = 0.75f;
            vColor[4] = 0.0f; vColor[5] = 1.0f; vColor[6] = 1.0f; vColor[7] = 0.75f;
            vColor[8] = 0.0f; vColor[9] = 1.0f; vColor[10] = 1.0f; vColor[11] = 0.75f;
        }

        vertexBfr = makeFloatBuffer(vVertCoord);
        colorBfr = makeFloatBuffer(vColor);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBfr);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBfr);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    protected void Example7 (GL10 gl) {
        if (typeFog == 0) {
            gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_EXP);
        }
        else if (typeFog == 1) {
            gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_EXP2);
        }
        else if (typeFog == 2) {
            gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_LINEAR);
        }

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -2.0f, -1.0f);
        mSphere.Draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -1.0f, -2.0f);
        mSphere.Draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.0f, -3.0f);
        mSphere.Draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 1.0f, -4.0f);
        mSphere.Draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 2.0f, -5.0f);
        mSphere.Draw(gl);
        gl.glPopMatrix();
    }

    protected void Example8 (GL10 gl) {
        if (aaEnable) {
            gl.glEnable(GL10.GL_LINE_SMOOTH);
            gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
        }
        else {
            gl.glDisable(GL10.GL_LINE_SMOOTH);
            gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_FASTEST);
        }

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        float vVertCoord[];
        vVertCoord = new float[12];
        FloatBuffer vertexBfr;

        float vColor[];
        vColor = new float[16];
        FloatBuffer colorBfr;

        vVertCoord[0] = -0.5f; vVertCoord[1] = 0.5f; vVertCoord[2] = 0.0f;
        vVertCoord[3] = 0.5f; vVertCoord[4] = -0.5f; vVertCoord[5] = 0.0f;
        vVertCoord[6] = 0.5f; vVertCoord[7] = 0.5f; vVertCoord[8] = 0.0f;
        vVertCoord[9] = -0.5f; vVertCoord[10] = -0.5f; vVertCoord[11] = 0.0f;

        vColor[0] = 0.0f; vColor[1] = 1.0f; vColor[2] = 0.0f; vColor[3] = 1.0f;
        vColor[4] = 0.0f; vColor[5] = 1.0f; vColor[6] = 0.0f; vColor[7] = 1.0f;
        vColor[8] = 0.0f; vColor[9] = 0.0f; vColor[10] = 1.0f; vColor[11] = 1.0f;
        vColor[12] = 0.0f; vColor[13] = 0.0f; vColor[14] = 1.0f; vColor[15] = 1.0f;

        vertexBfr = makeFloatBuffer(vVertCoord);
        colorBfr = makeFloatBuffer(vColor);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBfr);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBfr);
        gl.glDrawArrays(GL10.GL_LINES, 0, 4);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    protected void Example9 (GL10 gl) {
        float matSolid[] = {0.75f, 0.75f, 0.0f, 1.0f};
        float matZero[] = {0.0f, 0.0f, 0.0f, 1.0f};
        float matTransparent[] = {0.0f, 0.8f, 0.8f, 0.6f};
        float matEmission[] = {0.0f, 0.3f, 0.3f, 0.6f};

        FloatBuffer matSolidBfr;
        matSolidBfr = makeFloatBuffer(matSolid);
        FloatBuffer matZeroBfr;
        matZeroBfr = makeFloatBuffer(matZero);

        gl.glPushMatrix();
        gl.glTranslatef(-0.15f, -0.35f, solidZ);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, matZeroBfr);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matSolidBfr);
        mSphere.Draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.15f, 0.15f, transparentZ);
        gl.glRotatef(15.0f, 1.0f, 1.0f, 0.0f);
        gl.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);

        FloatBuffer matTransparentBft;
        matTransparentBft = makeFloatBuffer(matTransparent);
        FloatBuffer matEmissionBfr;
        matEmissionBfr = makeFloatBuffer(matEmission);

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, matEmissionBfr);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matTransparentBft);

        gl.glEnable (GL10.GL_BLEND);
        gl.glDepthMask (false);
        gl.glBlendFunc (GL10.GL_SRC_ALPHA, GL10.GL_ONE);

        mcube.Draw(gl);

        gl.glDepthMask (true);
        gl.glDisable (GL10.GL_BLEND);
        gl.glPopMatrix();

        if (!((solidZ <= MINZ || transparentZ >= MAXZ))) {
            solidZ -= ZINC;
            transparentZ += ZINC;
        }
    }

    public GlRenderer(Context context) {
        this.context = context;
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        if (exampleNum != 6) {
            gl.glColor4f(1, 1, 1, 1);
        }

        if (exampleNum == 0) {
            Example1(gl);
        }
        else if (exampleNum == 1) {
            Example2(gl);
        }
        else if (exampleNum == 2) {
            Example3(gl);
        }
        else if (exampleNum == 3) {
            Example4(gl);
        }
        else if (exampleNum == 4) {
            Example5(gl);
        }
        else if (exampleNum == 5) {
            Example6(gl);
        }
        else if (exampleNum == 6) {
            Example7(gl);
        }
        else if (exampleNum == 7) {
            Example8(gl);
        }
        else if (exampleNum == 8) {
            Example9(gl);
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // avoid division by zero
        if (width == 0) width = 1;
        if (height == 0) height = 1;
        // draw on the entire screen
        gl.glViewport(0, 0, width, height);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gl.glClearDepthf(1.0f);
        gl.glDisable(GL10.GL_DEPTH_TEST);

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        if (exampleNum == 4) {
            FloatBuffer matSpecularBfr;
            matSpecularBfr = makeFloatBuffer(matSpecular);
            FloatBuffer matShininessBfr;
            matShininessBfr = makeFloatBuffer(matShininess);
            FloatBuffer lightPositionBfr;
            lightPositionBfr = makeFloatBuffer(lightPosition);
            FloatBuffer whiteLightBfr;
            whiteLightBfr = makeFloatBuffer(whiteLight);

            gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, matSpecularBfr);
            gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SHININESS, matShininessBfr);
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBfr);
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, whiteLightBfr);
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, whiteLightBfr);

            gl.glEnable(GL10.GL_LIGHTING);
            gl.glEnable(GL10.GL_LIGHT0);
        }

        if (exampleNum == 6) {
            lightPosition[0] = 0.5f; lightPosition[1] = 0.5f; lightPosition[2] = 3.0f; lightPosition[3] = 0.0f;
            float matAmbient[] = {0.1745f, 0.01175f, 0.01175f, 1.0f};
            float matDiffuse[] = {0.61424f, 0.04136f, 0.04136f, 1.0f};
            matSpecular[0] = 0.727811f; matSpecular[1] = 0.626959f; matSpecular[2] = 0.626959f;
            matShininess[0] = 0.6f * 128.0f;

            FloatBuffer lightPositionBfr;
            lightPositionBfr = makeFloatBuffer(lightPosition);
            FloatBuffer matAmbientBfr;
            matAmbientBfr = makeFloatBuffer(matAmbient);
            FloatBuffer matDiffuseBfr;
            matDiffuseBfr = makeFloatBuffer(matDiffuse);
            FloatBuffer matSpecularBfr;
            matSpecularBfr = makeFloatBuffer(matSpecular);
            FloatBuffer matShininessBfr;
            matShininessBfr = makeFloatBuffer(matShininess);

            gl.glClearDepthf(1.0f);
            gl.glEnable(GL10.GL_DEPTH_TEST);

            gl.glEnable(GL10.GL_LIGHTING);
            gl.glEnable(GL10.GL_LIGHT0);

            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBfr);
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, matDiffuseBfr);
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, matSpecularBfr);
            gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT, matAmbientBfr);
            gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SHININESS, matShininessBfr);

            float fogColor[] = {0.5f, 0.5f, 0.5f, 1.0f};
            FloatBuffer fogColorBfr;
            fogColorBfr = makeFloatBuffer(fogColor);

            gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_EXP);
            gl.glFogfv(GL10.GL_FOG_COLOR, fogColorBfr);
            gl.glFogf(GL10.GL_FOG_DENSITY, 0.35f);
            gl.glHint(GL10.GL_FOG_HINT, GL10.GL_DONT_CARE);
            gl.glFogf(GL10.GL_FOG_START, 1.0f);
            gl.glFogf(GL10.GL_FOG_END, 5.0f);

            gl.glEnable(GL10.GL_FOG);

            gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();

            gl.glOrthof (-2.5f, 2.5f, -2.5f, 2.5f, -10.0f, 10.0f);
        }

        if (exampleNum == 7) {
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glLineWidth(1.5f);

            GLU.gluOrtho2D (gl, -1.0f, 1.0f, -1.0f, 1.0f);
        }

        if (exampleNum == 8) {
            lightPosition[0] = 0.5f; lightPosition[1] = 0.5f; lightPosition[2] = 1.0f;

            FloatBuffer matSpecularBfr;
            matSpecularBfr = makeFloatBuffer(matSpecular);
            FloatBuffer matShininessBfr;
            matShininessBfr = makeFloatBuffer(matShininess);
            FloatBuffer lightPositionBfr;
            lightPositionBfr = makeFloatBuffer(lightPosition);
            FloatBuffer whiteLightBfr;
            whiteLightBfr = makeFloatBuffer(whiteLight);

            gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, matSpecularBfr);
            gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, matShininessBfr);
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBfr);

            gl.glEnable(GL10.GL_LIGHTING);
            gl.glEnable(GL10.GL_LIGHT0);
            gl.glEnable(GL10.GL_DEPTH_TEST);

            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();

            gl.glEnable(GL10.GL_CULL_FACE);

            gl.glOrthof(-1.5f, 1.5f, -1.5f, 1.5f, -10.0f, 10.0f);
        }

        if ((exampleNum == 4) || (exampleNum == 6) || (exampleNum == 8)) {
            mSphere = new NewSphere();
        }

        if (exampleNum == 8) {
            mcube = new Cube();
        }
    }
}
