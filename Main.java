package pkg3dengine;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class Main {

	static Double Speed = 1d;

	public static void main2(String[] args) {
		long NumOfPoints = 10000000000l;
		Engine en = new Engine(new Camera());
		en.c.SetPosition(new Vector3(-20, 0, 5));
		long Start = System.currentTimeMillis();
		for (long i = 0; i < NumOfPoints; i++) {
			en.Conv(new Vector3(i * i, i, i / 10));
		}
		System.out.println("Time for " + NumOfPoints + " points took " + (System.currentTimeMillis() - Start) + " to run. "
				+ (NumOfPoints / (System.currentTimeMillis() - Start)) + " points per ms");
	}

	public static void main(String[] args) {
		ArrayList<Point3D> HeadPoints = new ArrayList<>();
		Point3D a = new Point3D(new Vector3(10, 10, 10), new ArrayList<>());
		Point3D b = new Point3D(new Vector3(10, 0 , 10), new ArrayList<Point3D>() {{add(a);}});
		Point3D c = new Point3D(new Vector3(0 , 10, 10), new ArrayList<Point3D>() {{add(a);}});
		Point3D d = new Point3D(new Vector3(10, 10, 0 ), new ArrayList<Point3D>() {{add(a);}});
		Point3D e = new Point3D(new Vector3(0 , 0 , 10), new ArrayList<Point3D>() {{add(b);add(c);}});
		Point3D f = new Point3D(new Vector3(0 , 10, 0 ), new ArrayList<Point3D>() {{add(d);add(c);}});
		Point3D g = new Point3D(new Vector3(10, 0 , 0 ), new ArrayList<Point3D>() {{add(d);add(b);}});
		Point3D h = new Point3D(new Vector3(0 , 0 , 0 ), new ArrayList<Point3D>() {{add(f);add(g);add(e);}});
		HeadPoints.add(h);

		Engine en = new Engine(new Camera());
		en.c.SetPosition(new Vector3(-20, 0, 5));
		Renderer r = new Renderer(en, HeadPoints);
		JFrame fr = new JFrame("DirectivePerspecitve");
		fr.setContentPane(r);
		fr.pack();
		fr.setVisible(true);
		fr.setDefaultCloseOperation(3);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int t;
			@Override
			public void run() {
					fr.repaint();
				t++;
				if (t % 25 == 0) {
					t = 0;
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							r.Disco[i][j] = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
						}
					}
				}
				if (Input.IsKeyDown(KeyEvent.VK_W)) {
					en.c.GetPos().Add(en.RotateAllReverse(new Vector3(Speed, 0, 0), -en.c.GetPitch(), -en.c.GetYaw(), -en.c.GetRoll()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_S)) {
					en.c.GetPos().Add(en.RotateAllReverse(new Vector3(-Speed, 0, 0), -en.c.GetPitch(), -en.c.GetYaw(), -en.c.GetRoll()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_D)) {
					en.c.GetPos().Add(en.RotateAllReverse(new Vector3(0, Speed, 0), -en.c.GetPitch(), -en.c.GetYaw(), -en.c.GetRoll()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_A)) {
					en.c.GetPos().Add(en.RotateAllReverse(new Vector3(0, -Speed, 0), -en.c.GetPitch(), -en.c.GetYaw(), -en.c.GetRoll()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_SPACE)) {
					en.c.GetPos().Add(en.RotateAllReverse(new Vector3(0, 0, Speed), -en.c.GetPitch(), -en.c.GetYaw(), -en.c.GetRoll()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_SHIFT)) {
					en.c.GetPos().Add(en.RotateAllReverse(new Vector3(0, 0, -Speed), -en.c.GetPitch(), -en.c.GetYaw(), -en.c.GetRoll()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_Q)) {
					en.c.SetYaw(en.c.GetYaw() + .04 * Speed);
					if (en.c.GetYaw() > jMath.PI) {
						en.c.SetYaw(en.c.GetYaw() - jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_E)) {
					en.c.SetYaw(en.c.GetYaw() - .04 * Speed);
					if (en.c.GetYaw() < -jMath.PI) {
						en.c.SetYaw(en.c.GetYaw() + jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_R)) {
					en.c.SetPitch(en.c.GetPitch() - .04 * Speed);
					if (en.c.GetPitch() < -jMath.PI) {
						en.c.SetPitch(en.c.GetPitch() + jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_F)) {
					en.c.SetPitch(en.c.GetPitch() + .04 * Speed);
					if (en.c.GetPitch() > jMath.PI) {
						en.c.SetPitch(en.c.GetPitch() - jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_X)) {
					en.c.SetRoll(en.c.GetRoll() - .04 * Speed);
					if (en.c.GetRoll() < -jMath.PI) {
						en.c.SetRoll(en.c.GetRoll() + jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_Z)) {
					en.c.SetRoll(en.c.GetRoll() + .04 * Speed);
					if (en.c.GetRoll() > jMath.PI) {
						en.c.SetRoll(en.c.GetRoll() - jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyPressed(KeyEvent.VK_ADD)) {
					Speed += .1;
				}
				if (Input.IsKeyPressed(KeyEvent.VK_SUBTRACT)) {
					Speed -= .1;
				}
			}
		}, 0, 16);
	}
}
