package render.shapes;

import java.awt.*;

public class Tetrahedron {
    private MyPolygon[] polygons;
    private Color color;

    public Tetrahedron(Color color, MyPolygon... polygons){
        this.color = color;
        this.polygons = polygons;
        this.setPolygonColor();
    }
    public Tetrahedron(MyPolygon... polygons){
        this.color = Color.WHITE;
        this.polygons = polygons;
    }

    public void render(Graphics g){
        for(MyPolygon polygon : this.polygons){
            polygon.render(g);
        }
    }
    
    public void rotate(boolean CW, double xDeg, double yDeg, double zDeg){
        for(MyPolygon p : this.polygons){
            p.rotate(CW, xDeg, yDeg, zDeg);
        }
        this.sortPolygons();
    }
    
    private void sortPolygons(){
        MyPolygon.sortPolygons(this.polygons);
    }

    private void setPolygonColor(){
        for(MyPolygon polygon: this.polygons){
            polygon.setColor(this.color);
        }
    }
}
