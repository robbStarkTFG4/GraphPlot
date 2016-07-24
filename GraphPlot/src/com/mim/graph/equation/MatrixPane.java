/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mim.graph.equation;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import com.mim.graph.util.PropertyWrapper;

/**
 *
 * @author marcoisaac
 */
public class MatrixPane extends GridPane {

    private final List<PropertyWrapper> propertyList = new ArrayList<>();

    public MatrixPane() {
    }

    public void add(PropertyWrapper property) {
        propertyList.add(property);
    }

    public void clearList() {
        propertyList.clear();
    }

    public List<PropertyWrapper> getPropertyList() {
        return propertyList;
    }
}
