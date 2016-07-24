/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mim.graph.surfaces;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import com.mim.graph.util.Surface;

/**
 *
 * @author marcoisaac
 */
public class EllipticParaboloid implements Surface {

    private DoubleProperty aProperty;
    private DoubleProperty bProperty;

    private Group plot;

    public EllipticParaboloid(Group plot, DoubleProperty aProperty, DoubleProperty bProperty) {
        this.aProperty = aProperty;
        this.bProperty = bProperty;
        this.plot = plot;
    }

    @Override
    public void build() {
        int radiusDivisions = 20;/// ESTE ES
        int tubeDivisions = 12;//12
        int numVerts = (int) (tubeDivisions * radiusDivisions);
        int faceCount = numVerts * 2;
        float[] points = new float[numVerts * 3 * 4],
                texCoords = new float[numVerts * 2];
        int[] faces = new int[faceCount * 6];
        int pointIndex = 0, texIndex = 0, faceIndex = 0;
        float tubeFraction = 1.0f / tubeDivisions;
        float radiusFraction = (float) (1.0f / radiusDivisions);
        float x, y, z;
        int p0 = 0, p1 = 0, p2 = 0, p3 = 0, t0 = 0, t1 = 0, t2 = 0, t3 = 0;

        for (int i = 0; i < 10; i++) {

            for (int tubeIndex = -tubeDivisions; tubeIndex < tubeDivisions; tubeIndex++) {

                for (int radiusIndex = -radiusDivisions; radiusIndex < radiusDivisions; radiusIndex++) {

                    double v = radiusIndex * 0.0116 * radiusDivisions + 1.5683;
                    double u = 0.2617 * tubeIndex + 3.1403;

                    points[pointIndex + 0] = x = (float) (aProperty.getValue() * Math.sqrt(50 * .10 * i) * Math.cos(u) * Math.sin(v));
                    points[pointIndex + 1] = y = (float) (bProperty.getValue() * Math.sqrt(50 * .10 * i) * Math.sin(u) * Math.sin(v));
                    points[pointIndex + 2] = z = (float) (50 * .10 * i);

                    /*System.out.println("x: " + points[pointIndex + 0]);
                System.out.println("y: " + points[pointIndex + 1]);
                System.out.println("z: " + points[pointIndex + 2]);*/
                    pointIndex += 3;

                }

            }

            double amount = points.length / 3;
            pointIndex = 0;
            //System.out.println("cantidad puntos: " + amount);
            for (int j = 0; j < points.length; j += 3) {
                Sphere sp = new Sphere(0.7);
                sp.setMaterial(new PhongMaterial(Color.BLACK));
                sp.setTranslateX(points[j + 0]);
                sp.setTranslateY(points[j + 1]);
                sp.setTranslateZ(points[j + 2]);
                /*sp.setTranslateX(arrayX[x]);
            sp.setTranslateY(arrayYN[x]);
            sp.setTranslateZ(arrayZN[x]);*/
                plot.getChildren().add(sp);

            }
        }
    }
}
