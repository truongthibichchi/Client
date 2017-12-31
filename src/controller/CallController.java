package controller;

import connection.Listener;
import connection.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import util.CallRecorder;
import util.VoiceUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CallController extends StageSceneController implements Initializable {
    @FXML private BorderPane pane;
    @FXML private ImageView imgClose;
    @FXML private Circle cirAvatar;
    @FXML private Label lblNickname, lblNoti;
    @FXML private ImageView imgAccept, imgDecline;

    private double xOffset;
    private double yOffset;

    private ArrayList<User> users;

    private Listener listener;
    private User userMain;

    public void setUser(User user) {
        this.userMain = user;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void imgCloseAction(){
        this.stage.close();
    }
    public void addDragAndDropHandler() {
        pane.setOnMousePressed(event -> {
            xOffset = this.stage.getX() - event.getScreenX();
            yOffset =this.stage.getY() - event.getScreenY();
            pane.setCursor(Cursor.CLOSED_HAND);
        });
        pane.setOnMouseDragged(event -> {
            this.stage.setX(event.getScreenX() + xOffset);
            this.stage.setY(event.getScreenY() + yOffset);
        });
        pane.setOnMouseReleased(event -> {
            pane.setCursor(Cursor.DEFAULT);
        });
    }

    public void drawUser(ArrayList<User> users){
        Platform.runLater(() -> {
           for(User user: users){
               if(!user.getUsername().equals(userMain.getUsername())){
                   Image imgAvatar = new Image(getClass().getClassLoader().getResource("images/avatars/" + user.getUsername() + ".png").toString());
                   cirAvatar.setFill(new ImagePattern(imgAvatar));
                   lblNickname.setText(user.getUsername());
               }
           }
        });
    }

    public void setVisibleForImgAccept(String username){
        if (!userMain.getUsername().equals(username)){
            imgAccept.setVisible(true);
        }
    }

    public void showNoti(String x){
        Platform.runLater(()->{
            lblNoti.setText(x);
        });
    }

    public void imgAcceptAction() throws IOException {
            listener.respondCallAccept(userMain.getUsername(), users);
            CallRecorder recorder = new CallRecorder();
            recorder.setUsername(userMain.getUsername());
            recorder.setParticipants(users);
            recorder.captureAudio();
    }

    public void imgDeclineAction() throws IOException {
        listener.respondCallDecline(userMain.getUsername(), users);
        CallRecorder.setRecording(false);
        showNoti("End call!");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgAccept.setVisible(false);
    }
}