package ru.develgame.redbookexamples;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {
    private final float size = 0.6f;

    private float verticles[] = new float[] {
            // front
            -size, size, size,
            size, size, size,
            size,-size, size,
            -size,-size, size,
            // back
            size, size,-size,
            -size, size,-size,
            -size,-size,-size,
            size,-size,-size,
            // top
            -size, size,-size,
            size, size,-size,
            size, size, size,
            -size, size, size,
            // bottom
            size,-size,-size,
            -size,-size,-size,
            -size,-size, size,
            size,-size, size,
            // left
            -size, size,-size,
            -size, size, size,
            -size,-size, size,
            -size,-size,-size,
            // right
            size, size, size,
            size, size,-size,
            size,-size,-size,
            size,-size, size
    };

    private float normals[] = new float[] {
            // front
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            // back
            0.0f, 0.0f,-1.0f,
            0.0f, 0.0f,-1.0f,
            0.0f, 0.0f,-1.0f,
            0.0f, 0.0f,-1.0f,
            // top
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            // bottom
            0.0f,-1.0f, 0.0f,
            0.0f,-1.0f, 0.0f,
            0.0f,-1.0f, 0.0f,
            0.0f,-1.0f, 0.0f,
            // left
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            // right
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f
    };

    private short indices[] = new short[] {
            0, 3, 1,  1, 3, 2, // front
            4, 7, 5,  5, 7, 6, // back
            8,11, 9,  9,11,10, // top
            12,15,13, 13,15,14, // bottom
            16,19,17, 17,19,18, // left
            20,23,21, 21,23,22  // right

    };

    private FloatBuffer vertexBfr;
    private FloatBuffer normalBfr;
    private ShortBuffer indicesBfr;

    public Cube() {
        Build();
    }

    public void Build() {
        vertexBfr = GlRenderer.makeFloatBuffer(verticles);
        normalBfr = GlRenderer.makeFloatBuffer(normals);
        indicesBfr = GlRenderer.makeShortBuffer(indices);
    }

    public void Draw (GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_MATERIAL);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBfr);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBfr);

        //gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indicesBfr);
        gl.glDisableClientState(GL10.GL_COLOR_MATERIAL);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
