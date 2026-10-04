package pkg3dengine;

public class jMath {

	public static final double PI   = 3.141592653589793238462643383279502884197;
	public static final double PIo2 = 1.570796326794896619231321691639751442098;
	public static final double PIo4 = 0.785398163397448309615660845819875721049;
	public static final double PIt2 = 6.283185307179586476925286766559005768394;
	public static double[] SineLookup;
	public static double[] CosineLookup;

	static {
		GenerateTrigLookupTables(10001);
	}

	public static void GenerateTrigLookupTables(int Size) {
		if (Size % 2 == 0){		//SIZE SHOULD ALWAYS BE ODD TO MAINTAIN SYMETRY
			Size++;
		}
		SineLookup = new double[Size];		//Initialize Arrays
		CosineLookup = new double[Size];	//Initialize Arrays
		for (int i = 0; i < Size; i++) {	//For each value
			SineLookup[i] = Math.sin(PI * (((double) i / ((double) Size / 2d)) - 1d));		//Get it's relative percentage through the array, divide by two, then
			CosineLookup[i] = Math.cos(PI * (((double) i / ((double) Size / 2d)) - 1d));
		}
	}

	public static double FastSin(double Angle) {
		if (Angle > PI) {
			Angle -= PIt2;
		}
		if (Angle < -PI) {
			Angle += PIt2;
		}
		return SineLookup[(int) ((Angle / PI) * (double) ((SineLookup.length - 1) / 2) + (SineLookup.length - 1) / 2)];
	}
	
	public static double FastCos(double Angle) {
		if (Angle > PI) {
			Angle -= PIt2;
		}
		if (Angle < -PI) {
			Angle += PIt2;
		}
		return CosineLookup[(int) ((Angle / PI) * (double) ((CosineLookup.length - 1) / 2) + (CosineLookup.length - 1) / 2)];
	}

	public static double getRadiansPointsTo(Vector2 From, Vector2 To) {
		double DifX = To.GetX() - From.GetX();
		double DifY = To.GetY() - From.GetY();
		double Angle;
		if (DifY != 0) {
			Angle = Math.atan2(DifY, DifX);
		} else {
			Angle = 0;
			if (DifX < 0) {
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
			if (DifX < 0) {
				Angle = PI;
			}
		}
		if (Angle > PI) {
			Angle -= PIt2;
		}
		return Angle;
	}
}
