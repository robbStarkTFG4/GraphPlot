/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing3d;

import testing3d.util.CurvaDatos;
import testing3d.util.Punto2D;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.fxyz.shapes.composites.SurfacePlot;
import testing3d.camera.ContentModel;
import testing3d.util.HiddenPane;
import testing3d.equation.MatrixPane;
import testing3d.surfaces.SurfaceManager;
import testing3d.equation.TextMatrix;

/**
 *
 * @author marcoisaac Transparent modifiers hex color:
 *
 * 100% — FF
 *
 * 95% — F2
 *
 * 90% — E6
 *
 * 85% — D9
 *
 * 80% — CC
 *
 * 75% — BF
 *
 * 70% — B3
 *
 * 65% — A6
 *
 * 60% — 99
 *
 * 55% — 8C
 *
 * 50% — 80
 *
 * 45% — 73
 *
 * 40% — 66
 *
 * 35% — 59
 *
 * 30% — 4D
 *
 * 25% — 40
 *
 * 20% — 33
 *
 * 15% — 26
 *
 * 10% — 1A
 *
 * 5% — 0D
 *
 * 0% — 00
 */
public class Test3D extends Application {

    private SurfacePlot surfacePlot;
    public AmbientLight selfLight = new AmbientLight(Color.WHITE);
    public double nodeRadius = 1;
    private final double axesSize = 1000;
    private final boolean normalized = false;
    public boolean selfLightEnabled = true;
    public Color color = Color.WHITE;
    private TriangleMesh mesh;
    public MeshView meshView;
    public PhongMaterial material;
    int size = 144;
    private final Group plot = new Group();

    float[] arrayX = new float[size];
    float[][] arrayY = new float[size][size];
    float[][] arrayZ = new float[size][size];

    float[] arrayXN = new float[2 * size];
    float[] arrayYN = new float[2 * size];
    float[] arrayZN = new float[2 * size];

    // Matrix tool
    public final static int ROWS = 3;
    public final static int COLLUMNS = 8;
    public final Node[][] nodeArray = new Node[ROWS][COLLUMNS];
    private HiddenPane menuPane;

    //calculates screen dimensions. width and height
    private final Rectangle2D screen = Screen.getPrimary().getVisualBounds();

    //screen width
    final double plotWidth = screen.getWidth();

    //screen height
    final double plotHeight = 7 * screen.getHeight() / 8;

    private final StackPane mainPanel = new StackPane();

    private ContentModel content;
    private final VBox root = new VBox();

    // here i add "plot" group
    Group innerGroup = new Group();
    // final String[][] surfaceTags = {{"Elipsoide", "Hiperboloide una hoja", "Hiperboloide dos hojas"}, {"Cono eliptico", "Paraboloide eliptico", "Paraboloide hiperbolico"}, {"Torus", "Cilindro", "NA"}};
    final String[][] surfaceTags = {{"Elipsoide", "Hiperboloide una hoja", "Hiperboloide dos hojas"}, {"Cono eliptico", "Paraboloide eliptico", "Torus"}, {"Cilindro"}};
    private Pane equationPane;
    private MatrixPane matrix;
    private SurfaceManager surf;
    private final StringProperty title = new SimpleStringProperty("GraphPlot");

    private Stage stageRef;

    private Map<Integer, List<Punto2D>> curvesInfo;
    private CurvaDatos curvaInf;
    private TextField txtRadio;
    private TextField kTxt;
    private TextField hTxt;
    private TextField hEllipTxt;
    private TextField aRadiusTxt;
    private TextField kEllipTxt;
    private TextField bRadiusTxt;
    private CurvaDatos curvaInf2;
    private Map<Integer, List<Punto2D>> curvesInfo2;
    private List<Punto2D> pointsCilindricas;
    private TextField hEllipTxtCuadri;
    private TextField aRadiusTxtCuadri;
    private TextField kEllipTxtCuadri;
    private TextField bRadiusTxtCuadri;

    @Override
    public void start(Stage primaryStage) {
        stageRef = primaryStage;
        initRoot();

        setUpTopMenu(screen, plotWidth, root);

        spaceAxis(plotWidth, plotHeight);
        slidePane(plotWidth, plotHeight);

        Scene scene = new Scene(root, plotWidth, screen.getHeight());

        stageRef.setScene(scene);
        stageRef.titleProperty().bind(title);
        stageRef.show();
    }

    // set dynamic witdh and height to main container
    private void initRoot() {
        mainPanel.setPrefHeight(plotHeight);
        mainPanel.setPrefWidth(plotWidth);
    }

    //setUp 3D space and equation pane.
    private void spaceAxis(final double plotWidth, final double plotHeight) {
        content = new ContentModel(plotWidth, plotHeight, 40);
        mainPanel.getChildren().add(content.getSubScene());

        double valX = plotWidth / 425;
        double resX = (valX * 425) - 425;

        double valY = plotHeight / 150;
        double resY = (valY * 150) - 150;

        equationPane = new Pane();
        equationPane.setStyle("-fx-background-color:rgba(95,158,160,0.3)");
        matrix = new TextMatrix("Hiperboloide dos hojas").getMatrix();
        equationPane.getChildren().add(matrix);

        SubScene scene = new SubScene(equationPane, 425, 150);
        scene.setTranslateX((-1 * resX / 2) * 0.95);
        scene.setTranslateY((-1 * resY / 2) * 0.9);

        VBox controlsPane = spaceControls();
        SubScene controlsScene = new SubScene(controlsPane, plotWidth / 14, 4 * plotHeight / 12);

        controlsScene.setTranslateX(6.7 * plotWidth / 14);
        controlsScene.setTranslateY(5.7 * plotHeight / 12);

        mainPanel.getChildren().addAll(scene, controlsScene);

        surf = new SurfaceManager(plot);

        changeWindownTitle("Hiperboloide dos hojas");
        surf.changeSurface("Hiperboloide dos hojas", matrix.getPropertyList());
        innerGroup.getChildren().add(plot);

        content.setContent(innerGroup);

        root.getChildren().add(mainPanel);
    }

    /**
     * setUp space controls (zoom in, zoom out)
     *
     * @return
     */
    private VBox spaceControls() {
        VBox controlsPane = new VBox();
        controlsPane.setStyle("-fx-background-color:rgba(95,158,160,0.5)");
        Button plus = new Button();
        plus.setOnMouseClicked(g -> {
            final double modifierFactor = 0.3;
            double xFlip = -1;
            double z = content.getCameraPosition().getZ();
            double newZ = z - xFlip * (1 + 1) * modifierFactor * 10;
            content.getCameraPosition().setZ(newZ);
        });
        GlyphsDude.setIcon(plus, FontAwesomeIcon.PLUS, "2em");
        plus.applyCss();
        Button minus = new Button();
        minus.setOnMouseClicked(h -> {
            final double modifierFactor = 0.3;
            double xFlip = 1;
            double z = content.getCameraPosition().getZ();
            double newZ = z - xFlip * (1 + 1) * modifierFactor * 10;
            content.getCameraPosition().setZ(newZ);
        });
        GlyphsDude.setIcon(minus, FontAwesomeIcon.MINUS, "2em");
        controlsPane.getChildren().add(plus);
        controlsPane.getChildren().add(minus);
        controlsPane.autosize();
        controlsPane.applyCss();
        return controlsPane;
    }

    /**
     * setUp hiddenPane(slideDown, top to down)
     *
     * @param plotWidth
     * @param plotHeight
     */
    private void slidePane(final double plotWidth, final double plotHeight) {
        menuPane = new HiddenPane(plotHeight);
        menuPane.setPrefWidth(plotWidth);
        menuPane.setStyle("-fx-background-color:rgba(0,100,0,0.7)");
        menuPane.setContent(paneOptions(surfaceTags));
        mainPanel.getChildren().add(menuPane);
    }

    /**
     * menu activated by buttons. this menu manages the content of the
     * hiddenPane.
     *
     * @param screen
     * @param plotWidth
     * @param root
     */
    private void setUpTopMenu(Rectangle2D screen, final double plotWidth, VBox root) {
        FlowPane topPane = new FlowPane();
        topPane.setPadding(new Insets(12));
        topPane.setPrefHeight(1 * screen.getHeight() / 8);
        topPane.setPrefWidth(plotWidth);
        topPane.setOrientation(Orientation.HORIZONTAL);
        topPane.setHgap(7);

        Button quadSurfaces = new Button("Cuadraticas");
        quadSurfaces.setPadding(new Insets(7));

        // EVENTS
        quadSurfaces.setOnMouseClicked(d -> {

            if (menuPane.isHiddenProperty()) {
                menuPane.show();
            } else {
                menuPane.hide();
            }
        });

        Button curvasNivelBtn = new Button("Curvas de nivel");
        curvasNivelBtn.setPadding(new Insets(7));

        curvasNivelBtn.setOnMouseClicked(r -> {
            curvasNivelWindown();
        });

        Button cilindricasBtn = new Button("Cilindricas");
        cilindricasBtn.setPadding(new Insets(7));

        cilindricasBtn.setOnMouseClicked(w -> {
            cilindricasWindown();
        });

        // END EVENTS
        topPane.getChildren().addAll(quadSurfaces, curvasNivelBtn, cilindricasBtn);
        root.getChildren().addAll(topPane);

    }

    private void curvasNivelWindown() {
        Stage dialog = new Stage();
        VBox rootCurvaNivel = new VBox();
        rootCurvaNivel.setPadding(new Insets(12));
        rootCurvaNivel.setSpacing(7);

        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton otroBtn = new RadioButton("otro");
        otroBtn.setUserData("other");
        //otroBtn.setSelected(true);
        otroBtn.setToggleGroup(toggleGroup);

        RadioButton circuloBtn = new RadioButton("circulo");
        circuloBtn.setUserData("circle");
        circuloBtn.setToggleGroup(toggleGroup);

        RadioButton ellipseBtn = new RadioButton("ellipse");
        ellipseBtn.setUserData("ellip");
        ellipseBtn.setToggleGroup(toggleGroup);

        HBox radioHolder = new HBox();
        radioHolder.setSpacing(7);
        radioHolder.getChildren().addAll(otroBtn, circuloBtn, ellipseBtn);

        rootCurvaNivel.getChildren().addAll(radioHolder);

        toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (rootCurvaNivel.getChildren().size() == 2) {
                rootCurvaNivel.getChildren().remove(rootCurvaNivel.getChildren().size() - 1);

                switch (newValue.getUserData().toString()) {
                    case "other":
                        rootCurvaNivel.getChildren().add(otroContent(dialog));
                        break;
                    case "circle":
                        rootCurvaNivel.getChildren().add(circleContent(dialog));
                        break;
                    case "ellip":
                        ellipseBtn.setSelected(true);
                        rootCurvaNivel.getChildren().add(ellipseContent(dialog));
                        break;
                }
            }
        });

        if (curvaInf != null) {
            switch (curvaInf.getTipo()) {
                case "otro":
                    otroBtn.setSelected(true);
                    rootCurvaNivel.getChildren().add(otroContent(dialog));
                    break;
                case "circle":
                    circuloBtn.setSelected(true);
                    rootCurvaNivel.getChildren().add(circleContent(dialog));
                    break;
                case "ellip":
                    ellipseBtn.setSelected(true);
                    rootCurvaNivel.getChildren().add(ellipseContent(dialog));
                    break;
            }
        } else {
            otroBtn.setSelected(true);
            rootCurvaNivel.getChildren().add(otroContent(dialog));
        }
        dialog.setScene(new Scene(rootCurvaNivel));
        dialog.setHeight(450);
        dialog.setWidth(500);
        dialog.setTitle("Escribe datos");
        dialog.setResizable(false);
        dialog.initOwner(stageRef);
        dialog.showAndWait();
    }

    private VBox ellipseContent(Stage dialog) {
        int ROWS = 3;
        int COLLUMNS = 12;
        Node[][] nodeArray = new Node[ROWS][COLLUMNS];
        MatrixPane matrix = new MatrixPane();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLLUMNS; j++) {
                if (i == 1 && j == 0) {
                    Text txt = new Text("[(x  -");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 1) {
                    hEllipTxt = new TextField("25");
                    hEllipTxt.setStyle("-fx-font-size:15");
                    hEllipTxt.setTranslateY(9);
                    hEllipTxt.setPrefHeight(5);
                    hEllipTxt.setPrefWidth(47);
                    hEllipTxt.setUserData("h");
                    nodeArray[i][j] = hEllipTxt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 2) {
                    Text txt = new Text(")^2]/");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 3) {
                    aRadiusTxt = new TextField("c");
                    aRadiusTxt.setStyle("-fx-font-size:15");
                    aRadiusTxt.setTranslateY(9);
                    aRadiusTxt.setPrefHeight(5);
                    aRadiusTxt.setPrefWidth(47);
                    aRadiusTxt.setUserData("a");
                    nodeArray[i][j] = aRadiusTxt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 4) {
                    Text txt = new Text("  +  ");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 5) {
                    Text txt = new Text("[(y  - ");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 6) {
                    kEllipTxt = new TextField("25");
                    kEllipTxt.setStyle("-fx-font-size:15");
                    kEllipTxt.setTranslateY(9);
                    kEllipTxt.setPrefHeight(5);
                    kEllipTxt.setPrefWidth(47);
                    kEllipTxt.setUserData("k");
                    nodeArray[i][j] = kEllipTxt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 7) {
                    Text txt = new Text(")^2]/");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 8) {
                    bRadiusTxt = new TextField("c");
                    bRadiusTxt.setStyle("-fx-font-size:15");
                    bRadiusTxt.setTranslateY(9);
                    bRadiusTxt.setPrefHeight(5);
                    bRadiusTxt.setPrefWidth(47);
                    bRadiusTxt.setUserData("b");
                    nodeArray[i][j] = bRadiusTxt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 9) {
                    Text txt = new Text("   =  1");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
            }

        }
        matrix.setPadding(new Insets(7, 7, 12, 7));

        HBox cHolder = new HBox();
        TextField cMin = new TextField("-10");
        Text cRangeLabel = new Text("  <=  C  >=  ");
        cRangeLabel.setFont(new Font(15));
        TextField cMax = new TextField("10");
        cHolder.getChildren().addAll(cMin, cRangeLabel, cMax);

        HBox afinHolder = new HBox();
        afinHolder.setSpacing(7);
        Text afinLabel = new Text("Afinador");
        TextField afinField = new TextField("1");
        afinHolder.getChildren().addAll(afinLabel, afinField);

        if (curvaInf == null) {
            curvaInf = new CurvaDatos();
        } else {
            curvaInf.setTipo("ellip");
            afinField.setText(String.valueOf(curvaInf.getAfinador()));
            hEllipTxt.setText(String.valueOf(curvaInf.getH()));
            kEllipTxt.setText(String.valueOf(curvaInf.getK()));
            cMin.setText(String.valueOf(curvaInf.getcMin()));
            cMax.setText(String.valueOf(curvaInf.getcMax()));
            aRadiusTxt.setText(String.valueOf(curvaInf.getaRadius()));
            bRadiusTxt.setText(String.valueOf(curvaInf.getbRadius()));
        }

        Button procesar = new Button("PROCESAR");
        procesar.setPadding(new Insets(7));
        procesar.setOnMouseClicked(a -> {
            ellipseNivelCircleProccessInfo(cMin, cMax, dialog, afinField, Double.parseDouble(hEllipTxt.getText()), Double.parseDouble(kEllipTxt.getText()), aRadiusTxt.getText(), bRadiusTxt.getText());
        });

        VBox contentCircle = new VBox();
        contentCircle.setPadding(new Insets(10));
        contentCircle.setSpacing(7);
        contentCircle.getChildren().addAll(matrix, cHolder, procesar, afinHolder);
        return contentCircle;
    }

    private VBox ellipseContent2(Stage dialog) {
        int ROWS = 3;
        int COLLUMNS = 12;
        Node[][] nodeArray = new Node[ROWS][COLLUMNS];
        MatrixPane matrix = new MatrixPane();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLLUMNS; j++) {
                if (i == 1 && j == 0) {
                    Text txt = new Text("[(x  -");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 1) {
                    hEllipTxtCuadri = new TextField("25");
                    hEllipTxtCuadri.setStyle("-fx-font-size:15");
                    hEllipTxtCuadri.setTranslateY(9);
                    hEllipTxtCuadri.setPrefHeight(5);
                    hEllipTxtCuadri.setPrefWidth(47);
                    hEllipTxtCuadri.setUserData("h");
                    nodeArray[i][j] = hEllipTxtCuadri;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 2) {
                    Text txt = new Text(")^2]/");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 3) {
                    aRadiusTxtCuadri = new TextField("1");
                    aRadiusTxtCuadri.setStyle("-fx-font-size:15");
                    aRadiusTxtCuadri.setTranslateY(9);
                    aRadiusTxtCuadri.setPrefHeight(5);
                    aRadiusTxtCuadri.setPrefWidth(47);
                    aRadiusTxtCuadri.setUserData("a");
                    nodeArray[i][j] = aRadiusTxtCuadri;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 4) {
                    Text txt = new Text("  +  ");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 5) {
                    Text txt = new Text("[(y  - ");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 6) {
                    kEllipTxtCuadri = new TextField("25");
                    kEllipTxtCuadri.setStyle("-fx-font-size:15");
                    kEllipTxtCuadri.setTranslateY(9);
                    kEllipTxtCuadri.setPrefHeight(5);
                    kEllipTxtCuadri.setPrefWidth(47);
                    kEllipTxtCuadri.setUserData("k");
                    nodeArray[i][j] = kEllipTxtCuadri;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 7) {
                    Text txt = new Text(")^2]/");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 8) {
                    bRadiusTxtCuadri = new TextField("1");
                    bRadiusTxtCuadri.setStyle("-fx-font-size:15");
                    bRadiusTxtCuadri.setTranslateY(9);
                    bRadiusTxtCuadri.setPrefHeight(5);
                    bRadiusTxtCuadri.setPrefWidth(47);
                    bRadiusTxtCuadri.setUserData("b");
                    nodeArray[i][j] = bRadiusTxtCuadri;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 9) {
                    Text txt = new Text("   =  1");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
            }

        }
        matrix.setPadding(new Insets(7, 7, 12, 7));

        HBox afinHolder = new HBox();
        afinHolder.setSpacing(7);
        Text afinLabel = new Text("Afinador");
        TextField afinField = new TextField("1");
        afinHolder.getChildren().addAll(afinLabel, afinField);

        TextField ampField = new TextField("1");
        afinHolder.getChildren().addAll(new Text("amplificador"), ampField);

        if (curvaInf2 == null) {
            curvaInf2 = new CurvaDatos();
        } else {
            curvaInf2.setTipo("ellip");
            afinField.setText(String.valueOf(curvaInf2.getAfinador()));
            hEllipTxtCuadri.setText(String.valueOf(curvaInf2.getH()));
            kEllipTxtCuadri.setText(String.valueOf(curvaInf2.getK()));

            aRadiusTxtCuadri.setText(String.valueOf(curvaInf2.getaRadius()));
            bRadiusTxtCuadri.setText(String.valueOf(curvaInf2.getbRadius()));
            ampField.setText(String.valueOf(curvaInf2.getAmplificador()));
        }

        Button procesar = new Button("PROCESAR");
        procesar.setPadding(new Insets(7));
        procesar.setOnMouseClicked(a -> {
            //private void ellipseNivelCircleCuadriProccessInfo(TextField afinField, double h, double k, double a, double b, TextField ampField) {
            ellipseNivelCircleCuadriProccessInfo(afinField, Double.parseDouble(hEllipTxtCuadri.getText()), Double.parseDouble(kEllipTxtCuadri.getText()), Double.parseDouble(aRadiusTxtCuadri.getText()), Double.parseDouble(bRadiusTxtCuadri.getText()), ampField);
            giveDepth(Double.parseDouble(afinField.getText()), Double.parseDouble(ampField.getText()));
        });

        VBox contentCircle = new VBox();
        contentCircle.setPadding(new Insets(10));
        contentCircle.setSpacing(7);
        contentCircle.getChildren().addAll(matrix, procesar, afinHolder);
        return contentCircle;
    }

    private VBox circleContent(Stage dialog) {
        int ROWS = 3;
        int COLLUMNS = 12;
        Node[][] nodeArray = new Node[ROWS][COLLUMNS];
        MatrixPane matrix = new MatrixPane();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLLUMNS; j++) {
                if (i == 1 && j == 0) {
                    Text txt = new Text("(x  -");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 1) {
                    hTxt = new TextField("25");
                    hTxt.setStyle("-fx-font-size:15");
                    hTxt.setTranslateY(9);
                    hTxt.setPrefHeight(5);
                    hTxt.setPrefWidth(47);
                    hTxt.setUserData("h");
                    nodeArray[i][j] = hTxt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 2) {
                    Text txt = new Text(")^2  +");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 3) {
                    Text txt = new Text("(y  -");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 4) {
                    kTxt = new TextField("25");
                    kTxt.setStyle("-fx-font-size:15");
                    kTxt.setTranslateY(9);
                    kTxt.setPrefHeight(5);
                    kTxt.setPrefWidth(47);
                    kTxt.setUserData("k");
                    nodeArray[i][j] = kTxt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 5) {
                    Text txt = new Text(")^2=");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 6) {
                    txtRadio = new TextField("c");
                    txtRadio.setStyle("-fx-font-size:15");
                    txtRadio.setTranslateY(9);
                    txtRadio.setPrefHeight(5);
                    txtRadio.setPrefWidth(47);
                    txtRadio.setUserData("r");
                    nodeArray[i][j] = txtRadio;
                    matrix.add(nodeArray[i][j], j, i);
                }
            }
        }
        matrix.setPadding(new Insets(7, 7, 12, 7));

        HBox cHolder = new HBox();
        TextField cMin = new TextField("-10");
        Text cRangeLabel = new Text("  <=  C  >=  ");
        cRangeLabel.setFont(new Font(15));
        TextField cMax = new TextField("10");
        cHolder.getChildren().addAll(cMin, cRangeLabel, cMax);

        HBox afinHolder = new HBox();
        afinHolder.setSpacing(7);
        Text afinLabel = new Text("Afinador");
        TextField afinField = new TextField("1");
        afinHolder.getChildren().addAll(afinLabel, afinField);

        if (curvaInf == null) {
            curvaInf = new CurvaDatos();
        } else {
            curvaInf.setTipo("circle");
            afinField.setText(String.valueOf(curvaInf.getAfinador()));
            txtRadio.setText(String.valueOf(curvaInf.getEcuacion()));
            hTxt.setText(String.valueOf(curvaInf.getH()));
            kTxt.setText(String.valueOf(curvaInf.getK()));
            cMin.setText(String.valueOf(curvaInf.getcMin()));
            cMax.setText(String.valueOf(curvaInf.getcMax()));
        }

        Button procesar = new Button("PROCESAR");
        procesar.setPadding(new Insets(7));
        procesar.setOnMouseClicked(a -> {
            curvasNivelCircleProccessInfo(cMin, cMax, dialog, afinField, txtRadio.getText(), Double.parseDouble(hTxt.getText()), Double.parseDouble(kTxt.getText()));
        });

        VBox contentCircle = new VBox();
        contentCircle.setPadding(new Insets(10));
        contentCircle.setSpacing(7);
        contentCircle.getChildren().addAll(matrix, cHolder, procesar, afinHolder);
        return contentCircle;
    }

    private VBox circleContent2(Stage dialog) {
        int ROWS = 3;
        int COLLUMNS = 12;
        Node[][] nodeArray = new Node[ROWS][COLLUMNS];
        MatrixPane matrix = new MatrixPane();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLLUMNS; j++) {
                if (i == 1 && j == 0) {
                    Text txt = new Text("(x  -");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 1) {
                    hTxt = new TextField("25");
                    hTxt.setStyle("-fx-font-size:15");
                    hTxt.setTranslateY(9);
                    hTxt.setPrefHeight(5);
                    hTxt.setPrefWidth(47);
                    hTxt.setUserData("h");
                    nodeArray[i][j] = hTxt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 2) {
                    Text txt = new Text(")^2  +");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 3) {
                    Text txt = new Text("(y  -");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 4) {
                    kTxt = new TextField("25");
                    kTxt.setStyle("-fx-font-size:15");
                    kTxt.setTranslateY(9);
                    kTxt.setPrefHeight(5);
                    kTxt.setPrefWidth(47);
                    kTxt.setUserData("k");
                    nodeArray[i][j] = kTxt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 5) {
                    Text txt = new Text(")^2=");
                    txt.setStyle("-fx-font-size:25");
                    txt.setTranslateY(10);
                    nodeArray[i][j] = txt;
                    matrix.add(nodeArray[i][j], j, i);
                }
                if (i == 1 && j == 6) {
                    txtRadio = new TextField("c");
                    txtRadio.setStyle("-fx-font-size:15");
                    txtRadio.setTranslateY(9);
                    txtRadio.setPrefHeight(5);
                    txtRadio.setPrefWidth(47);
                    txtRadio.setUserData("r");
                    nodeArray[i][j] = txtRadio;
                    matrix.add(nodeArray[i][j], j, i);
                }
            }
        }
        matrix.setPadding(new Insets(7, 7, 12, 7));

        HBox cHolder = new HBox();
        TextField cMin = new TextField("-10");
        Text cRangeLabel = new Text("  <=  C  >=  ");
        cRangeLabel.setFont(new Font(15));
        TextField cMax = new TextField("10");
        cHolder.getChildren().addAll(cMin, cRangeLabel, cMax);

        HBox afinHolder = new HBox();
        afinHolder.setSpacing(7);
        Text afinLabel = new Text("Afinador");
        TextField afinField = new TextField("1");
        afinHolder.getChildren().addAll(afinLabel, afinField);

        if (curvaInf == null) {
            curvaInf = new CurvaDatos();
        } else {
            curvaInf.setTipo("circle");
            afinField.setText(String.valueOf(curvaInf.getAfinador()));
            txtRadio.setText(String.valueOf(curvaInf.getEcuacion()));
            hTxt.setText(String.valueOf(curvaInf.getH()));
            kTxt.setText(String.valueOf(curvaInf.getK()));
            cMin.setText(String.valueOf(curvaInf.getcMin()));
            cMax.setText(String.valueOf(curvaInf.getcMax()));
        }

        Button procesar = new Button("PROCESAR");
        procesar.setPadding(new Insets(7));
        procesar.setOnMouseClicked(a -> {
            curvasNivelCircleProccessInfo(cMin, cMax, dialog, afinField, txtRadio.getText(), Double.parseDouble(hTxt.getText()), Double.parseDouble(kTxt.getText()));
        });

        VBox contentCircle = new VBox();
        contentCircle.setPadding(new Insets(10));
        contentCircle.setSpacing(7);
        contentCircle.getChildren().addAll(matrix, cHolder, procesar, afinHolder);
        return contentCircle;
    }

    private VBox otroContent(Stage dialog) {
        Text topLabel = new Text("Escribe ecuacion con la forma: ");
        Text formExampleLabel = new Text("F(Y) = X + C");

        TextField equationField = new TextField();
        equationField.setFocusTraversable(false);
        equationField.setPromptText("escribe ecuacion aqui....");

        HBox xHolder = new HBox();
        TextField xMin = new TextField("-50");

        Text xRangeLabel = new Text("  <=  X  >=  ");
        xRangeLabel.setFont(new Font(15));

        TextField xMax = new TextField("50");
        xHolder.getChildren().addAll(xMin, xRangeLabel, xMax);

        HBox cHolder = new HBox();
        TextField cMin = new TextField("-10");

        Text cRangeLabel = new Text("  <=  C  >=  ");
        cRangeLabel.setFont(new Font(15));

        TextField cMax = new TextField("10");
        cHolder.getChildren().addAll(cMin, cRangeLabel, cMax);

        HBox afinHolder = new HBox();
        afinHolder.setSpacing(7);
        Text afinLabel = new Text("Afinador");
        TextField afinField = new TextField("100");
        afinHolder.getChildren().addAll(afinLabel, afinField);

        if (curvaInf != null) {
            xMin.setText(String.valueOf(curvaInf.getxMin()));
            xMax.setText(String.valueOf(curvaInf.getxMax()));
            cMin.setText(String.valueOf(curvaInf.getcMin()));
            cMax.setText(String.valueOf(curvaInf.getcMax()));
            equationField.setText(curvaInf.getEcuacion());
            afinField.setText(String.valueOf(curvaInf.getAfinador()));
            curvaInf.setTipo("otro");
        }

        Button procesar = new Button("PROCESAR");
        procesar.setPadding(new Insets(7));
        procesar.setOnMouseClicked(a -> {
            curvasNivelProccessInfo(xMin, xMax, cMin, cMax, dialog, equationField, afinField);
        });

        VBox mainContent = new VBox();
        mainContent.setSpacing(7);
        mainContent.getChildren().addAll(topLabel, formExampleLabel, equationField, xHolder, cHolder, procesar, afinHolder);
        return mainContent;
    }

    private VBox otroContent2(Stage dialog) {
        Text topLabel = new Text("Escribe ecuacion con la forma: ");
        Text formExampleLabel = new Text("F(Y) = X ");

        TextField equationField = new TextField();
        equationField.setFocusTraversable(false);
        equationField.setPromptText("escribe ecuacion aqui....");

        HBox xHolder = new HBox();
        TextField xMin = new TextField("-50");

        Text xRangeLabel = new Text("  <=  X  >=  ");
        xRangeLabel.setFont(new Font(15));

        TextField xMax = new TextField("50");
        xHolder.getChildren().addAll(xMin, xRangeLabel, xMax);

        HBox afinHolder = new HBox();
        afinHolder.setSpacing(7);
        Text afinLabel = new Text("Afinador");
        TextField afinField = new TextField("100");
        afinHolder.getChildren().addAll(afinLabel, afinField);

        HBox ampHolder = new HBox();
        ampHolder.setSpacing(7);
        ampHolder.getChildren().add(new Text("amplificador"));

        TextField ampField = new TextField("1");
        ampHolder.getChildren().add(ampField);

        if (curvaInf2 != null) {
            xMin.setText(String.valueOf(curvaInf2.getxMin()));
            xMax.setText(String.valueOf(curvaInf2.getxMax()));
            equationField.setText(curvaInf2.getEcuacion());
            afinField.setText(String.valueOf(curvaInf2.getAfinador()));
            curvaInf2.setTipo("otro");
            ampField.setText(String.valueOf(curvaInf2.getAmplificador()));
        }

        Button procesar = new Button("PROCESAR");
        procesar.setPadding(new Insets(7));
        procesar.setOnMouseClicked(a -> {
            otroCilindricasProccessInfo(xMin, xMax, dialog, equationField, afinField, ampField);
            giveDepth(Double.parseDouble(afinField.getText()), Double.parseDouble(ampField.getText()));
            /* if (comboBox.getSelectionModel().getSelectedItem() != null) {
                System.out.println("dar altura");
              
            } else {
              
                System.out.println("draw curve");
            }*/
        });

        VBox mainContent = new VBox();
        mainContent.setSpacing(7);
        mainContent.getChildren().addAll(topLabel, formExampleLabel, equationField, xHolder, procesar, afinHolder, ampHolder);
        return mainContent;
    }

    private void curvasNivelProccessInfo(TextField xMin, TextField xMax, TextField cMin, TextField cMax, Stage dialog, TextField equationField, TextField afinField) throws NumberFormatException {
        curvesInfo = new HashMap<>();
        int minX = Integer.parseInt(xMin.getText());
        int maxX = Integer.parseInt(xMax.getText());

        int minC = Integer.parseInt(cMin.getText());
        int maxC = Integer.parseInt(cMax.getText());

        String equation = equationField.getText();
        if (equation != null && equation.length() > 0) {
            for (int i = minC; i <= maxC; i++) {
                List<Punto2D> points = new ArrayList<>();
                boolean save = false;
                for (int j = minX; j <= maxX; j++) {

                    Punto2D punto = null;

                    Expression e = new ExpressionBuilder(equation)
                            .variables("x", "c")
                            .build()
                            .setVariable("c", i)
                            .setVariable("x", j); //.setVariable("y", 3.14);
                    double result = e.evaluate();
                    if (Double.isNaN(result)) {
                        System.out.println("error"); //deten calculos con esta constante
                        save = true;
                        continue;
                    } else {
                        punto = new Punto2D(j, result);
                        points.add(punto);
                    }

                    if (punto != null) {
                        System.out.println("constante: " + i + "  valor en x: " + punto.getX_Point() + "  result:  " + punto.getY_Point());
                    }

                }
                if (!save) {
                    curvesInfo.put(i, points);
                } else {
                    System.out.println("una lista no se guardo");
                }
            }
            surf.destroySurface();
            if (equationPane.isVisible()) {
                equationPane.setVisible(false);
            }
            curvaInf = new CurvaDatos(equationField.getText(), Integer.parseInt(xMin.getText()), Integer.parseInt(xMax.getText()),
                    Integer.parseInt(cMin.getText()), Integer.parseInt(cMax.getText()), Double.parseDouble(afinField.getText()));
            curvaInf.setTipo("otro");

            drawCurvesPoints(Double.parseDouble(afinField.getText()));
        }

    }

    private GridPane paneOptions(String[][] surfaceTags) {

        GridPane pane = new GridPane();
        pane.setHgap(7);
        pane.setVgap(7);
        pane.setPadding(new Insets(20));

        for (int i = 0; i < surfaceTags.length; i++) {//2
            for (int j = 0; j < surfaceTags[i].length; j++) {//3
                final String tag = surfaceTags[i][j];
                if (!tag.equals("NA"));
                {
                    Button btn = new Button(surfaceTags[i][j]);

                    btn.setUserData(tag);
                    btn.setOnMouseClicked(d -> {
                        switch (tag) {
                            case "Elipsoide":
                                changeWindownTitle(tag);
                                destroyLink();
                                switchEquation(tag);
                                switchSurface(tag);
                                menuPane.hide();
                                break;
                            case "Hiperboloide una hoja":
                                changeWindownTitle(tag);
                                destroyLink();
                                switchEquation(tag);
                                switchSurface(tag);
                                menuPane.hide();
                                break;
                            case "Hiperboloide dos hojas":
                                changeWindownTitle(tag);
                                destroyLink();
                                switchEquation(tag);
                                switchSurface(tag);
                                menuPane.hide();
                                break;
                            case "Cono eliptico":
                                changeWindownTitle(tag);
                                destroyLink();
                                switchEquation(tag);
                                switchSurface(tag);
                                menuPane.hide();
                                break;
                            case "Paraboloide eliptico":
                                changeWindownTitle(tag);
                                destroyLink();
                                switchEquation(tag);
                                switchSurface(tag);
                                menuPane.hide();
                                break;
                            case "Paraboloide hiperbolico":
                                changeWindownTitle(tag);
                                destroyLink();
                                switchEquation(tag);
                                switchSurface(tag);
                                menuPane.hide();
                                break;
                            //"Torus", "Cilindro"
                            case "Torus":
                                changeWindownTitle(tag);
                                destroyLink();
                                switchEquation(tag);
                                switchSurface(tag);
                                menuPane.hide();
                                break;
                            case "Cilindro":
                                changeWindownTitle(tag);
                                destroyLink();
                                switchEquation(tag);
                                switchSurface(tag);
                                menuPane.hide();
                                break;
                        }
                    });
                    btn.setPrefWidth(270);
                    btn.setStyle("-fx-font-size:20");
                    btn.setPadding(new Insets(25));
                    pane.add(btn, j, i);
                }
            }
        }
        return pane;
    }

    private void switchEquation(String tag) {
        if (!equationPane.isVisible()) {
            equationPane.setVisible(true);
        }
        equationPane.getChildren().clear();
        matrix = new TextMatrix(tag).getMatrix();
        equationPane.getChildren().add(matrix);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void sombrero() {

        //int size = 10;
        //float[][] arrayY = new float[2 * size][2 * size];
        //The Sombrero
        /* for (int i = -size; i < size; i++) {
            System.out.println("i: " + i);
            for (int j = -size; j < size; j++) {
                System.out.println("j: " + j);
                double R = Math.sqrt((i * i) + (j * j)) + 0.00000000000000001;
                arrayY[i + size][j + size] = ((float) -(Math.sin(R) / R)) * 100;
            }
        }*/

 /*for (int i = -size; i < size; i++) {
            for (int j = -size; j < size; j++) {
                //Transcedental Gradient
                double xterm = Math.pow((Math.cos(Math.PI * i / size)), 2);
                double yterm = Math.pow((Math.cos(Math.PI * j / size)), 2);
                arrayY[i + size][j + size] = (float) (15 * (Math.pow((xterm + yterm), 2)));

            }
        }*/
    }

    private void switchSurface(String tag) {
        surf.changeSurface(tag, matrix.getPropertyList());
    }

    private void destroyLink() {

    }

    private void changeWindownTitle(String string) {
        title.set("GraphPlot " + string);
    }

    private void drawCurvesPoints(double afinador) {
        surf.drawCurves(curvesInfo, afinador);
    }

    private void curvasNivelCircleProccessInfo(TextField cMin, TextField cMax, Stage dialog, TextField afinField, String cExpr, double h, double k) {
        curvesInfo = new HashMap<>();

        int minC = Integer.parseInt(cMin.getText());
        int maxC = Integer.parseInt(cMax.getText());

        int radiusDivisions = 30;/// ESTE ES 20
        int tubeDivisions = 12;//12

        for (int i = minC; i <= maxC; i++) {
            List<Punto2D> points = new ArrayList<>();
            boolean save = false;
            for (int radiusIndex = -radiusDivisions; radiusIndex < radiusDivisions; radiusIndex++) {

                Punto2D punto = null;

                double u = 0.2617 * radiusIndex + 3.1403;

                Expression e = new ExpressionBuilder(cExpr)
                        .variables("c")
                        .build()
                        .setVariable("c", i); //.setVariable("y", 3.14);
                double result = e.evaluate();

                double r = Math.sqrt(result);
                double x = h + (r * Math.cos(u));
                double y = k + (r * Math.sin(u));

                if (Double.isNaN(x) || Double.isNaN(y)) {
                    System.out.println("error"); //deten calculos con esta constante
                    save = true;
                    continue;
                } else {
                    punto = new Punto2D(x, y);
                    points.add(punto);
                }

                if (punto != null) {
                    System.out.println("constante: " + i + "  valor en x: " + punto.getX_Point() + "  result:  " + punto.getY_Point());
                }

            }
            if (!save) {
                curvesInfo.put(i, points);
            } else {
                System.out.println("una lista no se guardo");
            }
        }
        surf.destroySurface();
        if (equationPane.isVisible()) {
            equationPane.setVisible(false);
        }
        curvaInf = new CurvaDatos(Integer.parseInt(cMin.getText()), Integer.parseInt(cMax.getText()), Double.parseDouble(afinField.getText()), h, k);
        curvaInf.setTipo("circle");
        curvaInf.setEcuacion(cExpr);

        drawCircleCurvesPoints(Double.parseDouble(afinField.getText()));

    }

    private void drawCircleCurvesPoints(double parseDouble) {
        surf.drawCurves(curvesInfo, parseDouble);
    }

    private void ellipseNivelCircleProccessInfo(TextField cMin, TextField cMax, Stage dialog, TextField afinField, double h, double k, String aRadius, String bRadius) {
        curvesInfo = new HashMap<>();

        int minC = Integer.parseInt(cMin.getText());
        int maxC = Integer.parseInt(cMax.getText());

        int radiusDivisions = 30;/// ESTE ES 20
        int tubeDivisions = 12;//12

        for (int i = minC; i <= maxC; i++) {
            List<Punto2D> points = new ArrayList<>();
            boolean save = false;
            for (int radiusIndex = -radiusDivisions; radiusIndex < radiusDivisions; radiusIndex++) {

                Punto2D punto = null;

                Expression e = new ExpressionBuilder(aRadius)
                        .variables("c")
                        .build()
                        .setVariable("c", i); //.setVariable("y", 3.14);
                double resultAradius = e.evaluate();

                Expression e2 = new ExpressionBuilder(bRadius)
                        .variables("c")
                        .build()
                        .setVariable("c", i); //.setVariable("y", 3.14);
                double resultBradius = e2.evaluate();

                double u = 0.2617 * radiusIndex + 3.1403;

                double aR = Math.sqrt(resultAradius + i);
                double bR = Math.sqrt(resultBradius + i);
                double x = h + (aR * Math.cos(u));
                double y = k + (bR * Math.sin(u));

                if (Double.isNaN(x) || Double.isNaN(y)) {
                    System.out.println("error"); //deten calculos con esta constante
                    save = true;
                    continue;
                } else {
                    punto = new Punto2D(x, y);
                    points.add(punto);
                }

                if (punto != null) {
                    System.out.println("constante: " + i + "  valor en x: " + punto.getX_Point() + "  result:  " + punto.getY_Point());
                }

            }
            if (!save) {
                curvesInfo.put(i, points);
            } else {
                System.out.println("una lista no se guardo");
            }
        }
        surf.destroySurface();
        if (equationPane.isVisible()) {
            equationPane.setVisible(false);
        }
        curvaInf = new CurvaDatos(Integer.parseInt(cMin.getText()), Integer.parseInt(cMax.getText()), Double.parseDouble(afinField.getText()), h, k, aRadius, bRadius);
        curvaInf.setTipo("ellip");

        drawCircleCurvesPoints(Double.parseDouble(afinField.getText()));

    }

    private void cilindricasWindown() {
        Stage dialog = new Stage();
        VBox rootCurvaNivel = new VBox();
        rootCurvaNivel.setPadding(new Insets(12));
        rootCurvaNivel.setSpacing(7);

        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton otroBtn = new RadioButton("otro");
        otroBtn.setUserData("other");
        //otroBtn.setSelected(true);
        otroBtn.setToggleGroup(toggleGroup);

        /*RadioButton circuloBtn = new RadioButton("circulo");
        circuloBtn.setUserData("circle");
        circuloBtn.setToggleGroup(toggleGroup);*/
        RadioButton ellipseBtn = new RadioButton("ellipse");
        ellipseBtn.setUserData("ellip");
        ellipseBtn.setToggleGroup(toggleGroup);

        HBox radioHolder = new HBox();
        radioHolder.setSpacing(7);
        radioHolder.getChildren().addAll(otroBtn, ellipseBtn);

        rootCurvaNivel.getChildren().addAll(radioHolder);

        toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (rootCurvaNivel.getChildren().size() == 2) {
                rootCurvaNivel.getChildren().remove(rootCurvaNivel.getChildren().size() - 1);

                switch (newValue.getUserData().toString()) {
                    case "other":
                        rootCurvaNivel.getChildren().add(otroContent2(dialog));
                        break;
                    case "circle":
                        rootCurvaNivel.getChildren().add(circleContent2(dialog));
                        break;
                    case "ellip":
                        ellipseBtn.setSelected(true);
                        rootCurvaNivel.getChildren().add(ellipseContent2(dialog));
                        break;
                }
            }
        });

        if (curvaInf2 != null) {
            switch (curvaInf2.getTipo()) {
                case "otro":
                    otroBtn.setSelected(true);
                    rootCurvaNivel.getChildren().add(otroContent2(dialog));
                    break;
                case "circle":
                    //circuloBtn.setSelected(true);
                    //rootCurvaNivel.getChildren().add(circleContent2(dialog));
                    break;
                case "ellip":
                    ellipseBtn.setSelected(true);
                    rootCurvaNivel.getChildren().add(ellipseContent2(dialog));
                    break;
            }
        } else {
            otroBtn.setSelected(true);
            rootCurvaNivel.getChildren().add(otroContent2(dialog));
        }
        dialog.setScene(new Scene(rootCurvaNivel));
        dialog.setHeight(450);
        dialog.setWidth(500);
        dialog.setTitle("Escribe datos");
        dialog.setResizable(false);
        dialog.initOwner(stageRef);
        dialog.showAndWait();
    }

    private void otroCilindricasProccessInfo(TextField xMin, TextField xMax, Stage dialog, TextField equationField, TextField afinField, TextField ampField) {
        //curvesInfo2 = new HashMap<>();
        int minX = Integer.parseInt(xMin.getText());
        int maxX = Integer.parseInt(xMax.getText());

        String equation = equationField.getText();
        if (equation != null && equation.length() > 0) {

            pointsCilindricas = new ArrayList<>();
            boolean save = false;
            for (int j = minX; j <= maxX; j++) {

                Punto2D punto = null;

                Expression e = new ExpressionBuilder(equation)
                        .variables("x")
                        .build()
                        .setVariable("x", j); //.setVariable("y", 3.14);
                double result = e.evaluate();
                if (Double.isNaN(result)) {
                    System.out.println("error"); //deten calculos con esta constante
                    save = true;
                    continue;
                } else {
                    punto = new Punto2D(j, result);
                    pointsCilindricas.add(punto);
                }

                if (punto != null) {
                    System.out.println("  valor en x: " + punto.getX_Point() + "  result:  " + punto.getY_Point());
                }
            }

            surf.destroySurface();
            if (equationPane.isVisible()) {
                equationPane.setVisible(false);
            }
            curvaInf2 = new CurvaDatos(equationField.getText(), Integer.parseInt(xMin.getText()), Integer.parseInt(xMax.getText()), Double.parseDouble(afinField.getText()));
            curvaInf2.setTipo("otro");
            curvaInf2.setAmplificador(Double.parseDouble(ampField.getText()));

            drawShape(Double.parseDouble(afinField.getText()), Double.parseDouble(ampField.getText()));
        }

    }

    private void drawShape(double afinador, double amplificador) {
        surf.drawShape(pointsCilindricas, afinador, amplificador);
    }

    private void giveDepth(double afinador, double amplificador) {
        surf.addDepth(pointsCilindricas, afinador, amplificador);
    }
//ellipseNivelCircleCuadriProccessInfo(afinField, Double.parseDouble(hEllipTxtCuadri.getText()), Double.parseDouble(kEllipTxt.getText()), aRadiusTxt.getText(), bRadiusTxt.getText(),ampField);

    private void ellipseNivelCircleCuadriProccessInfo(TextField afinField, double h, double k, double a, double b, TextField ampField) {

        int radiusDivisions = 30;/// ESTE ES 20
        int tubeDivisions = 12;//12

        pointsCilindricas = new ArrayList<>();
        boolean save = false;
        for (int radiusIndex = -radiusDivisions; radiusIndex < radiusDivisions; radiusIndex++) {

            Punto2D punto = null;

            double u = 0.2617 * radiusIndex + 3.1403;

            double aR = Math.sqrt(a);
            double bR = Math.sqrt(b);
            double x = h + (aR * Math.cos(u));
            double y = k + (bR * Math.sin(u));

            if (Double.isNaN(x) || Double.isNaN(y)) {
                System.out.println("error"); //deten calculos con esta constante
                save = true;
                continue;
            } else {
                punto = new Punto2D(x, y);
                pointsCilindricas.add(punto);
            }

            if (punto != null) {
                System.out.println("  valor en x: " + punto.getX_Point() + "  result:  " + punto.getY_Point());
            }

        }
        surf.destroySurface();
        if (equationPane.isVisible()) {
            equationPane.setVisible(false);
        }
        curvaInf2 = new CurvaDatos(h, k, Math.sqrt(a), Math.sqrt(b));
        curvaInf2.setaRadius(aRadiusTxt.getText());
        curvaInf2.setbRadius(bRadiusTxt.getText());
        curvaInf2.setAmplificador(Double.parseDouble(ampField.getText()));
        curvaInf2.setTipo("ellip");

        drawShape(Double.parseDouble(afinField.getText()), Double.parseDouble(ampField.getText()));
    }

}
