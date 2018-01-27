package engine;

import javafx.geometry.Point3D;

public class Utils {

	public static Point3D getAxis(String face) {
		Point3D p = new Point3D(0, 0, 0);
		switch (face.substring(0, 1)) {
		case "L":
			p = new Point3D(1, 0, 0);
			break;
		case "R":
			p = new Point3D(1, 0, 0);
			break;
		case "U":
			p = new Point3D(0, 1, 0);
			break;
		case "D":
			p = new Point3D(0, 1, 0);
			break;
		case "F":
			p = new Point3D(0, 0, 1);
			break;
		case "B":
			p = new Point3D(0, 0, 1);
			break;
		}
		return p;
	}

	public static int getCenter(String face) {
		int c = 0;
		switch (face.substring(0, 1)) {
		case "L":
			c = 16;
			break;
		case "R":
			c = 10;
			break;
		case "U":
			c = 14;
			break;
		case "D":
			c = 12;
			break;
		case "F":
			c = 4;
			break;
		case "B":
			c = 22;
			break;
		}
		return c;
	}

}
