package com.learn2code.shop.service.areaCalculator;

import com.learn2code.shop.domain.Statue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;


public class Drawing extends JPanel {

    Random rand = new Random();

    List<Rectangle> statuesToDraw;

    public Drawing(List<Rectangle> statuesToDraw) {
        this.statuesToDraw = statuesToDraw;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        this.setBackground(Color.WHITE);

        java.awt.Rectangle rectangle = new java.awt.Rectangle(0, 0, 0, 0);

        Random rand = new Random();

        statuesToDraw.forEach(
                (statueToDraw) -> {
                    int x;
                    int y;
                    int width;
                    int height;

                    if (statueToDraw.x == 0) {
                        x = 0;
                    } else x = statueToDraw.x / 5;

                    if (statueToDraw.y == 0) {
                        y = 0;
                    } else y = statueToDraw.y / 5;

                    if (statueToDraw.width == 0) {
                        width = 0;
                    } else width = statueToDraw.width / 5;

                    if (statueToDraw.height == 0) {
                        height = 0;
                    } else height = statueToDraw.height / 5;

                    graphics.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
                    graphics.fillRect(x, y, width, height);
                });

        graphics.setColor(Color.RED);
        graphics.drawString("Truck1", 25, 65);
    }

}
