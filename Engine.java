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
		double WidthAtX = c.GetTanHalfFOV() * OriginP.GetX();
		double Perc1 = .5 + (OriginP.GetY() / WidthAtX);
		double Perc2 = .5 - (OriginP.GetZ() / WidthAtX);
		return RollPoint(new Vector2(Perc1 * ScreenSize, Perc2 * ScreenSize), c.GetRoll());
	}

	public Vector3 RotatePointXY(Vector3 P, double Angle) {
		double Cur = jMath.getRadiansPointsTo(new Vector2(P.GetX(), P.GetY()));
		double Mag = Math.sqrt(P.GetX() * P.GetX() + P.GetY() * P.GetY());
		double New = Cur - Angle;
		New %= jMath.PIt2;
		return new Vector3(Math.cos(New) * Mag, Math.sin(New) * Mag, P.GetZ());
//		double si = Math.sin(Angle);
//		double co = Math.cos(Angle);
//		return new Vector3(P.GetX() * co - P.GetY() * si, P.GetY() * co - P.GetX() * si, P.GetZ());
	}

	public Vector3 RotatePointXZ(Vector3 P, double Angle) {
		double Cur = jMath.getRadiansPointsTo(new Vector2(P.GetX(), P.GetZ()));
		double Mag = Math.sqrt(P.GetX() * P.GetX() + P.GetZ() * P.GetZ());
		double New = Cur - Angle;
		New %= jMath.PIt2;
		return new Vector3(Math.cos(New) * Mag, P.GetY(), Math.sin(New) * Mag);
//		double si = Math.sin(Angle);
//		double co = Math.cos(Angle);
//		return new Vector3(P.GetX() * co - P.GetZ() * si, P.GetY(), P.GetZ() * co - P.GetX() * si);
	}
	public Vector2 RollPoint(Vector2 P, double Angle) {
//		double Cur = jMath.getRadiansPointsTo(new Vector2(P.GetX(), P.GetZ()));
//		double Mag = Math.sqrt(P.GetX() * P.GetX() + P.GetZ() * P.GetZ());
//		double New = Cur + Angle;
//		New %= jMath.PIt2;
//		return new Vector3(Math.cos(New) * Mag, P.GetY(), Math.sin(New) * Mag);
		double si = Math.sin(Angle);
		double co = Math.cos(Angle);
		return new Vector2(P.GetX() * co - P.GetY() * si, P.GetY() * co - P.GetX() * si);
	}

//	public Vector3 RotateXY(Vector3 Point) {
//		return new Vector3(((Point.GetX() >= 0) ? 1 : -1) * Math.sqrt((Point.GetX() * Point.GetX()) + (Point.GetY() * Point.GetY())), 0, Point.GetZ());
//	}
//
//	public Vector3 RotateXZ(Vector3 Point) {
//		return new Vector3(((Point.GetX() >= 0) ? 1 : -1) * Math.sqrt((Point.GetX() * Point.GetX()) + (Point.GetZ() * Point.GetZ())), Point.GetY(), 0);
//	}
//	public Vector2 ConvOld(Vector3 P) {
//		double HalfFOV = c.GetFOV() / 2;
//		Vector3 CamPos = c.GetPos();
//		Vector3 OriginP = new Vector3(P.GetX() + CamPos.GetX(), P.GetY() - CamPos.GetY(), P.GetZ() - CamPos.GetZ());
//		Vector3 Pxy = RotateXY(OriginP);
//		Vector3 Pxz = RotateXZ(OriginP);
//		double AngleTo1 = jMath.getRadiansPointsTo(new Vector2(Pxz.GetX(), Pxz.GetY()));
//		double AngleTo2 = jMath.getRadiansPointsTo(new Vector2(Pxy.GetX(), Pxy.GetZ()));
//		double Perc1 = .5 - ((c.GetPitch() + AngleTo1) / HalfFOV / 2);
//		double Perc2 = .5 - ((c.GetYaw() + AngleTo2) / HalfFOV / 2);
//		if (v) {
//			System.out.printf("Positions(x,y) - CamPos(%7.4f, %7.4f) & PointPos(%7.4f, %7.4f) Angle1: %7.4f Percentage1: %7.4f%n", CamPos.GetX(), CamPos.GetY(), P.GetX(), P.GetY(), AngleTo1, Perc1);
//			System.out.printf("Positions(x,z) - CamPos(%7.4f, %7.4f) & PointPos(%7.4f, %7.4f) Angle1: %7.4f Percentage1: %7.4f%n", CamPos.GetX(), CamPos.GetZ(), P.GetX(), P.GetZ(), AngleTo2, Perc2);
//		}
//		return new Vector2(Perc1 * ScreenSize, Perc2 * ScreenSize);
//	}
}
