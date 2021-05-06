package render;

import render.point.MyPoint;
import render.shapes.MyPolygon;
import render.shapes.Tetrahedron;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable{

    // Written with the help of MeanRollerCoding 3D Renderer tutorials at
    // https://www.youtube.com/playlist?list=PLgRPwj3No0VLXFoqYnL2aYhczXB2qwKvp

    private Thread thread;
    private JFrame frame;
    private static String title = "3D";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    private boolean running = false;

    private Tetrahedron tetrahedron;

    public Display(){
        this.frame = new JFrame();
        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);
    }

    public static void main(String[] args){
        Display display = new Display();
        display.frame.setTitle(title);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setIconImage(new ImageIcon("C:\\Users\\Niilo\\IdeaProjects\\3D_Render\\src\\logo3D.png")
                .getImage());
        display.frame.setResizable(false);
        display.frame.setVisible(true);
        display.start();
    }

    public synchronized void start() {
        this.running = true;
        thread = new Thread(this, "display");
        this.thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60; // 60Hz
        double delta = 0;
        int frames = 0;
        init();
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                delta--;
                render();
                frames++;
            }

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                this.frame.setTitle(title + "  |  " + frames + " fps");
                frames = 0;
            }
        }
        stop();
    }

    private void init() {
        int size = 100;
        MyPoint[] cube = cube(size);
        tetrahedron = new Tetrahedron(
                new MyPolygon(Color.decode("#8C00FF"), cube[0], cube[1], cube[2], cube[3]),
                new MyPolygon(Color.decode("#6D00D0"), cube[4], cube[5], cube[6], cube[7]),
                new MyPolygon(Color.decode("#6000BF"), cube[0], cube[1], cube[5], cube[4]),
                new MyPolygon(Color.decode("#5300A6"), cube[0], cube[4], cube[7], cube[3]),
                new MyPolygon(Color.decode("#420083"), cube[1], cube[5], cube[6], cube[2]),
                new MyPolygon(Color.decode("#390072"), cube[3], cube[2], cube[6], cube[7])
                );
    }

    // Create the polygons required for a cube of a specified size.
    private MyPoint[] cube(int size) {
        MyPoint[] cube = new MyPoint[8];
        MyPoint p1 = new MyPoint(size/2, -size/2, -size/2);
        MyPoint p2 = new MyPoint(size/2, size/2, -size/2);
        MyPoint p3 = new MyPoint(size/2, size/2, size/2);
        MyPoint p4 = new MyPoint(size/2, -size/2, size/2);
        MyPoint p5 = new MyPoint(-size/2, -size/2, -size/2);
        MyPoint p6 = new MyPoint(-size/2, size/2, -size/2);
        MyPoint p7 = new MyPoint(-size/2, size/2, size/2);
        MyPoint p8 = new MyPoint(-size/2, -size/2, size/2);
        cube[0] = p1; cube[1] = p2; cube[2] = p3; cube[3] = p4;cube[4] = p5; cube[5] = p6; cube[6] = p7; cube[7] = p8;
        return cube;
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.decode("#2B2B2B"));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        tetrahedron.render(g);

        g.dispose();
        bs.show();
    }

    private void update() {
        this.tetrahedron.rotate(true, 1, 1, 2);
    }
}