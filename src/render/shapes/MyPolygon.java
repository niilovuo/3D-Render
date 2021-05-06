package render.shapes;
import render.point.MyPoint;
import render.point.PointConverter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyPolygon {
    private MyPoint[] points;
    private Color color;
    
    /**
     * Defines a custom polygon.
     * @param color     The color of the polygon.
     * @param points    The points that comprise the vertices of the polygon.
     */
    public MyPolygon(Color color, MyPoint... points){
        this.points = new MyPoint[points.length];
        this.color = color;
        for(int i = 0; i < points.length; i++){
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
        }
    } public MyPolygon(MyPoint... points){
        this.points = new MyPoint[points.length];
        this.color = Color.WHITE;
        for(int i = 0; i < points.length; i++){
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
        }
    }
    
    /**
     * Changes the color of a given custom polygon.
     * @param color The color to change the polygon to.
     */
    public void setColor(Color color){
        this.color = color;
    }
    
    /**
     * Renders the polygon on the screen.
     * @param g The graphics object that the polygon gets rendered to.
     */
    public void render(Graphics g){
        Polygon polygon = new Polygon();
        for(int i = 0; i < points.length; i++){
            Point p = PointConverter.convertPoint(points[i]);
            polygon.addPoint(p.x, p.y);
        }
        g.setColor(this.color);
        g.fillPolygon(polygon);
    }
    
    /**
     * Rotates a polygon on the x, y and z axes.
     * @param CW        Clockwise/Counter-clockwise.
     * @param xDeg      The amount of degrees to rotate on the x-axis.
     * @param yDeg      The amount of degrees to rotate on the y-axis.
     * @param zDeg      The amount of degrees to rotate on the z-axis.
     */
    public void rotate(boolean CW, double xDeg, double yDeg, double zDeg){
        for(MyPoint p: points){
            PointConverter.rotateAxisX(p, CW, xDeg);
            PointConverter.rotateAxisY(p, CW, yDeg);
            PointConverter.rotateAxisZ(p, CW, zDeg);
        }
    }
    
    /**
     * Gets the average x-coordinate of all the vertex coordinates of a polygon.
     * @return  The average x-coordinate of the polygon.
     */
    public double getAverageX() {
        double sum = 0;
        for(MyPoint p : this.points){
            sum += p.x;
        }
        return sum/this.points.length;
    }
    
    /**
     * Sorts the polygons in an order such that polygons meant to be behind other objects are not displayed in front.
     * Note: Edge cases that do not work apply due to the parameter used to sort.
     * @param polygons      The array of polygons to sort.
     * @return              The sorted array of polygons according to the x-coordinates of their vertices.
     */
    public static MyPolygon[] sortPolygons(MyPolygon[] polygons){
        List polygonsList = new ArrayList<MyPolygon>();
        
        for(MyPolygon p : polygons){
            polygonsList.add(p);
        }
        Collections.sort(polygonsList, new Comparator<MyPolygon>(){
            @Override
            public int compare(MyPolygon p1, MyPolygon p2){
                return p2.getAverageX() - p1.getAverageX() < 0 ? 1 : -1;
            }
        });
        for(int i = 0; i < polygons.length; i++){
            polygons[i] = (MyPolygon) polygonsList.get(i);
        }
        
        return polygons;
    }
}
