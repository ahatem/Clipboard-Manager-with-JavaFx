import java.util.HashMap;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    public static ListView<String> list;
    public static GetClipboardText getText = new GetClipboardText();
    public static HashMap<String,String> allText = new HashMap<String,String>();
    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Clipboard Manager - Ahmed Hatem");

        TextArea textArea = new TextArea();
        textArea.setFont(Font.font("Arial" , FontWeight.BOLD , 16));

        list = new ListView<String>();
        list.setStyle("-fx-font-weight: bold");
        list.getItems().add("element");
        list.getSelectionModel().selectedItemProperty().addListener((v , oldVal , newVal) -> {
            textArea.setText(allText.get(newVal));
        });
        list.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    TextInputDialog tid = new TextInputDialog();
                    tid.setTitle("Rename this value");
                    tid.setHeaderText("Enter your new Text");
                    tid.showAndWait();
                    if (!tid.getResult().equals(null)){
                        String temp = allText.get(list.getSelectionModel().getSelectedItem());
                        allText.remove(list.getSelectionModel().getSelectedItem());
                        allText.put(tid.getResult(), temp);
                        list.getItems().set(list.getSelectionModel().getSelectedIndex() , tid.getResult());
                    }
                }
            }
        });


        SplitPane centerLayout = new SplitPane();
        centerLayout.getItems().addAll(list , textArea);

        Button delBtn = new Button("Delete");
        delBtn.setFont(Font.font("Arial" , FontWeight.BOLD , 16));
        delBtn.setOnAction(e -> {
            allText.remove(list.getItems().get(list.getSelectionModel().getSelectedIndex()));
            list.getItems().remove(list.getSelectionModel().getSelectedIndex());
        });

        Button copyBtn = new Button("Copy Value");
        copyBtn.setFont(Font.font("Arial" , FontWeight.BOLD , 16));
        copyBtn.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(textArea.getText());
            clipboard.setContent(content);
        });

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(delBtn , copyBtn);

        BorderPane mainLayout = new BorderPane();
        
        mainLayout.setCenter(centerLayout);
        mainLayout.setTop(toolBar);

        Scene scene = new Scene(mainLayout , 600 , 400);
        mainStage.setScene(scene);
        mainStage.setAlwaysOnTop(false);
        mainStage.show();

        
        list.getItems().remove(0);
        getText.start();
        
    }
 
    @Override
    public void stop() throws Exception {
        getText.run = false;
    }

    public static void main(String[] args) {
        launch(args);        
    }
}
