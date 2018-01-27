package game;

import java.util.Timer;
import java.util.TimerTask;

import engine.autoMatically;
import engine.frameWork;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GameFrame extends Application {
	private Group root = new Group();
	private final BorderPane pane = new BorderPane();
	private frameWork rubik;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ToolBar leftBottom = new ToolBar(new Button("��1"), new Button("��2"), new Button("��3"), new Separator(),
				new Button("��1"), new Button("��2"), new Button("��3"));
		leftBottom.setOrientation(Orientation.VERTICAL);
		pane.setLeft(leftBottom);
		ToolBar tbBottom = new ToolBar(new Button("��1"), new Button("��2"), new Button("��3"), new Separator(),
				new Button("��1"), new Button("��2"), new Button("��3"));
		pane.setBottom(tbBottom);
		ToolBar rightBottom = new ToolBar(new Button("���1"), new Button("���2"), new Button("���3"), new Separator(),
				new Button("�Ҳ�1"), new Button("�Ҳ�2"), new Button("�Ҳ�3"));
		rightBottom.setOrientation(Orientation.VERTICAL);
		pane.setRight(rightBottom);
		tbBottom.getItems().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getClickCount() != 0) {
					System.out.println("��1");
				}
			}
		});

		tbBottom.getItems().get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getClickCount() != 0) {
					System.out.println("��2");
				}
			}
		});

		tbBottom.getItems().get(2).setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getClickCount() != 0) {
					System.out.println("��3");
				}
			}
		});

		tbBottom.getItems().get(4).setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getClickCount() != 0) {
					System.out.println("��1");
				}
			}
		});

		tbBottom.getItems().get(5).setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getClickCount() != 0) {
					System.out.println("��2");
				}
			}
		});

		tbBottom.getItems().get(6).setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getClickCount() != 0) {
					System.out.println("��3");
				}
			}
		});

		final Scene scene = new Scene(root, 1024, 768, true);
//		rubik = new frameWork(scene);
		rubik = new frameWork();
		root.getChildren().add(rubik.getRoot());
		scene.setFill(Color.ALICEBLUE);
		scene.setCamera(rubik.getCamera());
		primaryStage.setTitle("Rubik's Cube");
		primaryStage.setScene(scene);
		primaryStage.show();
		actionEvent(scene);
	}

	public void actionEvent(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.X) {
					System.out.println("Rotation X");
				} else if (event.getCode() == KeyCode.Y) {
					rubik.rotation("Y",1);
					System.out.println("Rotation Y");
				} else if (event.getCode() == KeyCode.R) {
					rubik.rotation("Ri",1);
					System.out.println("Rotation Ri");
				} else if (event.getCode() == KeyCode.D) {
					rubik.rotation("D",1);
					System.out.println("Rotation Di");
				} else if (event.getCode() == KeyCode.L) {
					rubik.rotation("Li",1);
					System.out.println("Rotation Li");
				} else if (event.getCode() == KeyCode.U) {
					rubik.rotation("U",1);
					System.out.println("Rotation U");
				}else if (event.getCode() == KeyCode.F) {
					rubik.rotation("Fi",1);
					System.out.println("Rotation Fi");
				}else if (event.getCode() == KeyCode.Z) {
					rubik.rotation("Z",1);
					System.out.println("Rotation Z");
				}else if (event.getCode() == KeyCode.B) {
					rubik.rotation("B",1);
					System.out.println("Rotation B");
				}else if (event.getCode() == KeyCode.Q) {
					rubik.cubeRecover();
					System.out.println(rubik.getRecoverWay().size());
					System.out.println(rubik.getRecoverWay());
					System.out.println("Rotation B");

				}else if(event.getCode() == KeyCode.E){
					IntegerProperty  count = new SimpleIntegerProperty();
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(count.get() >=rubik.getRecoverWay().size()-1){
								timer.cancel();
							}
							System.out.println("rot:"+rubik.getRecoverWay().get(count.get()));
							rubik.rotation(rubik.getRecoverWay().get(count.get()),1);
							count.set(count.get()+1);
						}
					}, 20,1000);
				}
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
