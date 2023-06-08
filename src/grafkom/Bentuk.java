/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafkom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
/**
 *
 * @author User
 */
public class Bentuk extends JComponent{
    
    Image image;
    Graphics2D graphics2D;
    Gambar drawing;
    Color lineColor = Color.BLACK;
    Color fillColor = Color.WHITE;

    Shape shape;

    int selected = 0;
    String objectType = "";
    String lineType = "";
    String strokeType = "";
    double midX, midY;

    Stroke stroke = new BasicStroke(1f);
    float[] pattern;
    int nowX, nowY, oldX, oldY;

    public Bentuk() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                oldX = e.getX();
                oldY = e.getY();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                nowX = e.getX();
                nowY = e.getY();

                System.out.println("old X : " + oldX);
                System.out.println("old Y : " + oldY);
                System.out.println("current X : " + nowX);
                System.out.println("current Y : " + nowY);
                chosingObject();

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }

            public void mouseDragged(MouseEvent e) {
                nowX = e.getX();
                nowY = e.getY();

                switch (objectType) {
                    case "Drawline":
                        doDraw();
                        break;
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            clearAll();
        }

        g.drawImage(image, 0, 0, null);

    }

    public void clearAll() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.black);

        repaint();
    }


    public void chosingObject(){
        strokeCustom();
        switch (objectType) {
            case "Rectangle":
                doRectangle();
                break;
            case "Triangle":
                doTriangle();
                break;

            case "Circle":
                doCircle();
                break;

            case "Oval":
                doOval();
                break;
            case "Hexagon":

                break;
            case "Square":
                doSquare();
                break;

            case "Line":
                doLine();
                break;
        }
    }

    public void strokeCustom(){
        if (!strokeType.equals("")) {
            if(!lineType.equals("Line")){
                switch (strokeType) {
                    case "1f":
                        switch (lineType) {
                            case "Dash":
                                pattern = new float[]{4f, 3f};
                                break;
                            case "Dot":
                                pattern = new float[]{1f, 3f}; // kiri ukuran, kanan jarak
                                break;
                            
                        }
                        stroke = new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1.0f, pattern,1.0f);
                        break;
                    case "3f":
                        switch (lineType) {
                            case "Dash":
                                pattern = new float[]{6f, 5f};
                                break;
                            case "Dot":
                                pattern = new float[]{1f, 5f}; // kiri ukuran, kanan jarak
                                break;
                           
                        }
                        stroke = new BasicStroke(3f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1.0f, pattern,3.0f);
                        break;
                    case "5f":
                        switch (lineType) {
                            case "Dash":
                                pattern = new float[]{10f, 9f};
                                break;
                            case "Dot":
                                pattern = new float[]{1f, 9f}; // kiri ukuran, kanan jarak
                                break;
                           
                        }
                        stroke = new BasicStroke(5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1.0f, pattern,5.0f);
                        break;
                    case "7f":
                        switch (lineType) {
                            case "Dash":
                                pattern = new float[]{12f, 11f};
                                break;
                            case "Dot":
                                pattern = new float[]{1f, 11f}; // kiri ukuran, kanan jarak
                                break;
                            
                        }
                        stroke = new BasicStroke(7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1.0f, pattern,7.0f);
                        break;
                }
            }
            else{
                switch (strokeType) {
                    case "1f":
                        stroke = new BasicStroke(1f);
                        break;
                    case "3f":
                        stroke = new BasicStroke(3f);
                        break;
                    case "5f":
                        stroke = new BasicStroke(5f);
                        break;
                    case "7f":
                        stroke = new BasicStroke(7f);
                        break;
                }
            }
        }
    }

    public void doRectangle(){
        int width = nowX - oldX < 0 ? oldX - nowX : nowX - oldX;
        int height = nowY - oldY < 0 ? oldY - nowY : nowY - oldY;
        int startX = nowX - oldX < 0 ? nowX : oldX;
        int startY = nowY - oldY < 0 ? nowY : oldY;

        midX = startX + width / 2;
        midY = startY + height / 2;

        graphics2D.setStroke(stroke);
        if (!lineColor.equals(Color.black)) {
            graphics2D.setPaint(lineColor);
            graphics2D.drawRect(startX, startY, width, height);
        } else if (!fillColor.equals(Color.white)) {
            graphics2D.setPaint(fillColor);
            graphics2D.fillRect(startX, startY, width, height);
        } else {
            graphics2D.drawRect(startX, startY, width, height);
        }
        repaint();
    }

    public void doTriangle(){
        int sisiTigaX, sisiTigaY; //= (oldX + oldY) / 2;

        if(oldX > nowX){
            sisiTigaX = nowX+Math.abs((oldX-nowX)*2);
        } else {
            sisiTigaX = nowX-Math.abs((oldX-nowX)*2);
        }
        if(oldY > nowY){
            sisiTigaY = oldY;
        }
        else {
            sisiTigaY = nowY;
        }
        midX = sisiTigaX;
        midY = sisiTigaY;
        int startY = nowY - oldY < 0 ? nowY : oldY;
        int height = nowY - oldY < 0 ? oldY - nowY : nowY - oldY;
//        midX = midPointX;
//        midY = startY + height / 2;
        int xPoints[] = new int[]{oldX, nowX, sisiTigaX};
        int yPoints[] = new int[]{oldY, nowY, sisiTigaY};
        graphics2D.setStroke(stroke);
        if (!lineColor.equals(Color.black)) {
            graphics2D.setPaint(lineColor);
            graphics2D.drawPolygon(xPoints, yPoints, 3);
        } else if (!fillColor.equals(Color.white)) {
            graphics2D.setPaint(fillColor);
            graphics2D.fillPolygon(xPoints, yPoints, 3);
        } else {
            graphics2D.drawPolygon(xPoints, yPoints, 3);
        }

        repaint();
    }

    public void doCircle(){
        int minX = Math.min(oldX, nowX);
        int minY = Math.min(oldY, nowY);
        int maxX = Math.max(oldX, nowX);
        int maxY = Math.max(oldY, nowY);

//                        box = new Rectangle(minX, minY, maxX - minX, maxY - minY);

        int size = Math.min(maxX - minX, maxY - minY);
        if (minX < nowX) {
            minX = nowX - size;
        }
        if (minY < nowY) {
            minY = nowY - size;
        }
        midX = minX + size / 2;
        midY = minY + size / 2;

        shape = new Ellipse2D.Double(minX, minY, size, size);
        graphics2D.setStroke(stroke);
        if (!lineColor.equals(Color.black)) {
            graphics2D.setPaint(lineColor);
            graphics2D.draw(shape);
        } else if (!fillColor.equals(Color.white)) {
            graphics2D.setPaint(fillColor);
            graphics2D.fill(shape);
        } else {
            graphics2D.draw(shape);
        }
        repaint();
    }

    public void doOval(){
        int xOval = oldX < nowX ? oldX : nowX;
        int yOval = oldY < nowY ? oldY : nowY;
        int width = nowX - oldX < 0 ? oldX - nowX : nowX - oldX;
        int height = nowY - oldY < 0 ? oldY - nowY : nowY - oldY;

        midX = xOval + width / 2;
        midY = yOval + height / 2;

        graphics2D.setStroke(stroke);
        if (!lineColor.equals(Color.black)) {
            graphics2D.setPaint(lineColor);
            graphics2D.drawOval(xOval, yOval, width, height);
        } else if (!fillColor.equals(Color.white)) {
            graphics2D.setPaint(fillColor);
            graphics2D.fillOval(xOval, yOval, width, height);
        } else {
            graphics2D.drawOval(xOval, yOval, width, height);
        }
        repaint();
    }

    public void doDraw(){
        graphics2D.setPaint(lineColor);
        graphics2D.drawLine(oldX, oldY, nowX, nowY);
        repaint();

        oldX = nowX;
        oldY = nowY;
    }

    public void doLine(){
        int width = nowX - oldX < 0 ? oldX - nowX : nowX - oldX;
        int height = nowY - oldY < 0 ? oldY - nowY : nowY - oldY;
        int startX = nowX - oldX < 0 ? nowX : oldX;
        int startY = nowY - oldY < 0 ? nowY : oldY;

        midX = startX + width / 2;
        midY = startY + height / 2;
        graphics2D.setStroke(stroke);
        graphics2D.setPaint(lineColor);
        graphics2D.drawLine(oldX, oldY, nowX, nowY);
        repaint();
    }

    public void doSquare(){
        int minX = Math.min(oldX, nowX);
        int minY = Math.min(oldY, nowY);
        int maxX = Math.max(oldX, nowX);
        int maxY = Math.max(oldY, nowY);

        int size = Math.min(maxX - minX, maxY - minY);
        if (minX < nowX) {
            minX = nowX - size;
        }
        if (minY < nowY) {
            minY = nowY - size;
        }

        midX = minX + size / 2;
        midY = minY + size / 2;

        graphics2D.setStroke(stroke);
        if (!lineColor.equals(Color.black)) {
            graphics2D.setPaint(lineColor);
            graphics2D.drawRect(minX, minY, size, size);
        } else if (!fillColor.equals(Color.white)) {
            graphics2D.setPaint(fillColor);
            graphics2D.fillRect(minX, minY, size, size);
        } else {
            graphics2D.drawRect(minX, minY, size, size);
        }

        repaint();
    }

   

    public void redrawingObjek(){
        clearAll();
        chosingObject();
    }
    
}
