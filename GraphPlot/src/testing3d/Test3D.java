/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing3d;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.fxyz.shapes.composites.SurfacePlot;
import testing3d.camera.ContentModel;
import testing3d.util.HiddenPane;
import testing3d.equation.MatrixPane;
import testing3d.util.SurfaceManager;
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
    private double axesSize = 1000;
    private boolean normalized = false;
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

    /*    float[] arrayX = new float[2 * size];
    float[][] arrayY = new float[2 * size][2 * size];
    float[][] arrayZ = new float[2 * size][2 * size];*/
    float[] arrayXN = new float[2 * size];
    float[] arrayYN = new float[2 * size];
    float[] arrayZN = new float[2 * size];

    // Matrix tool
    public final static int ROWS = 3;
    public final static int COLLUMNS = 8;
    public final Node[][] nodeArray = new Node[ROWS][COLLUMNS];
    private HiddenPane menuPane;

    private final Rectangle2D screen = Screen.getPrimary().getVisualBounds();

    final double plotWidth = screen.getWidth();
    final double plotHeight = 7 * screen.getHeight() / 8;
    private final StackPane mainPanel = new StackPane();

    private ContentModel content;
    private final VBox root = new VBox();

    Group innerGroup = new Group();// here i add "plot" group

    final String[][] surfaceTags = {{"Elipsoide", "Hiperboloide una hoja", "Hiperboloide dos hojas"}, {"Cono eliptico", "Paraboloide eliptico", "Paraboloide hiperbolico"}};
    private Pane equationPane;
    private MatrixPane matrix;
    private SurfaceManager surf;

    @Override
    public void start(Stage primaryStage) {

        initRoot();

        setUpTopMenu(screen, plotWidth, root);

        spaceAxis(plotWidth, plotHeight);
        slidePane(plotWidth, plotHeight);

        Scene scene = new Scene(root, plotWidth, screen.getHeight());
        primaryStage.setTitle("GraphPlot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initRoot() {
        mainPanel.setPrefHeight(plotHeight);
        mainPanel.setPrefWidth(plotWidth);
    }

    private void spaceAxis(final double plotWidth, final double plotHeight) {
        content = new ContentModel(plotWidth, plotHeight, 40);
        mainPanel.getChildren().add(content.getSubScene());

        double valX = plotWidth / 300;
        double resX = (valX * 300) - 300;

        double valY = plotHeight / 150;
        double resY = (valY * 150) - 150;

        equationPane = new Pane();
        equationPane.setStyle("-fx-background-color:rgba(95,158,160,0.5)");
        matrix = new TextMatrix("Hiperboloide dos hojas").getMatrix();
        equationPane.getChildren().add(matrix);

        SubScene scene = new SubScene(equationPane, 300, 150);
        scene.setTranslateX((-1 * resX / 2) * 0.95);
        scene.setTranslateY((-1 * resY / 2) * 0.9);

        VBox controlsPane = new VBox();
        controlsPane.setStyle("-fx-background-color:rgba(95,158,160,0.5)");
        Button plus = new Button();
        plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final double modifierFactor = 0.3;
                double xFlip = -1;
                double z = content.getCameraPosition().getZ();
                double newZ = z - xFlip * (1 + 1) * modifierFactor * 7;
                content.getCameraPosition().setZ(newZ);
            }
        });
        GlyphsDude.setIcon(plus, FontAwesomeIcon.PLUS, "2em");
        plus.applyCss();

        Button minus = new Button();
        minus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final double modifierFactor = 0.3;
                double xFlip = 1;
                double z = content.getCameraPosition().getZ();
                double newZ = z - xFlip * (1 + 1) * modifierFactor * 7;
                content.getCameraPosition().setZ(newZ);
            }
        });
        GlyphsDude.setIcon(minus, FontAwesomeIcon.MINUS, "2em");

        controlsPane.getChildren().add(plus);
        controlsPane.getChildren().add(minus);

        controlsPane.autosize();
        controlsPane.applyCss();
        SubScene controlsScene = new SubScene(controlsPane, plotWidth / 14, 4 * plotHeight / 12);

        controlsScene.setTranslateX(6.7 * plotWidth / 14);
        controlsScene.setTranslateY(5.7 * plotHeight / 12);

        mainPanel.getChildren().addAll(scene, controlsScene);

        surf = new SurfaceManager(plot);

        surf.changeSurface("Hiperboloide dos hojas", matrix.getPropertyList());
        innerGroup.getChildren().add(plot);

        content.setContent(innerGroup);

        root.getChildren().add(mainPanel);
    }

    private void slidePane(final double plotWidth, final double plotHeight) {
        menuPane = new HiddenPane(plotHeight);
        menuPane.setPrefWidth(plotWidth);
        menuPane.setStyle("-fx-background-color:rgba(0,100,0,0.7)");
        menuPane.setContent(paneOptions());
        mainPanel.getChildren().add(menuPane);
    }

    private void setUpTopMenu(Rectangle2D screen, final double plotWidth, VBox root) {
        FlowPane topPane = new FlowPane();
        topPane.setPadding(new Insets(12));
        topPane.setPrefHeight(1 * screen.getHeight() / 8);
        topPane.setPrefWidth(plotWidth);
        Button quadSurfaces = new Button("Cuadraticas");
        quadSurfaces.setPadding(new Insets(7));
        topPane.getChildren().add(quadSurfaces);

        // EVENTS
        quadSurfaces.setOnMouseClicked(d -> {

            if (menuPane.isHiddenProperty()) {
                menuPane.show();
            } else {
                menuPane.hide();
            }
        });
        // END EVENTS
        root.getChildren().add(topPane);

    }

    private GridPane paneOptions() {

        GridPane pane = new GridPane();
        pane.setHgap(7);
        pane.setVgap(7);
        pane.setPadding(new Insets(20));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button(surfaceTags[i][j]);
                final String tag = surfaceTags[i][j];
                btn.setUserData(tag);
                btn.setOnMouseClicked(d -> {
                    switch (tag) {
                        case "Elipsoide":
                            destroyLink();
                            switchEquation(tag);
                            switchSurface(tag);
                            menuPane.hide();
                            break;
                        case "Hiperboloide una hoja":
                            destroyLink();
                            switchEquation(tag);
                            switchSurface(tag);
                            menuPane.hide();
                            break;
                        case "Hiperboloide dos hojas":
                            destroyLink();
                            switchEquation(tag);
                            switchSurface(tag);
                            menuPane.hide();
                            break;
                        case "Cono eliptico":
                            destroyLink();
                            switchEquation(tag);
                            switchSurface(tag);
                            menuPane.hide();
                            break;
                        case "Paraboloide eliptico":
                            destroyLink();
                            switchEquation(tag);
                            switchSurface(tag);
                            menuPane.hide();
                            break;
                        case "Paraboloide hiperbolico":
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
        return pane;
    }

    private void switchEquation(String tag) {
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

    public void setHeightData(int spacing, Color color, boolean ambient, boolean fill) {

        /*plot = new Group() {
            private void init() {
                if (selfLightEnabled) {
                    getChildren().add(selfLight);
                }
                setDepthTest(DepthTest.ENABLE);
            }
        };*/
        material = new PhongMaterial();
        material.setSpecularColor(Color.RED);
        material.setDiffuseColor(Color.GREEN);

        mesh = new TriangleMesh();

        /////////////   CALCULATE POINTS
        for (int i = -size; i < size; i++) {
            for (int j = -size; j < size; j++) {

                for (int k = -size; k < size; k++) {

                    /*
                   x = (a + r cos u) cos v
                   y = (a + r cos u) sin v
                   z = r sin u
                   u ∈ (−π, π), v ∈ (0, 2π)*/
                    double a = 7;
                    double r = 4;
                    double R = 6;
                    //U = 0.1047x + 9E-17
                    double u = 0.1047 * i + (9 * Math.pow(10, -17));

                    //V = 0.0524x + 3.141
                    double v = 0.0524 * j + 3.141;

                    //Math.PI * i / size
                    double xterm = (a + r * Math.cos(u)) * Math.cos(v);
                    double yterm = (1) * (a + r * Math.cos(u)) * Math.sin(v);
                    double zterm = (1) * r * Math.sin(u);

                    System.out.println("xterm: " + u);

                    arrayX[i + size] = (float) (1 * xterm);
                    arrayY[i + size][j + size] = (float) (1 * yterm);
                    arrayZ[i + size][k + size] = (float) zterm;
                    /* arrayXN[i + size] = (float) (1 * xterm);
                    arrayYN[i + size] = (float) (1 * yterm);
                    arrayZN[i + size] = (float) zterm;*/
                }
            }
        }
        ////////////  END CALCULATE POINTS
        // Fill Points
        for (int x = 0; x < arrayY.length; x++) {
            for (int z = 0; z < arrayY[0].length; z++) {
                //mesh.getPoints().addAll(x * spacing, arrayY[x][z], z * spacing);
                mesh.getPoints().addAll(arrayX[x], arrayY[x][z], arrayZ[x][z]);
            }
        }

        //for now we'll just make an empty texCoordinate group
        mesh.getTexCoords().addAll(0, 0);
        for (int x = 0; x < arrayY.length; x++) {
            for (int z = 0; z < arrayY[0].length; z++) {
                //mesh.getPoints().addAll(x * spacing, arrayY[x][z], z * spacing);
                //mesh.getPoints().addAll(arrayX[x], arrayY[x][z], arrayZ[x][z]);
                Sphere sp = new Sphere(.05);
                sp.setMaterial(new PhongMaterial(Color.RED));
                sp.setTranslateX(arrayX[x]);
                sp.setTranslateY(arrayY[x][z]);
                sp.setTranslateZ(arrayZ[x][z]);
                /*sp.setTranslateX(arrayX[x]);
                sp.setTranslateY(arrayYN[x]);
                sp.setTranslateZ(arrayZN[x]);*/
                plot.getChildren().add(sp);
            }
        }

        /* int total = arrayY.length * arrayY.length;
        int nextRow = arrayY.length;
        //Add the faces "winding" the points generally counter clock wise
        for (int i = 0; i < total - nextRow - 1; i++) {
            //Top upper left triangle
            mesh.getFaces().addAll(i, 0, i + nextRow, 0, i + 1, 0);
            //Top lower right triangle
            mesh.getFaces().addAll(i + nextRow, 0, i + nextRow + 1, 0, i + 1, 0);

            //Bottom            
        }*/
        //Create a viewable MeshView to be added to the scene
        //To add a TriangleMesh to a 3D scene you need a MeshView container object
        meshView = new MeshView(mesh);
        //The MeshView allows you to control how the TriangleMesh is rendered
        if (fill) {
            meshView.setDrawMode(DrawMode.FILL);
        } else {
            meshView.setDrawMode(DrawMode.LINE); //show lines only by default
        }
        //meshView.setCullFace(CullFace.BACK); //Removing culling to show back lines

        meshView.setMaterial(material);
        plot.getChildren().add(meshView);
        if (ambient) {
            selfLight.getScope().add(meshView);
            if (!plot.getChildren().contains(selfLight)) {
                plot.getChildren().add(selfLight);
            }
        } else if (plot.getChildren().contains(selfLight)) {
            plot.getChildren().remove(selfLight);
        }
        //setDepthTest(DepthTest.ENABLE);
    }

    private void switchSurface(String tag) {
        surf.changeSurface(tag, matrix.getPropertyList());
    }

    private void destroyLink() {

    }

}
