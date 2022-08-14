package com.learn2code.shop.service.areaCalculator;

import com.learn2code.shop.domain.Statue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;


public class Drawing extends JPanel {

    Random rand = new Random();

    List<Rectangle> statuesToDraw;

    List<Statue> statuesToInsert;

    public Drawing(List<Rectangle> statuesToDraw, List<Statue> statuesToInsert) {
        this.statuesToDraw = statuesToDraw;
        this.statuesToInsert = statuesToInsert;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        this.setBackground(Color.WHITE);

        java.awt.Rectangle rectangle = new java.awt.Rectangle(0, 0, 0, 0);

        Random rand = new Random();

        int i = 0;
        for (Rectangle statueToDraw : statuesToDraw) {
                    int x;
                    int y;
                    int width;
                    int height;
                    String statueName = "";
                    for (Statue statue : statuesToInsert) {
                        if ((statue.getWidth() == statueToDraw.width) && (statue.getLength() == statueToDraw.height)) {
                            statueName = statue.getName();
                        }
                    }

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
                    graphics.setColor(Color.BLACK);
                    graphics.drawString(statuesToInsert.get(i).getName(), x, y + 20);
                    graphics.drawString(statueToDraw.width + "x" + statueToDraw.height, x, y + 45);
                    i++;
                }
    }

}
