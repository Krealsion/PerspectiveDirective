package pkg3dengine;

import java.util.ArrayList;

public class Engine {

	Camera c;
	public static final int ScreenSize = 800;
	boolean v = false;	//Verbose

	public Engine(Camera C) {
		c = C;
	}

	public ArrayList<Line> Conv(Vector2 Point, ArrayList<Point3D> Links) {
		ArrayList<Line> t = new ArrayList<>();
		for (int i = 0; i < Links.size(); i++) {
			Vector2 LinkPos = Conv(Links.get(i).Position);
			t.add(new Line(Point, LinkPos));	//This adds a line from the point to what it is linked to
			t.addAll(Conv(LinkPos, Links.get(i).Linked));
		}
		return t;
	}


	public Vector2 Conv(Vector3 P) {
		Vector3 CamPos = c.GetPos();
		Vector3 OriginP = new Vector3(P.GetX() - CamPos.GetX(), P.GetY() - CamPos.GetY(), P.GetZ() - CamPos.GetZ());
		OriginP = RotatePointXY(OriginP, c.GetYaw());
		OriginP = RotatePointXZ(OriginP, c.GetPitch());
		OriginP = RotatePointYZ(OriginP, c.GetRoll());
		double WidthAtX = c.GetTanHalfFOV() * OriginP.GetX();
		double Perc1 = .5 + (OriginP.GetY() / WidthAtX);
		double Perc2 = .5 - (OriginP.GetZ() / WidthAtX);
		return new Vector2(Perc1 * ScreenSize, Perc2 * ScreenSize);
	}

	public Vector3 RotatePointXY(Vector3 P, double Angle) {
		double si = Math.sin(Angle);
		double co = Math.cos(Angle);
		return new Vector3(P.GetX() * co - P.GetY() * si, P.GetY() * co + P.GetX() * si, P.GetZ());
	}

	public Vector3 RotatePointXZ(Vector3 P, double Angle) {
		double si = Math.sin(Angle);
		double co = Math.cos(Angle);
		return new Vector3(P.GetX() * co - P.GetZ() * si, P.GetY(), P.GetZ() * co + P.GetX() * si);
	}
	public Vector3 RotatePointYZ(Vector3 P, double Angle) {
		double si = Math.sin(Angle);
		double co = Math.cos(Angle);
		return new Vector3(P.GetX(), P.GetY() * co - P.GetZ() * si, P.GetZ() * co + P.GetY() * si);
	}
}
