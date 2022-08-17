package com.learn2code.shop.service.volumeCalculator.visualization;

import com.learn2code.shop.service.volumeCalculator.interfaces.RectangularBox;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawRectangleElements {

    private final List<RectangularBox> rectangularBoxes = new ArrayList<>();
    private final Map<RectangularBox, String> styles = new HashMap<>();

    public void pridaj(final RectangularBox rectangularBox) {
        this.rectangularBoxes.add(rectangularBox);
    }

    public void setStyl(final RectangularBox rectangularBox, final String style) {
        this.styles.put(rectangularBox, style);
    }

    public void zapisHtmlSubor(final File file) throws IOException {
        FileUtils.write(file, this.vytvorHtml(), StandardCharsets.UTF_8);
    }

    public String vytvorHtml() {

        var html = new StringBuilder();

        html
                .append("<!DOCTYPE html>\n")
                .append("<html>\n")
                .append("<head>\n")
                .append("<meta charset=\"utf-8\" />\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<script src=\"https://threejs.org/build/three.js\"></script>\n")
                .append("<script src=\"https://threejs.org/examples/js/controls/OrbitControls.js\"></script>\n")
                .append("<script>\n");

        html

                .append("const scene = new THREE.Scene();\n")
                .append("const camera = new THREE.PerspectiveCamera( 45, window.innerWidth / window.innerHeight, -500000, 0);\n")
                .append("camera.position.z = 55000;\n")
                .append("camera.position.y = 1;\n")
                .append("camera.position.z = 1;\n")
                .append("camera.up.set( 0, 0, 1 );\n")
                .append("const renderer = new THREE.WebGLRenderer();\n")
                .append("renderer.setSize( window.innerWidth, window.innerHeight );\n")
                .append("renderer.setClearColor( 0xeeeeee );\n")
                .append("document.body.appendChild( renderer.domElement );\n");

        html
                .append("let material;\n")
                .append("let geometry;\n")
                .append("let cube;\n");

        for (var box : this.rectangularBoxes) {

            html.append("geometry = new THREE.BoxGeometry(")
                    .append(box.sirkaS()).append(", ")
                    .append(box.vyskaS()).append(",")
                    .append(box.dlzkaS())
                    .append(");\n");

            var style = this.styles.get(box);
            if (style == null)
                style = "color: 0x000000, wireframe: true";

            html.append("material = new THREE.MeshBasicMaterial({").append(style).append("});\n");

            html.append("cube = new THREE.Mesh(geometry, material);\n")
                    .append("cube.position.x = ").append(box.sirka()).append(";\n")
                    .append("cube.position.y = ").append(box.vyska()).append(";\n")
                    .append("cube.position.z = ").append(box.dlzka()).append(";\n");

            html.append("scene.add(cube)\n");
        }

        html
                .append("camera.position.x = 500;\n")
                .append("camera.position.y = 500;\n")
                .append("camera.position.z = 500;\n");

        html
                .append("function animate() {\n")
                .append("renderer.render( scene, camera );\n")
                .append("}\n");

        html
                .append("var controls = new THREE.OrbitControls( camera, renderer.domElement );\n")
                .append("controls.addEventListener( 'change', animate );\n")
                .append("animate();\n");

        html
                .append("</script>\n")
                .append("</body>\n")
                .append("</html>\n");


        return html.toString();
    }

}
