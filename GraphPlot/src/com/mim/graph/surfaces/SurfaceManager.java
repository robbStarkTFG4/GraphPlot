/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mim.graph.surfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import com.mim.graph.util.PropertyWrapper;
import com.mim.graph.util.Punto2D;
import com.mim.graph.util.Surface;
import java.util.ArrayList;
import org.fxyz.geometry.Point3D;
import org.fxyz.shapes.composites.PolyLine3D;

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

    public void destroySurface() {
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
                setUpEllipticalCone();
                break;
            case "Paraboloide eliptico":
                setUpEllipticParaboloid();
                break;
            case "Paraboloide hiperbolico":

                break;
            case "Torus":
                setUpTorus();
                break;
            case "Cilindro":
                setUpCylinder();
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

        DoubleProperty aProperty = new SimpleDoubleProperty(null, "a", 40);
        DoubleProperty bProperty = new SimpleDoubleProperty(null, "b", 42);
        DoubleProperty cProperty = new SimpleDoubleProperty(null, "c", 45);

        constantsMap.put("a", aProperty);
        constantsMap.put("b", bProperty);
        constantsMap.put("c", cProperty);

        surf = new TwoSheet(plot, aProperty, bProperty, cProperty);
        surf.build();
        addListeners();
    }

    private void setUpEllipticalCone() {
        constantsMap.clear();

        DoubleProperty aProperty = new SimpleDoubleProperty(null, "a", 10);
        DoubleProperty bProperty = new SimpleDoubleProperty(null, "b", 15);
        DoubleProperty cProperty = new SimpleDoubleProperty(null, "c", 45);

        constantsMap.put("a", aProperty);
        constantsMap.put("b", bProperty);
        constantsMap.put("c", cProperty);

        surf = new EllipticalCone(plot, aProperty, bProperty, cProperty);
        surf.build();
        addListeners();
    }

    private void setUpTorus() {
        constantsMap.clear();

        DoubleProperty aProperty = new SimpleDoubleProperty(null, "a", 10);
        DoubleProperty cProperty = new SimpleDoubleProperty(null, "c", 45);

        constantsMap.put("a", aProperty);
        constantsMap.put("c", cProperty);

        surf = new Torus(plot, aProperty, cProperty);
        surf.build();
        addListeners();
    }

    private void setUpCylinder() {
        constantsMap.clear();

        DoubleProperty aProperty = new SimpleDoubleProperty(null, "a", 10);
        DoubleProperty bProperty = new SimpleDoubleProperty(null, "b", 45);

        constantsMap.put("a", aProperty);
        constantsMap.put("b", bProperty);

        surf = new Cylinder(plot, aProperty, bProperty);
        surf.build();
        addListeners();
    }

    private void addListeners() {
        for (Map.Entry<String, DoubleProperty> entry : constantsMap.entrySet()) {
            DoubleProperty db = entry.getValue();
            propertyList.stream().filter((wrap) -> (wrap.getName().equals(db.getName()))).map((wrap) -> {
                wrap.getProperty().setValue(String.valueOf(db.doubleValue()));
                return wrap;
            }).forEach((wrap) -> {
                wrap.getProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    db.set(Double.parseDouble(newValue));
                    updateSurface();
                });
            });
        }

    }

    private void updateSurface() {
        plot.getChildren().clear();
        surf.build();
    }

    public void removeListeners() {

    }

    public void drawPolyLines(Map<Integer, List<Punto2D>> curvesInfo, double afinador) {
        Random generator = new Random();

        Color c = Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
        for (Map.Entry<Integer, List<Punto2D>> entry : curvesInfo.entrySet()) {
            Integer key = entry.getKey();
            List<Punto2D> value = entry.getValue();

            List<Point3D> pointsList = new ArrayList<>();
            for (int i = 0; i < value.size(); i++) {
                Punto2D punto2D = value.get(i);
                Point3D p = new Point3D((float) punto2D.getX_Point(), 0, (float) punto2D.getY_Point());
                pointsList.add(p);
            }
            PolyLine3D line = new PolyLine3D(pointsList, 1, c);
            plot.getChildren().add(line);

        }
    }

    public void drawCurves(Map<Integer, List<Punto2D>> curvesInfo, double afinador) {

        for (Map.Entry<Integer, List<Punto2D>> entry : curvesInfo.entrySet()) {
            Integer key = entry.getKey();
            List<Punto2D> value = entry.getValue();
            Random generator = new Random();

            Color c = Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
            for (int i = 0; i < value.size(); i++) {

                Punto2D punto2D = value.get(i);

                Sphere sp = new Sphere(0.35);
                sp.setMaterial(new PhongMaterial(c));
                sp.setTranslateX(punto2D.getX_Point());
                sp.setTranslateZ(punto2D.getY_Point() / afinador);

                plot.getChildren().add(sp);
            }

        }
    }

    public void drawShape(List<Punto2D> points, double afinador, double amplificador) {
        Random generator = new Random();
        /*Color c = Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
        for (int i = 0; i < points.size(); i++) {

            Punto2D punto2D = points.get(i);

            Sphere sp = new Sphere(0.35);
            sp.setMaterial(new PhongMaterial(c));
            sp.setTranslateX(amplificador * punto2D.getX_Point());
            //if (punto2D.getY_Point() < 100) {
            sp.setTranslateZ(amplificador * punto2D.getY_Point() / afinador);
            // } else {
            //sp.setTranslateZ(punto2D.getY_Point() / 10);
            //}
            plot.getChildren().add(sp);
        }*/
        Color c = Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));

        List<Point3D> pointsList = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Punto2D punto2D = points.get(i);
            Point3D p = new Point3D((float) ((punto2D.getX_Point() * amplificador) / afinador), 0,
                    (float) ((punto2D.getY_Point() * amplificador * amplificador) / afinador));
            pointsList.add(p);
        }
        PolyLine3D line = new PolyLine3D(pointsList, 4, c);
        plot.getChildren().add(line);

    }

    public void addDepth(List<Punto2D> points, double afinador, double amplificador) {
        Random generator = new Random();
        Color c = Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
        for (int j = 0; j < 10; j++) {

            for (int i = 0; i < points.size(); i++) {

                Punto2D punto2D = points.get(i);

                Sphere sp = new Sphere(0.35);
                sp.setMaterial(new PhongMaterial(c));
                sp.setTranslateX((amplificador * punto2D.getX_Point()) / afinador);
                sp.setTranslateY(j + 1);
                sp.setTranslateZ(amplificador * punto2D.getY_Point() / afinador);

                plot.getChildren().add(sp);
            }
        }

        for (int j = 0; j < 10; j++) {

            for (int i = 0; i < points.size(); i++) {

                Punto2D punto2D = points.get(i);

                Sphere sp = new Sphere(0.35);
                sp.setMaterial(new PhongMaterial(c));
                sp.setTranslateX((amplificador * punto2D.getX_Point())/afinador);
                sp.setTranslateY(-1 * j - 1);
                sp.setTranslateZ((amplificador * punto2D.getY_Point()) / afinador);

                plot.getChildren().add(sp);
            }
        }
    }

    private void setUpEllipticParaboloid() {
        constantsMap.clear();

        DoubleProperty aProperty = new SimpleDoubleProperty(null, "a", 5);
        DoubleProperty bProperty = new SimpleDoubleProperty(null, "b", 5);

        constantsMap.put("a", aProperty);
        constantsMap.put("b", bProperty);

        surf = new EllipticParaboloid(plot, aProperty, bProperty);
        surf.build();
        addListeners();
    }

}
