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
public class Ellipsoid implements Surface {

    private DoubleProperty aProperty;
    private DoubleProperty bProperty;
    private DoubleProperty cProperty;
    private Group plot;

    public Ellipsoid(Group plot, DoubleProperty aProperty, DoubleProperty bProperty, DoubleProperty cProperty) {
        this.aProperty = aProperty;
        this.bProperty = bProperty;
        this.cProperty = cProperty;
        this.plot = plot;
    }

    @Override
    public void build() {
        int radiusDivisions = 80;/// ESTE ES
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
        for (int tubeIndex = -tubeDivisions; tubeIndex < tubeDivisions; tubeIndex++) {

            float radian = 1 * tubeIndex * 2.0f * 3.141592653589793f;

            for (int radiusIndex = -radiusDivisions; radiusIndex < radiusDivisions; radiusIndex++) {

                /*x = acosusinv
                y = bsinusinv
                z = ccosv
                u in [0,2pi) and v in [0,pi].
                u = 0.2617x + 3.1403
                
                 */
                double v = radiusIndex * 0.0116 * radiusDivisions + 1.5683;
                double u = 0.2617 * tubeIndex + 3.1403;

                points[pointIndex + 0] = x = (float) (aProperty.getValue() * Math.cos(u) * Math.sin(v));
                points[pointIndex + 1] = y = (float) (bProperty.getValue() * Math.sin(u) * Math.sin(v));
                points[pointIndex + 2] = z = (float) (cProperty.getValue() * Math.cos(v));

                /*System.out.println("x: " + points[pointIndex + 0]);
                System.out.println("y: " + points[pointIndex + 1]);
                System.out.println("z: " + points[pointIndex + 2]);*/
                pointIndex += 3;

            }

        }
        double amount = points.length / 3;
        //System.out.println("cantidad puntos: " + amount);
        for (int i = 0; i < points.length; i += 3) {
            Sphere sp = new Sphere(0.7);
            sp.setMaterial(new PhongMaterial(Color.BLACK));
            sp.setTranslateX(points[i + 0]);
            sp.setTranslateY(points[i + 1]);
            sp.setTranslateZ(points[i + 2]);
            /*sp.setTranslateX(arrayX[x]);
            sp.setTranslateY(arrayYN[x]);
            sp.setTranslateZ(arrayZN[x]);*/
            plot.getChildren().add(sp);

        }
        /*Sphere sp = new Sphere(5);
        sp.setMaterial(new PhongMaterial(Color.BLUEVIOLET));
        sp.setTranslateX(20);
        sp.setTranslateY(0);
        sp.setTranslateZ(20);
        /*sp.setTranslateX(arrayX[x]);
            sp.setTranslateY(arrayYN[x]);
            sp.setTranslateZ(arrayZN[x]);*/
        //plot.getChildren().add(sp);
    }
}
