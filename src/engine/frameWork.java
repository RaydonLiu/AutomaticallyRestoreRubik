package engine;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import modelImporter.ImportModel;
import modelImporter.cameraAndaxis;
import rubik.Cube;
import rubik.Rubik;

public class frameWork {

    private Group root = new Group();
    private ArrayList<Integer> original;
    private ArrayList<Integer> updates;
    private ArrayList<Integer> layer = new ArrayList<Integer>();
    private int[][][] cube = {{{46, 63, 72}, {53, 61, 70}, {52, 60, 69}},
            {{48, 56, 65}, {54, 62, 71}, {51, 55, 64}}, {{59, 47, 68}, {49, 57, 66}, {50, 58, 67}}};
    private int level = 3;
    private ImportModel modelImporter;
    private Map<String, MeshView> meshViews;
    private int[][][] stableCube ={{{46, 63, 72}, {53, 61, 70}, {52, 60, 69}},
            {{48, 56, 65}, {54, 62, 71}, {51, 55, 64}}, {{59, 47, 68}, {49, 57, 66}, {50, 58, 67}}};
    private int[][][] temp = new int[level][level][level];
    private double mouseOldX = 0;
    private double mouseOldY = 0;
    private double mouseOldZ = 0;
    private double mouseX = 0;
    private double mouseY = 0;
    private double mouseZ = 0;
    private final double mouseSensitivityMin = 10;
    private final double mouseSensitivityMax = 60;
    private final double mouseSensitivityDefault = 20;
    private double sensitivity = 20;
    private Point3D axis = new Point3D(0, 0, 0);
    private Rubik rubik;
    private cameraAndaxis cameraAndaxis;
    private autoMatically recover;
    private BooleanProperty canRot = new SimpleBooleanProperty(true);
    private Timer gameTime;
    private ArrayList<TimerAdapter> timerAdapters = new ArrayList<TimerAdapter>();
    private ArrayList<RubikRecoverAdapter> rubikRecoverAdapters = new ArrayList<RubikRecoverAdapter>();
    private long time;  //gameTimeShow
    private Timer Rubikrecover;
    private int disorganizeRubikCount = 5;    //初始魔方置乱次数
    private String gameSavePath;
    private saveIOer saveImporter;

    public frameWork() {
        original = getCube();
        rubik = new Rubik();
        cameraAndaxis = new cameraAndaxis();
        modelImporter();
        root.getChildren().add(rubik.getRoot());
        root.getChildren().add(cameraAndaxis.getRoot());
        cubeAction();
        recover = new autoMatically();
        recover.initialize();
        createGameTimer();
        String path =this.getClass().getResource("")+"save1.save";
        path=path.substring(6);
        gameSavePath=new String(path);
        saveImporter=new saveIOer(gameSavePath);
    }

    public Camera getCamera() {
        return cameraAndaxis.getCamera();
    }

    public void setCube() {

    }

    public void modelImporter() {
        URL modelUrl = this.getClass().getResource("Cube.obj");
        modelImporter = new ImportModel(modelUrl);
        meshViews = modelImporter.getMapMeshes();
        Set<Entry<String, MeshView>> sets = meshViews.entrySet();
        for (Entry<String, MeshView> entry : sets) { // 将模型导入至相应的Rubik并且附上相应编号
            rubik.importCube(
                    entry.getKey().substring(entry.getKey().indexOf("Block") + 5, entry.getKey().indexOf("Block") + 7),
                    entry.getValue());
        }

    }

    public ArrayList<Integer> getCube() {
        ArrayList<Integer> cubeList = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    cubeList.add(cube[i][j][k]);
                }
            }
        }
        return cubeList;
    }

    public void saveCube() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    cube[i][j][k] = temp[i][j][k];
                }
            }
        }
    }

    public void printCube() {
        for (int x = 0; x < level; x++) {
            for (int y = 0; y < level; y++) {
                for (int z = 0; z < level; z++) {
                    System.out.print(cube[x][y][z] + "\t");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }

    public void rotation(String rotType, int status) {   //status==1 为有旋转动画  status==0 为没有旋转动画
        if (this.canRot.get() == true) {
            double angle = 0;
            axis = Utils.getAxis(rotType);
            if (axis.getX() != 0)
                angle = axis.getX();
            else if (axis.getY() != 0)
                angle = axis.getY();
            else if (axis.getZ() != 0)
                angle = axis.getZ();
            if (rotType.contains("i"))
                angle = -angle;
            original= getCube();    //为旋转时的数组序列
            turn(rotType);
            updates = getCube();    //已旋转后的数组序列
            AtomicInteger index = new AtomicInteger();
            layer.clear();
            for (Integer o : original) {
                if (!o.equals(updates.get(index.getAndIncrement()))) {
                    layer.add(o);
                } else {
                    System.out.print(o + ",");
                }
            }
            original = updates;
            layer.add(0, updates.get(Utils.getCenter(rotType)));
            System.out.println("\nlayer:"+layer);
            angle = angle * 90;
            double rotAngle = angle;
            double angEnd = rotAngle;
            if (status == 1) {
                this.canRot.set(false);
                Timeline timeline = new Timeline();
                Duration duration = Duration.millis(700);
                EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        cubeRotion(rotAngle);
                        canRot.set(true);
                    }
                };

                for (Integer cubeName : layer) {
                    Cube cube = rubik.getCubeModel().get(cubeName.toString());
                    if (axis.equals(new Point3D(0, 1, 0))) {
                        timeline.getKeyFrames().add(
                                new KeyFrame(duration, onFinished, new KeyValue(cube.getRy().angleProperty(), angEnd)));
                    } else if (axis.equals(new Point3D(1, 0, 0))) {
                        timeline.getKeyFrames().add(
                                new KeyFrame(duration, onFinished, new KeyValue(cube.getRx().angleProperty(), angEnd)));
                    } else if (axis.equals(new Point3D(0, 0, 1))) {
                        timeline.getKeyFrames().add(
                                new KeyFrame(duration, onFinished, new KeyValue(cube.getRz().angleProperty(), angEnd)));
                    }

                }
                timeline.play();
            } else if (status == 0) {
                cubeRotion(rotAngle);
            }
            recover.turn(rotType);
            checkSuccess();
            System.out.println("Rotation");
        }
    }

    public void cubeRotion(double rotAngle) {
        for (Integer cubeName : layer) {
            Cube cube = rubik.getCubeModel().get(cubeName.toString());
            for (MeshView entry : cube.getCubeMesh()) {
                Affine a = new Affine(entry.getTransforms().get(0));
                a.prepend(new Rotate(rotAngle, axis));
                entry.getTransforms().setAll(a);
            }
            cube.setAllxyz();
        }
    }

    public void cubeAction() {
        Set<Entry<String, Cube>> sets = rubik.getCubeModel().entrySet();
        for (Entry<String, Cube> entry : sets) {
            entry.getValue().getRoot().setOnMousePressed(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    // TODO Auto-generated method stub
                    // mouseOldX = event.getSceneX();
                    // mouseOldY = event.getSceneY();
                    mouseOldX = event.getX();
                    mouseOldY = event.getY();
                    mouseOldZ = event.getZ();
                    System.out.println("mouseOldX:" + mouseOldX + "\tmouseOldY" + mouseOldY);
                }

            });
            entry.getValue().getRoot().setOnMouseDragged(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    // TODO Auto-generated method stub
                    mouseX = event.getX();
                    mouseY = event.getY();
                    mouseZ = event.getZ();
                    System.out.println(entry.getValue().getCubeName());
                    System.out.println("X:" + event.getX());
                    System.out.println("Y:" + event.getY());
                    System.out.println("Z:" + event.getZ());
                    if ((event.getZ() < -52 && event.getZ() > -54) // F
                            && (event.getX() < 53 && event.getX() > -53) && (event.getY() < 53 && event.getY() > -53)) {
                        System.out.println("F");
                        if ((event.getY() > -53 && event.getY() < -19)) { // 底层
                            double distance = Math.abs(mouseX - mouseOldX);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseX > mouseOldX) { // 向右滑动
                                    System.out.println("1");
                                    rotation("Di", 1);
//									recoverTurn("Di");
                                } else if (mouseX < mouseOldX) { // 向左滑动
                                    System.out.println("2");
                                    rotation("D", 1);
//									recoverTurn("D");
                                }
                            }
                        }
                        if ((event.getY() < 53 && event.getY() > 19)) { // 上层
                            double distance = Math.abs(mouseX - mouseOldX);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseX > mouseOldX) { // 向右滑动
                                    System.out.println("3");
                                    rotation("Ui", 1);
//									recoverTurn("Ui");
                                } else if (mouseX < mouseOldX) { // 向左滑动
                                    System.out.println("4");
                                    rotation("U", 1);
//									recoverTurn("U");
                                }
                            }
                        }
                        if ((event.getX() < 53 && event.getX() > 19)) { // 左层
                            double distance = Math.abs(mouseY - mouseOldY);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseY > mouseOldY) { // 向上滑动
                                    System.out.println("5");
                                    rotation("L", 1);
//									recoverTurn("L");
                                } else if (mouseY < mouseOldY) { // 向 下滑动
                                    System.out.println("6");
                                    rotation("Li", 1);
//									recoverTurn("Li");
                                }
                            }
                        }
                        if ((event.getX() < -19 && event.getX() > -53)) { // 右层
                            double distance = Math.abs(mouseY - mouseOldY);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseY > mouseOldY) { // 向上滑动
                                    System.out.println("7");
                                    rotation("R", 1);
//									recoverTurn("R");
                                } else if (mouseY < mouseOldY) { // 向下滑动
                                    System.out.println("8");
                                    rotation("Ri", 1);
//									recoverTurn("Ri");
                                }
                            }
                        }
                    } else if ((event.getZ() < 54 && event.getZ() > 52) // B
                            && (event.getX() < 53 && event.getX() > -53) && (event.getY() < 53 && event.getY() > -53)) {
                        System.out.println("B");
                        if ((event.getY() > -53 && event.getY() < -19)) { // 底层
                            double distance = Math.abs(mouseX - mouseOldX);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseX > mouseOldX) { // 向右滑动
                                    System.out.println("1");
                                    rotation("D", 1);
//									recoverTurn("D");
                                } else if (mouseX < mouseOldX) { // 向左滑动
                                    System.out.println("2");
                                    rotation("Di", 1);
//									recoverTurn("Di");
                                }
                            }
                        }
                        if ((event.getY() < 53 && event.getY() > 19)) { // 上层
                            double distance = Math.abs(mouseX - mouseOldX);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseX > mouseOldX) { // 向右滑动
                                    System.out.println("3");
                                    rotation("U", 1);
//									recoverTurn("U");
                                } else if (mouseX < mouseOldX) { // 向左滑动
                                    System.out.println("4");
                                    rotation("Ui", 1);
//									recoverTurn("Ui");
                                }
                            }
                        }
                        if ((event.getX() < 53 && event.getX() > 19)) { // 左层
                            double distance = Math.abs(mouseY - mouseOldY);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseY > mouseOldY) { // 向上滑动
                                    System.out.println("5");
                                    rotation("Li", 1);
//									recoverTurn("Li");
                                } else if (mouseY < mouseOldY) { // 向 下滑动
                                    System.out.println("6");
                                    rotation("L", 1);
//									recoverTurn("L");
                                }
                            }
                        }
                        if ((event.getX() < -19 && event.getX() > -53)) { // 右层
                            double distance = Math.abs(mouseY - mouseOldY);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseY > mouseOldY) { // 向上滑动
                                    System.out.println("7");
                                    rotation("Ri", 1);
//									recoverTurn("Ri");
                                } else if (mouseY < mouseOldY) { // 向下滑动
                                    System.out.println("8");
                                    rotation("R", 1);
//									recoverTurn("R");
                                }
                            }
                        }

                    } else if ((event.getX() < 54 && event.getX() > 52) // L
                            && (event.getY() < 53 && event.getY() > -53) && (event.getZ() < 53 && event.getZ() > -53)) {
                        System.out.println("L");
                        if ((event.getY() > -53 && event.getY() < -19)) { // 底层
                            double distance = Math.abs(mouseZ - mouseOldZ);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseZ > mouseOldZ) { // 向右滑动
                                    System.out.println("1");
                                    rotation("Di", 1);
//									recoverTurn("Di");
                                } else if (mouseZ < mouseOldZ) { // 向左滑动
                                    System.out.println("2");
                                    rotation("D", 1);
//									recoverTurn("D");
                                }
                            }
                        }
                        if ((event.getY() < 53 && event.getY() > 19)) { // 上层
                            double distance = Math.abs(mouseZ - mouseOldZ);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseZ > mouseOldZ) { // 向右滑动
                                    System.out.println("3");
                                    rotation("Ui", 1);
//									recoverTurn("Ui");
                                } else if (mouseZ < mouseOldZ) { // 向左滑动
                                    System.out.println("4");
                                    rotation("U", 1);
//									recoverTurn("U");
                                }
                            }
                        }
                        if ((event.getZ() < 53 && event.getZ() > 19)) { // 左层
                            double distance = Math.abs(mouseY - mouseOldY);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseY > mouseOldY) { // 向上滑动
                                    System.out.println("5");
                                    rotation("B", 1);
//									recoverTurn("B");
                                } else if (mouseY < mouseOldY) { // 向 下滑动
                                    System.out.println("6");
                                    rotation("Bi", 1);
//									recoverTurn("Bi");
                                }
                            }
                        }
                        if ((event.getZ() < -19 && event.getZ() > -53)) { // 右层
                            double distance = Math.abs(mouseY - mouseOldY);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseY > mouseOldY) { // 向上滑动
                                    System.out.println("7");
                                    rotation("F", 1);
//									recoverTurn("F");
                                } else if (mouseY < mouseOldY) { // 向下滑动
                                    System.out.println("8");
                                    rotation("Fi", 1);
//									recoverTurn("Fi");
                                }
                            }
                        }
                    } else if ((event.getX() < -52 && event.getX() > -54) // R
                            && (event.getY() < 53 && event.getY() > -53) && (event.getZ() < 53 && event.getZ() > -53)) {
                        System.out.println("R");
                        if ((event.getY() > -53 && event.getY() < -19)) { // 底层
                            double distance = Math.abs(mouseZ - mouseOldZ);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseZ > mouseOldZ) { // 向右滑动
                                    System.out.println("1");
                                    rotation("D", 1);
//									recoverTurn("D");
                                } else if (mouseZ < mouseOldZ) { // 向左滑动
                                    System.out.println("2");
                                    rotation("Di", 1);
//									recoverTurn("Di");
                                }
                            }
                        }
                        if ((event.getY() < 53 && event.getY() > 19)) { // 上层
                            double distance = Math.abs(mouseZ - mouseOldZ);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseZ > mouseOldZ) { // 向右滑动
                                    System.out.println("3");
                                    rotation("U", 1);
//									recoverTurn("U");
                                } else if (mouseZ < mouseOldZ) { // 向左滑动
                                    System.out.println("4");
                                    rotation("Ui", 1);
//									recoverTurn("Ui");
                                }
                            }
                        }
                        if ((event.getZ() < 53 && event.getZ() > 19)) { // 左层
                            double distance = Math.abs(mouseY - mouseOldY);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseY > mouseOldY) { // 向上滑动
                                    System.out.println("5");
                                    rotation("Bi", 1);
//									recoverTurn("Bi");
                                } else if (mouseY < mouseOldY) { // 向 下滑动
                                    System.out.println("6");
                                    rotation("B", 1);
//									recoverTurn("B");
                                }
                            }
                        }
                        if ((event.getZ() < -19 && event.getZ() > -53)) { // 右层
                            double distance = Math.abs(mouseY - mouseOldY);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseY > mouseOldY) { // 向上滑动
                                    System.out.println("7");
                                    rotation("Fi", 1);
//									recoverTurn("Fi");
                                } else if (mouseY < mouseOldY) { // 向下滑动
                                    System.out.println("8");
                                    rotation("F", 1);
//									recoverTurn("F");
                                }
                            }
                        }
                    } else if ((event.getY() < 54 && event.getY() > 52) // U
                            && (event.getX() < 53 && event.getX() > -53) && (event.getZ() < 53 && event.getZ() > -53)) {
                        System.out.println("U");
                        if ((event.getZ() > -53 && event.getZ() < -19)) { // 底层
                            double distance = Math.abs(mouseX - mouseOldX);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseX > mouseOldX) { // 向右滑动
                                    System.out.println("1");
                                    rotation("Fi", 1);
//									recoverTurn("Fi");
                                } else if (mouseX < mouseOldX) { // 向左滑动
                                    System.out.println("2");
                                    rotation("F", 1);
//									recoverTurn("F");
                                }
                            }
                        }
                        if ((event.getZ() < 53 && event.getZ() > 19)) { // 上层
                            double distance = Math.abs(mouseX - mouseOldX);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseX > mouseOldX) { // 向右滑动
                                    System.out.println("3");
                                    rotation("Bi", 1);
//									recoverTurn("Bi");
                                } else if (mouseX < mouseOldX) { // 向左滑动
                                    System.out.println("4");
                                    rotation("B", 1);
//									recoverTurn("B");
                                }
                            }
                        }
                        if ((event.getX() < 53 && event.getX() > 19)) { // 左层
                            double distance = Math.abs(mouseZ - mouseOldZ);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseZ > mouseOldZ) { // 向上滑动
                                    System.out.println("5");
                                    rotation("L", 1);
//									recoverTurn("L");
                                } else if (mouseZ < mouseOldZ) { // 向 下滑动
                                    System.out.println("6");
                                    rotation("Li", 1);
//									recoverTurn("Li");
                                }
                            }
                        }
                        if ((event.getX() < -19 && event.getX() > -53)) { // 右层
                            double distance = Math.abs(mouseZ - mouseOldZ);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseZ > mouseOldZ) { // 向上滑动
                                    System.out.println("7");
                                    rotation("R", 1);
//									recoverTurn("R");
                                } else if (mouseZ < mouseOldZ) { // 向下滑动
                                    System.out.println("8");
                                    rotation("Ri", 1);
//									recoverTurn("Ri");
                                }
                            }
                        }
                    } else if ((event.getY() < -52 && event.getY() > -54) // D
                            && (event.getX() < 53 && event.getX() > -53) && (event.getZ() < 53 && event.getZ() > -53)) {
                        System.out.println("D");
                        if ((event.getZ() > -53 && event.getZ() < -19)) { // 底层
                            double distance = Math.abs(mouseX - mouseOldX);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseX > mouseOldX) { // 向右滑动
                                    System.out.println("1");
                                    rotation("F", 1);
//									recoverTurn("F");
                                } else if (mouseX < mouseOldX) { // 向左滑动
                                    System.out.println("2");
                                    rotation("Fi", 1);
//									recoverTurn("Fi");
                                }
                            }
                        }
                        if ((event.getZ() < 53 && event.getZ() > 19)) { // 上层
                            double distance = Math.abs(mouseX - mouseOldX);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseX > mouseOldX) { // 向右滑动
                                    System.out.println("3");
                                    rotation("B", 1);
//									recoverTurn("B");
                                } else if (mouseX < mouseOldX) { // 向左滑动
                                    System.out.println("4");
                                    rotation("Bi", 1);
//									recoverTurn("Bi");
                                }
                            }
                        }
                        if ((event.getX() < 53 && event.getX() > 19)) { // 左层
                            double distance = Math.abs(mouseZ - mouseOldZ);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseZ > mouseOldZ) { // 向上滑动
                                    System.out.println("5");
                                    rotation("Li", 1);
//									recoverTurn("Li");
                                } else if (mouseZ < mouseOldZ) { // 向 下滑动
                                    System.out.println("6");
                                    rotation("L", 1);
//									recoverTurn("L");
                                }
                            }
                        }
                        if ((event.getX() < -19 && event.getX() > -53)) { // 右层
                            double distance = Math.abs(mouseZ - mouseOldZ);
                            System.out.println("distance:" + distance);
                            if (distance > sensitivity) { // 灵敏度控制
                                if (mouseZ > mouseOldZ) { // 向上滑动
                                    System.out.println("7");
                                    rotation("Ri", 1);
//									recoverTurn("Ri");
                                } else if (mouseZ < mouseOldZ) { // 向下滑动
                                    System.out.println("8");
                                    rotation("R", 1);
//									recoverTurn("R");
                                }
                            }
                        }
                    }
                    event.consume();
                }

            });
        }
    }

    public Point3D getCubeIndex(int cube) {
        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                for (int z = 0; z < level; z++) {
                    if (this.cube[i][j][z] == cube) {
                        return new Point3D(i, j, z);
                    }
                }

            }
        }
        return null;

    }

    public ArrayList<String> getRotationable(Point3D cubePoint) {
        int x = (int) cubePoint.getX();
        int y = (int) cubePoint.getY();
        int z = (int) cubePoint.getZ();
        ArrayList<String> canRot = new ArrayList<String>();
        if (z == 0) {
            canRot.add("D");
            canRot.add("Di");
        }
        if (z == level - 1) {
            canRot.add("U");
            canRot.add("Ui");
        }
        if (x == 0) {
            canRot.add("F");
            canRot.add("Fi");
        }
        if (x == level - 1) {
            canRot.add("B");
            canRot.add("Bi");
        }
        if (y == 0) {
            canRot.add("R");
            canRot.add("Ri");
        }
        if (y == level - 1) {
            canRot.add("L");
            canRot.add("Li");
        }
        return canRot;

    }

    public void turn(String rotType) {
        for (int x = 0; x < level; x++) {
            for (int y = 0; y < level; y++) {
                for (int z = 0; z < level; z++) {
                    switch (rotType) {
                        case "X":
                            temp[z][y][level - 1 - x] = cube[x][y][z];
                            break;
                        case "Xi":
                            temp[level - 1 - z][y][x] = cube[x][y][z];
                            break;
                        case "Yi":
                            temp[y][level - 1 - x][z] = cube[x][y][z];
                            break;
                        case "Y":
                            temp[level - 1 - y][x][z] = cube[x][y][z];
                            break;
                        case "Z":
                            temp[x][level - 1 - z][y] = cube[x][y][z];
                            break;
                        case "Zi":
                            temp[x][z][level - 1 - y] = cube[x][y][z];
                            break;
                        case "F":
                            if (x == 0) {
                                temp[x][level - 1 - z][y] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "Fi":
                            if (x == 0) {
                                temp[x][z][level - 1 - y] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "B":
                            if (x == 2) {
                                temp[x][level - 1 - z][y] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "Bi":
                            if (x == 2) {
                                temp[x][z][level - 1 - y] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "R":
                            if (y == 0) {
                                temp[z][y][level - 1 - x] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "Ri":
                            if (y == 0) {
                                temp[level - 1 - z][y][x] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "L":
                            if (y == 2) {
                                temp[z][y][level - 1 - x] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "Li":
                            if (y == 2) {
                                temp[level - 1 - z][y][x] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "Di":
                            if (z == 0) {
                                temp[y][level - 1 - x][z] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "D":
                            if (z == 0) {
                                temp[level - 1 - y][x][z] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "U":
                            if (z == 2) {
                                temp[level - 1 - y][x][z] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                        case "Ui":
                            if (z == 2) {
                                temp[y][level - 1 - x][z] = cube[x][y][z];
                            } else {
                                temp[x][y][z] = cube[x][y][z];
                            }
                            break;
                    }
                }
            }
        }
        saveCube();
    }

    public ArrayList<String> getRecoverWay() {
        return recover.getRecoverWay();
    }

    public void cubeRecover() {
        recover.startRecover();
        recover.printColor();
    }

    public void recoverRubik() {
        IntegerProperty count = new SimpleIntegerProperty();
        Rubikrecover = new Timer();
        Rubikrecover.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (count.get() >= getRecoverWay().size() - 1) {
                    Rubikrecover.cancel();
                }
                System.out.println("rot:" + getRecoverWay().get(count.get()));
                rotation(getRecoverWay().get(count.get()), 1);
                count.set(count.get() + 1);
            }
        }, 20, 1000);
    }

    public void pauseRubikRecover() {
        Rubikrecover.cancel();
    }

    public ArrayList<String> getTips() {
        this.cubeRecover();
        return RecoverTips.getTips(getRecoverWay());
    }

    public void checkSuccess() {
        if (recover.isRecover()) {
            this.gameTime.cancel();
            for(RubikRecoverAdapter adapter : rubikRecoverAdapters){
                RubikRecoverEvent event = new RubikRecoverEvent();
                event.setIsrecover(true);
                adapter.isRecover(event);
            }
        } else {
            System.out.println("check:false");
        }
    }

    public void disorganizeRubik() {
        Random a = new Random();
        for (int i = 0; i < disorganizeRubikCount; i++) {
            int random = a.nextInt(12);
            switch (random) {
                case 0:
                    rotation("F", 0);
                    System.out.println("F");
                    break;
                case 1:
                    rotation("Fi", 0);
                    System.out.println("Fi");
                    break;
                case 2:
                    rotation("B", 0);
                    System.out.println("B");
                    break;
                case 3:
                    rotation("Bi", 0);
                    System.out.println("Bi");
                    break;
                case 4:
                    rotation("R", 0);
                    System.out.println("R");
                    break;
                case 5:
                    rotation("Ri", 0);
                    System.out.println("Ri");
                    break;
                case 6:
                    rotation("L", 0);
                    System.out.println("L");
                    break;
                case 7:
                    rotation("Li", 0);
                    System.out.println("Li");
                    break;
                case 8:
                    rotation("U", 0);
                    System.out.println("U");
                    break;
                case 9:
                    rotation("Ui", 0);
                    System.out.println("Ui");
                    break;
                case 10:
                    rotation("D", 0);
                    System.out.println("D");
                    break;
                case 11:
                    rotation("Di", 0);
                    System.out.println("Di");
                    break;
                default:
                    System.out.println("error!");
            }
        }
    }

    public void setCameraOnMousePressed(MouseEvent event) {
        cameraAndaxis.cameraSetOnMousePressed(event);
    }

    public void setCameraOnMouseDragged(MouseEvent event) {
        cameraAndaxis.cameraSetOnMouseDragged(event);
    }

    public void setCameraOnScroll(ScrollEvent event) {
        cameraAndaxis.cameraSetOnScroll(event);
    }

    public void startGameTime() {
        gameTime.schedule(new TimerTask() {
            @Override
            public void run() {
                time += 1;
                for (TimerAdapter adapter : timerAdapters) {
                    TimerTextEvent event = new TimerTextEvent();
                    event.setTime(time);
                    adapter.getTimerText(event);
                }
            }
        }, 1, 1000);
    }

    public ArrayList<String> getCubeArray(){
        ArrayList<String> cubeSeqence = new ArrayList<String>();
        for (int x = 0; x < level; x++) {
            for (int y = 0; y < level; y++) {
                for (int z = 0; z < level; z++) {
                    cubeSeqence.add(String.valueOf(cube[x][y][z]));
                }
            }
        }
        return cubeSeqence;
    }

    public void setCubeArray(ArrayList<String> cubeSeqence){
        int index=0;
        for (int x = 0; x < level; x++) {
            for (int y = 0; y < level; y++) {
                for (int z = 0; z < level; z++) {
                    cube[x][y][z]= Integer.parseInt(cubeSeqence.get(index));
                    index++;
                }
            }
        }
    }

    public ArrayList<String> getRecoverCubeArray(){
        return recover.getColorArray();
    }

    public void setRecoverCubeArray(ArrayList<String> cubeSeqence){
        recover.setColorArray(cubeSeqence);
    }
    public void saveGame(){
        saveImporter.openFileWriter();
        int count=0;
        for(String data : this.getCubeArray()){
            saveImporter.writeFile(data);
        }
        saveImporter.writeFile("##");
        for(String data :this.getRecoverCubeArray()){
            saveImporter.writeFile(data);
        }
        saveImporter.writeFile("##");
        layer.clear();
        for (int x = 0; x < level; x++) {
            for (int y = 0; y < level; y++) {
                for (int z = 0; z < level; z++) {
                    layer.add(cube[x][y][z]);
                }
            }
        }
        for (Integer cubeName : layer) {
            Cube cube = rubik.getCubeModel().get(cubeName.toString());
            for (MeshView entry : cube.getCubeMesh()) {
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getMxx()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getMxy()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getMxz()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getMyx()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getMyy()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getMyz()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getMzx()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getMzy()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getMzz()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getTx()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getTy()));
                saveImporter.writeFile(String.valueOf(entry.getTransforms().get(0).getTz()));
                count+=12;
            }
        }
        System.out.println(count);
        saveImporter.closeFileWriter();
        this.printCube();
    }

    public void loadGameSave(){
        this.shutDownEngine();
        this.createGameTimer();
        this.gameTimerClear();
        this.startGameTime();
        saveImporter.openFileRead();
        ArrayList<String> cubeSeqence = new ArrayList<String>();
        String data=null;
        while(!(data=saveImporter.readFile()).equals("##")){
            cubeSeqence.add(data);
            System.out.println(data);
        }
        this.setCubeArray(cubeSeqence);
        System.out.println("##");
        cubeSeqence.clear();
        while(!(data=saveImporter.readFile()).equals("##")){
            cubeSeqence.add(data);
            System.out.println(data);
        }
        this.setRecoverCubeArray(cubeSeqence);
        layer.clear();
        int count=0;
        for (int x = 0; x < level; x++) {
            for (int y = 0; y < level; y++) {
                for (int z = 0; z < level; z++) {
                    layer.add(cube[x][y][z]);
                }
            }
        }
        for (Integer cubeName : layer) {
            Cube cube = rubik.getCubeModel().get(cubeName.toString());
            for (MeshView entry : cube.getCubeMesh()) {
                Affine a = new Affine();
                a.setMxx(Double.valueOf(saveImporter.readFile()));
                a.setMxy(Double.valueOf(saveImporter.readFile()));
                a.setMxz(Double.valueOf(saveImporter.readFile()));
                a.setMyx(Double.valueOf(saveImporter.readFile()));
                a.setMyy(Double.valueOf(saveImporter.readFile()));
                a.setMyz(Double.valueOf(saveImporter.readFile()));
                a.setMzx(Double.valueOf(saveImporter.readFile()));
                a.setMzy(Double.valueOf(saveImporter.readFile()));
                a.setMzz(Double.valueOf(saveImporter.readFile()));
                a.setTx(Double.valueOf(saveImporter.readFile()));
                a.setTy(Double.valueOf(saveImporter.readFile()));
                a.setTz(Double.valueOf(saveImporter.readFile()));
                entry.getTransforms().setAll(a);
                count+=12;
            }
            cube.setAllxyz();
        }
        System.out.println(count);
            saveImporter.closeFileReader();
            this.printCube();
    }

    public void addTimerAdapter(TimerAdapter timerAdapter) {
        timerAdapters.add(timerAdapter);
    }

    public void addRubikCoverAdapter(RubikRecoverAdapter adapter){
        this.rubikRecoverAdapters.add(adapter);
    }

    public double getMouseSensitivityMin(){
        return mouseSensitivityMin;
    }

    public double getMouseSensitivityMax(){
        return mouseSensitivityMax;
    }

    public double getMouseSensitivity(){
        return sensitivity;
    }

    public void setMouseSensitivity(double sensitivity){
        this.sensitivity=sensitivity;
    }

    public double getMouseSensitivityDefault(){
        return mouseSensitivityDefault;
    }

    public void createGameTimer(){
        gameTime = new Timer();
    }

    public void gameTimerClear(){
        this.time=0;
    }
    public void pauseGameTimer(){
        gameTime.cancel();
    }

    public void shutDownEngine(){
        gameTime.cancel();
    }
    public Group getRoot() {
        return root;
    }
}
