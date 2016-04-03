/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing3d.util;

import javafx.beans.property.StringProperty;

/**
 *
 * @author marcoisaac
 */
public class PropertyWrapper {
    private StringProperty property;
    private String name;

    public PropertyWrapper(StringProperty property, String name) {
        this.property = property;
        this.name = name;
    }

    public StringProperty getProperty() {
        return property;
    }

    public void setProperty(StringProperty property) {
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
