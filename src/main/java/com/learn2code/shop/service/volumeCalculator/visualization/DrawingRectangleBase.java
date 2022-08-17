package com.learn2code.shop.service.volumeCalculator.visualization;

import com.learn2code.shop.service.volumeCalculator.interfaces.Placement;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public abstract class DrawingRectangleBase {

    protected final DrawRectangleElements drawing = new DrawRectangleElements();

    public void ukazZvysne(final Placement placement) {
        int num = 0;
        for (var zvysok : placement.zvysne()) {

            num++;
            this.drawing.pridaj(zvysok);

            if (num < 3)
                this.drawing.setStyl(zvysok, "color: 0xffff00, wireframe: false, opacity: 0.25, transparent: true");
            else if (num < 5)
                this.drawing.setStyl(zvysok, "color: 0x00ff00, wireframe: false, opacity: 0.25, transparent: true");
            else if (num < 7)
                this.drawing.setStyl(zvysok, "color: 0x0000ff, wireframe: false, opacity: 0.25, transparent: true");
            else if (num == 9)
                num = 0;
        }
    }

    public void zobrazZvysneDraw(final Placement placement) {

        this.ukazZvysne(placement);

        for (var dieta : placement.dieta()) {
            zobrazZvysneDraw(dieta);
        }

    }

    public void zapisHtmlSubor() throws IOException {
        var file = new File(FileUtils.getTempDirectory(), this.getClass().getSimpleName() + ".html");
        System.out.println("Writing to " + file);
        this.drawing.zapisHtmlSubor(file);
    }
}
