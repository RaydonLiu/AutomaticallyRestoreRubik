package modelImporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.javafx.experiments.importers.obj.*;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Affine;

public class ImportModel {
	private ObjImporter importer;
	private Set<String> meshes;
	private final Map<String, MeshView> mapMeshes = new HashMap<>();
	List<PhongMaterial> mapValuesSet = new ArrayList<PhongMaterial>();

	public ImportModel(URL modelUrl) {
		try {
			importer = new ObjImporter(modelUrl.toExternalForm());
			meshes = importer.getMeshes();
			System.out.println(meshes.size());
			for(String s : meshes)
			{
				MeshView cubiePart = importer.buildMeshView(s);
				Affine affineCube = new  Affine();
				cubiePart.getTransforms().add(affineCube);
				PhongMaterial material = (PhongMaterial) cubiePart.getMaterial();
				material.setSpecularPower(1);
				cubiePart.setMaterial(material);
				mapMeshes.put(s, cubiePart);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Map<String, MeshView> getMapMeshes() {
		return mapMeshes;
	}

}