package render.point;

import render.Display;

import java.awt.*;

public class PointConverter {
    private static double scale = 1;
    
    /**
     * Converts a point from 3D to screen (2D) coordinates.
     * @param point3D   The 3D point in the form of a MyPoint object.
     * @return          The coordinates of the point on the screen in the form of a Point object.
     */
    public static Point convertPoint(MyPoint point3D){
        double x3D = point3D.y * scale;
        double y3D = point3D.z * scale;
        double depth = point3D.x * scale;
        double[] newVal = scale(x3D, y3D, depth);

        int xScreen = (int) (Display.WIDTH / 2 + newVal[0]);
        int yScreen = (int) (Display.HEIGHT / 2 - newVal[1]);

        Point pointScreen = new Point(xScreen, yScreen);
        return pointScreen;
    }
    
    /**
     * Scales a point based on its distance from the camera.
     * @param x3D       The 3D x-coordinate of the point.
     * @param y3D       The 3D y-coordinate of the point.
     * @param depth     The depth of the point from the camera.
     * @return          An array of the new coordinates based on the input values.
     */
    private static double[] scale(double x3D, double y3D, double depth){
        double dist = Math.sqrt(Math.pow(x3D, 2) + Math.pow(y3D, 2));
        double theta = Math.atan2(y3D, x3D);
        double depth2 = 15 - depth;
        // As depth2 gets larger, the local scale gets smaller (camera is further away, objects appear smaller)
        double localScale = Math.abs(1400/(depth2+1400));
        dist *= localScale;
        double[] val = new double[2];
        val[0] = dist * Math.cos(theta);
        val[1] = dist * Math.sin(theta);

        return val;
    }
    
    /**
     * Rotates a point around the x-axis.
     * @param p             The point to be rotated.
     * @param CW            Clockwise/Counter-clockwise.
     * @param degree        The amount of degrees to rotate the point.
     */
    public static void rotateAxisX(MyPoint p, boolean CW, double degree){
        double radius = Math.sqrt(Math.pow(p.y, 2) + Math.pow(p.z, 2));
        double theta = Math.atan2(p.z, p.y);
        theta += 2*Math.PI/360*degree*(CW ? -1 : 1);
        p.y = radius*Math.cos(theta);
        p.z = radius*Math.sin(theta);
    }
    
    /**
     * Rotates a point around the y-axis.
     * @param p             The point to be rotated.
     * @param CW            Clockwise/Counter-clockwise.
     * @param degree        The amount of degrees to rotate the point.
     */
    public static void rotateAxisY(MyPoint p, boolean CW, double degree){
        double radius = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.z, 2));
        double theta = Math.atan2(p.x, p.z);
        theta += 2*Math.PI/360*degree*(CW ? -1 : 1);
        p.z = radius*Math.cos(theta);
        p.x = radius*Math.sin(theta);
    }
    
    /**
     * Rotates a point around the z-axis.
     * @param p             The point to be rotated.
     * @param CW            Clockwise/Counter-clockwise.
     * @param degree        The amount of degrees to rotate the point.
     */
    public static void rotateAxisZ(MyPoint p, boolean CW, double degree){
        double radius = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
        double theta = Math.atan2(p.y, p.x);
        theta += 2*Math.PI/360*degree*(CW ? -1 : 1);
        p.x = radius*Math.cos(theta);
        p.y = radius*Math.sin(theta);
    }
}
