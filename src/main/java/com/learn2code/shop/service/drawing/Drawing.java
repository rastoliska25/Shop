package com.learn2code.shop.service.drawing;

import com.learn2code.shop.domain.Statue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Drawing extends JPanel {
    Random rand = new Random();
    List<Statue> statues;

    public Drawing(List<Statue> statues){
        this.statues = statues;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        this.setBackground(Color.WHITE);

        Rectangle rectangle = new Rectangle(0, 0, 0, 0);

        Random rand = new Random();

        graphics.setColor(Color.lightGray);
        graphics.fillRect(0, 0, 200, 200);

        graphics.setColor(Color.RED);
        graphics.drawString("Truck1", 25, 65);



    }

    void filling(Rectangle rectangle, Graphics graphics, int width, int height) {
        System.out.println(rectangle.x + " " + rectangle.y + " " + rectangle.width + " " + rectangle.height);
        graphics.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
        graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
