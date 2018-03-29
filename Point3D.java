package pkg3dengine;

import java.util.ArrayList;

public class Point3D{	//This is a single 3D point with the edges it follows to other points.
	Vector3 Position;
	ArrayList<Point3D> Linked;
	
	public Point3D(Vector3 Point, ArrayList<Point3D> Links){
		this.Position = Point;
		this.Linked = Links;
	}
}