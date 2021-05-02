package render.point;

import render.Display;

import java.awt.*;

public class PointConverter {
    public static Point convertPoint(MyPoint point3D){
        int xScreen = (int) (Display.WIDTH / 2 + point3D.y);
        int yScreen = (int) (Display.HEIGHT / 2 - point3D.z);

        Point pointScreen = new Point(xScreen, yScreen);
        return pointScreen;
    }

    private static double[] scale(double x3D, double y3D){

    }
}
