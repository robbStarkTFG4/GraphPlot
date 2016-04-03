/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing3d.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import testing3d.surfaces.Ellipsoid;
import testing3d.surfaces.OneSheet;
import testing3d.surfaces.TwoSheet;

/**
 *
 * @author marcoisaac
 */
public class SurfaceManager {

    private Group plot;
    private List<PropertyWrapper> propertyList;
    private final Map<String, DoubleProperty> constantsMap = new HashMap<>();
    private String currentSurface;
    private Surface surf;

    public SurfaceManager(Group plot) {
        this.plot = plot;
    }

    private void destroySurface() {
        if (plot != null) {
            if (plot.getChildren().size() > 0) {
                plot.getChildren().clear();
            }
        }
    }

    public void changeSurface(String tag, List<PropertyWrapper> propertyList) {
        if (this.propertyList != null) {
            this.propertyList.clear();
        }
        this.propertyList = null;
        this.propertyList = propertyList;
        currentSurface = tag;
        build(tag);
    }

    private void build(String tag) {
        //System.out.println("changing surface");
        destroySurface();
        switch (tag) {
            case "Elipsoide":
                setUpEllipsoid();
                break;
            case "Hiperboloide una hoja":
                setUpOneSheet();
                break;
            case "Hiperboloide dos hojas":
                setUpTwoSheet();
                break;
            case "Cono eliptico":
                torus();
                break;
            case "Paraboloide eliptico":
                cylinder();
                break;
            case "Paraboloide hiperbolico":

                break;
        }
    }

    private void setUpOneSheet() {
        constantsMap.clear();

        DoubleProperty aProperty = new SimpleDoubleProperty(null, "a", 1);
        DoubleProperty bProperty = new SimpleDoubleProperty(null, "b", 1);
        DoubleProperty cProperty = new SimpleDoubleProperty(null, "c", 1);

        constantsMap.put("a", aProperty);
        constantsMap.put("b", bProperty);
        constantsMap.put("c", cProperty);

        surf = new OneSheet(plot, aProperty, bProperty, cProperty);
        surf.build();
        addListeners();
    }

    private void setUpEllipsoid() {
        constantsMap.clear();

        DoubleProperty aProperty = new SimpleDoubleProperty(null, "a", 10);
        DoubleProperty bProperty = new SimpleDoubleProperty(null, "b", 15);
        DoubleProperty cProperty = new SimpleDoubleProperty(null, "c", 45);

        constantsMap.put("a", aProperty);
        constantsMap.put("b", bProperty);
        constantsMap.put("c", cProperty);

        surf = new Ellipsoid(plot, aProperty, bProperty, cProperty);
        surf.build();
        addListeners();
    }

    private void setUpTwoSheet() {
        constantsMap.clear();

        DoubleProperty aProperty = new SimpleDoubleProperty(null, "a", 10);
        DoubleProperty bProperty = new SimpleDoubleProperty(null, "b", 15);
        DoubleProperty cProperty = new SimpleDoubleProperty(null, "c", 45);

        constantsMap.put("a", aProperty);
        constantsMap.put("b", bProperty);
        constantsMap.put("c", cProperty);

        surf = new TwoSheet(plot, aProperty, bProperty, cProperty);
        surf.build();
        addListeners();
    }

    private void torus() {
        //setHeightData(15, Color.AQUA, false, false);
        int radiusDivisions = 135;
        int tubeDivisions = 12;
        float radius = 27;
        float tRadius = 12;
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

            float radian = 1 * tubeIndex * 2.0f * 3.141592653589793f;

            for (int radiusIndex = 0; radiusIndex < radiusDivisions; radiusIndex++) {

                float localRadian = radiusFraction * (radiusIndex) * 2.0f * 3.141592653589793f;

                points[pointIndex + 0] = x = ((radius + tRadius * ((float) Math.cos(radian))) * ((float) Math.cos(localRadian)));
                points[pointIndex + 1] = y = ((radius + tRadius * ((float) Math.cos(radian))) * ((float) Math.sin(localRadian)));
                points[pointIndex + 2] = z = (tRadius * (float) Math.sin(radian));

                /*System.out.println("x: " + points[pointIndex + 0]);
                System.out.println("y: " + points[pointIndex + 1]);
                System.out.println("z: " + points[pointIndex + 2]);*/
                pointIndex += 3;

            }

        }

        double amount = points.length / 3;
        //System.out.println("cantidad puntos: " + amount);
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

    private void cylinder() {
        double a = 10;
        double b = 15;
        double c = 45;
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
        for (int tubeIndex = -tubeDivisions; tubeIndex < tubeDivisions; tubeIndex++) {

            float radian = 1 * tubeIndex * 2.0f * 3.141592653589793f;

            for (int radiusIndex = -radiusDivisions; radiusIndex < radiusDivisions; radiusIndex++) {

                /*x = acosu
                y = bsinu
                z = v
 
                u in[0,2pi) and v in(0, h);*/
                double v = radiusIndex * 0.0116 * radiusDivisions + 1.5683;
                double u = 0.2617 * tubeIndex + 3.1403;

                points[pointIndex + 0] = x = (float) (a * Math.cos(u));
                points[pointIndex + 1] = y = (float) (b * Math.sin(u));
                points[pointIndex + 2] = z = radiusIndex;

                /*System.out.println("x: " + points[pointIndex + 0]);
                System.out.println("y: " + points[pointIndex + 1]);
                System.out.println("z: " + points[pointIndex + 2]);*/
                pointIndex += 3;

            }

        }

        //plot = new Group();
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
    }

    private void addListeners() {
        for (Map.Entry<String, DoubleProperty> entry : constantsMap.entrySet()) {
            DoubleProperty db = entry.getValue();
            for (PropertyWrapper wrap : propertyList) {
                if (wrap.getName().equals(db.getName())) {
                    wrap.getProperty().setValue(String.valueOf(db.doubleValue()));
                    wrap.getProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            db.set(Double.parseDouble(newValue));
                            updateSurface();
                        }
                    });
                }
            }
        }

    }

    private void updateSurface() {
        plot.getChildren().clear();
        surf.build();
    }

    public void removeListeners() {

    }
}
