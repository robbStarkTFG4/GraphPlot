/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing3d.surfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import testing3d.util.PropertyWrapper;
import testing3d.util.Surface;

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

                break;
            case "Paraboloide eliptico":

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
