/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing3d.equation;

import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import testing3d.util.PropertyWrapper;

/**
 *
 * @author marcoisaac
 */
public class TextMatrix {

    private final static int ROWS = 3;
    private final static int COLLUMNS = 8;
    private final Node[][] nodeArray = new Node[ROWS][COLLUMNS];
    private final MatrixPane matrix;

    public TextMatrix() {
        matrix = new MatrixPane();
        matrix.setPadding(new Insets(12));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLLUMNS; j++) {
                elipsoide(i, j, matrix);
            }
        }

    }

    public TextMatrix(final String tag) {
        matrix = new MatrixPane();
        matrix.setPadding(new Insets(12));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLLUMNS; j++) {
                switch (tag) {
                    case "Elipsoide":
                        elipsoide(i, j, matrix);
                        break;
                    case "Hiperboloide una hoja":
                        oneSheetHyperboloid(i, j, matrix);
                        break;
                    case "Hiperboloide dos hojas":
                        twoSheetHyperboloid(i, j, matrix);
                        break;
                    case "Cono eliptico":
                        conoEliptico(i, j, matrix);
                        break;
                    case "Paraboloide eliptico":
                        paraboloideEliptico(i, j, matrix);
                        break;
                    case "Paraboloide hiperbolico":
                        paraboloideHiperbolico(i, j, matrix);
                        break;
                }
            }

        }

    }

    /*
    Usage
    @Override  
    public void start(Stage primaryStage) {

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();

        TextMatrix test = new TextMatrix();

        FlowPane root = new FlowPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setPrefWidth(screen.getWidth());
        root.setMinWidth(screen.getWidth());
        root.getChildren().add(matrix);
        root.setVgap(20);

        Button btn = new Button("Print");
        btn.setOnMouseClicked(d -> {
            printArray();
        });

        root.getChildren().add(btn);
        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());

        primaryStage.setScene(scene);
        primaryStage.show();

    }*/
    public Node[][] getNodeArray() {
        return nodeArray;
    }

    public MatrixPane getMatrix() {
        return matrix;
    }

    private void paraboloideEliptico(int i, int j, GridPane matrix) {

        if (i == 0 && j == 3) {
            Text txt = new Text("x^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 5) {
            Text txt = new Text("y^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 1) {
            Text txt = new Text("z");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 2) {
            Text txt = new Text(" = ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 3) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 4) {
            Text txt = new Text("  +  ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 5) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 2 && j == 3) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 5) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
    }

    private void dataEntry(int j, int i, GridPane matrix) {
        TextField txt = new TextField("change");
        txt.setPrefWidth(50);
        if ((j % 2) == 0) {
            //txt.set("-fx-fill: red");
        }

        nodeArray[i][j] = txt;
        matrix.add(nodeArray[i][j], j, i);
    }

    private void twoSheetHyperboloid(int i, int j, GridPane matrix) {
        if (i == 0 && j == 0) {
            Text txt = new Text("z^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 2) {
            Text txt = new Text("x^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 4) {
            Text txt = new Text("y^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 0) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 1) {
            Text txt = new Text(" - ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 2) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 3) {
            Text txt = new Text(" - ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 4) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 5) {
            Text txt = new Text("  =");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 6) {
            Text txt = new Text("  1");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 0) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            txt.setUserData("c");
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 2) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            txt.setUserData("a");
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 4) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            txt.setUserData("b");
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        createProperties();
    }

    private void oneSheetHyperboloid(int i, int j, GridPane matrix) {
        if (i == 0 && j == 0) {
            Text txt = new Text("x^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 2) {
            Text txt = new Text("y^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 4) {
            Text txt = new Text("z^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 0) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 1) {
            Text txt = new Text("  +  ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 2) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 3) {
            Text txt = new Text("  -  ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 4) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 5) {
            Text txt = new Text("  =");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 6) {
            Text txt = new Text(" 1");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 0) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            txt.setUserData("a");
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 2) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            txt.setUserData("b");
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 4) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            txt.setUserData("c");
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        createProperties();
    }

    private void elipsoide(int i, int j, MatrixPane matrix) {

        ///////////////// INSERT CODE HERE
        if (i == 0 && j == 0) {
            Text txt = new Text("x^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 2) {
            Text txt = new Text("y^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 4) {
            Text txt = new Text("z^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 0) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 1) {
            Text txt = new Text("   +");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 2) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 3) {
            Text txt = new Text("   +");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 4) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 5) {
            Text txt = new Text("  =");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 6) {
            Text txt = new Text("   1");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 0) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            txt.setUserData("a");
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 2) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            txt.setUserData("b");
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 4) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            txt.setUserData("c");
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        createProperties();
    }

    private void createProperties() {
        matrix.clearList();
        for (Node[] node : nodeArray) {
            for (Node nf : node) {
                if (nf instanceof TextField) {
                    matrix.add(new PropertyWrapper(((TextField) nf).textProperty(), nf.getUserData().toString()));
                }
            }
        }
    }

    private void paraboloideHiperbolico(int i, int j, GridPane matrix) {
        if (i == 0 && j == 3) {
            Text txt = new Text("y^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 5) {
            Text txt = new Text("x^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 1) {
            Text txt = new Text("z");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 2) {
            Text txt = new Text(" = ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 3) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 4) {
            Text txt = new Text("  -  ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 5) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 2 && j == 3) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 5) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
    }

    private void conoEliptico(int i, int j, GridPane matrix) {
        if (i == 0 && j == 0) {
            Text txt = new Text("x^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 2) {
            Text txt = new Text("y^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 0 && j == 4) {
            Text txt = new Text("z^2");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 0) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 1) {
            Text txt = new Text(" +  ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 2) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 3) {
            Text txt = new Text(" +  ");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 4) {
            Rectangle rect = new Rectangle(40, 2);
            rect.setTranslateY(6);
            matrix.add(rect, j, i);
        }
        if (i == 1 && j == 5) {
            Text txt = new Text(" =");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 1 && j == 6) {
            Text txt = new Text("  0");
            txt.setStyle("-fx-font-size:25");
            txt.setTranslateY(10);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 0) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 2) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
        if (i == 2 && j == 4) {
            TextField txt = new TextField("25");
            txt.setStyle("-fx-font-size:15");
            txt.setTranslateY(9);
            txt.setPrefHeight(5);
            txt.setPrefWidth(47);
            nodeArray[i][j] = txt;
            matrix.add(nodeArray[i][j], j, i);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void printArray() {
        System.out.println("//////////////////////// BEGIN CODE");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLLUMNS; j++) {
                TextField node = (TextField) nodeArray[i][j];
                if (!node.getText().startsWith("change")) {
                    System.out.println("if(i==" + i + "&&" + "j==" + j + "){");
                    if (node.getText().startsWith("x") || node.getText().startsWith("y") || node.getText().startsWith("z")) {
                        System.out.println("Text txt = new Text(\"" + node.getText() + "\");");
                        System.out.println("txt.setStyle(\"-fx-font-size:25\");");
                        System.out.println("txt.setTranslateY(10);");
                        System.out.println("nodeArray[i][j] = txt;");
                        System.out.println(" matrix.add(nodeArray[i][j], j, i);");
                    } else if (node.getText().startsWith("-")) {
                        System.out.println(" Rectangle rect = new Rectangle(40, 2);");
                        System.out.println("rect.setTranslateY(6);");
                        System.out.println(" matrix.add(rect, j, i);");
                    } else if (node.getText().startsWith("*")) {
                        String constant = node.getText().substring(1);
                        System.out.println("TextField txt = new TextField(\"25\");");
                        System.out.println("txt.setStyle(\"-fx-font-size:15\");");
                        System.out.println("txt.setTranslateY(9);");
                        System.out.println("txt.setPrefHeight(5);");
                        System.out.println("txt.setPrefWidth(47);");
                        System.out.println("txt.setUserData(" + constant + ");");
                        System.out.println("nodeArray[i][j] = txt;");
                        System.out.println("matrix.add(nodeArray[i][j], j, i);");
                    } else {
                        System.out.println("Text txt = new Text(\"" + node.getText() + "\");");
                        System.out.println("txt.setStyle(\"-fx-font-size:25\");");
                        System.out.println("txt.setTranslateY(10);");
                        System.out.println("nodeArray[i][j] = txt;");
                        System.out.println(" matrix.add(nodeArray[i][j], j, i);");
                    }
                    System.out.println("}");
                }
            }
        }
        System.out.println("//////////////////////// END CODE");
    }

}
