package rubik;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

public class Cube {
	
    private  Rotate rx = new Rotate(0, Rotate.X_AXIS);  
    private  Rotate ry = new Rotate(0, Rotate.Y_AXIS);  
    private  Rotate rz = new Rotate(0, Rotate.Z_AXIS);  
	private ArrayList<String> cubeName = new ArrayList<String>();
	private ArrayList<MeshView> cubeMesh = new ArrayList<MeshView>();
	private Group root = new Group();
	public Cube(){
		root.getTransforms().addAll(rz, ry, rx);  
	}
	
	public ArrayList<String> getCubeName(){
		return cubeName;
	}
	
	public ArrayList<MeshView> getCubeMesh(){
		return cubeMesh;
	}
	
	public void addCubeMesh(MeshView cubeMesh){
		this.cubeMesh.add(cubeMesh);
		root.getChildren().add(cubeMesh);
	}
	
	public void addCubeName(String cubeName){
		this.cubeName.add(cubeName);
	}
	
	public Group getRoot(){
		return root;
	}

	public Rotate getRx() {
		return rx;
	}

	public Rotate getRy() {
		return ry;
	}

	public Rotate getRz() {
		return rz;
	}
	public void setAllxyz(){
	    rx = new Rotate(0, Rotate.X_AXIS);  
	    ry = new Rotate(0, Rotate.Y_AXIS);  
	    rz = new Rotate(0, Rotate.Z_AXIS); 
		root.getTransforms().setAll(rz, ry, rx);
	}
}
