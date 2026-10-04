package pkg3dengine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class GraphicsFunctions {
	
	private static class Function {
		double DomainStart, DomainEnd;
		double Slope;
		double Intercept;
		boolean Undefined;
		boolean Reversed;

		public double ValueAtPerc(double Percent) {
			if (Percent < 0 || Percent > 1) {
				throw new IllegalArgumentException();
			}
			if (Undefined) {
				return Intercept + (Slope - Intercept) * Percent;
			} else {
				return Intercept + (DomainStart + ((DomainEnd - DomainStart) * Percent)) * Slope;
			}
		}
		public double XAtPerc(double Percent) {
			return (DomainStart + ((DomainEnd - DomainStart) * Percent));
		}
		double ValueAtX(double x) {
			if (Undefined) {
				return Slope;
			}
			return Intercept + x * Slope;
		}
	}

	private static Function GetFunction(Vector2 A, Vector2 B) {
		Function Funct = new Function();
		if (Math.abs(A.GetX() - B.GetX()) > 1) {
			Funct.Slope = (A.GetY() - B.GetY()) / (A.GetX() - B.GetX());	//Gets the slope
			Funct.Intercept = -Funct.Slope * A.GetX() + A.GetY();
			Funct.DomainStart = Math.min(A.GetX(), B.GetX());
			Funct.DomainEnd = Math.max(A.GetX(), B.GetX());
			if (Funct.DomainStart == B.GetX()) {
				Funct.Reversed = true;
			}
		} else {
			Funct.Slope = B.GetY();
			Funct.Intercept = A.GetY();
			Funct.DomainStart = A.GetX();
			Funct.DomainEnd = A.GetX();
			Funct.Undefined = true;
		}
		return Funct;
	}

	/**
	 * This method takes 4 points of a 4 sided polygon starting from the "top left" and going clockwise and maps texture data to it. The order is important to make sure the texture is mapped in the correct way and is not rotated
	 *
	 * @param P	Four Points containing the visual location of the shape
	 * @param Texture The image data
	 * @param g
	 */
	public static void DrawTexture(ArrayList<Vector2> P, Color[][] Texture, Graphics g) throws IllegalArgumentException {
		if (P.size() != 4 || Texture == null) {
			throw new IllegalArgumentException();
		}
		Function Top = GetFunction(P.get(0), P.get(1));
		Function Right = GetFunction(P.get(1), P.get(2));
		Function Bot = GetFunction(P.get(3), P.get(2));
		Function Left = GetFunction(P.get(0), P.get(3));
		int TextureSizeX = Texture[0].length;
		int TextureSizeY = Texture.length;
		Function[] FSetX = new Function[TextureSizeX];
		Function[] FSetY = new Function[TextureSizeY];
		for (double i = 0; i < FSetX.length; i++) {
			double CorrectionTop = i / FSetY.length;
			double CorrectionBot = i / FSetY.length;
			if (Top.Reversed) {
				CorrectionTop = 1 - CorrectionTop;
			}
			if (Bot.Reversed) {
				CorrectionBot = 1 - CorrectionBot;
			}
			FSetX[(int) i] = GetFunction(new Vector2(Top.XAtPerc(CorrectionTop), Top.ValueAtPerc(CorrectionTop)),
					new Vector2(Bot.XAtPerc(CorrectionBot), Bot.ValueAtPerc(CorrectionBot)));
		}
		for (double i = 0; i < FSetY.length; i++) {
			double CorrectionRight = i / FSetY.length;
			double CorrectionLeft = i / FSetY.length;
			if (Right.Reversed) {
				CorrectionRight = 1 - CorrectionRight;
			}
			if (Left.Reversed) {
				CorrectionLeft = 1 - CorrectionLeft;
			}

			FSetY[(int) i] = GetFunction(new Vector2(Right.XAtPerc(CorrectionRight), Right.ValueAtPerc(CorrectionRight)), 
					new Vector2(Left.XAtPerc(CorrectionLeft), Left.ValueAtPerc(CorrectionLeft)));
		}
		double MinX = Math.min(Top.DomainStart, Math.min(Bot.DomainStart, Math.min(Left.DomainStart, Right.DomainStart)));
		double MaxX = Math.max(Top.DomainEnd, Math.max(Bot.DomainEnd, Math.max(Left.DomainEnd, Right.DomainEnd)));
		double MinY = Math.min(Top.ValueAtPerc(0), Math.min(Bot.ValueAtPerc(0), Math.min(Top.ValueAtPerc(1), Bot.ValueAtPerc(1))));
		double MaxY = Math.max(Top.ValueAtPerc(0), Math.max(Bot.ValueAtPerc(0), Math.max(Top.ValueAtPerc(1), Bot.ValueAtPerc(1))));
		Function[] Set = {Top, Right, Bot, Left};
		for (double i = Math.max(0, MinX); i < Math.min(800, MaxX); i++) {
			for (double j = Math.max(0, MinY); j < Math.min(800, MaxY); j++) {
				if (Inside(Set, new Vector2(i, j))) {
					int XLoc = 0;
					int YLoc = 0;
					for (int k = 1; k < FSetY.length; k++) {
						if (FSetY[k].Undefined) {
							if (FSetY[k].DomainEnd < i) {
								XLoc = k;
							}
						} else if (!FSetY[k].Reversed && FSetY[k].ValueAtX(i) > j) {
							YLoc = k;
						} else if (FSetY[k].Reversed && FSetY[k].ValueAtX(i) < j) {
							YLoc = k;
						}
					}
					for (int k = 1; k < FSetX.length; k++) {
						if (FSetX[k].Undefined) {
							if (FSetX[k].DomainEnd < i) {
								XLoc = k;
							}
						} else if (!FSetX[k].Reversed && FSetX[k].ValueAtX(i) > j) {
							XLoc = k;
						} else if (FSetX[k].Reversed && FSetX[k].ValueAtX(i) < j) {
							XLoc = k;
						}
					}
					g.setColor(Texture[YLoc][XLoc]);
					g.fillRect((int) i, (int) j, 1, 1);
				}
			}
		}
	}

	private static boolean Inside(Function[] FSet, Vector2 Pos) {
		boolean Inside = false;
		for (int i = 0; i < FSet.length; i++) {
			if (FSet[i].DomainStart < Pos.GetX() && FSet[i].DomainEnd > Pos.GetX()) {
				if (!FSet[i].Undefined) {
					if (FSet[i].ValueAtX(Pos.GetX()) >= Pos.GetY()) {
						Inside = !Inside;
					}
				}
			}
		}
		return Inside;
	}
}
