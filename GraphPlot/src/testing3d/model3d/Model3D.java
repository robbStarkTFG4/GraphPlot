package testing3d.model3d;

import com.javafx.experiments.importers.obj.ObjImporter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

/**
 *
 * @author jpereda, April 2014 - @JPeredaDnr
 */
public class Model3D {

    /*
    Cube.obj, downloaded from  http://tf3dm.com/3d-model/rubik39s-cube-79189.html from http://tf3dm.com/user/3dregenerator
    Contains 117 meshes, so each of the 27 cubies has 5 to 6 meshes.
    They are marked as "Block46", "Block46 (2)",...,"Block72 (6)" in this set:
     */
    private Set<String> meshes;
    /*
    HashMap to store a MeshView of each mesh with its key.
     */
    private final Map<String, MeshView> mapMeshes = new HashMap<>();

    public Model3D(String name) {
        importObj(name);
    }

    private void importObj(String name) {
        try {// cube.obj
            ObjImporter reader = new ObjImporter(getClass().getResource(name).toExternalForm());
            meshes = reader.getMeshes(); // set with the names of 117 meshes
            
            meshes.stream().forEach(s -> {
                MeshView cubiePart = reader.buildMeshView(s);
                PhongMaterial material = (PhongMaterial) cubiePart.getMaterial();
                material.setSpecularPower(1);
                cubiePart.setMaterial(material);
                mapMeshes.put(s, cubiePart);
            });
        } catch (IOException e) {
            System.out.println("Error loading model " + e.toString());
        }
    }

    public Map<String, MeshView> getMapMeshes() {
        return mapMeshes;
    }
}
