package pkg3dengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Renderer extends JPanel {

	Engine e;
	ArrayList<Point3D> HeadPoints;
	public static double LatticeSize = 1;
	public static double LatticeRange = 10;
	public static int VertexFieldSize = 100;

	public static int WINDOWHEIGHT = 800;
	public static int WINDOWWIDTH = 800;

	private static int CROSSHAIRTHICKNESS = 2;
	private static int CROSSHAIRLENGTH = 30;

	public Color[][] Disco = new Color[10][10];
	public Map<String, Color[][]> Textures;

	Renderer(Engine e, ArrayList<Point3D> HeadPoints) {
		this.e = e;
		this.HeadPoints = HeadPoints;
		this.setPreferredSize(new Dimension(WINDOWHEIGHT, WINDOWWIDTH));
		Textures = new HashMap<>();
		Image img = null;
		try {
			img = ImageIO.read(new File("src/pkg3dengine/Images/RingRune.jpg"));
		} catch (IOException ex) {
			Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
		}
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		int[] pixels = new int[w * h];
		PixelGrabber pg = new PixelGrabber(img, 0, 0, w, h, pixels, 0, w);
		try {
			pg.grabPixels();
		} catch (InterruptedException ex) {
			Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
		}
		Color[][] c = new Color[h][w];
		for (int i = 0; i < pixels.length; i++) {
			Color temp = new Color(pixels[i]);
			c[(int) (i / w)][i % w] = temp;
		}
		Textures.put("src/pkg3dengine/Images/RingRune.jpg", c);
	}

	@Override
	public void paintComponent(Graphics g) {
		long start = System.currentTimeMillis();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WINDOWHEIGHT, WINDOWWIDTH);
		//DrawVertexField(g);
		DrawLatticeGrid(g);
		ArrayList<Line> l = e.Conv(e.Conv(HeadPoints.get(0).Position), HeadPoints.get(0).Linked);
		for (int i = 0; i < l.size(); i++) {
			DrawLine(g, l.get(i));
			DrawPoint(g, l.get(i).GetPoint1());
			DrawPoint(g, l.get(i).GetPoint2());
		}

		ArrayList<Vector2> t = new ArrayList<>();
		t.add(e.Conv(new Vector3(0, 0, 10)));
		t.add(e.Conv(new Vector3(0, 10, 10)));
		t.add(e.Conv(new Vector3(0, 10, 0)));
		t.add(e.Conv(new Vector3(0, 0, 0)));

		ArrayList<Vector2> t2 = new ArrayList<>();
		t2.add(e.Conv(new Vector3(10, 0, 10)));
		t2.add(e.Conv(new Vector3(10, 10, 10)));
		t2.add(e.Conv(new Vector3(0, 10, 10)));
		t2.add(e.Conv(new Vector3(0, 0, 10)));
		Vector3 avg1 = new Vector3(0, 5, 5);
		Vector3 avg2 = new Vector3(5, 5, 10);
		for (int i = 0; i < t.size(); i++) {
			DrawPoint(g, t.get(i));
		}

		if (DistanceBetween(avg1, e.c.GetPos()) > DistanceBetween(avg2, e.c.GetPos())) {
			GraphicsFunctions.DrawTexture(t, Textures.get("src/pkg3dengine/Images/RingRune.jpg"), g);
			GraphicsFunctions.DrawTexture(t2, Disco, g);
		} else {
			GraphicsFunctions.DrawTexture(t2, Disco, g);
			GraphicsFunctions.DrawTexture(t, Textures.get("src/pkg3dengine/Images/RingRune.jpg"), g);
		}
		DrawCompass(g);
		DrawCrosshair(g);
		long end = System.currentTimeMillis();
		g.setColor(Color.red);
		g.drawString("" + (end - start), 10, 20);
		g.drawString("" + 1000/((double)end - (double)start), 10, 40);
	}

	public double DistanceBetween(Vector3 v1, Vector3 v2) {
		return Math.sqrt(Math.pow(v1.GetX() - e.c.GetPos().GetX(), 2) + Math.pow(v1.GetY() - e.c.GetPos().GetY(), 2) + Math.pow(v1.GetZ() - e.c.GetPos().GetZ(), 2));
	}

	public static void DrawLine(Graphics g, Line l) {
		Color Original = g.getColor();
		g.setColor(Color.RED);
		g.drawLine((int) l.GetPoint1().GetX(), (int) l.GetPoint1().GetY(), (int) l.GetPoint2().GetX(), (int) l.GetPoint2().GetY());
		g.setColor(Original);
	}

	private void DrawCrosshair(Graphics g) {
		Color Original = g.getColor();
		g.setColor(Color.WHITE);
		for (int i = 0; i < CROSSHAIRTHICKNESS; i++) {
			g.drawLine(WINDOWWIDTH / 2 - CROSSHAIRTHICKNESS / 2 + i,
					WINDOWHEIGHT / 2 - CROSSHAIRLENGTH / 2,
					WINDOWWIDTH / 2 - CROSSHAIRTHICKNESS / 2 + i,
					WINDOWHEIGHT / 2 - CROSSHAIRLENGTH / 2 + CROSSHAIRLENGTH);
			g.drawLine(WINDOWWIDTH / 2 - CROSSHAIRLENGTH / 2,
					WINDOWHEIGHT / 2 - CROSSHAIRTHICKNESS / 2 + i,
					WINDOWWIDTH / 2 - CROSSHAIRLENGTH / 2 + CROSSHAIRLENGTH,
					WINDOWHEIGHT / 2 - CROSSHAIRTHICKNESS / 2 + i);
		}
		g.setColor(Original);
	}

	public static void DrawPoint(Graphics g, Vector2 P) {
		Color Original = g.getColor();
		g.setColor(Color.WHITE);
		g.fillOval((int) P.GetX() - 3, (int) P.GetY() - 3, 7, 7);
		g.setColor(Original);
	}

	public static void DrawPoint(Graphics g, Vector2 P, Color c) {
		Color Original = g.getColor();
		g.setColor(c);
		g.fillOval((int) P.GetX() - 3, (int) P.GetY() - 3, 7, 7);
		g.setColor(Original);
	}

	private void DrawVertexField(Graphics g) {
		Color prev = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		for (int x = 0; x < VertexFieldSize; x++) {
			for (int y = 0; y < VertexFieldSize; y++) {
				for (int z = 0; z < VertexFieldSize; z++) {
					Vector2 v = e.Conv(new Vector3(x, y, z));
					g.fillRect((int) v.GetX(), (int) v.GetY(), 1, 1);
				}
			}
		}
		g.setColor(prev);
	}

	private void DrawLatticeGrid(Graphics g) {
		Color prev = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		for (double x = -LatticeRange; x <= LatticeRange; x += LatticeSize) {
			for (double y = -LatticeRange; y <= LatticeRange; y += LatticeSize) {
				Vector2 v = e.Conv(new Vector3(x, y, 0));
				Vector2 v1 = e.Conv(new Vector3(x + LatticeSize, y, 0));
				Vector2 v2 = e.Conv(new Vector3(x - LatticeSize, y, 0));
				Vector2 v3 = e.Conv(new Vector3(x, y + LatticeSize, 0));
				Vector2 v4 = e.Conv(new Vector3(x, y - LatticeSize, 0));
				g.drawLine((int) v.GetX(), (int) v.GetY(), (int) v1.GetX(), (int) v1.GetY());
				g.drawLine((int) v.GetX(), (int) v.GetY(), (int) v2.GetX(), (int) v2.GetY());
				g.drawLine((int) v.GetX(), (int) v.GetY(), (int) v3.GetX(), (int) v3.GetY());
				g.drawLine((int) v.GetX(), (int) v.GetY(), (int) v4.GetX(), (int) v4.GetY());
			}
		}
		g.setColor(prev);
	}

	private void DrawCompass(Graphics g) {
		Color prev = g.getColor();
		Vector2 vUP = e.Conv(new Vector3(0, 0, 2));
		Vector2 vDown = e.Conv(new Vector3(0, 0, -2));
		Vector2 vLeft = e.Conv(new Vector3(-2, 0, 0));
		Vector2 vRight = e.Conv(new Vector3(2, 0, 0));
		Vector2 vForward = e.Conv(new Vector3(0, 2, 0));
		Vector2 vBackward = e.Conv(new Vector3(0, -2, 0));
		g.setColor(Color.MAGENTA);
		DrawThickLine(g, vUP, vDown);
		g.setColor(Color.CYAN);
		DrawThickLine(g, vLeft, vRight);
		g.setColor(Color.YELLOW);
		DrawThickLine(g, vForward, vBackward);
		g.setColor(Color.GREEN);
		g.fillRect((int) vUP.GetX() - 3, (int) vUP.GetY() - 3, 7, 7);
		g.setColor(Color.RED);
		g.fillRect((int) vRight.GetX() - 3, (int) vRight.GetY() - 3, 7, 7);
		g.setColor(Color.BLUE);
		g.fillRect((int) vForward.GetX() - 3, (int) vForward.GetY() - 3, 7, 7);
		g.setColor(prev);
	}

	private void DrawThickLine(Graphics g, Vector2 P1, Vector2 P2) {
		g.drawLine((int) P1.GetX(), (int) P1.GetY(), (int) P2.GetX(), (int) P2.GetY());
		g.drawLine((int) P1.GetX() + 1, (int) P1.GetY(), (int) P2.GetX() + 1, (int) P2.GetY());
		g.drawLine((int) P1.GetX() - 1, (int) P1.GetY(), (int) P2.GetX() - 1, (int) P2.GetY());
		g.drawLine((int) P1.GetX(), (int) P1.GetY() + 1, (int) P2.GetX(), (int) P2.GetY() + 1);
		g.drawLine((int) P1.GetX(), (int) P1.GetY() - 1, (int) P2.GetX(), (int) P2.GetY() - 1);
	}
}
