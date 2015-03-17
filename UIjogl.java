


/**
 * UIjogl -- A class to create a very simple 2D scene with JOGL

 * 
 * @author rdb
 * 
 * Derived loosely from code written by Derek Dupuis, Fall 2012. 
 *   The overall framework for Derek's code came from OpenGL demo code 
 *   he found on the web, but did not identify.
 * A few framework modifications were based on the tutorial by Justin
 *   Stoecker at 
 *   https://sites.google.com/site/justinscsstuff/jogl-tutorials
 * It looks like Derek's code may have come from the Stoecker tutorial,
 *   but there are a few differences. 
 * The version available to me was dated 2011.
 * 
 * The main class is responsible for creating the needed JOGL objects:
 *     gl:   the openGL Drawable reference
 *     glut: the GLUT reference
 *     glu:  the GL Utility reference
 *  and assigning them to the JOGL "holder" class.
 * 
 *  Once this class has created these objects, it does not maintain a local 
 *  copy, but always references the objects through the JOGL variable. 
 * 
 * @version 0.1 08/23/2013
 * @version 0.2 09/30/2013
 *     - discovered an error in display method; was initializing ModelView
 *       stack AFTER calling render.
 *     - added Animator creation, but don't use it????
 *     - added some comments
 */

import java.util.*;
import java.awt.*;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;

import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;

public class UIjogl extends JFrame implements GLEventListener {
	// -------------------- class variables ----------------------------
	private static boolean guiWindow = false; // set to true if want 2 windows

	private static int width, height;

	private FPSAnimator animator;

	public static Map<String, Triangle> triSceneObj = new HashMap<String, Triangle>();

	public static Map<String, RightTriangle> riTriSceneObj = new HashMap<String, RightTriangle>();

	public static Map<String, Rectangle> rectSceneObj = new HashMap<String, Rectangle>();

	public static Map<String, Quad> quadSceneObj = new HashMap<String, Quad>();

	public static Map<String, QuadDiamond> quadDiaSceneObj = new HashMap<String, QuadDiamond>();

	public static Map<String, Polygon> polySceneObj = new HashMap<String, Polygon>();

	public static Map<String, Pentagon> pentSceneObj = new HashMap<String, Pentagon>();

	public static Map<String, Octagon> octSceneObj = new HashMap<String, Octagon>();

	public static GLCanvas glCanvas;

	// ------------------ constructors ----------------------------------
	public UIjogl(int w, int h) {
		super("UIjogl demo");
		width = w;
		height = h;

		this.setSize(width, height);
		this.setLocation(500, -700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupOpenGL();

		if (guiWindow) {
			ControlPanelW controlPanelW = ControlPanelW.getInstance();
			this.add(glCanvas);
		} else {
			ControlPanel controlPanel = ControlPanel.getInstance();
			controlPanel.addDrawPanel(glCanvas);
			this.add(controlPanel);

		}
		animator = new FPSAnimator(glCanvas, 40);
		// animator.add(glCanvas);
		animator.start();
		this.setVisible(true);
	}

	// --------------------- setupOpenGL( int win ) -------------------------
	/**
	 * Set up the open GL drawing window
	 */
	void setupOpenGL() {
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		glCanvas = new GLCanvas(caps);

		// When a GL event occurs, need to tell the canvas to send the event
		// to the UIjogl object, which knows how to draw the scene.
		glCanvas.addGLEventListener(this);

		// This program doesn't need an animator since all image changes
		// occur because of interactions with the user and should
		// get triggered as long as the GLCanvas.repaint method is called.
	}

	// +++++++++++++++ GLEventListener override methods ++++++++++++++++++++
	// -------------------- display -------------------------------------
	/**
	 * Override the parent display method In this framework, the display method
	 * is responsible for setting up the projection specification, but the
	 * "render" method is responsible for the View and Model specifications.
	 * 
	 * This display method is reasonably application-independent; It defines a
	 * pattern that can be reused with the exception of the specifying the
	 * actual objects to render.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		// set op projection matrix
		JOGL.gl.glMatrixMode(GL2.GL_PROJECTION);
		JOGL.gl.glLoadIdentity();

		// Set up a projection specification that will "see" objects
		// defined in a coordinate system plane and "window" that
		// approximates the size of the window.
		//
		JOGL.glu.gluOrtho2D(0.0, width, 0.0, height);

		//
		// Change back to model view matrix and initialize it
		JOGL.gl.glMatrixMode(GL2.GL_MODELVIEW);
		JOGL.gl.glLoadIdentity();
		

		render(drawable);
	}

	// --------------------- dispose ------------------------------
	@Override
	public void dispose(GLAutoDrawable arg0) {
		// nothing to dispose of...
	}

	// --------------------- init ------------------------------
	@Override
	public void init(GLAutoDrawable drawable) {
		JOGL.gl = drawable.getGL().getGL2();

		JOGL.gl.setSwapInterval(1); // animation event occurs (maybe)
									// only at end of frame draw.
									// 0 => render as fast as possible
		JOGL.glu = new GLU();
		JOGL.glut = new GLUT();
			}

	// --------------------- reshape ----------------------------------------
	/**
	 * Window has been resized, readjust internal information
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
		JOGL.gl = drawable.getGL().getGL2();
		JOGL.gl.glViewport(0, 0, w, h);
		JOGL.glu.gluOrtho2D(0.0, w, 0.0, h);
		System.out.println("Viewport size: " + w + " x " + h);
	}

	// +++++++++++++++++++ GUI responder hack
	// ++++++++++++++++++++++++++++++++++++++
	public static void changeEvent(String event, int value) {
		
		Shape lastObj = null;

		if (ControlPanel.triButton.isSelected()) {
			lastObj = triSceneObj.get("T" + (ControlPanel.tCount - 1));
		}

		if (ControlPanel.triRightButton.isSelected()) {
			lastObj = riTriSceneObj.get("RT" + (ControlPanel.t1Count - 1));
		}

		if (ControlPanel.rectButton.isSelected()) {
			lastObj = rectSceneObj.get("R" + (ControlPanel.rCount - 1));
		}

		if (ControlPanel.quadButton.isSelected()) {
			lastObj = quadSceneObj.get("Q" + (ControlPanel.qCount - 1));
		}

		if (ControlPanel.quadDiamondButton.isSelected()) {
			lastObj = quadDiaSceneObj.get("D" + (ControlPanel.q1Count - 1));
		}

		if (ControlPanel.polyButton.isSelected()) {
			lastObj = polySceneObj.get("P" + (ControlPanel.pCount - 1));
		}

		if (ControlPanel.pentButton.isSelected()) {
			lastObj = pentSceneObj.get("PE" + (ControlPanel.p1Count - 1));
		}

		if (ControlPanel.octButton.isSelected()) {
			lastObj = octSceneObj.get("O" + (ControlPanel.p2Count - 1));
		}

		if (lastObj == null) {
			return;
		}
		if (event.equals("x"))
			lastObj.setLocation(value, lastObj.getY());
		else if (event.equals("y"))
			lastObj.setLocation(lastObj.getX(), value);
		else
			lastObj.setSize(value, value);
		glCanvas.repaint();

	}

	// ---------------------- render ---------------------------------------
	/**
	 * Do the actual drawing
	 */
	public static void render(GLAutoDrawable drawable) {

		JOGL.gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		JOGL.gl.glClearColor(1, 1, 1, 0);

		for (Triangle tri : triSceneObj.values()) {
			tri.redraw();
		}

		for (RightTriangle riTri : riTriSceneObj.values()) {
			riTri.redraw();
		}

		for (Rectangle rect : rectSceneObj.values()) {
			rect.redraw();
		}
		for (Quad quad : quadSceneObj.values()) {
			quad.redraw();
		}

		for (QuadDiamond quaddia : quadDiaSceneObj.values()) {
			quaddia.redraw();
		}

		for (Polygon poly : polySceneObj.values()) {
			poly.redraw();
		}
		for (Pentagon pent : pentSceneObj.values()) {
			pent.redraw();
		}

		for (Pentagon pent : pentSceneObj.values()) {
			pent.redraw();
		}
		for (Octagon oct : octSceneObj.values()) {
			oct.redraw();
		}

	}

	// ++++++++++++++++++++++++++++ main ++++++++++++++++++++++++++++++++++++++
	public static void main(String[] args) {
		int winW = 800, winH = 600;
		if (args.length > 0)
			guiWindow = !guiWindow; // reverse the default setting of the
									// guiWindow

		UIjogl scene = new UIjogl(winW, winH);
	}
}
