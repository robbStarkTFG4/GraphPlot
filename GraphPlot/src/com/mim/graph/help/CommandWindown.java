/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mim.graph.help;

import com.mim.graph.util.Comands;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author marcoisaac
 */
public class CommandWindown extends Stage {

    private ObservableList<Comands> observableList;

    public CommandWindown() {

        TableView<Comands> table = new TableView<>();

        TableColumn<Comands, String> nameColumn = new TableColumn<>("descripcion");
        nameColumn.setCellValueFactory((TableColumn.CellDataFeatures<Comands, String> param) -> new SimpleStringProperty(param.getValue().getName()));

        TableColumn<Comands, String> descriptionColumn = new TableColumn<>("uso");
        descriptionColumn.setCellValueFactory((TableColumn.CellDataFeatures<Comands, String> param) -> new SimpleStringProperty(param.getValue().getDescription()));

        feedData(table);
        table.getColumns().addAll(nameColumn, descriptionColumn);

        VBox root = new VBox();
        root.getChildren().add(table);
        root.setPadding(new Insets(7));

        Scene scene = new Scene(root);
        //this.setWidth(0);
        this.setScene(scene);
    }

    private void feedData(TableView table) {

        List<Comands> list = new ArrayList<>();
        list.add(new Comands("raiz cuadrada", "sqrt(x)"));
        list.add(new Comands("arco seno", "asin(x)"));
        list.add(new Comands("arco coseno", "acos(x)"));
        list.add(new Comands("arco tangente", "atan(x)"));
        list.add(new Comands("raiz cubica", "cbrt(x)"));
        list.add(new Comands("coseno", "cos(x)"));
        list.add(new Comands("coseno hiperbolico", "cosh(x)"));
        list.add(new Comands("exponencial", "exp(x)"));
        list.add(new Comands("logaritmo natural", "log(x)"));
        list.add(new Comands("logaritmo base 10", "log10(x)"));
        list.add(new Comands("logaritmo base 2", "log2(x)"));
        list.add(new Comands("seno", "sin(x)"));
        list.add(new Comands("seno hiperbolico", "sinh(x)"));
        list.add(new Comands("tangente", "tan(x)"));
        list.add(new Comands("tangente hiperbolico", "tanh(x)"));
        observableList = FXCollections.observableArrayList(list);
        table.setItems(observableList);
    }

}
