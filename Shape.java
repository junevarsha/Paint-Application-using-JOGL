


/**
 * Shape.java - a simple abstract class to represent graphical objects in a JOGL
 * environment
 * 
 * @author rdb Derived from Shape.cpp/h 27 August 2013
 */
public abstract class Shape {
	// ------------------------- instance variables -----------------------
	float xLoc, yLoc; // location (origin) of the object
	float xSize, ySize; // size of the object
	float red, green, blue;
	protected boolean boundary;// color of object

	// ---------------------------- Constructor ---------------------------
	public Shape() {
		setColor(1, 0, 0); // default color is red
		setLocation(0, 0);
		setSize(1, 1);
	}

	// -------------------------- public methods --------------------------
	// ----------------------------- redraw -------------------------------
	/**
	 * abstract redraw method must be defined by subclasses.
	 */
	abstract public void redraw();

	// ------------------ setLocation( float, float ) ------------------------
	/**
	 * set the location of the object to the x,y position defined by the args
	 */
	public void setLocation(float x, float y) {
		xLoc = x;
		yLoc = y;
	}

	// ------------------ getX() ------------------------
	/**
	 * return the value of the x origin of the shape
	 */
	public float getX() {
		return xLoc;
	}

	// ------------------ getY() ------------------------
	/**
	 * return the value of the y origin of the shape
	 */
	float getY() {
		return yLoc;
	}

	// ------------------ setColor( float, float, float ) -------------------
	/**
	 * set the "nominal" color of the object to the specified color; this does
	 * not require that ALL components of the object must be the same color.
	 * Typically, the largest component will take on this color, but the
	 * decision is made by the child class.
	 */
	void setColor(float r, float g, float b) {
		red = r;
		green = g;
		blue = b;
	}

	// ------------------ setSize( float, float ) ------------------------
	/**
	 * set the size of the shape to be scaled by xs, ys That is, the shape has
	 * an internal fixed size, the shape parameters scale that internal size.
	 */
	void setSize(float xs, float ys) {
		xSize = xs;
		ySize = ys;
	}

	public boolean isBoundary() {
		return boundary;
	}

	// ------------------ setBoundary(boolean boundary) ------------------------
	/**
	 * Sets the boundary for the shape based on boolean method if true-puts
	 * boundary
	 */

	public void setBoundary(boolean boundary) {
		this.boundary = boundary;
	}

}
