


import java.awt.Color;

import javax.media.opengl.*;
import javax.media.opengl.glu.*; // GL utility library

import com.jogamp.opengl.util.gl2.GLUT; // GLUT library

/**
 * This class defines a rectangle object and is responsible for drawing it.
 * Several rectangles are created with multiple copies, colors and sizes.
 * gl.glBegin(GL2.GL_LINE_LOOP); is used in order to create boundaries for the
 * rectangle.
 */

public class Rectangle extends Shape {

	public static GL2 gl = null; // the GL2 encapsulation of the openGL state

	public static GLUT glut = null; // the GLUT state
	public static GLU glu = null; // the GLU state
	// ---------------- instance variables ------------------------

	private float[] dx;
	private float[] dy;
	private final Color color = Color.RED;

	// -------------------- constructors ---------------------------
	/**
	 * Constructor
	 */
	public Rectangle() {

		float dx[] = { 1.0f, 1.0f, 4.0f, 4.0f };
		float dy[] = { 1.0f, 4.0f, 4.0f, 1.0f };

		initialize(dx, dy);
	}

	// -------------------------- initialize ------------------------
	public void initialize(float[] x, float[] y) {
		dx = new float[4];
		dy = new float[4];
		for (int i = 0; i < x.length; i++) {
			dx[i] = x[i];
			dy[i] = y[i];
		}
		// access global information about JOGL components
		gl = JOGL.gl;
		glu = JOGL.glu;
		glut = JOGL.glut;
	}

	// ------------- redraw ---------------------------

	// Need to actually do the drawing here

	public void redraw() {

		if (isBoundary() == true) {
			gl.glBegin(GL.GL_LINE_LOOP);
		} else {
			gl.glBegin(GL2.GL_POLYGON);
		}

		gl.glColor3f(red, green, blue);

		for (int i = 0; i < dx.length; i++) {

			gl.glVertex2f(xLoc + dx[i] * xSize, yLoc + dy[i] * ySize);

		}

		gl.glEnd();

	}
}
