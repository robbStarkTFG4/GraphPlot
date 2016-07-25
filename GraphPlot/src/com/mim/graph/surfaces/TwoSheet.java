/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mim.graph.surfaces;

import com.mim.graph.util.Punto2D;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import com.mim.graph.util.Surface;
import java.util.ArrayList;
import java.util.List;
import org.fxyz.geometry.Point3D;
import org.fxyz.shapes.composites.PolyLine3D;

/**
 *
 * @author marcoisaac
 */
public class TwoSheet implements Surface {

    private DoubleProperty aProperty;
    private DoubleProperty bProperty;
    private DoubleProperty cProperty;
    private Group plot;

    public TwoSheet(Group plot, DoubleProperty aProperty, DoubleProperty bProperty, DoubleProperty cProperty) {
        this.aProperty = aProperty;
        this.bProperty = bProperty;
        this.cProperty = cProperty;
        this.plot = plot;
    }

    @Override
    public void build() {
        //setHeightData(15, Color.AQUA, false, false);
        int radiusDivisions = 80;/// ESTE ES
        int tubeDivisions = 12;//12

        int numVerts = (int) (tubeDivisions * radiusDivisions);
        int faceCount = numVerts * 2;
        float[] points = new float[numVerts * 3 * 4];
        float[] points2 = new float[numVerts * 3 * 4],
                texCoords = new float[numVerts * 2];
        int[] faces = new int[faceCount * 6];
        int pointIndex = 0, texIndex = 0, faceIndex = 0;
        float tubeFraction = 1.0f / tubeDivisions;
        float radiusFraction = (float) (1.0f / radiusDivisions);
        float x, y, z;
        int p0 = 0, p1 = 0, p2 = 0, p3 = 0, t0 = 0, t1 = 0, t2 = 0, t3 = 0;
        for (int tubeIndex = -tubeDivisions; tubeIndex < tubeDivisions; tubeIndex++) {

            for (int radiusIndex = -radiusDivisions; radiusIndex < radiusDivisions; radiusIndex++) {

                //double v = radiusIndex * 0.0116 * radiusDivisions + 1.5683;
                double v = -0.9767 * radiusIndex + 3.1416;

                // double v = radiusIndex * 0.0116 * radiusDivisions + 1.5683;
                double u = 0.2617 * tubeIndex + 3.1403;

                points[pointIndex + 0] = x = (float) (aProperty.getValue() * 1 * (Math.sinh(u)) * (Math.cos(v)));
                points[pointIndex + 1] = y = (float) (bProperty.getValue() * 1 * (Math.sinh(u) * (Math.sin(v))));
                points[pointIndex + 2] = z = (float) (cProperty.getValue() * (Math.cosh(u)));

                pointIndex += 3;

            }

        }

        double amount = points.length / 3;

        for (int i = 0; i < points.length; i += 3) {
            Sphere sp = new Sphere(0.7);
            sp.setMaterial(new PhongMaterial(Color.BLACK));
            sp.setTranslateX(points[i + 0] / 40);
            sp.setTranslateY(points[i + 1] / 40);
            sp.setTranslateZ(points[i + 2] / 15);

            plot.getChildren().add(sp);

        }

        ///// SECOND PART
        pointIndex = 0;
        for (int tubeIndex = -tubeDivisions; tubeIndex < tubeDivisions; tubeIndex++) {

            for (int radiusIndex = -radiusDivisions; radiusIndex < radiusDivisions; radiusIndex++) {

                double v = radiusIndex * 0.0116 * radiusDivisions + 1.5683;
                double u = 0.2617 * tubeIndex + 3.1403;

                points2[pointIndex + 0] = x = (float) (aProperty.getValue() * 1 * (Math.sinh(u)) * (Math.cos(v)));
                points2[pointIndex + 1] = y = (float) (bProperty.getValue() * 1 * (Math.sinh(u) * (Math.sin(v))));
                points2[pointIndex + 2] = z = (float) (-1 * cProperty.getValue() * (Math.cosh(u)));

                pointIndex += 3;

            }

        }

        //plot = new Group();
        //System.out.println("cantidad puntos: " + amount);
        for (int i = 0; i < points2.length; i += 3) {
            Sphere sp = new Sphere(0.7);
            sp.setMaterial(new PhongMaterial(Color.BLACK));
            sp.setTranslateX(points2[i + 0] / 40);
            sp.setTranslateY(points2[i + 1] / 40);
            sp.setTranslateZ(points2[i + 2] / 15);
            /*sp.setTranslateX(arrayX[x]);
            sp.setTranslateY(arrayYN[x]);
            sp.setTranslateZ(arrayZN[x]);*/
            plot.getChildren().add(sp);

        }
    }
}
