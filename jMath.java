package pkg3dengine;

public class jMath {

	public static final double PI	= 3.141592653589793238462643383279502884197;
	public static final double PIo2 = 1.570796326794896619231321691639751442098;
	public static final double PIt2 = 6.283185307179586476925286766559005768394;

//	public static double getRadiansPointsTo(Vector2 From, Vector2 To) {
//		double DifX = To.GetX() - From.GetX();
//		double DifY = To.GetY() - From.GetY();
//		double Angle;
//		if (DifY != 0) {
//			Angle = Math.atan2(DifY,DifX);
//		} else {
//			Angle = 0;	//TODO Either PI or 0 based on in front of or behind
//			if (From.GetX() > To.GetX()){
//				Angle = PI;
//			}
//		}
////		if (Angle < 0) {
////			Angle += PIt2;
////		}
//		//System.out.println("The angle from " + From.GetX() + " " + From.GetY() + " to " + To.GetX() + " " + To.GetY() + " is " + Angle);
//		return Angle;
//	}
	public static double getRadiansPointsTo(Vector2 From, Vector2 To) {
		double DifX = To.GetX() - From.GetX();
		double DifY = To.GetY() - From.GetY();
		double Angle;
		if (DifY != 0) {
			Angle = Math.atan2(DifY, DifX);
		} else {
			Angle = 0;
			if (DifX < 0){
				Angle = PI;
			}
		}
		if (Angle > PI) {
			Angle -= PIt2;
		}
		return Angle;
	}
	public static double getRadiansPointsTo(Vector2 To) {
		double DifX = To.GetX();
		double DifY = To.GetY();
		double Angle;
		if (DifY != 0) {
			Angle = Math.atan2(DifY, DifX);
		} else {
			Angle = 0;
			if (DifX < 0){
				Angle = PI;
			}
		}
		if (Angle > PI) {
			Angle -= PIt2;
		}
		return Angle;
	}
}
