package pkg3dengine;

public class jMath {

	public static final double PI = 3.141592653589793238462643383279502884197;
	public static final double PIo2 = 1.570796326794896619231321691639751442098;
	public static final double PIt2 = 6.283185307179586476925286766559005768394;
	public static double[] SineLookup;
	public static double[] CosineLookup;

	static {
		GenerateSineLookupTable(1000000);
		GenerateCosineLookupTable(1000000);
	}

	public static void GenerateSineLookupTable(int Size) {
		SineLookup = new double[Size];
		for (int i = 0; i < Size; i++) {
			SineLookup[i] = Math.sin(PI * (((double) i / ((double) Size / 2d)) - 1d));
		}
	}

	public static double FastSin(double Angle) {
		return SineLookup[(int) (((Angle / PI) + 1d) * (double) SineLookup.length / 2 - 1)];
	}

	public static void GenerateCosineLookupTable(int Size) {
		CosineLookup = new double[Size];
		for (int i = 0; i < Size; i++) {
			CosineLookup[i] = Math.cos(PI * (((double) i / ((double) Size / 2d)) - 1d));
		}
	}

	public static double FastCos(double Angle) {
		return CosineLookup[(int) (((Angle / PI) + 1d) * (double) CosineLookup.length / 2 - 1)];
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
