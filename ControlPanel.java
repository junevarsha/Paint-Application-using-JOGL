
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;


import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class ControlPanel extends JPanel {
	// ---------------- class variables ------------------------------
	private static ControlPanel instance = null;

	private boolean dragging;
	// ---------------- hash map ------------------------------
	/**
	 * Hash map is created to store shape Ids that has to be deleted.
	 * 
	 */
	HashMap<String, Integer> hm = new HashMap<String, Integer>();

	// ---------------- Count Variables ------------------------------

	public static int rCount = 0;

	public static int tCount = 0;
	public static int t1Count = 0;

	public static int qCount = 0;

	public static int q1Count = 0;

	public static int pCount = 0;
	public static int p1Count = 0;
	public static int p2Count = 0;

	public Point beginning, finish;

	// ---------------- Buttons and label
	// variables------------------------------

	public static JLabel label;
	public static JRadioButton rectButton;
	public static JRadioButton triButton;
	public static JRadioButton triRightButton;
	public static JRadioButton quadButton;
	public static JRadioButton quadDiamondButton;
	public static JRadioButton polyButton;
	public static JRadioButton pentButton;
	public static JRadioButton octButton;
	public static JOptionPane myJOptionPane = new JOptionPane();

	private static JSlider slider;
	private static JSlider sliderOne;
	private static JSlider sliderTwo;
	private static JSlider sliderThree;

	public static JCheckBox checkPolyBoundary;
	public static JCheckBox checkPolyInterior;
	private static JTextArea tracker;
	private static String deleteId;

	public static JList fillList;
	public static JList boundList;
	private static String[] colorNames = { "black", "red", "green", "orange" };
	private static String[] colorNamesNew = { "black", "red", "green", "orange" };
	private static Color[] colors = { Color.BLACK, Color.RED, Color.GREEN,
			Color.ORANGE };

	private float locX, locY, sizeX, sizeY;

	public static int listIndex = 0;
	private static JPanel bPanel = new JPanel();

	// --------------- instance variables ----------------------------
	JPanel drawPanel = null; // this will be used for the rendering area

	// ------------------- constructor -------------------------------
	/**
	 * return singleton instance of ControlPanel
	 */
	public static ControlPanel getInstance() {
		if (instance == null)
			instance = new ControlPanel();
		return instance;
	}

	/**
	 * Constructor is private so can implement the Singleton pattern
	 */
	private ControlPanel() {
		this.setLayout(new BorderLayout());
		locX = 0.0f;
		locY = 0.0f;
		sizeX = 0.0f;
		sizeY = 0.0f;
		buildGUI();
	}

	// --------------- addDrawPanel() -----------------------------
	/**
	 * add component to draw panel
	 */
	public void addDrawPanel(Component drawArea) {
		this.add(drawArea, BorderLayout.CENTER);
		drawArea.addMouseListener(new Mouse());
		drawArea.addMouseMotionListener(new Mouse());
	}

	// --------------- getDrawPanel() -----------------------------
	/**
	 * return reference to the drawing area
	 */
	public JPanel getDrawPanel() {
		return drawPanel;
	}

	// --------------- nextScene() -------------------------------
	/**
	 * Go to the next scene
	 */
	public void nextScene() {
		System.out.println("Next Scene");
	}

	// --------------- build GUI components --------------------
	/**
	 * Create all the components
	 */
	private void buildGUI() {

		// build the button menu
		buildButtons();

		// build the radio button panel to change the color
		buildRadio();

		// build the check box button panel for fill and boundary
		// operations
		buildCheck();

		// build the sliders for X and Y translations
		buildSliders();

		// build the ListBox for color changes
		buildListBox();

		// text input window
		inputDialogFrame();

		// build label for static text
		buildLabel();

	}

	// --------------------- buildLabel ------------------------------------
	private void buildLabel() {
		label = new JLabel();
		label.setVisible(true);
		label.setText("T0");
		label.setBorder(BorderFactory.createTitledBorder("Lab"));
		bPanel.add(label);

	}

	// --------------------- buildButtons ------------------------------------
	/**
	 * build a button panel; to erase all objects
	 */
	private void buildButtons() {
		bPanel.setBorder(new LineBorder(Color.BLACK, 2));

		JButton eraseAll = new JButton("eraseAll");
		eraseAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String command = ae.getActionCommand();
				if (command.equals("eraseAll")) {
					UIjogl.triSceneObj.clear();
					UIjogl.rectSceneObj.clear();
					UIjogl.quadSceneObj.clear();
					UIjogl.quadDiaSceneObj.clear();
					UIjogl.riTriSceneObj.clear();
					UIjogl.polySceneObj.clear();
					UIjogl.pentSceneObj.clear();
					UIjogl.octSceneObj.clear();

					listIndex = 0;
				}
			}
		});
		bPanel.add(eraseAll);

	}

	// --------------------- buildRadio ------------------------------------
	/**
	 * build a radio button panel with exclusive behavior (1 button pressed at a
	 * time.
	 */
	private void buildRadio() {
		// The ButtonGroup defines a set of RadioButtons that must be
		// "exclusive"
		// -- only 1 can be "active" at a time.

		ButtonGroup bGroup = new ButtonGroup();

		rectButton = new JRadioButton("Rectangle");
		rectButton.setVisible(true);
		bPanel.add(rectButton);
		bGroup.add(rectButton);

		triButton = new JRadioButton("Triangle");
		bPanel.add(triButton);
		triButton.setSelected(true);

		bGroup.add(triButton);

		triRightButton = new JRadioButton("RightTriangle");
		bPanel.add(triRightButton);
		triRightButton.setSelected(true);
		bGroup.add(triRightButton);

		quadButton = new JRadioButton("Quad");
		bPanel.add(quadButton);
		bGroup.add(quadButton);

		quadDiamondButton = new JRadioButton("QuadDiamond");
		bPanel.add(quadDiamondButton);
		bGroup.add(quadDiamondButton);

		polyButton = new JRadioButton("Polygon");
		bPanel.add(polyButton);
		bGroup.add(polyButton);

		pentButton = new JRadioButton("Pentagon");
		bPanel.add(pentButton);
		bGroup.add(pentButton);

		octButton = new JRadioButton("Octagon");
		bPanel.add(octButton);
		bGroup.add(octButton);

	}

	private void buildSliders() {

		// ////////////////////////////////////////////////////////////////
		// 1. Copy and edit the above Y slider code to make an X slider
		// in the SOUTH border region.
		// Add code to the SliderListener code to process the X-slider events
		// 2. Copy and edit again to create the Size slider for controlling
		// size of the target JShape.
		// Add code to SliderListener to process S-slider events.
		// ////////////////////////////////////////////////////////////////
		// ------------- X Slider ------------------------------------
		JSlider xSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 250);
		addLabels(xSlider, 100);
		xSlider.addChangeListener(new SliderListener(xSlider, "x"));
		xSlider.setBorder(new LineBorder(Color.BLACK, 2));
		bPanel.add(xSlider);

		// ------------- Y Slider ------------------------------------
		JSlider ySlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 250);
		ySlider.setInverted(true); // puts min slider value at top
		addLabels(ySlider, 100);
		ySlider.addChangeListener(new SliderListener(ySlider, "y"));
		ySlider.setBorder(new LineBorder(Color.BLACK, 2));
		bPanel.add(ySlider);
	}

	// ---------------- addLabels( JSlider, int ) -----------------------
	/**
	 * a utility method to add tick marks. First argument is the slider, the
	 * second represents the major tick mark interval minor tick mark interval
	 * will be 1/10 of that.
	 */
	private void addLabels(JSlider slider, int majorTicks) {
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(majorTicks);
		slider.setMinorTickSpacing(majorTicks / 10);
	}

	// ++++++++++++++++++++++++ SliderListener inner class
	// ++++++++++++++++++++++
	/**
	 * The SliderListener needs access to -- the slider it is associated with
	 * (to get that slider's value) -- the JShape that is being controlled. -- a
	 * string that serves as an identifier for the slider These are passed to
	 * the constructor.
	 */
	public class SliderListener implements ChangeListener {
		private JSlider _slider;
		private String _id;

		public SliderListener(JSlider slider, String id) {
			_slider = slider;
			_id = id;
		}

		// ------------------- stateChanged -----------------------------
		/**
		 * Invoked whenever user changes the state of a slider
		 */
		public void stateChanged(ChangeEvent ev) {
			// ////////////////////////////////////////////////////////////
			// a. add code to respond to the y-slider. it needs to
			// change the y-position of the target rectangle
			// b. After adding the x-slider, need to test here which slider
			// generated the event; can do that by testing the
			// _id field that was set in the constructor.
			// Compare it (using the String equals method) to the
			// String used to create this instance of the SliderListener.
			// c. After adding the size slider, need to augment this code
			// to identify and handle events from that slider.
			// ///////////////////////////////////////////////////////////

			if (_id.equals("y")) // y-slider
				System.out.println("Y: " + _slider.getValue());
			else if (_id.equals("x")) // x-slider
				System.out.println("X: " + _slider.getValue());
			else
				// size slider
				System.out.println("S: " + _slider.getValue());
			// +++++++++ this is a very poor "hack": just a crude way
			// of getting event to show up in the display of this demo
			// A real application needs a cleaner set of interfaces between
			// the Listener object and the Responder object (in the
			// Source/Listener/Responder interaction model).
			UIjogl.changeEvent(_id, _slider.getValue());
		}
	}

	// ------------------- buildCheck -----------------------------
	private void buildCheck() {

		// bPanel = new JPanel();
		checkPolyBoundary = new JCheckBox("Polygon Boundary");

		bPanel.add(checkPolyBoundary, BorderLayout.SOUTH);
		checkPolyInterior = new JCheckBox("Polygon Interior");
		checkPolyInterior.setSelected(true);
		checkPolyInterior.setBorder(new LineBorder(Color.BLACK, 2));
		bPanel.add(checkPolyInterior, BorderLayout.SOUTH);

	}

	// ------------------- buildListBox -----------------------------

	private void buildListBox() {

		fillList = new JList(colorNames);
		fillList.setVisibleRowCount(4);
		fillList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fillList.setBorder(BorderFactory.createTitledBorder("fill"));
		fillList.setSelectedIndex(1);
		bPanel.add(new JScrollPane(fillList));

		boundList = new JList(colorNamesNew);
		boundList.setVisibleRowCount(4);
		boundList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		boundList.setBorder(BorderFactory.createTitledBorder("bound"));
		bPanel.add(new JScrollPane(boundList));

	}

	// ------------------- Text input window -----------------------------

	public void inputDialogFrame() {

		JFrame frame = new JFrame("CONTROL PANEL");

		JButton button = new JButton("Delete shape input");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				deleteId = JOptionPane.showInputDialog(null, "Enter a shape ID : ");

				if(deleteId == null){
					return ;
				}
				
				if (triButton.isSelected()) {
					Triangle toDelete = UIjogl.triSceneObj.get(deleteId);
					if (toDelete != null) {
						UIjogl.triSceneObj.remove(deleteId);
					} else {
						deleteTypes(deleteId);
					}
				}

				if (triRightButton.isSelected()) {
					RightTriangle todelrt = UIjogl.riTriSceneObj.get(deleteId);
					if (todelrt != null) {
						UIjogl.riTriSceneObj.remove(deleteId);
					} else {
						deleteTypes(deleteId);
					}
				}

				if (rectButton.isSelected()) {
					Rectangle todelrect = UIjogl.rectSceneObj.get(deleteId);
					if (todelrect != null) {
						UIjogl.rectSceneObj.remove(deleteId);
					} else {
						deleteTypes(deleteId);
					}
				}

				if (quadButton.isSelected()) {
					Quad todelquad = UIjogl.quadSceneObj.get(deleteId);
					if (todelquad != null) {
						UIjogl.quadSceneObj.remove(deleteId);
					} else {
						deleteTypes(deleteId);
					}
				}

				if (quadDiamondButton.isSelected()) {
					QuadDiamond todelquadd = UIjogl.quadDiaSceneObj
							.get(deleteId);
					if (todelquadd != null) {
						UIjogl.quadDiaSceneObj.remove(deleteId);
					} else {
						deleteTypes(deleteId);
					}
				}

				// ----- for polygon ------

				if (polyButton.isSelected()) {
					Polygon todelpoly = UIjogl.polySceneObj.get(deleteId);
					if (todelpoly != null) {
						UIjogl.polySceneObj.remove(deleteId);
					} else {
						deleteTypes(deleteId);
					}
				}

				if (pentButton.isSelected()) {
					Pentagon todelpolype = UIjogl.pentSceneObj.get(deleteId);
					if (todelpolype != null) {
						UIjogl.pentSceneObj.remove(deleteId);
					} else {
						deleteTypes(deleteId);
					}
				}

				if (octButton.isSelected()) {
					Octagon todeloct = UIjogl.octSceneObj.get(deleteId);
					if (todeloct != null) {
						UIjogl.octSceneObj.remove(deleteId);
					} else {
						deleteTypes(deleteId);
					}
				}

				JOptionPane.showMessageDialog(null, "You entered the ID : "
						+ deleteId, "Delete", 1);
				System.out.println(myJOptionPane.getValue().toString());

			}
		});

		bPanel.add(button, BorderLayout.EAST);
		frame.add(bPanel);
		frame.setSize(500, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	// ------------------- mouseListener inner class
	// -----------------------------

	public class Mouse implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {

			dragging = true;
			int relX = e.getX();
			int relY = e.getY();

			finish = new Point(e.getX(), e.getY());

			setLocAndSizeValues();

			if (triButton.isSelected()) {
				drawTriangle();
			}
			if (triRightButton.isSelected()) {
				drawRightTriangle();
			}
			if (rectButton.isSelected()) {
				drawRectangle();
			}
			if (quadButton.isSelected()) {
				drawQuad();
			}

			if (quadDiamondButton.isSelected()) {
				drawDiamondQuad();
			}
			if (polyButton.isSelected()) {
				drawPolygon();
			}

			if (pentButton.isSelected()) {
				drawPentagon();
			}

			if (octButton.isSelected()) {
				drawOctagon();
			}

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {

			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {

			beginning = new Point(e.getX(), e.getY());

			Color currentColor = Color.red;
			/**
			 * --------------------------------------------------- If Triangle
			 * radio button is selected perform the following operations
			 * ---------------------------------------------------
			 */
			if (triButton.isSelected()) {
				Triangle tri = new Triangle();

				if (checkPolyBoundary.isSelected()) {

					currentColor = colors[boundList.getSelectedIndex()];

					tri.setBoundary(true);

				}
				if (checkPolyInterior.isSelected()) {
					currentColor = colors[fillList.getSelectedIndex()];

					tri.setBoundary(false);
				}
				tri.setColor(currentColor.getRed(), currentColor.getGreen(),
						currentColor.getBlue());
				tri.setLocation((float) beginning.getX(),
						(float) beginning.getY());
				tri.setSize(200, 300);

				UIjogl.triSceneObj.put("T" + tCount, tri);

			}
			/**
			 * --------------------------------------------------- If
			 * RightTriangle radio button is selected perform the following
			 * operations ---------------------------------------------------
			 */
			if (triRightButton.isSelected()) {
				RightTriangle triRight = new RightTriangle();
				if (checkPolyBoundary.isSelected()) {
					currentColor = colors[boundList.getSelectedIndex()];
					triRight.setBoundary(true);

				}
				if (checkPolyInterior.isSelected()) {
					currentColor = colors[fillList.getSelectedIndex()];
					triRight.setBoundary(false);

				}
				triRight.setColor(currentColor.getRed(),
						currentColor.getGreen(), currentColor.getBlue());

				triRight.setLocation((float) beginning.getX(),
						(float) beginning.getY());
				triRight.setSize(200, 300);

				UIjogl.riTriSceneObj.put("RT" + t1Count, triRight);

			}

			/**
			 * --------------------------------------------------- If Rectangle
			 * radio button is selected perform the following operations
			 * ---------------------------------------------------
			 */

			if (rectButton.isSelected()) {
				Rectangle rect = new Rectangle();
				if (checkPolyBoundary.isSelected()) {
					currentColor = colors[boundList.getSelectedIndex()];
					rect.setBoundary(true);

				}
				if (checkPolyInterior.isSelected()) {
					currentColor = colors[fillList.getSelectedIndex()];
					rect.setBoundary(false);

				}
				rect.setColor(currentColor.getRed(), currentColor.getGreen(),
						currentColor.getBlue());
				rect.setLocation((float) beginning.getX(),
						(float) beginning.getY());
				rect.setSize(300, 200);
				System.out.println("list Index " + listIndex);

				UIjogl.rectSceneObj.put("R" + rCount, rect);

			}

			/**
			 * --------------------------------------------------- If Quad radio
			 * button is selected perform the following operations
			 * ---------------------------------------------------
			 */
			if (quadButton.isSelected()) {
				Quad quad = new Quad();
				if (checkPolyBoundary.isSelected()) {
					currentColor = colors[boundList.getSelectedIndex()];
					quad.setBoundary(true);

				}
				if (checkPolyInterior.isSelected()) {
					currentColor = colors[fillList.getSelectedIndex()];
					quad.setBoundary(false);

				}

				quad.setColor(currentColor.getRed(), currentColor.getGreen(),
						currentColor.getBlue());
				quad.setLocation((float) beginning.getX(),
						(float) beginning.getY());
				quad.setSize(300, 200);
				System.out.println("list Index " + listIndex);
				UIjogl.quadSceneObj.put("Q" + qCount, quad);

			}

			/**
			 * --------------------------------------------------- If
			 * QuadDiamond radio button is selected perform the following
			 * operations ---------------------------------------------------
			 */
			if (quadDiamondButton.isSelected()) {
				QuadDiamond quadDia = new QuadDiamond();
				if (checkPolyBoundary.isSelected()) {
					currentColor = colors[boundList.getSelectedIndex()];
					quadDia.setBoundary(true);

				}
				if (checkPolyInterior.isSelected()) {
					currentColor = colors[fillList.getSelectedIndex()];
					quadDia.setBoundary(false);

				}

				quadDia.setColor(currentColor.getRed(),
						currentColor.getGreen(), currentColor.getBlue());
				quadDia.setLocation((float) beginning.getX(),
						(float) beginning.getY());
				quadDia.setSize(300, 200);
				System.out.println("list Index " + listIndex);

				UIjogl.quadDiaSceneObj.put("D" + q1Count, quadDia);

			}

			/**
			 * --------------------------------------------------- If Polygon
			 * radio button is selected perform the following operations
			 * ---------------------------------------------------
			 */
			if (polyButton.isSelected()) {
				Polygon poly = new Polygon();
				if (checkPolyBoundary.isSelected()) {
					currentColor = colors[boundList.getSelectedIndex()];
					poly.setBoundary(true);

				}
				if (checkPolyInterior.isSelected()) {
					currentColor = colors[fillList.getSelectedIndex()];
					poly.setBoundary(false);

				}

				poly.setColor(currentColor.getRed(), currentColor.getGreen(),
						currentColor.getBlue());
				poly.setLocation((float) beginning.getX(),
						(float) beginning.getY());
				poly.setSize(300, 200);
				UIjogl.polySceneObj.put("P" + pCount, poly);

			}

			/**
			 * --------------------------------------------------- If Pentagon
			 * radio button is selected perform the following operations
			 * ---------------------------------------------------
			 */
			if (pentButton.isSelected()) {
				Pentagon pent = new Pentagon();
				if (checkPolyBoundary.isSelected()) {
					currentColor = colors[boundList.getSelectedIndex()];
					pent.setBoundary(true);

				}
				if (checkPolyInterior.isSelected()) {
					currentColor = colors[fillList.getSelectedIndex()];
					pent.setBoundary(false);

				}

				pent.setColor(currentColor.getRed(), currentColor.getGreen(),
						currentColor.getBlue());
				pent.setLocation((float) beginning.getX(),
						(float) beginning.getY());
				pent.setSize(300, 200);

				UIjogl.pentSceneObj.put("PE" + p1Count, pent);

			}

			/**
			 * --------------------------------------------------- If Octagon
			 * radio button is selected perform the following operations
			 * ---------------------------------------------------
			 */

			if (octButton.isSelected()) {
				Octagon oct = new Octagon();
				if (checkPolyBoundary.isSelected()) {
					currentColor = colors[boundList.getSelectedIndex()];
					oct.setBoundary(true);

				}
				if (checkPolyInterior.isSelected()) {
					currentColor = colors[fillList.getSelectedIndex()];
					oct.setBoundary(false);

				}

				oct.setColor(currentColor.getRed(), currentColor.getGreen(),
						currentColor.getBlue());
				oct.setLocation((float) beginning.getX(),
						(float) beginning.getY());
				oct.setSize(300, 200);
				System.out.println("list Index " + listIndex);
				UIjogl.octSceneObj.put("O" + p2Count, oct);

			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			dragging = false;

			/**
			 * listIndex is incremented everytime a new object instance is
			 * created and counter associated with the Jlabel is increased
			 */

			if (triButton.isSelected()) {
				// hm.put("T" + tCount, listIndex);
				tCount++;
				label.setText("T" + tCount);

			}
			if (triRightButton.isSelected()) {
				// hm.put("RT" + t1Count, listIndex);
				t1Count++;
				label.setText("RT" + t1Count);

			}
			if (rectButton.isSelected()) {
				// hm.put("R" + rCount, listIndex);
				rCount++;
				label.setText("R" + rCount);

			}
			if (quadButton.isSelected()) {
				// hm.put("Q" + qCount, listIndex);
				qCount++;
				label.setText("Q" + qCount);

			}

			if (quadDiamondButton.isSelected()) {
				// hm.put("D" + q1Count, listIndex);
				q1Count++;
				label.setText("D" + q1Count);

			}

			if (polyButton.isSelected()) {
				// hm.put("P" + pCount, listIndex);
				pCount++;
				label.setText("P" + pCount);

			}
			if (pentButton.isSelected()) {
				// hm.put("PE" + p1Count, listIndex);
				p1Count++;
				label.setText("PE" + p1Count);

			}
			if (octButton.isSelected()) {
				// hm.put("O" + p2Count, listIndex);
				p2Count++;
				label.setText("O" + p2Count);

			}
			// lastLine
			listIndex++;
			bPanel.add(label);

		}

		private void drawTriangle() {

			Triangle newTri = UIjogl.triSceneObj.get("T" + tCount);

			if (checkPolyBoundary.isSelected()) {
				newTri.setBoundary(true);
				Color currentBoundary = colors[boundList.getSelectedIndex()];
				newTri.setColor(currentBoundary.getRed(),
						currentBoundary.getGreen(), currentBoundary.getBlue());

			}
			if (checkPolyInterior.isSelected()) {
				newTri.setBoundary(false);
				Color currentColor = colors[fillList.getSelectedIndex()];
				newTri.setColor(currentColor.getRed(), currentColor.getGreen(),
						currentColor.getBlue());
			}

			newTri.setLocation(locX, locY);
			newTri.setSize(sizeX, sizeY);

		}

	}

	private void drawRightTriangle() {

		RightTriangle newRightTri = UIjogl.riTriSceneObj.get("RT" + t1Count);

		if (checkPolyBoundary.isSelected()) {
			newRightTri.setBoundary(true);
			Color currentBoundary = colors[boundList.getSelectedIndex()];
			newRightTri.setColor(currentBoundary.getRed(),
					currentBoundary.getGreen(), currentBoundary.getBlue());

		}
		if (checkPolyInterior.isSelected()) {
			newRightTri.setBoundary(false);
			Color currentColor = colors[fillList.getSelectedIndex()];
			newRightTri.setColor(currentColor.getRed(),
					currentColor.getGreen(), currentColor.getBlue());
		}

		newRightTri.setLocation(locX, locY);
		newRightTri.setSize(sizeX, sizeY);

	}

	private void drawQuad() {

		Quad newQuad = (Quad) UIjogl.quadSceneObj.get("Q" + qCount);

		if (checkPolyBoundary.isSelected()) {
			newQuad.setBoundary(true);
			Color currentBoundary = colors[boundList.getSelectedIndex()];
			newQuad.setColor(currentBoundary.getRed(),
					currentBoundary.getGreen(), currentBoundary.getBlue());

		}
		if (checkPolyInterior.isSelected()) {
			newQuad.setBoundary(false);
			Color currentColor = colors[fillList.getSelectedIndex()];
			newQuad.setColor(currentColor.getRed(), currentColor.getGreen(),
					currentColor.getBlue());
		}

		newQuad.setLocation(locX, locY);
		newQuad.setSize(sizeX, sizeY);

	}

	private void drawDiamondQuad() {

		QuadDiamond newDiaQuad = UIjogl.quadDiaSceneObj.get("D" + q1Count);

		if (checkPolyBoundary.isSelected()) {
			newDiaQuad.setBoundary(true);
			Color currentBoundary = colors[boundList.getSelectedIndex()];
			newDiaQuad.setColor(currentBoundary.getRed(),
					currentBoundary.getGreen(), currentBoundary.getBlue());

		}
		if (checkPolyInterior.isSelected()) {
			newDiaQuad.setBoundary(false);
			Color currentColor = colors[fillList.getSelectedIndex()];
			newDiaQuad.setColor(currentColor.getRed(), currentColor.getGreen(),
					currentColor.getBlue());
		}

		newDiaQuad.setLocation(locX, locY);
		newDiaQuad.setSize(sizeX, sizeY);

	}

	private void drawPolygon() {

		Polygon newPolygon = UIjogl.polySceneObj.get("P" + pCount);

		if (checkPolyBoundary.isSelected()) {
			newPolygon.setBoundary(true);
			Color currentBoundary = colors[boundList.getSelectedIndex()];
			newPolygon.setColor(currentBoundary.getRed(),
					currentBoundary.getGreen(), currentBoundary.getBlue());

		}
		if (checkPolyInterior.isSelected()) {
			newPolygon.setBoundary(false);
			Color currentColor = colors[fillList.getSelectedIndex()];
			newPolygon.setColor(currentColor.getRed(), currentColor.getGreen(),
					currentColor.getBlue());
		}

		newPolygon.setLocation(locX, locY);
		newPolygon.setSize(sizeX, sizeY);

	}

	private void drawPentagon() {

		Pentagon newPent = (Pentagon) UIjogl.pentSceneObj.get("PE" + p1Count);

		if (checkPolyBoundary.isSelected()) {
			newPent.setBoundary(true);
			Color currentBoundary = colors[boundList.getSelectedIndex()];
			newPent.setColor(currentBoundary.getRed(),
					currentBoundary.getGreen(), currentBoundary.getBlue());

		}
		if (checkPolyInterior.isSelected()) {
			newPent.setBoundary(false);
			Color currentColor = colors[fillList.getSelectedIndex()];
			newPent.setColor(currentColor.getRed(), currentColor.getGreen(),
					currentColor.getBlue());
		}

		newPent.setLocation(locX, locY);
		newPent.setSize(sizeX, sizeY);

	}

	private void drawOctagon() {

		Octagon newOct = (Octagon) UIjogl.octSceneObj.get("O" + p2Count);

		if (checkPolyBoundary.isSelected()) {
			newOct.setBoundary(true);
			Color currentBoundary = colors[boundList.getSelectedIndex()];
			newOct.setColor(currentBoundary.getRed(),
					currentBoundary.getGreen(), currentBoundary.getBlue());

		}
		if (checkPolyInterior.isSelected()) {
			newOct.setBoundary(false);
			Color currentColor = colors[fillList.getSelectedIndex()];
			newOct.setColor(currentColor.getRed(), currentColor.getGreen(),
					currentColor.getBlue());
		}

		newOct.setLocation(locX, locY);
		newOct.setSize(sizeX, sizeY);

	}

	private void drawRectangle() {

		Shape newRect = UIjogl.rectSceneObj.get("R" + rCount);

		if (checkPolyBoundary.isSelected()) {
			newRect.setBoundary(true);
			Color currentBoundary = colors[boundList.getSelectedIndex()];
			newRect.setColor(currentBoundary.getRed(),
					currentBoundary.getGreen(), currentBoundary.getBlue());

		}
		if (checkPolyInterior.isSelected()) {
			newRect.setBoundary(false);
			Color currentColor = colors[fillList.getSelectedIndex()];
			newRect.setColor(currentColor.getRed(), currentColor.getGreen(),
					currentColor.getBlue());
		}

		newRect.setLocation(locX, locY);
		newRect.setSize(sizeX, sizeY);
	}

	private void setLocAndSizeValues() {

		if ((beginning.getX() > finish.getX())
				&& (beginning.getY() > finish.getY())) {
			locX = (float) finish.getX();
			locY = (float) beginning.getY();
			sizeX = (float) (beginning.getX() - finish.getX());
			sizeY = (float) (beginning.getY() - finish.getY());
		} else if ((beginning.getX() < finish.getX())
				&& (beginning.getY() < finish.getY())) {

			locX = (float) beginning.getX();
			locY = (float) finish.getY();
			sizeX = (float) (finish.getX() - beginning.getX());
			sizeY = (float) (finish.getY() - beginning.getY());

		}

		else if ((finish.getX() > beginning.getX())
				&& (beginning.getY() > finish.getY())) {
			// System.out.println("inside if part");

			locX = (float) beginning.getX();
			locY = (float) finish.getX();
			sizeX = (float) (finish.getX() - beginning.getX());
			sizeY = (float) (beginning.getY() - finish.getY());

		}

		else if ((beginning.getX() > finish.getX())
				&& (beginning.getY() < finish.getY())) {

			locX = (float) beginning.getX();
			locY = (float) finish.getX();
			sizeX = (float) (beginning.getX() - finish.getX());
			sizeY = (float) (finish.getY() - beginning.getY());

		}
	}

	private void deleteTypes(String deleteId) {

		if (deleteId.charAt(0) == 'T' && deleteId.charAt(1) == '*') {
			UIjogl.triSceneObj.clear();
		} else if (deleteId.charAt(0) == 'R' && deleteId.charAt(1) == 'T'
				&& deleteId.charAt(2) == '*') {
			UIjogl.riTriSceneObj.clear();
		}

		else if (deleteId.charAt(0) == 'R' && deleteId.charAt(1) == '*') {
			UIjogl.rectSceneObj.clear();
		}

		else if (deleteId.charAt(0) == 'Q' && deleteId.charAt(1) == '*') {
			UIjogl.quadSceneObj.clear();
		}

		else if (deleteId.charAt(0) == 'D' && deleteId.charAt(1) == '*') {
			UIjogl.quadDiaSceneObj.clear();
		} else if (deleteId.charAt(0) == 'P' && deleteId.charAt(1) == '*') {
			UIjogl.polySceneObj.clear();
		}

		else if (deleteId.charAt(0) == 'P' && deleteId.charAt(1) == 'E'
				&& deleteId.charAt(1) == '*') {
			UIjogl.pentSceneObj.clear();
		}

		else if (deleteId.charAt(0) == 'O' && deleteId.charAt(1) == '*') {
			UIjogl.octSceneObj.clear();
		}

	}

}
