


import javax.media.opengl.*;
import javax.media.opengl.glu.*; // GL utility library

import com.jogamp.opengl.util.gl2.GLUT; // GLUT library

/**
 * This class defines a polygon object constructor that can take information
 * defining any number of points.
 * 
 * Several sided objects are created that illustrates various sizes, location
 * and colors.
 */

public class Octagon extends Shape {
	// ---------------- instance variables ------------------------
	// private Color _borderColor;
	// private Color _fillColor;
	// private int _lineWidth = 2;

	private float[] dx;
	private float[] dy;

	private float[] dx1;
	private float[] dy1;

	public static GL2 gl = null; // the GL2 encapsulation of the openGL state

	public static GLUT glut = null; // the GLUT state
	public static GLU glu = null; // the GLU state

	// -------------------- constructors ---------------------------
	/**
	 * Constructor
	 */
	public Octagon() {

		float[] dx = { 0.4f, 0.3f, 0.3f, 0.4f, 0.5f, 0.6f, 0.6f, 0.5f };
		float[] dy = { 0.5f, 0.4f, 0.3f, 0.2f, 0.2f, 0.3f, 0.4f, 0.5f };

		initialize(dx, dy);
	}

	// -------------------------- initialize ------------------------
	public void initialize(float[] x, float[] y) {
		dx = new float[8];
		dy = new float[8];
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
	/**
	 * Need to actually do the drawing here
	 */
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
