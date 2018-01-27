package frame;

import engine.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class gameFrameController {
    frameWork rubik;

    @FXML
    private SubScene gameScene;

    @FXML
    private Button optionButton;

    @FXML
    private Button startButton;

    @FXML
    private Pane menuPane;

    @FXML
    private TextArea gameInfo;

    @FXML
    private Pane optionPanel;

    @FXML
    private Button tipsButtion;

    @FXML
    private Button optionPanel_restButton;

    @FXML
    private Button optionPanel_colseButton;

    @FXML
    private Slider optionPanel_RubikSensibilityControler;

    @FXML
    private ScrollPane gameInfoPane;

    @FXML
    private Button recoverButton;

    @FXML
    private Button restButton;

    @FXML
    private Button readButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button gameInfoButton;

    @FXML
    private Group mainFrame;

    @FXML
    private Button saveButton;

    @FXML
    private Label timeLabel;

    @FXML
    void handleStartButtonOnMouseAction(ActionEvent event) {
        if(startButton.getText().equals("开始")){
            startButton.setText("暂停");
            if(rubik !=null){
                rubik.shutDownEngine();
            }
            rubik = new frameWork();
            gameScene.setRoot(rubik.getRoot());
            gameScene.setCamera(rubik.getCamera());
            rubik.disorganizeRubik();
            rubik.gameTimerClear();
            rubik.startGameTime();
            rubik.addTimerAdapter(new TimerAdapter() {
                @Override
                public void getTimerText(TimerTextEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Update UI here.
                            timeLabel.setText("" + event.getTime());
                        }
                    });
                }
            });

            rubik.addRubikCoverAdapter(new RubikRecoverAdapter() {
                @Override
                public void isRecover(RubikRecoverEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Update UI here.
                            if(event.getIsrecover())
                                recoverButton.setText("复原");
                        }
                    });
                }
            });

            gameScene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    rubik.setCameraOnMousePressed(event);
                }
            });

            gameScene.setOnMouseDragged(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    rubik.setCameraOnMouseDragged(event);
                }
            });

            gameScene.setOnScroll(new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent event) {
                    rubik.setCameraOnScroll(event);

                }
            });
        }else if(startButton.getText().equals("暂停")){
            startButton.setText("继续");
            rubik.pauseGameTimer();
            gameScene.setDisable(true);

        }else if(startButton.getText().equals("继续")){
            startButton.setText("暂停");
            rubik.createGameTimer();
            rubik.startGameTime();
            gameScene.setDisable(false);
        }
    }

    @FXML
    void handleRestButtonOnMouseAction(ActionEvent event) {
        if(rubik !=null){
            rubik.shutDownEngine();
        }
        rubik = new frameWork();
        gameScene.setRoot(rubik.getRoot());
        gameScene.setCamera(rubik.getCamera());
        rubik.disorganizeRubik();
        rubik.gameTimerClear();
        rubik.startGameTime();
        rubik.addTimerAdapter(new TimerAdapter() {
            @Override
            public void getTimerText(TimerTextEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // Update UI here.
                        timeLabel.setText("" + event.getTime());
                    }
                });
            }
        });

    }

    @FXML
    void hundleCloseButtonAction(ActionEvent event) {
        if (closeButton.getText().equals("↑")) {
            menuPane.setVisible(false);
            closeButton.setText("↓");
        } else if (closeButton.getText().equals("↓")) {
            menuPane.setVisible(true);
            closeButton.setText("↑");
        }


    }

    @FXML
    void hundlerecoverButtonAction(ActionEvent event) {
        if (recoverButton.getText().equals("复原")) {
            rubik.cubeRecover();
            rubik.recoverRubik();
            recoverButton.setText("暂停");
        } else if (recoverButton.getText().equals("暂停")) {
            rubik.pauseRubikRecover();
            recoverButton.setText("复原");
        }

    }

    @FXML
    void hundleTipsButtionAction(ActionEvent event) {
        String recoverWay=new String();
        for (String str : rubik.getTips()) {
            System.out.println(str);
            recoverWay+=(str+"\n");
        }
        gameInfo.setText(recoverWay);
    }

    @FXML
    void handleSaveButtonAction(ActionEvent event){
        rubik.saveGame();
    }

    @FXML
    void handleReadButtonAction(ActionEvent event){
        rubik.loadGameSave();
    }

    @FXML
    void handleOptionButtonAction(ActionEvent event){
        double MajorTickUnit=(rubik.getMouseSensitivityMax()-rubik.getMouseSensitivityMin())/5;
        optionPanel.setVisible(true);
        optionPanel_RubikSensibilityControler.setMin(rubik.getMouseSensitivityMin());
        optionPanel_RubikSensibilityControler.setMax(rubik.getMouseSensitivityMax());
        optionPanel_RubikSensibilityControler.setMajorTickUnit(MajorTickUnit);
        optionPanel_RubikSensibilityControler.setValue(rubik.getMouseSensitivity());
        optionPanel_RubikSensibilityControler.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                rubik.setMouseSensitivity((Double) newValue);
                System.out.println(rubik.getMouseSensitivity());
            }
        });
    }


    @FXML
    void huandleOptionPanel_colseButtonAction(ActionEvent event){
        optionPanel.setVisible(false);
    }

    @FXML
    void hundleOptionPanel_restButtonAction(ActionEvent event){
        rubik.setMouseSensitivity(rubik.getMouseSensitivityDefault());
        optionPanel_RubikSensibilityControler.setValue(rubik.getMouseSensitivityDefault());
    }

    @FXML
    void hundleGameInfoButtonAction(ActionEvent event){
        if(gameInfoButton.getText().equals("信息面板(开)")){
            gameInfoPane.setVisible(true);
            gameInfoButton.setText("信息面板(关)");
        }else{
            gameInfoPane.setVisible(false);
            gameInfoButton.setText("信息面板(开)");
        }

    }
}
