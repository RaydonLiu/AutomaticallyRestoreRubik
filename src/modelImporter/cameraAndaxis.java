package modelImporter;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import modelImporter.Xform;

public class cameraAndaxis {

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private double mouseDeltaX;
    private double mouseDeltaY;

    private Group root = new Group();
    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();
    private final Xform axisGroup = new Xform();
    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_INITIAL_X_ANGLE = 40.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 30.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;

    public cameraAndaxis() {
        builCamera();
        buildAxes();
//		hanleMouse();
    }

    public void builCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    private void buildAxes() {
        System.out.println("buildAxes()");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
        System.out.println(yAxis.getTranslateX());
        System.out.println(yAxis.getTranslateY());
        System.out.println(yAxis.getTranslateZ());
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);
        root.getChildren().addAll(axisGroup);
    }

    public Group getRoot() {
        return root;
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }

    public Xform getCameraXform() {
        return cameraXform;
    }

    public Xform getCameraXform2() {
        return cameraXform2;
    }

    public Xform getCameraXform3() {
        return cameraXform3;
    }

    public void cameraSetOnMousePressed(MouseEvent event) {
        mousePosX = event.getSceneX();
        mousePosY = event.getSceneY();
        mouseOldX = event.getSceneX();
        mouseOldY = event.getSceneY();
    }


    public void cameraSetOnMouseDragged(MouseEvent event) {
        mouseOldX = mousePosX;
        mouseOldY = mousePosY;
        mousePosX = event.getSceneX();
        mousePosY = event.getSceneY();
        mouseDeltaX = (mousePosX - mouseOldX);
        mouseDeltaY = (mousePosY - mouseOldY);
        double modifier = 5.0;
        if (event.isControlDown()) {
            modifier = CONTROL_MULTIPLIER;
        }
        if (event.isShiftDown()) {
            modifier = SHIFT_MULTIPLIER;
        }
        if (event.isPrimaryButtonDown()) {
            getCameraXform().ry.setAngle(
                    getCameraXform().ry.getAngle() - mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
            getCameraXform().rx.setAngle(
                    getCameraXform().rx.getAngle() + mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
        } else if (event.isSecondaryButtonDown()) {
            getCameraXform2().t
                    .setX(getCameraXform2().t.getX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
            getCameraXform2().t
                    .setY(getCameraXform2().t.getY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
        }
    }

    public void cameraSetOnScroll(ScrollEvent event) {
        double modifier = 5.0;
        double z = getCamera().getTranslateZ() - (event.getDeltaY() * 0.2);
        double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
        getCamera().setTranslateZ(newZ);
    }
}

//    public void hanleMouse(SubScene scene) {
//        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent me) {
//                mousePosX = me.getSceneX();
//                mousePosY = me.getSceneY();
//                mouseOldX = me.getSceneX();
//                mouseOldY = me.getSceneY();
//            }
//        });
//        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                 TODO Auto-generated method stub
//                mouseOldX = mousePosX;
//                mouseOldY = mousePosY;
//                mousePosX = event.getSceneX();
//                mousePosY = event.getSceneY();
//                mouseDeltaX = (mousePosX - mouseOldX);
//                mouseDeltaY = (mousePosY - mouseOldY);
//                double modifier = 5.0;
//                if (event.isControlDown()) {
//                    modifier = CONTROL_MULTIPLIER;
//                }
//                if (event.isShiftDown()) {
//                    modifier = SHIFT_MULTIPLIER;
//                }
//                if (event.isPrimaryButtonDown()) {
//                    getCameraXform().ry.setAngle(
//                            getCameraXform().ry.getAngle() - mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
//                    getCameraXform().rx.setAngle(
//                            getCameraXform().rx.getAngle() + mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
//                } else if (event.isSecondaryButtonDown()) {
//                    getCameraXform2().t
//                            .setX(getCameraXform2().t.getX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
//                    getCameraXform2().t
//                            .setY(getCameraXform2().t.getY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
//                }
//            }
//            });
//        scene.setOnScroll(new EventHandler<ScrollEvent>()
//
//            {
//
//                @Override
//                public void handle (ScrollEvent event){
//
//                double modifier = 5.0;
//                double z = getCamera().getTranslateZ() - (event.getDeltaY() * 0.2);
//                double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
//                getCamera().setTranslateZ(newZ);
//            }
//            });
//        }

