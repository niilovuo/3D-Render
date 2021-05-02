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

    public void render(Graphics g){
        for(MyPolygon polygon : this.polygons){
            polygon.render(g);
        }
    }

    private void sortPoly(){

    }

    private void setPolygonColor(){
        for(MyPolygon polygon: this.polygons){
            polygon.setColor(this.color);
        }
    }
}
