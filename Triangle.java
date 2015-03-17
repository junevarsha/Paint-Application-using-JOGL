


import java.awt.geom.*;
import java.awt.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*; // GL utility library

import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT; // GLUT library

public class Triangle extends Shape {
	// ---------------- class variables ------------------------
	public static GL2 gl = null; // the GL2 encapsulation of the openGL state

	public static GLUT glut = null; // the GLUT state
	public static GLU glu = null; // the GLU state

	// ---------------- instance variables ------------------------
	private Color _borderColor;
	private Color _fillColor;
	private int _lineWidth = 2;

	private final Color defaultColor = Color.RED;

	private float[] dx;
	private float[] dy;

	// -------------------- constructors ---------------------------
	/**
	 * Constructor
	 */
	public Triangle() {
		// default triangle is equilateral triangle with center at origin
		// and base and height of size 1.
		float dxDefault[] = { -0.5f, 0.0f, 0.5f };
		float dyDefault[] = { -0.5f, 0.5f, -0.5f };

		initialize(dxDefault, dyDefault);
	}

	public Triangle(float[] x, float[] y) {
		initialize(x, y);
	}

	// -------------------------- initialize ------------------------
	public void initialize(float[] x, float[] y) {
		dx = new float[3];
		dy = new float[3];
		for (int i = 0; i < x.length; i++) {
			dx[i] = x[i];
			dy[i] = y[i];
		}
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

		// The triangle is defined by positions relative to its location stored
		// in the dx and dy arrays
		// The scale factor applies to the relative offset of each coordinate
		// from the
		// origin (which is xLoc, yLoc )

		for (int i = 0; i < dx.length; i++) {

			gl.glVertex2f(xLoc + dx[i] * xSize, yLoc + dy[i] * ySize);
		}
		gl.glEnd();
	}
}