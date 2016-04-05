/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing3d.surfaces;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import testing3d.util.Surface;

/**
 *
 * @author marcoisaac
 */
public class Torus implements Surface {

    private DoubleProperty aProperty;
    private DoubleProperty cProperty;
    private Group plot;

    public Torus(Group plot, DoubleProperty aProperty, DoubleProperty cProperty) {
        this.aProperty = aProperty;
        this.cProperty = cProperty;
        this.plot = plot;
    }

    @Override
    public void build() {

        //setHeightData(15, Color.AQUA, false, false);
        int radiusDivisions = 135;
        int tubeDivisions = 12;

        float tubeStartAngle = 0;
        float xOffset = 0;
        float yOffset = 0;
        float zOffset = 1;
        int numVerts = (int) (tubeDivisions * radiusDivisions);
        int faceCount = numVerts * 2;
        float[] points = new float[numVerts * 3],
                texCoords = new float[numVerts * 2];
        int[] faces = new int[faceCount * 6];
        int pointIndex = 0, texIndex = 0, faceIndex = 0;
        float tubeFraction = 1.0f / tubeDivisions;
        float radiusFraction = (float) (1.0f / radiusDivisions);
        float x, y, z;
        int p0 = 0, p1 = 0, p2 = 0, p3 = 0, t0 = 0, t1 = 0, t2 = 0, t3 = 0;
        for (int tubeIndex = 0; tubeIndex < tubeDivisions; tubeIndex++) {

            float v = 1 * tubeIndex * 2.0f * 3.141592653589793f;

            for (int radiusIndex = 0; radiusIndex < radiusDivisions; radiusIndex++) {

                float u = radiusFraction * (radiusIndex) * 2.0f * 3.141592653589793f;

                points[pointIndex + 0] = x = (float) ((cProperty.getValue() + aProperty.getValue() * (Math.cos(v))) * (Math.cos(u)));
                points[pointIndex + 1] = y = (float) ((cProperty.getValue() + aProperty.getValue() * (Math.cos(v))) * (Math.sin(u)));
                points[pointIndex + 2] = z = (float) (aProperty.getValue() * Math.sin(v));

                /*System.out.println("x: " + points[pointIndex + 0]);
                System.out.println("y: " + points[pointIndex + 1]);
                System.out.println("z: " + points[pointIndex + 2]);*/
                pointIndex += 3;

            }

        }

        for (int i = 0; i < points.length; i += 3) {
            Sphere sp = new Sphere(2);
            sp.setMaterial(new PhongMaterial(Color.RED));
            sp.setTranslateX(points[i + 0]);
            sp.setTranslateY(points[i + 1]);
            sp.setTranslateZ(points[i + 2]);
            /*sp.setTranslateX(arrayX[x]);
            sp.setTranslateY(arrayYN[x]);
            sp.setTranslateZ(arrayZN[x]);*/
            plot.getChildren().add(sp);

        }
    }
}
