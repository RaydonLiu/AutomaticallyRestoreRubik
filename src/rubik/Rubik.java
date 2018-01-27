package rubik;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javafx.scene.Group;
import javafx.scene.shape.MeshView;

public class Rubik {

	private Map<String, Cube> cubeModel = new HashMap<String, Cube>();

	private Group root = new Group();

	public Rubik() {
	}

	public void importCube(String cubeName, MeshView cubeMesh) {
		if (cubeModel.keySet().isEmpty() || !cubeModel.keySet().toString().contains(cubeName.toString())) {
			cubeModel.put(cubeName, new Cube());
		}
		cubeModel.get(cubeName).addCubeMesh(cubeMesh);
		cubeModel.get(cubeName).addCubeName(cubeName);
	}

	public Map<String, Cube> getCubeModel() {
		return cubeModel;
	}

	public Group getRoot() {
		Set<Entry<String, Cube>> sets = cubeModel.entrySet();
		for (Entry<String, Cube> entry : sets) {
			root.getChildren().add(entry.getValue().getRoot());
		}

		return root;
	}
}
