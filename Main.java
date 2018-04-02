package pkg3dengine;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args){
	//private void dummy(){
		Engine en = new Engine(new Camera());
		en.c.SetPosition(new Vector3(-20, 0, 0));
		for (long i = 0; i < 20000000000l; i++){
			en.Conv(new Vector3(i,i,i));
		}
	}

	//public static void main(String[] args) {
	private void dummy(){
		ArrayList<Point3D> HeadPoints = new ArrayList<>();
		Point3D a = new Point3D(new Vector3(10,10,10), new ArrayList<>());
		Point3D b = new Point3D(new Vector3(10,0,10), new ArrayList<Point3D>(){{add(a);}});
		Point3D c = new Point3D(new Vector3(0,10,10), new ArrayList<Point3D>(){{add(a);}});
		Point3D d = new Point3D(new Vector3(10,10,0), new ArrayList<Point3D>(){{add(a);}});
		Point3D e = new Point3D(new Vector3(0,0,10), new ArrayList<Point3D>(){{add(b);add(c);}});
		Point3D f = new Point3D(new Vector3(0,10,0), new ArrayList<Point3D>(){{add(d);add(c);}});
		Point3D g = new Point3D(new Vector3(10,0,0), new ArrayList<Point3D>(){{add(d);add(b);}});
		Point3D h = new Point3D(new Vector3(0,0,0), new ArrayList<Point3D>(){{add(f);add(g);add(e);}});
		HeadPoints.add(h);
		
		Engine en = new Engine(new Camera());
		en.c.SetPosition(new Vector3(-20, 0, 0));
		Renderer r = new Renderer(en, HeadPoints);
		JFrame fr = new JFrame("Testing 3D");
		fr.setContentPane(r);
		fr.pack();
		fr.setVisible(true);
		fr.setDefaultCloseOperation(3);
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
				if (Input.IsKeyDown(KeyEvent.VK_W)){
					double a = en.c.GetYaw();
					double x = Math.cos(a);
					double y = Math.sin(a);
					en.c.SetPosition(new Vector3(en.c.GetPos().GetX() + x,en.c.GetPos().GetY() + y,en.c.GetPos().GetZ()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_S)){
					double a = en.c.GetYaw();
					double x = Math.cos(a);
					double y = Math.sin(a);
					en.c.SetPosition(new Vector3(en.c.GetPos().GetX() - x,en.c.GetPos().GetY() - y,en.c.GetPos().GetZ()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_A)){
					double a = en.c.GetYaw() - jMath.PIo2;
					double x = Math.cos(a);
					double y = Math.sin(a);
					en.c.SetPosition(new Vector3(en.c.GetPos().GetX()+x,en.c.GetPos().GetY()+y,en.c.GetPos().GetZ()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_D)){
					double a = en.c.GetYaw() + jMath.PIo2;
					double x = Math.cos(a);
					double y = Math.sin(a);
					en.c.SetPosition(new Vector3(en.c.GetPos().GetX()+x,en.c.GetPos().GetY()+y,en.c.GetPos().GetZ()));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_SPACE)){
					en.c.SetPosition(new Vector3(en.c.GetPos().GetX(),en.c.GetPos().GetY(),en.c.GetPos().GetZ()+1));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_SHIFT)){
					en.c.SetPosition(new Vector3(en.c.GetPos().GetX(),en.c.GetPos().GetY(),en.c.GetPos().GetZ()-1));
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_Q)){
					en.c.SetYaw(en.c.GetYaw() + .04);
					if (en.c.GetYaw() < -jMath.PI){
						en.c.SetYaw(en.c.GetYaw() + jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_E)){
					en.c.SetYaw(en.c.GetYaw() - .04);
					if (en.c.GetYaw() > jMath.PI){
						en.c.SetYaw(en.c.GetYaw() - jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_R)){
					en.c.SetPitch(en.c.GetPitch() - .04);
					if (en.c.GetPitch() < -jMath.PI){
						en.c.SetPitch(en.c.GetPitch() + jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_F)){
					en.c.SetPitch(en.c.GetPitch() + .04);
					if (en.c.GetPitch() > jMath.PI){
						en.c.SetPitch(en.c.GetPitch() - jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_X)){
					en.c.SetRoll(en.c.GetRoll() - .04);
					if (en.c.GetRoll() < -jMath.PI){
						en.c.SetRoll(en.c.GetRoll() + jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyDown(KeyEvent.VK_Z)){
					en.c.SetRoll(en.c.GetRoll() + .04);
					if (en.c.GetRoll() > jMath.PI){
						en.c.SetRoll(en.c.GetRoll() - jMath.PIt2);
					}
					fr.repaint();
				}
				if (Input.IsKeyPressed(KeyEvent.VK_V)){
					en.v = !en.v;
					fr.repaint();
				}
				//System.out.println(en.c.GetPos().GetX() + " " + en.c.GetPos().GetY() + " " + en.c.GetPos().GetZ());
            }
        }, 0, 16);
	}
}
