/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing3d.util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author marcoisaac
 */
public class HiddenPane extends StackPane {

    private final BooleanProperty hiddenProperty = new SimpleBooleanProperty(false);
    private Node content;
    private double parentHeight;
    private Timeline showAnimation;

    public HiddenPane(double parentHeight) {
        this.parentHeight = parentHeight;
        this.setMaxHeight(2 * parentHeight / 4);
        homePosition();
        hide();
    }

    public HiddenPane(Node content, double parentHeight) {
        this.content = content;
        this.parentHeight = parentHeight;
        this.setMaxHeight(2 * parentHeight / 4);
        homePosition();
        hide();
    }

    public Node getContent() {
        return content;
    }

    public void setContent(Node content) {
        this.content = content;
    }

    public boolean isHiddenProperty() {
        return hiddenProperty.get();
    }

    public void show() {
        if (showAnimation == null) {
            showAnimation = new Timeline(new KeyFrame(Duration.millis(600), new KeyValue(content.opacityProperty(), 1)));
            showAnimation.setAutoReverse(false);
        }

        this.getChildren().add(content);
        content.setOpacity(0);
        this.setMaxHeight(2 * parentHeight / 4);
        showAnimation.play();

        this.hiddenProperty.set(false);
    }

    public void hide() {
        this.getChildren().clear();
        this.setMaxHeight(0);
        this.hiddenProperty.set(true);
    }

    private void homePosition() {
        this.setTranslateY(-1 * parentHeight / 4);
    }

}
