package engine;

import java.util.ArrayList;

import javafx.geometry.Point3D;

public class autoMatically {
    private int level = 3;
    private int[][][] color = new int[level + 2][level + 2][level + 2];
    private int[][][] temp = new int[level + 2][level + 2][level + 2];
    private int[][][] cubeBackup = new int[level + 2][level + 2][level + 2];
    private ArrayList<Point3D> edges = new ArrayList<Point3D>();
    private ArrayList<Point3D> corners = new ArrayList<Point3D>();
    private ArrayList<Point3D> centers = new ArrayList<Point3D>();
    private ArrayList<String> recoverWay = new ArrayList<String>();

    public autoMatically() {
        getEdges();
        getCenters();
        getCorners();
    }

    public void turn(String rotType) {
        for (int x = 0; x < level + 2; x++) {
            for (int y = 0; y < level + 2; y++) {
                for (int z = 0; z < level + 2; z++) {
                    switch (rotType) {
                        case "X":
                            temp[z][y][level + 2 - 1 - x] = color[x][y][z];
                            break;
                        case "Xi":
                            temp[level + 2 - 1 - z][y][x] = color[x][y][z];
                            break;
                        case "Yi":
                            temp[y][level + 2 - 1 - x][z] = color[x][y][z];
                            break;
                        case "Y":
                            temp[level + 2 - 1 - y][x][z] = color[x][y][z];
                            break;
                        case "Z":
                            temp[x][level + 2 - 1 - z][y] = color[x][y][z];
                            break;
                        case "Zi":
                            temp[x][z][level + 2 - 1 - y] = color[x][y][z];
                            break;
                        case "F":
                            if (x == 0) {
                                temp[x][level + 2 - 1 - z][y] = color[x][y][z];
                                temp[x + 1][level + 2 - 1 - z][y] = color[x + 1][y][z];
                            } else if (x != 1) {
                                temp[x][y][z] = color[x][y][z];

                            }
                            break;
                        case "Fi":
                            if (x == 0) {
                                temp[x][z][level + 2 - 1 - y] = color[x][y][z];
                                temp[x + 1][z][level + 2 - 1 - y] = color[x + 1][y][z];
                            } else if (x != 1) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "B":
                            if (x == level + 2 - 1) {
                                temp[x][level + 2 - 1 - z][y] = color[x][y][z];
                                temp[x - 1][level + 2 - 1 - z][y] = color[x - 1][y][z];
                            } else if (x != level + 2 - 2) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "Bi":
                            if (x == level + 2 - 1) {
                                temp[x][z][level + 2 - 1 - y] = color[x][y][z];
                                temp[x - 1][z][level + 2 - 1 - y] = color[x - 1][y][z];
                            } else if (x != level + 2 - 2) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "R":
                            if (y == 0) {
                                temp[z][y][level + 2 - 1 - x] = color[x][y][z];
                                temp[z][y + 1][level + 2 - 1 - x] = color[x][y + 1][z];
                            } else if (y != 1) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "Ri":
                            if (y == 0) {
                                temp[level + 2 - 1 - z][y][x] = color[x][y][z];
                                temp[level + 2 - 1 - z][y + 1][x] = color[x][y + 1][z];
                            } else if (y != 1) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "L":
                            if (y == level + 2 - 1) {
                                temp[z][y][level + 2 - 1 - x] = color[x][y][z];
                                temp[z][y - 1][level + 2 - 1 - x] = color[x][y - 1][z];
                            } else if (y != level + 2 - 2) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "Li":
                            if (y == level + 2 - 1) {
                                temp[level + 2 - 1 - z][y][x] = color[x][y][z];
                                temp[level + 2 - 1 - z][y - 1][x] = color[x][y - 1][z];
                            } else if (y != level + 2 - 2) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "Di":
                            if (z == 0) {
                                temp[y][level + 2 - 1 - x][z] = color[x][y][z];
                                temp[y][level + 2 - 1 - x][z + 1] = color[x][y][z + 1];
                            } else if (z != 1) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "D":
                            if (z == 0) {
                                temp[level + 2 - 1 - y][x][z] = color[x][y][z];
                                temp[level + 2 - 1 - y][x][z + 1] = color[x][y][z + 1];
                            } else if (z != 1) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "U":
                            if (z == level + 2 - 1) {
                                temp[level + 2 - 1 - y][x][z] = color[x][y][z];
                                temp[level + 2 - 1 - y][x][z - 1] = color[x][y][z - 1];
                            } else if (z != level + 2 - 2) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                        case "Ui":
                            if (z == level + 2 - 1) {
                                temp[y][level + 2 - 1 - x][z] = color[x][y][z];
                                temp[y][level + 2 - 1 - x][z - 1] = color[x][y][z - 1];
                            } else if (z != level + 2 - 2) {
                                temp[x][y][z] = color[x][y][z];
                            }
                            break;
                    }
                }
            }
        }
        saveCube();
    }

    public void initialize() {
        for (int i = 0; i < level + 2; i++) {
            for (int j = 0; j < level + 2; j++) {
                for (int k = 0; k < level + 2; k++) {
                    if (i == 0 && j > 0 && j < 4 && k > 0 && k < 4)// F
                        color[i][j][k] = 1;
                    else if (i == 4 && j > 0 && j < 4 && k > 0 && k < 4)// B
                        color[i][j][k] = 2;
                    else if (j == 0 && i > 0 && i < 4 && k > 0 && k < 4)// R
                        color[i][j][k] = 3;
                    else if (j == 4 && i > 0 && i < 4 && k > 0 && k < 4)// L
                        color[i][j][k] = 4;
                    else if (k == 0 && i > 0 && i < 4 && j < 4 && j > 0)// U
                        color[i][j][k] = 5;
                    else if (k == 4 && i > 0 && i < 4 && j < 4 && j > 0)// D
                        color[i][j][k] = 6;
                    else
                        color[i][j][k] = 0;
                }
            }
        }
    }

    public void printColor() {
        System.out.println();
        for (int i = 0; i < level + 2; i++) {
            for (int j = 0; j < level + 2; j++) {
                for (int k = 0; k < level + 2; k++) {
                    System.out.print(color[i][j][k] + "\t");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }

    public ArrayList<Point3D> getFace(Point3D center) {
        ArrayList<Point3D> face = new ArrayList<Point3D>();
        face.addAll(this.getSameFaceEdges(center));
        face.addAll(this.getSameFaceCorners(center));
        face.add(center);
        return face;
    }

    public ArrayList<Point3D> getSameFaceEdges(Point3D center) {
        ArrayList<Point3D> face = new ArrayList<Point3D>();
        for (Point3D point : edges) {
            if (point.getX() == center.getX() && (center.getX() == 0 || center.getX() == level + 2 - 1)) {
                face.add(point);
            }
            if (point.getY() == center.getY() && (center.getY() == 0 || center.getY() == level + 2 - 1)) {
                face.add(point);
            }
            if (point.getZ() == center.getZ() && (center.getZ() == 0 || center.getZ() == level + 2 - 1)) {
                face.add(point);
            }
        }
        return face;

    }

    public ArrayList<Point3D> getSameFaceCorners(Point3D center) {
        ArrayList<Point3D> face = new ArrayList<Point3D>();
        for (Point3D point : corners) {
            if (point.getX() == center.getX() && (center.getX() == 0 || center.getX() == level + 2 - 1)) {
                face.add(point);
            }
            if (point.getY() == center.getY() && (center.getY() == 0 || center.getY() == level + 2 - 1)) {
                face.add(point);
            }
            if (point.getZ() == center.getZ() && (center.getZ() == 0 || center.getZ() == level + 2 - 1)) {
                face.add(point);
            }
        }
        return face;
    }

    public void printTemp() {
        for (int i = 0; i < level + 2; i++) {
            for (int j = 0; j < level + 2; j++) {
                for (int k = 0; k < level + 2; k++) {
                    System.out.print(temp[i][j][k] + "\t");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }

    public void saveCube() {
        for (int i = 0; i < level + 2; i++) {
            for (int j = 0; j < level + 2; j++) {
                for (int k = 0; k < level + 2; k++) {
                    color[i][j][k] = temp[i][j][k];
                }
            }
        }
    }

    public void setCubeBackup() {
        for (int i = 0; i < level + 2; i++) {
            for (int j = 0; j < level + 2; j++) {
                for (int k = 0; k < level + 2; k++) {
                    cubeBackup[i][j][k] = color[i][j][k];
                }
            }
        }
    }

    public int[][][] getCubeBackup() {
        return cubeBackup;
    }

    public ArrayList<String> rotList(Point3D rot) {
        int x = (int) rot.getX();
        int y = (int) rot.getY();
        int z = (int) rot.getZ();
        ArrayList<String> rotable = new ArrayList<String>();
        if (y == 4 || y == 3) {
            rotable.add("L");
            rotable.add("Li");
        } else if (y == 0 || y == 1) {
            rotable.add("R");
            rotable.add("Ri");
        }
        if (x == 0 || x == 1) {
            rotable.add("F");
            rotable.add("Fi");
        } else if (x == 4 || x == 3) {
            rotable.add("B");
            rotable.add("Bi");
        }
        if (z == 3 || z == 4) {
            rotable.add("U");
            rotable.add("Ui");
        } else if (z == 0 || z == 1) {
            rotable.add("D");
            rotable.add("Di");
        }
        return rotable;
    }

    public ArrayList<Point3D> getEdges() {
        for (int x = 0; x < level + 2; x++) {
            for (int y = 0; y < level + 2; y++) {
                for (int z = 0; z < level + 2; z++) {
                    if (z == 0 || z == level + 2 - 1) {// U D
                        if ((y > 1 && y < level + 2 - 2) && (x == 1 || x == level + 2 - 2)
                                || ((x > 1 && x < level + 2 - 2) && (y == 1 || y == level + 2 - 2))) {
                            edges.add(new Point3D(x, y, z));
                        }
                    }
                    if (y == 0 || y == level + 2 - 1) {// R L
                        if ((z > 1 && z < level + 2 - 2) && (x == 1 || x == level + 2 - 2)
                                || ((x > 1 && x < level + 2 - 2) && (z == 1 || z == level + 2 - 2))) {
                            edges.add(new Point3D(x, y, z));
                        }
                    }
                    if (x == 0 || x == level + 2 - 1) {// F B
                        if ((z > 1 && z < level + 2 - 2) && (y == 1 || y == level + 2 - 2)
                                || ((y > 1 && y < level + 2 - 2) && (z == 1 || z == level + 2 - 2))) {
                            edges.add(new Point3D(x, y, z));
                        }
                    }
                }
            }
        }
        return edges;
    }

    public ArrayList<Point3D> getCorners() {
        for (int x = 0; x < level + 2; x++) {
            for (int y = 0; y < level + 2; y++) {
                for (int z = 0; z < level + 2; z++) {
                    if (z == 0 || z == level + 2 - 1) {// U D
                        if ((y == 1 || y == level + 2 - 2) && (x == 1 || x == level + 2 - 2)) {
                            corners.add(new Point3D(x, y, z));
                        }
                    }
                    if (y == 0 || y == level + 2 - 1) {// R L
                        if ((z == 1 || z == level + 2 - 2) && (x == 1 || x == level + 2 - 2)) {
                            corners.add(new Point3D(x, y, z));
                        }
                    }
                    if (x == 0 || x == level + 2 - 1) {// F B
                        if ((z == 1 || z == level + 2 - 2) && (y == 1 || y == level + 2 - 2)) {
                            corners.add(new Point3D(x, y, z));
                        }
                    }
                }
            }
        }
        return corners;
    }

    public ArrayList<Point3D> getCenters() {
        centers.add(new Point3D(0, (level + 2) / 2, (level + 2) / 2));// F
        centers.add(new Point3D(level + 2 - 1, (level + 2) / 2, (level + 2) / 2));// B
        centers.add(new Point3D((level + 2) / 2, 0, (level + 2) / 2));// R
        centers.add(new Point3D((level + 2) / 2, level + 2 - 1, (level + 2) / 2));// L
        centers.add(new Point3D((level + 2) / 2, (level + 2) / 2, 0));// D
        centers.add(new Point3D((level + 2) / 2, (level + 2) / 2, level + 2 - 1));// U
        return centers;

    }

    public Point3D getCenters(Point3D point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int z = (int) point.getZ();
        if (x == 0)
            return new Point3D(0, (level + 2) / 2, (level + 2) / 2);// F
        else if (x == level + 2 - 1)
            return new Point3D(level + 2 - 1, (level + 2) / 2, (level + 2) / 2);// B
        else if (y == 0)
            return new Point3D((level + 2) / 2, 0, (level + 2) / 2);// R
        else if (y == level + 2 - 1)
            return new Point3D((level + 2) / 2, level + 2 - 1, (level + 2) / 2);// L
        else if (z == 0)
            return new Point3D((level + 2) / 2, (level + 2) / 2, 0);// D
        else if (z == 4)
            return new Point3D((level + 2) / 2, (level + 2) / 2, level + 2 - 1);// U
        return null;
    }

    public Point3D Findturn(String rotType, Point3D point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int z = (int) point.getZ();
        switch (rotType) {
            case "X":
                return new Point3D(z, y, level + 2 - 1 - x);
            case "Xi":
                return new Point3D(level + 2 - 1 - z, y, x);
            case "Yi":
                return new Point3D(y, level + 2 - 1 - x, z);
            case "Y":
                return new Point3D(level + 2 - 1 - y, x, z);
            case "Z":
                return new Point3D(x, level + 2 - 1 - z, y);
            case "Zi":
                return new Point3D(x, z, level + 2 - 1 - y);
            case "F":
                return new Point3D(x, level + 2 - 1 - z, y);
            case "Fi":
                return new Point3D(x, z, level + 2 - 1 - y);
            case "B":
                return new Point3D(x, level + 2 - 1 - z, y);
            case "Bi":
                return new Point3D(x, z, level + 2 - 1 - y);
            case "R":
                return new Point3D(z, y, level + 2 - 1 - x);
            case "Ri":
                return new Point3D(level + 2 - 1 - z, y, x);
            case "L":
                return new Point3D(z, y, level + 2 - 1 - x);
            case "Li":
                return new Point3D(level + 2 - 1 - z, y, x);
            case "Di":
                return new Point3D(y, level + 2 - 1 - x, z);
            case "D":
                return new Point3D(level + 2 - 1 - y, x, z);
            case "U":
                return new Point3D(level + 2 - 1 - y, x, z);
            case "Ui":
                return new Point3D(y, level + 2 - 1 - x, z);
        }
        return null;
    }

    public ArrayList<Point3D> getColorEdges(int color) {
        ArrayList<Point3D> edges = new ArrayList<Point3D>();

        for (Point3D point : this.edges) {
            if (this.color[(int) point.getX()][(int) point.getY()][(int) point.getZ()] == color) {
                edges.add(point);
            }
        }
        return edges;
    }

    public ArrayList<Point3D> getColorCorners(int color) {
        ArrayList<Point3D> Corners = new ArrayList<Point3D>();
        for (Point3D point : this.corners) {
            if (this.color[(int) point.getX()][(int) point.getY()][(int) point.getZ()] == color) {
                Corners.add(point);
            }
        }
        int index = 0; // 优先复原色块在第三层的
        Point3D temp;
        for (Point3D point : Corners) {
            if (point.getZ() == 3) {
                index = Corners.indexOf(point);
            }
        }
        temp = Corners.get(index);
        Corners.remove(index);
        Corners.add(0, temp);
        return Corners;
    }

    public int getCubeColor(Point3D point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int z = (int) point.getZ();
        return color[x][y][z];
    }

    public String getDisRot(String rot) {
        switch (rot) {
            case "F":
                return "Fi";
            case "Fi":
                return "F";
            case "B":
                return "Bi";
            case "Bi":
                return "B";
            case "U":
                return "Ui";
            case "Ui":
                return "U";
            case "D":
                return "Di";
            case "Di":
                return "D";
            case "L":
                return "Li";
            case "Li":
                return "L";
            case "R":
                return "Ri";
            case "Ri":
                return "R";
        }
        return rot;
    }

    public boolean isSame(Point3D point1, Point3D point2) {
        if (point1.getX() == point2.getX() && point1.getY() == point2.getY() && point1.getZ() == point2.getZ()) {
            return true;
        }
        return false;
    }

    public Point3D getOtherEdges(Point3D point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int z = (int) point.getZ();
        if ((x == 3 && y == 0) || (x == 4 && y == 1) || (x == 1 && y == 4) || (x == 0 && y == 3))// Z
            return new Point3D(level + 2 - 1 - y, level + 2 - 1 - x, z);
        else if ((x == 1 && y == 0) || (x == 0 && y == 1) || (x == 3 && y == 4) || (x == 4 && y == 3))
            return new Point3D(y, x, z);
        else if ((x == 0 && z == 3) || (x == 1 && z == 4) || (x == 4 && z == 1) || (x == 3 && z == 0))// Y
            return new Point3D(level + 2 - 1 - z, y, level + 2 - 1 - x);
        else if ((x == 0 && z == 1) || (x == 1 && z == 0) || (x == 4 && z == 3) || (x == 3 && z == 4))
            return new Point3D(z, y, x);
        else if ((y == 1 && z == 4) || (y == 0 && z == 3) || (y == 3 && z == 0) || (y == 4 && z == 1))// X
            return new Point3D(x, level + 2 - 1 - z, level + 2 - 1 - y);
        else if ((y == 1 && z == 0) || (y == 0 && z == 1) || (y == 3 && z == 4) || (y == 4 && z == 3))
            return new Point3D(x, z, y);
        return null;

    }

    public ArrayList<Point3D> getOtherCorners(Point3D point) {
        ArrayList<Point3D> cube = new ArrayList<Point3D>();
        int x = (int) point.getX();
        int y = (int) point.getY();
        int z = (int) point.getZ();
        if ((x == 3 && y == 0) || (x == 4 && y == 1) || (x == 1 && y == 4) || (x == 0 && y == 3))// Z
            cube.add(new Point3D(level + 2 - 1 - y, level + 2 - 1 - x, z));
        else if ((x == 1 && y == 0) || (x == 0 && y == 1) || (x == 3 && y == 4) || (x == 4 && y == 3))
            cube.add(new Point3D(y, x, z));
        if ((x == 0 && z == 3) || (x == 1 && z == 4) || (x == 4 && z == 1) || (x == 3 && z == 0))// Y
            cube.add(new Point3D(level + 2 - 1 - z, y, level + 2 - 1 - x));
        else if ((x == 0 && z == 1) || (x == 1 && z == 0) || (x == 4 && z == 3) || (x == 3 && z == 4))
            cube.add(new Point3D(z, y, x));
        if ((y == 1 && z == 4) || (y == 0 && z == 3) || (y == 3 && z == 0) || (y == 4 && z == 1))// X
            cube.add(new Point3D(x, level + 2 - 1 - z, level + 2 - 1 - y));
        else if ((y == 1 && z == 0) || (y == 0 && z == 1) || (y == 3 && z == 4) || (y == 4 && z == 3))
            cube.add(new Point3D(x, z, y));
        return cube;
    }

    // 复原第一层及底面
    public void step1() {
        ArrayList<Point3D> edges;
        Point3D center = new Point3D(2, 2, 0);
        int edgesIndex = 0;
        System.out.println((color[2][1][4] == color[2][2][0]));
        System.out.println((color[1][2][4] == color[2][2][0]));
        System.out.println((color[2][3][4] == color[2][2][0]));
        System.out.println((color[3][2][4] == color[2][2][0]));
        while ((color[2][1][4] != color[2][2][0] || color[1][2][4] != color[2][2][0] || color[2][3][4] != color[2][2][0]
                || color[3][2][4] != color[2][2][0])) { // 判断是否有满足中心点不同色的十字架条件
            edges = this.getColorEdges(color[(int) center.getX()][(int) center.getY()][(int) center.getZ()]);
            System.out.println("edges:" + edges);
            for (Point3D point : edges) {
                if (!(isSame(point, new Point3D(2, 1, 4)) || isSame(point, new Point3D(2, 3, 4))
                        || isSame(point, new Point3D(1, 2, 4)) || isSame(point, new Point3D(3, 2, 4)))) {
                    edgesIndex = edges.indexOf(point);
                    step1_1(edges.get(edgesIndex));

                    System.out.println((color[2][1][4] == color[2][2][0]));
                    System.out.println((color[1][2][4] == color[2][2][0]));
                    System.out.println((color[2][3][4] == color[2][2][0]));
                    System.out.println((color[3][2][4] == color[2][2][0]));
                    System.out.println("again\n\n");
                    break;
                }
            }

        }

        // 生成相同中心块的十字架
        while ((color[2][1][0] != color[2][2][0] || color[1][2][0] != color[2][2][0] || color[2][3][0] != color[2][2][0]
                || color[3][2][0] != color[2][2][0])) { // 判断是否有满足中心点同色的十字架条件
            edges = this.getColorEdges(color[(int) center.getX()][(int) center.getY()][(int) center.getZ()]);
            System.out.println("edges:" + edges);
            // System.out.println("point");
            for (Point3D point : edges) {
                if (point.getZ() > 0) {
                    step1_2(point);
                    break;
                }
            }
        }
        this.printColor();
        System.out.println("-------------------------------------------------------------");

        System.out.println(this.getColorCorners(color[(int) center.getX()][(int) center.getY()][(int) center.getZ()]));
        ArrayList<Point3D> corners = new ArrayList<Point3D>();
        int cornersIndex = 0;
        Boolean tag = false;
        do {
            tag = false;
            corners = this.getColorCorners(color[(int) center.getX()][(int) center.getY()][(int) center.getZ()]); // 获取相应中心色块的角块
            // 开始查找底层的角块所在位置，并进行底层角块的复原
            for (Point3D point : corners) {    //确保底面恢复完成
                if (!this.isfinsh(this.getOtherCorners(point).get(0), this.getOtherCorners(point).get(1))
                        || this.getCubeColor(this.getCenters(point)) != this.getCubeColor(new Point3D(2, 2, 0))
                        && point.getZ() != 0) {
                    tag = true;
                    System.out.println("haha:" + this.isfinsh(this.getOtherCorners(point).get(0), this.getOtherCorners(point).get(1)));
                    break;
                }
            }
            for (Point3D point : corners) {
                System.out.println("corners:" + corners + "\n");
                if (point.getZ() > 0) {

                    // 当底面有色块（相邻的色块）位置不对时，忽略，用上层的色块来替换它
                    cornersIndex = corners.indexOf(point);
                    System.out.println("corner:" + corners.get(cornersIndex) + "\n");
                    step1_4(corners.get(cornersIndex)); // 开始复原底层角块
                    this.printColor();
                    break;
                } else if (!this.isfinsh(this.getOtherCorners(point).get(0), this.getOtherCorners(point).get(1))) {    //存在需要复原的面块已经在第一层的情况,但没有归位

                    for (String rot : this.rotList(this.getCenters(this.getOtherCorners(point).get(0)))) {
                        if (this.Findturn(rot, point).getZ() > 1) {
                            turn(rot);
                            recoverWay.add(rot);
                            point = this.Findturn(rot, point);
                            if (this.getCubeColor(this.getCenters(this.getOtherCorners(point).get(0))) == this.getCubeColor(new Point3D(2, 2, 4))) {
                                for (String rot1 : this.rotList(this.getCenters(this.getOtherCorners(point).get(0)))) {
                                    turn(rot1);
                                    recoverWay.add(rot1);
                                    point = this.Findturn(rot1, point);
                                    break;
                                }
                            } else {
                                for (String rot1 : this.rotList(this.getCenters(this.getOtherCorners(point).get(1)))) {
                                    turn(rot1);
                                    recoverWay.add(rot1);
                                    point = this.Findturn(rot1, point);
                                    break;
                                }
                            }
                            turn(this.getDisRot(rot));
                            recoverWay.add(this.getDisRot(rot));
                            point = this.Findturn(this.getDisRot(rot), point);
                            break;
                        }
                    }
                }
            }
        } while (tag);
        // this.printColor();
    }

    public void step1_1(Point3D point) {
        System.out.println("step1_1:" + point + "\t" + this.getCubeColor(point));
        if (this.getCubeColor(point) == color[2][2][0] && point.getZ() == level + 2 - 1) {
            System.out.println("\nrunout\n");
            return;
        }
        System.out.println(this.rotList(point));
        for (String rot : this.rotList(point)) {
            if (this.Findturn(rot, point).getZ() > 1 && this.Findturn(rot, point).getZ() < 3) { // 旋转后在中间层的时候
                // 若出现底层方块的正上方（第四层），则移动顶层
                while (this.getCubeColor(point) == color[2][2][0]
                        && this.getCubeColor(new Point3D(point.getX(), point.getY(), 4)) == color[2][2][0]) {
                    this.turn("U");
                    recoverWay.add("U");
                    System.out.println("U");
                }
                System.out.println("rot:" + rot);
                System.out.println("turnPoint:" + this.Findturn(rot, point));
                this.turn(rot);
                recoverWay.add(rot);
                point = this.Findturn(rot, point);
                System.out.println("Point:\t" + point);
                step1_1(point);
                return;
            } else if (this.Findturn(rot, point).getZ() == level + 2 - 1) {
                System.out.println("rot1:" + rot);
                System.out.println("turnPoint:" + this.Findturn(rot, point));
                System.out.println("copy:" + (this.getCubeColor(this.Findturn(rot, point)) == color[2][2][0]));
                while (this.getCubeColor(this.Findturn(rot, point)) == color[2][2][0]) {
                    this.turn("U");
                    recoverWay.add("U");
                    System.out.println("U");
                }

                System.out.println((color[2][1][4] == color[2][2][0]));
                System.out.println((color[1][2][4] == color[2][2][0]));
                System.out.println((color[2][3][4] == color[2][2][0]));
                System.out.println((color[3][2][4] == color[2][2][0]));
                System.out.println();
                System.out.println();
                System.out.println("rot2:" + rot);
                this.turn(rot);
                recoverWay.add(rot);
                point = this.Findturn(rot, point);
                return;
            }

        }

    }

    public void step1_2(Point3D point) { // 将边块调整至与中心块颜色相同
        System.out.println("step1_2:" + point);
        System.out.println("color:" + this.getCubeColor(point) + "\t" + point);
        while (this.getCubeColor(this.getOtherEdges(point)) != this
                .getCubeColor(this.getCenters(this.getOtherEdges(point)))) {
            turn("U");
            recoverWay.add("U");
            point = this.Findturn("U", point);
            System.out.println("Step1_2:U");
        }
        System.out.println(this.getCubeColor(this.getOtherEdges(point)));
        System.out.println(this.getCubeColor(this.getCenters(this.getOtherEdges(point))));
        System.out.println("---------------------------------------------------");
        step1_3(point);
        System.out.println();
    }

    // 复原底层十字架
    public void step1_3(Point3D point) {
        System.out.println(this.rotList(this.getCenters(this.getOtherEdges(point))));
        for (String rot : this.rotList(this.getCenters(this.getOtherEdges(point)))) {
            turn(rot);
            turn(rot);
            recoverWay.add(rot);
            recoverWay.add(rot);
            point = this.Findturn(rot, point);
            point = this.Findturn(rot, point);
            break;
        }
        System.out.println(this.getCubeColor(point));
        System.out.println(this.getCubeColor(this.getCenters(point)));
        System.out.println();
    }

    // 复原底层
    public Point3D step1_4(Point3D point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int z = (int) point.getZ();
        // System.out.println(this.getOtherCorners(point));
        if (z == level + 2 - 2) { // 欲复原角块位于第三层
            System.out.println("step1_3" + this.rotList(this.getCenters(this.getOtherCorners(point).get(1))));
            for (String rot : this.rotList(this.getCenters(this.getOtherCorners(point).get(1)))) {
                while (this.getCubeColor(this.getOtherCorners(point).get(0)) != this
                        .getCubeColor(this.getCenters(this.getOtherCorners(point).get(0)))) { // 复原底层角块时寻找底面角块相邻色块并旋转至与其中心块共面
                    turn(rot);
                    recoverWay.add(rot);
                    System.out.println("rot1:" + rot);
                    point = this.Findturn(rot, point);
                }
                break;
            }
            System.out.println("step1_4" + this.rotList(this.getCenters(point)) + "\t" + point);
            for (String rot : this.rotList(this.getCenters(point))) { // 开始复原底层
                if (this.Findturn(rot, point).getZ() == point.getZ()) { // 判断旋转之后是否还位于第三层
                    turn(rot);
                    recoverWay.add(rot);
                    System.out.println("rot2:" + rot);
                    point = this.Findturn(rot, point);
                    System.out.println("step1_5" + this.rotList(this.getCenters(this.getOtherCorners(point).get(1)))
                            + "\t" + point);
                    for (String rot1 : this.rotList(this.getCenters(this.getOtherCorners(point).get(1)))) {
                        if (this.getCubeColor(this.getCenters(this.Findturn(rot1, point))) != this
                                .getCubeColor(this.getCenters(this.getOtherCorners(point).get(0)))) {
                            turn(rot1);
                            recoverWay.add(rot1);
                            point = this.Findturn(rot1, point);
                            System.out.println("rot3:" + rot1);
                            System.out.println(this.getCubeColor(point) + "\t"
                                    + this.getCubeColor(new Point3D(point.getX(), point.getY(), point.getZ() - 1)));
                            System.out.println("step1_7"
                                    + this.rotList(this.getCenters(this.getOtherCorners(point).get(0))) + "\t" + point);
                            for (String rot2 : this.rotList(this.getCenters(this.getOtherCorners(point).get(0)))) {
                                while (this.getCubeColor(this.getCenters(point)) != this
                                        .getCubeColor(new Point3D(2, 2, 0))) {
                                    System.out.println("rot4:" + rot2);
                                    turn(rot2);
                                    recoverWay.add(rot2);
                                    point = this.Findturn(rot2, point);
                                }
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        } else { // 欲复原角块位于顶层或第一层
            System.out.println(point);
            if (point.getZ() == 1) { // 角块在第一层 需要将角块旋转至顶层
                System.out.println("test:" + this.rotList(this.getCenters(this.getOtherCorners(point).get(0))));
                for (String rot3 : this.rotList(this.getCenters(this.getOtherCorners(point).get(0)))) {
                    if (this.Findturn(rot3, point).getZ() != 0) {
                        turn(rot3);
                        recoverWay.add(rot3);
                        point = this.Findturn(rot3, point);
                        System.out.println("step1_8" + rot3 + "\t" + point);
                        for (String rot4 : this.rotList(this.getCenters(point))) {
                            if (this.getCubeColor(this.getCenters(this.getOtherCorners(point).get(0))) == this
                                    .getCubeColor(
                                            this.getCenters(this.Findturn(rot4, this.getOtherCorners(point).get(1))))) {
                                turn(rot4);
                                recoverWay.add(rot4);
                                point = this.Findturn(rot4, point);
                                System.out.println("step1_9" + rot4 + "\t" + point);
                                break;
                            }
                        }
                        turn(this.getDisRot(rot3));
                        recoverWay.add(this.getDisRot(rot3));
                        System.out.println("disrot:" + this.getDisRot(rot3));
                        break;
                    }
                }
            } else if (point.getZ() == level + 2 - 1) { // 角块在顶层的情况
                System.out.println(this.getCubeColor(new Point3D(point.getX(), point.getY(), 0)));
                while (this.getCubeColor(new Point3D(point.getX(), point.getY(), 0)) == this.getCubeColor(point)) { // 向下查找一个空白的位置
                    for (String rot5 : this.rotList(this.getCenters(point))) {
                        turn(rot5);
                        recoverWay.add(rot5);
                        point = this.Findturn(rot5, point);
                        System.out.println("step1_10" + rot5 + "\t" + point);
                        break;
                    }
                }
                System.out.println("Point:" + point);
                System.out.println(color[1][1][0] == color[2][2][0]);
                System.out.println(color[3][1][0] == color[2][2][0]);
                System.out.println(color[1][3][0] == color[2][2][0]);
                System.out.println(color[3][3][0] == color[2][2][0]);
                for (String rot6 : this.rotList(this.getOtherCorners(point).get(0))) {
                    if (this.Findturn(rot6, point).getZ() != 1) { // 旋转后位于第三层
                        turn(rot6);
                        recoverWay.add(rot6);
                        point = this.Findturn(rot6, point);
                        System.out.println("step1_11" + rot6 + "\t" + point);
                        for (String rot7 : this.rotList(this.getCenters(this.getOtherCorners(point).get(1)))) {
                            if (this.getCubeColor(this.getCenters(this.getOtherCorners(point).get(0))) != this
                                    .getCubeColor(this.getCenters(this.Findturn(rot7, point)))) { // 旋转至不同边块的那一侧
                                turn(rot7);
                                turn(rot7); // 旋转两次是为了预防有两个面块都在顶层且同侧造成的边块列表死循环的情况
                                recoverWay.add(rot7);
                                recoverWay.add(rot7);
                                point = this.Findturn(rot7, point);
                                System.out.println("step1_12" + rot7 + "\t" + point);
                                point = this.Findturn(rot7, point);
                                System.out.println("step1_12" + rot7 + "\t" + point);
                                break;
                            }
                        }
                        System.out.println("disRot:" + this.getDisRot(rot6));
                        turn(this.getDisRot(rot6));
                        recoverWay.add(this.getDisRot(rot6));
                        break;
                    }
                }
            }
        }
        return point;
    }

    public ArrayList<Point3D> getColorEdges(int color, int limit) {
        ArrayList<Point3D> edges = new ArrayList<Point3D>();
        Point3D center = null;
        for (Point3D point : centers) {
            if (this.getCubeColor(point) == color) {
                center = point;
                break;
            }
        }
        for (Point3D point : this.getFace(center)) {
            if ((point.getZ() == limit || limit == -1) && !this.isSame(point, center)) {
                edges.add(point);
            }
        }
        return edges;
    }

    public boolean isfinsh(Point3D point1, Point3D point2) {
        if ((this.getCubeColor(point1) == this.getCubeColor(this.getCenters(point1)))
                && (this.getCubeColor(point2) == this.getCubeColor(this.getCenters(point2)))) {
            return true;
        }
        return false;
    }

    public Point3D getNearCube(Point3D point, int localLF, int localUD) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int z = (int) point.getZ();
        if (x == 0 || x == level + 2 - 1) {
            return new Point3D(point.getX(), point.getY() + localLF, point.getZ() + localUD);
        } else if (y == 0 || y == level + 2 - 1) {
            return new Point3D(point.getX() + localLF, point.getY(), point.getZ() + localUD);
        } else if (z == 0 || z == level + 2 - 1) {
            return new Point3D(point.getX() + localLF, point.getY() + localUD, point.getZ());
        }
        return null;
    }

    // 复原第二层
    public void step2() {
        for (int i = 1; i <= 4; i++) { // 按每一个面进行复原
            System.out.println("i=" + i);
            while (!this.isfinsh(this.getColorEdges(i, 2).get(0), this.getOtherEdges(this.getColorEdges(i, 2).get(0)))
                    || !this.isfinsh(this.getColorEdges(i, 2).get(1),
                    this.getOtherEdges(this.getColorEdges(i, 2).get(1)))) {
                System.out.println(this.isfinsh(this.getColorEdges(i, 2).get(0),
                        this.getOtherEdges(this.getColorEdges(i, 2).get(0))));
                System.out.println(this.isfinsh(this.getColorEdges(i, 2).get(1),
                        this.getOtherEdges(this.getColorEdges(i, 2).get(1))));
                ArrayList<Point3D> edges = this.getColorEdges(i); // 获取该面的边块
                System.out.println(edges);
                for (Point3D point : edges) {
                    System.out.println(point);
                    if (point.getZ() > 1
                            && (this.getCubeColor(new Point3D(2, 2, 4)) != this.getCubeColor(this.getOtherEdges(point)))
                            && (!this.isfinsh(point, this.getOtherEdges(point)))) { // 去除位于第一层或已复原、与顶层相邻的边块
                        if (point.getZ() == 2) { // 一种色块在第二层但色序不同的解决方案,将色块调整至第三层
                            System.out.println("point=2:" + this.rotList(this.getCenters(this.getOtherEdges(point))));
                            for (String rot : this.rotList(this.getCenters(this.getOtherEdges(point)))) {
                                if (this.getCubeColor(this.getCenters(this.Findturn(rot, point))) == this
                                        .getCubeColor(new Point3D(2, 2, 4))) {
                                    Point3D shadow = point;
                                    turn(rot);
                                    recoverWay.add(rot);
                                    point = this.Findturn(rot, point);
                                    System.out.println("rot:" + rot + "\t" + point);
                                    for (String rot1 : this.rotList(this.getCenters(point))) {
                                        if (this.getCubeColor(
                                                this.getCenters(this.getOtherEdges(this.Findturn(rot1, point)))) != this
                                                .getCubeColor(this.getCenters(shadow))) {
                                            turn(rot1);
                                            recoverWay.add(rot1);
                                            point = this.Findturn(rot1, point);
                                            System.out.println("rot1:" + rot1 + "\t" + point);
                                            turn(this.getDisRot(rot));
                                            recoverWay.add(this.getDisRot(rot));
                                            System.out.println("rot1:" + this.getDisRot(rot) + "\t" + point);
                                            // point = this.Findturn(rot,
                                            // point);
                                            turn(rot1);
                                            recoverWay.add(rot1);
                                            point = this.Findturn(rot1, point);
                                            System.out.println("rot1:" + rot1 + "\t" + point);
                                            for (String rot2 : this.rotList(this.getCenters(shadow))) {
                                                if (this.getCubeColor(this.getCenters(
                                                        this.getOtherEdges(this.Findturn(rot2, shadow)))) == this
                                                        .getCubeColor(new Point3D(2, 2, 4))) {
                                                    turn(rot2);
                                                    recoverWay.add(rot2);
                                                    System.out.println("rot2:" + rot2 + "\t" + point);
                                                    turn(this.getDisRot(rot1));
                                                    recoverWay.add(this.getDisRot(rot1));
                                                    point = this.Findturn(this.getDisRot(rot1), point);
                                                    System.out.println("rot2:" + this.getDisRot(rot1) + "\t" + point);

                                                    turn(this.getDisRot(rot2));
                                                    recoverWay.add(this.getDisRot(rot2));
                                                    System.out.println("rot2:" + this.getDisRot(rot2) + "\t" + point);
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                        if (point.getZ() == level + 2 - 1) {
                            point = this.getOtherEdges(point);
                        }
                        System.out.println(point);
                        for (String rot : this.rotList(this.getCenters(this.getOtherEdges(point)))) { // 旋转至与中心块共面
                            System.out.println("step2-1:" + this.rotList(this.getCenters(this.getOtherEdges(point))));
                            while (this.getCubeColor(point) != this.getCubeColor(this.getCenters(point))) {
                                turn(rot);
                                recoverWay.add(rot);
                                point = this.Findturn(rot, point);
                                System.out.println("step2-2:" + rot + "\t" + point);
                            }
                            break;
                        }

                        for (String rot1 : this.rotList(this.getCenters(this.getOtherEdges(point)))) {
                            System.out.println("step2-3:" + this.rotList(this.getCenters(this.getOtherEdges(point)))
                                    + "\t" + point);
                            if (this.getCubeColor(this.getOtherEdges(point)) != this
                                    .getCubeColor(this.getCenters(this.Findturn(rot1, point)))) {
                                turn(rot1);
                                recoverWay.add(rot1);
                                System.out.println("rot1:" + rot1);
                                Point3D shadow = new Point3D(point.getX(), point.getY(), point.getZ());
                                point = this.Findturn(rot1, point);
                                for (String rot2 : this
                                        .rotList(this.getCenters(this.Findturn(this.getDisRot(rot1), shadow)))) {
                                    turn(rot2);
                                    recoverWay.add(rot2);
                                    if ((this.getCubeColor(this.getNearCube(shadow, -1, 0)) == this
                                            .getCubeColor(new Point3D(2, 2, 0)))
                                            || (this.getCubeColor(this.getNearCube(shadow, 1, 0)) == this
                                            .getCubeColor(new Point3D(2, 2, 0)))) {
                                        turn(this.getDisRot(rot1));
                                        recoverWay.add(this.getDisRot(rot1));
                                        point = this.Findturn(this.getDisRot(rot1), point);
                                        turn(this.getDisRot(rot2));
                                        turn(this.getDisRot(rot1));
                                        recoverWay.add(this.getDisRot(rot2));
                                        recoverWay.add(this.getDisRot(rot1));
                                        shadow = new Point3D(point.getX(), point.getY(), point.getZ());
                                        point = this.Findturn(this.getDisRot(rot1), point);
                                        for (String rot4 : this.rotList(this.getCenters(shadow))) {
                                            turn(rot4);
                                            recoverWay.add(rot4);
                                            if ((this.getCubeColor(this.getNearCube(point, 1, -1)) == this
                                                    .getCubeColor(new Point3D(2, 2, 0)))
                                                    || (this.getCubeColor(this.getNearCube(point, -1, -1)) == this
                                                    .getCubeColor(new Point3D(2, 2, 0)))) {
                                                turn(rot1);
                                                recoverWay.add(rot1);
                                                point = this.Findturn(rot1, point);
                                                for (String rot5 : this.rotList(this.getCenters(point))) {
                                                    if (this.getCubeColor(this.getCenters(
                                                            this.getOtherEdges(this.Findturn(rot5, point)))) == this
                                                            .getCubeColor(this.getOtherEdges(point))) {
                                                        System.out.println(point);
                                                        System.out.println("win");
                                                        turn(rot5);
                                                        recoverWay.add(rot5);
                                                        break;
                                                    }
                                                }
                                                break;
                                            }
                                            turn(this.getDisRot(rot4));
                                            recoverWay.add(this.getDisRot(rot4));
                                        }
                                        break;

                                    }
                                    turn(this.getDisRot(rot2));
                                    recoverWay.add(this.getDisRot(rot2));
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            System.out.println(
                    this.isfinsh(this.getColorEdges(i, 2).get(0), this.getOtherEdges(this.getColorEdges(i, 2).get(0))));
            System.out.println(
                    this.isfinsh(this.getColorEdges(i, 2).get(1), this.getOtherEdges(this.getColorEdges(i, 2).get(1))));
        }
        this.printColor();
    }

    // 复原顶层十字架
    public void step3() {
        System.out.println(this.getCubeColor(new Point3D(2, 1, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
        System.out.println(this.getCubeColor(new Point3D(2, 3, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
        System.out.println(this.getCubeColor(new Point3D(1, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
        System.out.println(this.getCubeColor(new Point3D(3, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
        while ((this.getCubeColor(new Point3D(2, 1, 4)) != this.getCubeColor(new Point3D(2, 2, 4)))
                || (this.getCubeColor(new Point3D(2, 3, 4)) != this.getCubeColor(new Point3D(2, 2, 4)))
                || (this.getCubeColor(new Point3D(1, 2, 4)) != this.getCubeColor(new Point3D(2, 2, 4)))
                || (this.getCubeColor(new Point3D(3, 2, 4)) != this.getCubeColor(new Point3D(2, 2, 4)))) {
            ArrayList<Point3D> edges = this.step3_1();
            System.out.println(edges);
            System.out.println("step3_1:" + this.rotList(this.getCenters(this.getOtherEdges(edges.get(0)))));
            for (String rot : this.rotList(this.getCenters(this.getOtherEdges(edges.get(0))))) {
                if (this.getCubeColor(this.getCenters(this.Findturn(rot, edges.get(0)))) == this
                        .getCubeColor(this.getCenters(this.getOtherEdges(edges.get(1))))) {
                    System.out.println("step3_2:" + this.rotList(this.getCenters(this.getOtherEdges(edges.get(1)))));
                    for (String rot1 : this.rotList(this.getCenters(this.getOtherEdges(edges.get(1))))) {
                        if (this.getCubeColor(this.getCenters(this.Findturn(rot1, edges.get(1)))) != this
                                .getCubeColor(this.getCenters(this.getOtherEdges(edges.get(0))))) {
                            System.out.println("rot:" + rot);
                            System.out.println("rot1" + rot1);
                            turn(rot);
                            recoverWay.add(rot);
                            turn(rot1);
                            recoverWay.add(rot1);
                            for (String rot3 : this.rotList(this.getCenters(edges.get(0)))) {
                                if (this.getCubeColor(
                                        this.getCenters(this.Findturn(rot3, this.getOtherEdges(edges.get(0))))) == this
                                        .getCubeColor(edges.get(0))) {
                                    turn(rot3);
                                    recoverWay.add(rot3);
                                    turn(this.getDisRot(rot1));
                                    recoverWay.add(this.getDisRot(rot1));
                                    turn(this.getDisRot(rot3));
                                    recoverWay.add(this.getDisRot(rot3));
                                    turn(this.getDisRot(rot));
                                    recoverWay.add(this.getDisRot(rot));
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }

        System.out.println(this.getCubeColor(new Point3D(2, 1, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
        System.out.println(this.getCubeColor(new Point3D(2, 3, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
        System.out.println(this.getCubeColor(new Point3D(1, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
        System.out.println(this.getCubeColor(new Point3D(3, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
        this.printColor();
    }

    public ArrayList<Point3D> step3_1() {
        ArrayList<Point3D> edges = new ArrayList<Point3D>();
        if ((this.getCubeColor(new Point3D(2, 1, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))
                && (this.getCubeColor(new Point3D(2, 3, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))) {
            edges.add(new Point3D(1, 2, 4));
            edges.add(new Point3D(2, 1, 4));
        } else if ((this.getCubeColor(new Point3D(1, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))
                && (this.getCubeColor(new Point3D(3, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))) {
            edges.add(new Point3D(3, 2, 4));
            edges.add(new Point3D(2, 1, 4));
        } else if ((this.getCubeColor(new Point3D(1, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))
                && (this.getCubeColor(new Point3D(2, 1, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))) {
            edges.add(new Point3D(1, 2, 4));
            edges.add(new Point3D(2, 1, 4));
        } else if ((this.getCubeColor(new Point3D(2, 1, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))
                && (this.getCubeColor(new Point3D(3, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))) {
            edges.add(new Point3D(2, 1, 4));
            edges.add(new Point3D(3, 2, 4));
        } else if ((this.getCubeColor(new Point3D(3, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))
                && (this.getCubeColor(new Point3D(2, 3, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))) {
            edges.add(new Point3D(3, 2, 4));
            edges.add(new Point3D(2, 3, 4));
        } else if ((this.getCubeColor(new Point3D(2, 3, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))
                && (this.getCubeColor(new Point3D(1, 2, 4)) == this.getCubeColor(new Point3D(2, 2, 4)))) {
            edges.add(new Point3D(2, 3, 4));
            edges.add(new Point3D(1, 2, 4));
        } else {
            edges.add(new Point3D(2, 3, 4));
            edges.add(new Point3D(1, 2, 4));
        }
        return edges;

    }

    // 复原顶面
    public void step4() {
        while ((this.getCubeColor(new Point3D(1, 1, 4)) != this.getCubeColor(new Point3D(2, 2, 4)))
                || (this.getCubeColor(new Point3D(3, 1, 4)) != this.getCubeColor(new Point3D(2, 2, 4)))
                || (this.getCubeColor(new Point3D(1, 3, 4)) != this.getCubeColor(new Point3D(2, 2, 4)))
                || (this.getCubeColor(new Point3D(3, 3, 4)) != this.getCubeColor(new Point3D(2, 2, 4)))) { // 循环直到顶层复原
            System.out.println(this.getCubeColor(new Point3D(1, 1, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
            System.out.println(this.getCubeColor(new Point3D(3, 1, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
            System.out.println(this.getCubeColor(new Point3D(1, 3, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
            System.out.println(this.getCubeColor(new Point3D(3, 3, 4)) == this.getCubeColor(new Point3D(2, 2, 4)));
            Point3D center = null;
            Point3D rotPoint = null;
            Point3D rotOtherPoint = null;
            Point3D changePoint = null;
            ArrayList<Point3D> points = this.step4_1(); // 获取顶层没有复原的角块位置
            if (points.size() == 3) { // 如果没有复原的角块数量为3
                if ((points.get(0).getX() == points.get(1).getX() && points.get(0).getY() == points.get(2).getY())
                        || (points.get(0).getX() == points.get(2).getX()
                        && points.get(0).getY() == points.get(1).getY())) {
                    center = points.get(0);
                } else if ((points.get(1).getX() == points.get(0).getX()
                        && points.get(1).getY() == points.get(2).getY())
                        || (points.get(1).getX() == points.get(2).getX()
                        && points.get(1).getY() == points.get(0).getY())) {
                    center = points.get(1);
                } else if ((points.get(2).getX() == points.get(0).getX()
                        && points.get(2).getY() == points.get(1).getY())
                        || (points.get(2).getX() == points.get(1).getX()
                        && points.get(2).getY() == points.get(0).getY())) {
                    center = points.get(2);
                }
                System.out.println("center:" + center);

                for (Point3D point : this.getOtherCorners(center)) { // 通过中心角块来判断使用小鱼1还是小鱼2
                    if (this.getCubeColor(point) != this.getCubeColor(this.getCenters(center))) {
                        for (Point3D point1 : points) {
                            if ((!this.isSame(center, point1))
                                    && (point.getX() == point1.getX() || point.getY() == point1.getY())) {
                                rotPoint = point1;
                                break;
                            }
                        }
                        break;
                    }
                }
                System.out.println(rotPoint);
                for (Point3D point : this.getOtherCorners(rotPoint)) {
                    System.out.println(this.getCubeColor(point) + "\t" + this.getCubeColor(this.getCenters(rotPoint)));
                    if (this.getCubeColor(point) != this.getCubeColor(this.getCenters(rotPoint))) {
                        rotOtherPoint = point;
                    } else {
                        changePoint = point;
                    }
                }
                System.out.println("rotOtherPoint:" + rotOtherPoint + "\tchangePoint:" + changePoint);

            } else if (points.size() == 4) { // 如果未复原的角块数量为4
                rotOtherPoint = new Point3D(0, 1, 3);
                changePoint = new Point3D(1, 0, 3);
                rotPoint = new Point3D(1, 1, 4);
            } else if (points.size() == 2) { // 如果未复原的角块数量为2
                ArrayList<Point3D> states = new ArrayList<Point3D>();
                for (Point3D point : points) {
                    for (Point3D point1 : this.getOtherCorners(point)) {
                        if (this.getCubeColor(point1) == this.getCubeColor(new Point3D(2, 2, 4))) { // 获取与顶层中心块同色的面块(角块的邻面)位置
                            states.add(point1);
                        }
                    }
                }
                System.out.println("states:" + states);
                if ((states.get(0).getX() == states.get(1).getX()
                        && (states.get(0).getY() != 0 && states.get(0).getY() != level + 2 - 1))
                        || (states.get(0).getY() == states.get(1).getY()
                        && (states.get(0).getX() != 0 && states.get(0).getX() != level + 2 - 1))) { // 判断这两个面块是否在同一面上
                    if ((this.isSame(new Point3D(1, 1, 4), points.get(0))
                            && this.isSame(new Point3D(1, 3, 4), points.get(1)))
                            || (this.isSame(new Point3D(1, 3, 4), points.get(0))
                            && this.isSame(new Point3D(1, 1, 4), points.get(1)))) { // 判断这两个面块在哪一个面
                        rotPoint = new Point3D(1, 3, 4);
                    } else if ((this.isSame(new Point3D(3, 3, 4), points.get(0))
                            && this.isSame(new Point3D(1, 3, 4), points.get(1)))
                            || (this.isSame(new Point3D(1, 3, 4), points.get(0))
                            && this.isSame(new Point3D(3, 3, 4), points.get(1)))) {
                        rotPoint = new Point3D(1, 3, 4);
                    } else if ((this.isSame(new Point3D(1, 1, 4), points.get(0))
                            && this.isSame(new Point3D(3, 1, 4), points.get(1)))
                            || (this.isSame(new Point3D(3, 1, 4), points.get(0))
                            && this.isSame(new Point3D(1, 1, 4), points.get(1)))) {
                        rotPoint = new Point3D(1, 1, 4);
                    } else if ((this.isSame(new Point3D(3, 1, 4), points.get(0))
                            && this.isSame(new Point3D(3, 3, 4), points.get(1)))
                            || (this.isSame(new Point3D(3, 3, 4), points.get(0))
                            && this.isSame(new Point3D(3, 1, 4), points.get(1)))) {
                        rotPoint = new Point3D(3, 1, 4);
                    }
                    System.out.println(points);
                    System.out.println("rotPoint:" + rotPoint);
                    System.out.println(this.getOtherCorners(rotPoint));
                    for (Point3D point : this.getOtherCorners(rotPoint)) {
                        if (this.getCubeColor(point) == this.getCubeColor(new Point3D(2, 2, 4))) {
                            changePoint = point;
                        } else {
                            rotOtherPoint = point;
                        }
                    }
                    System.out.println("rotPoint:" + rotPoint);
                    System.out.println("changePoint:" + changePoint);
                    System.out.println("rotOtherPoint:" + rotOtherPoint);
                } else {
                    rotOtherPoint = new Point3D(0, 1, 3);
                    changePoint = new Point3D(1, 0, 3);
                    rotPoint = new Point3D(1, 1, 4);
                }
            }
            System.out.println("step4_1:" + this.rotList(this.getCenters(rotOtherPoint)));
            step4_2(rotOtherPoint, rotPoint, changePoint); // 小鱼公式
        }
        this.printColor();
    }

    // 统计顶层没有复原的角块位置
    public ArrayList<Point3D> step4_1() {
        ArrayList<Point3D> points = new ArrayList<Point3D>();
        if (this.getCubeColor(new Point3D(1, 1, 4)) != this.getCubeColor(new Point3D(2, 2, 4))) {
            points.add(new Point3D(1, 1, 4));
        }
        if (this.getCubeColor(new Point3D(3, 1, 4)) != this.getCubeColor(new Point3D(2, 2, 4))) {
            points.add(new Point3D(3, 1, 4));
        }
        if (this.getCubeColor(new Point3D(1, 3, 4)) != this.getCubeColor(new Point3D(2, 2, 4))) {
            points.add(new Point3D(1, 3, 4));
        }
        if (this.getCubeColor(new Point3D(3, 3, 4)) != this.getCubeColor(new Point3D(2, 2, 4))) {
            points.add(new Point3D(3, 3, 4));
        }
        return points;
    }

    // 小鱼公式
    public void step4_2(Point3D rotOtherPoint, Point3D rotPoint, Point3D changePoint) {
        System.out.println("rotOtherPoint:" + rotOtherPoint);
        System.out.println("rotPoint:" + rotPoint);
        System.out.println("changePoint:" + changePoint);
        for (String rot : this.rotList(this.getCenters(rotOtherPoint))) {
            if (this.getCubeColor(this.getCenters(this.Findturn(rot, changePoint))) == this
                    .getCubeColor(this.getCenters(rotPoint))) {
                System.out.println("step4_2:" + this.rotList(this.getCenters(rotPoint)));
                for (String rot1 : this.rotList(this.getCenters(rotPoint))) {
                    if (this.getCubeColor(this.getCenters(this.Findturn(rot1, rotOtherPoint))) == this
                            .getCubeColor(this.getCenters(changePoint))) {
                        turn(rot);
                        recoverWay.add(rot);
                        rotPoint = this.Findturn(rot, rotPoint);
                        System.out.println(rot + "\t" + rotPoint);
                        turn(rot1);
                        recoverWay.add(rot1);
                        rotPoint = this.Findturn(rot1, rotPoint);
                        System.out.println(rot1 + "\t" + rotPoint);
                        turn(this.getDisRot(rot));
                        recoverWay.add(this.getDisRot(rot));
                        rotPoint = this.Findturn(this.getDisRot(rot), rotPoint);
                        System.out.println(this.getDisRot(rot) + "\t" + rotPoint);
                        turn(rot1);
                        recoverWay.add(rot1);
                        // rotPoint=this.Findturn(rot1, rotPoint);
                        System.out.println(rot1 + "\t" + rotPoint);
                        turn(rot);
                        recoverWay.add(rot);
                        rotPoint = this.Findturn(rot, rotPoint);
                        System.out.println(rot + "\t" + rotPoint);
                        turn(this.getDisRot(rot1));
                        recoverWay.add(this.getDisRot(rot1));
                        rotPoint = this.Findturn(this.getDisRot(rot1), rotPoint);
                        System.out.println(this.getDisRot(rot1) + "\t" + rotPoint);
                        turn(this.getDisRot(rot1));
                        recoverWay.add(this.getDisRot(rot1));
                        rotPoint = this.Findturn(this.getDisRot(rot1), rotPoint);
                        System.out.println(this.getDisRot(rot1) + "\t" + rotPoint);
                        turn(this.getDisRot(rot));
                        recoverWay.add(this.getDisRot(rot));
                        // rotPoint=this.Findturn(this.getDisRot(rot),
                        // rotPoint);
                        System.out.println(this.getDisRot(rot) + "\t" + rotPoint);
                        System.out.println("finish");
                        break;
                    }
                }
                break;
            }
        }
    }

    // 调整第三层角块的色序
    public void step5() {
        System.out.println(this.step5_1(new Point3D(0, 1, 3), new Point3D(0, 3, 3)));
        System.out.println(this.step5_1(new Point3D(4, 1, 3), new Point3D(4, 3, 3)));
        System.out.println(this.step5_1(new Point3D(1, 0, 3), new Point3D(3, 0, 3)));
        System.out.println(this.step5_1(new Point3D(1, 4, 3), new Point3D(3, 4, 3)));
        while (!this.step5_1(new Point3D(0, 1, 3), new Point3D(0, 3, 3))
                || !this.step5_1(new Point3D(4, 1, 3), new Point3D(4, 3, 3))
                || !this.step5_1(new Point3D(1, 0, 3), new Point3D(3, 0, 3))
                || !this.step5_1(new Point3D(1, 4, 3), new Point3D(3, 4, 3))) {
            Point3D corner = null;
            Point3D corner1 = null;
            if (this.step5_2(new Point3D(0, 1, 3), new Point3D(0, 3, 3))) {
                corner = new Point3D(0, 1, 3);
                corner1 = new Point3D(0, 3, 3);
            } else if (this.step5_2(new Point3D(4, 1, 3), new Point3D(4, 3, 3))) {
                corner = new Point3D(4, 1, 3);
                corner1 = new Point3D(4, 3, 3);
            } else if (this.step5_2(new Point3D(1, 0, 3), new Point3D(3, 0, 3))) {
                corner = new Point3D(1, 0, 3);
                corner1 = new Point3D(3, 0, 3);
            } else if (this.step5_2(new Point3D(1, 4, 3), new Point3D(3, 4, 3))) {
                corner = new Point3D(1, 4, 3);
                corner1 = new Point3D(3, 4, 3);
            } else {
                corner = new Point3D(0, 1, 3);
                corner1 = new Point3D(0, 3, 3);
            }

            System.out.println(corner + "\t" + corner1);
            System.out.println("step5_1:" + this.rotList(this.getCenters(corner)));
            for (String rot : this.rotList(this.getCenters(corner))) {
                if (this.getCubeColor(this.getCenters(this.getOtherCorners(corner).get(0))) == this
                        .getCubeColor(this.getCenters(this.Findturn(rot, this.getOtherCorners(corner).get(1))))) {
                    System.out.println("rot:" + rot);
                    // System.out.println(this.rotList(this.getCenters(this.getOtherCorners(corner1).get(0))));
                    for (String rot1 : this.rotList(this.getCenters(this.getOtherCorners(corner1).get(0)))) {
                        if (this.getCubeColor(
                                this.getCenters(this.Findturn(rot1, this.getOtherCorners(corner1).get(1)))) == this
                                .getCubeColor(this.getCenters(corner1))) {
                            System.out.println("rot1:" + rot1);
                            for (String rot2 : this.rotList(this.getCenters(this.getOtherCorners(corner).get(0)))) {
                                if (this.getCubeColor(this
                                        .getCenters(this.Findturn(rot2, this.getOtherCorners(corner).get(1)))) == this
                                        .getCubeColor(this.getCenters(corner))) {
                                    System.out.println("rot2:" + rot2);
                                    turn(rot);
                                    recoverWay.add(rot);
                                    turn(rot);
                                    recoverWay.add(rot);
                                    turn(rot1);
                                    recoverWay.add(rot1);
                                    turn(rot1);
                                    recoverWay.add(rot1);
                                    turn(this.getDisRot(rot));
                                    recoverWay.add(this.getDisRot(rot));
                                    turn(rot2);
                                    recoverWay.add(rot2);
                                    turn(rot);
                                    recoverWay.add(rot);
                                    turn(this.getDisRot(rot1));
                                    recoverWay.add(this.getDisRot(rot1));
                                    turn(this.getDisRot(rot1));
                                    recoverWay.add(this.getDisRot(rot1));
                                    turn(this.getDisRot(rot));
                                    recoverWay.add(this.getDisRot(rot));
                                    turn(this.getDisRot(rot2));
                                    recoverWay.add(this.getDisRot(rot2));
                                    turn(this.getDisRot(rot));
                                    recoverWay.add(this.getDisRot(rot));
                                    while (this.getCubeColor(corner) != this.getCubeColor(this.getCenters(corner))) {
                                        System.out.println("U");
                                        turn("U");
                                        recoverWay.add("U");
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }

        this.printColor();
    }

    public boolean step5_1(Point3D point, Point3D point1) {
        if (this.getCubeColor(point) == this.getCubeColor(point1)
                && this.getCubeColor(this.getCenters(point)) == this.getCubeColor(point)) {
            return true;
        }
        return false;
    }

    public boolean step5_2(Point3D point, Point3D point1) {
        if (this.getCubeColor(point) == this.getCubeColor(point1)) {
            return true;
        }
        return false;
    }

    // 第三层复原的最后一步
    public void step6() {
        while (this.getCubeColor(new Point3D(0, 2, 3)) != this.getCubeColor(this.getCenters(new Point3D(0, 2, 3)))
                || this.getCubeColor(new Point3D(2, 0, 3)) != this.getCubeColor(this.getCenters(new Point3D(2, 0, 3)))
                || this.getCubeColor(new Point3D(4, 2, 3)) != this.getCubeColor(this.getCenters(new Point3D(4, 2, 3)))
                || this.getCubeColor(new Point3D(2, 4, 3)) != this
                .getCubeColor(this.getCenters(new Point3D(2, 4, 3)))) {
            Point3D front = null;
            Point3D rotOtherPoint;
            Point3D changePoint;
            Point3D rotPoint = null;
            if (this.getCubeColor(this.getCenters(new Point3D(0, 2, 3))) == this.getCubeColor(new Point3D(0, 2, 3))) { // 查找是否有一个面已复原
                front = new Point3D(0, 2, 3);
            } else if (this.getCubeColor(this.getCenters(new Point3D(4, 2, 3))) == this
                    .getCubeColor(new Point3D(4, 2, 3))) {
                front = new Point3D(4, 2, 3);
            } else if (this.getCubeColor(this.getCenters(new Point3D(2, 4, 3))) == this
                    .getCubeColor(new Point3D(2, 4, 3))) {
                front = new Point3D(2, 4, 3);
            } else if (this.getCubeColor(this.getCenters(new Point3D(2, 0, 3))) == this
                    .getCubeColor(new Point3D(2, 0, 3))) {
                front = new Point3D(2, 0, 3);
            } else {
                front = new Point3D(2, 0, 3);
            }
            System.out.println(front);
            System.out.println(this.rotList(this.getCenters(this.getOtherEdges(front))));
            Point3D shadow = null;
            int status = 0;
            for (String rot : this.rotList(this.getCenters(this.getOtherEdges(front)))) {
                shadow = this.Findturn(rot, front);
                if (this.getCubeColor(this.getCenters(this.Findturn(rot, shadow))) == this.getCubeColor(shadow)
                        || this.getCubeColor(this.getCenters(this.Findturn(rot, this.Findturn(rot, shadow)))) == this
                        .getCubeColor(shadow)
                        || this.getCubeColor(this
                        .getCenters(this.Findturn(rot, this.Findturn(rot, this.Findturn(rot, shadow))))) == this
                        .getCubeColor(this.Findturn(rot, shadow))
                        || this.getCubeColor(this.getCenters(this.Findturn(rot,
                        this.Findturn(rot, this.Findturn(rot, this.Findturn(rot, shadow)))))) == this
                        .getCubeColor(this.Findturn(rot, this.Findturn(rot, shadow)))
                        || this.getCubeColor(this.getCenters(this.Findturn(rot,
                        this.Findturn(rot,
                                this.Findturn(rot, this.Findturn(rot, this.Findturn(rot, shadow))))))) == this
                        .getCubeColor(this.Findturn(rot,
                                this.Findturn(rot, this.Findturn(rot, shadow))))) { // 判断
                    // 在哪个位置使用小鱼1
                    if (this.isSame(front, new Point3D(0, 2, 3))) { // 若顺序是逆时针或是中心对称
                        rotPoint = new Point3D(3, 1, 4);
                    } else if (this.isSame(front, new Point3D(4, 2, 3))) {
                        rotPoint = new Point3D(1, 3, 4);
                    } else if (this.isSame(front, new Point3D(2, 4, 3))) {
                        rotPoint = new Point3D(1, 1, 4);
                    } else if (this.isSame(front, new Point3D(2, 0, 3))) {
                        rotPoint = new Point3D(3, 3, 4);
                    }
                    status = 0;
                    break;
                } else {
                    if (this.isSame(front, new Point3D(0, 2, 3))) {
                        rotPoint = new Point3D(3, 3, 4);
                    } else if (this.isSame(front, new Point3D(4, 2, 3))) {
                        rotPoint = new Point3D(1, 1, 4);
                    } else if (this.isSame(front, new Point3D(2, 4, 3))) {
                        rotPoint = new Point3D(3, 1, 4);
                    } else if (this.isSame(front, new Point3D(2, 0, 3))) {
                        rotPoint = new Point3D(1, 3, 4);
                    }
                    status = 1;
                    break;
                }
            }
            changePoint = this.step6_1(rotPoint, front, status).get(0);
            rotOtherPoint = this.step6_1(rotPoint, front, status).get(1);
            System.out.println("rotPoint" + rotPoint);
            System.out.println("rotOtherPoint" + rotOtherPoint);
            System.out.println("changePoint" + changePoint);
            System.out.println(this.step6_1(rotPoint, shadow, status));
            this.step4_2(rotOtherPoint, rotPoint, changePoint);
            this.step4();
        }
    }

    public ArrayList<Point3D> step6_1(Point3D rotPoint, Point3D front, int status) {
        Point3D rotOtherPoint = null;
        Point3D changePoint = null;
        ArrayList<Point3D> points = new ArrayList<Point3D>();
        System.out.println("shadow:" + front);
        if (status == 0) {

        }
        if (front.getX() == 0 || front.getX() == level + 2 - 1) {
            for (Point3D point : this.getOtherCorners(rotPoint)) {
                if ((point.getX() == 0 || point.getX() == level + 2 - 1) && status == 0) {
                    changePoint = point;
                    break;
                } else if (status == 1 && (point.getX() != 0 || point.getX() != level + 2 - 1)) {
                    changePoint = point;
                    break;
                }
            }
        } else if (front.getY() == 0 || front.getY() == level + 2 - 1) {
            for (Point3D point : this.getOtherCorners(rotPoint)) {
                if ((point.getY() == 0 || point.getY() == level + 2 - 1) && status == 0) {
                    changePoint = point;
                    break;
                } else if ((point.getY() != 0 || point.getY() != level + 2 - 1) && status == 1) {
                    changePoint = point;
                    break;
                }
            }
        }
        points.add(changePoint);
        for (Point3D point : this.getOtherCorners(rotPoint)) {
            if (!points.contains(point)) {
                points.add(point);
            }
        }
        System.out.println(changePoint);
        System.out.println(rotOtherPoint);

        return points;
    }

    public ArrayList<String> getRecoverWay() {
        return recoverWay;
    }

    public void startRecover() {
        recoverWay.clear();
        setCubeBackup();
        this.step1();
        this.step2();
        this.step3();
        this.step4();
        this.step5();
        this.step6();
        recoverCubeArray(color);
    }

    public void recoverCubeArray(int[][][] color) {
        for (int i = 0; i < level + 2; i++) {
            for (int j = 0; j < level + 2; j++) {
                for (int k = 0; k < level + 2; k++) {
                    color[i][j][k] = cubeBackup[i][j][k];
                }
            }
        }
    }

    public boolean isRecover(){
        boolean isrecover =true;
        for(Point3D point : edges){
            if(this.getCubeColor(point) !=this.getCubeColor(this.getCenters(point))){
                isrecover=false;
                break;
            }
        }
        for(Point3D point : corners){
            if(this.getCubeColor(point) !=this.getCubeColor(this.getCenters(point))){
                isrecover=false;
                break;
            }
        }
        System.out.println("recover:\t"+isrecover);
        return isrecover;
    }

    public ArrayList<String> getColorArray(){
        ArrayList<String> colorSequence = new ArrayList<String>();
        for (int i = 0; i < level + 2; i++) {
            for (int j = 0; j < level + 2; j++) {
                for (int k = 0; k < level + 2; k++) {
                    colorSequence.add(String.valueOf(color[i][j][k]));
                }
            }
        }
        return colorSequence;
    }

    public void setColorArray(ArrayList<String> colorSequence){
        int index=0;
        for (int i = 0; i < level + 2; i++) {
            for (int j = 0; j < level + 2; j++) {
                for (int k = 0; k < level + 2; k++) {
                    color[i][j][k]= Integer.parseInt(colorSequence.get(index));
                    index++;
                }
            }
        }
    }
}
