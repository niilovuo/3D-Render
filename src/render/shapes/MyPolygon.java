package render.shapes;
import render.point.MyPoint;
import render.point.PointConverter;

import java.awt.*;

public class MyPolygon {
    private MyPoint[] points;
    private Color color;

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

    public void setColor(Color color){
        this.color = color;
    }

    public void render(Graphics g){
        Polygon polygon = new Polygon();
        for(int i = 0; i < points.length; i++){
            Point p = PointConverter.convertPoint(points[i]);
            polygon.addPoint(p.x, p.y);
        }
        g.setColor(this.color);
        g.fillPolygon(polygon);
    }
}
