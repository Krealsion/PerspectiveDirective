package pkg3dengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Renderer extends JPanel {

	Engine e;
	ArrayList<Point3D> HeadPoints;
	double LatticeSize = 1;
	double LatticeRange = 10;

	Renderer(Engine e, ArrayList<Point3D> HeadPoints) {
		this.e = e;
		this.HeadPoints = HeadPoints;
		this.setPreferredSize(new Dimension(800, 800));
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 800);
		DrawVertexField(g);
		DrawLatticeGrid(g);
		g.setColor(Color.RED);
		ArrayList<Line> l = e.Conv(e.Conv(HeadPoints.get(0).Position), HeadPoints.get(0).Linked);
		for (int i = 0; i < l.size(); i++) {
			g.drawLine((int) l.get(i).GetPoint1().GetX(), (int) l.get(i).GetPoint1().GetY(), (int) l.get(i).GetPoint2().GetX(), (int) l.get(i).GetPoint2().GetY());
		}
		DrawCompass(g);
	}

	private void DrawVertexField(Graphics g) {
		Color prev = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		for (int x = 0; x < 75; x++) {		//TODO UNDERSTAND WHY THIS REVEALS THE TRUE NATURE OF THE UNIVERSE
			for (int y = 0; y < 75; y++) {
				for (int z = 0; z < 75; z++) {
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
				Vector2 v = e.Conv(new Vector3(x, y, 0));		//TODO set this to 0 z
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
		g.setColor(Color.MAGENTA);//CHANGE IN Z
		DrawThickLine(g, vUP, vDown);
		g.setColor(Color.CYAN);//CHANGE IN X
		DrawThickLine(g, vLeft, vRight);
		g.setColor(Color.YELLOW);//CHANGE IN Y
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
