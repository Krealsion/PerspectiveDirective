package pkg3dengine;

public class Line{
	private Vector2 a, b;
	public Line(Vector2 Point1, Vector2 Point2){
		a = Point1;
		b = Point2;
	}
	public Vector2 GetPoint1(){
		return a;
	}
	public Vector2 GetPoint2(){
		return b;
	}
}