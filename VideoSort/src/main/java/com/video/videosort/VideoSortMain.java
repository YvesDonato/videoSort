package com.video.videosort;

// Jaffree imports
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Stream;

// Javafx imports
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

// Other imports
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class VideoSortMain extends Application {
    private static ArrayList<Video> Videos = new ArrayList();
    private static Boolean toggle = true;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Library lib = new Library();
        primaryStage.setResizable(false);

        // ComboBox
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("Name");
        comboBox.getItems().add("File Type");
        comboBox.getItems().add("Length");
        comboBox.getItems().add("Size");
        comboBox.getItems().add("Resolution");
        comboBox.setPrefWidth(150.0);

        // ListView
        ListView<String> listView = new ListView<>();
        listView.setMaxHeight(150);

        // DirectoryChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // Buttons
        Button options = new Button("Options");
        options.setPrefWidth(65.0);
        Button back = new Button("Return");
        Button button = new Button("Select Directory");
        ToggleButton toggleButton1 = new ToggleButton("OFF");
        toggleButton1.setPrefWidth(80.0);
        ColorPicker color = new ColorPicker();

        TextField text = new TextField();

        HBox hbox = new HBox(comboBox,text);

        // Anchorpanes
        AnchorPane plane1 = new AnchorPane();

        AnchorPane.setTopAnchor(options, 10.0);
        AnchorPane.setRightAnchor(options, 10.0);

        AnchorPane.setTopAnchor(hbox, 50.0);
        AnchorPane.setLeftAnchor(hbox, 100.0);
        AnchorPane.setRightAnchor(hbox, 100.0);

        AnchorPane.setTopAnchor(listView, 100.0);
        AnchorPane.setLeftAnchor(listView, 100.0);
        AnchorPane.setRightAnchor(listView, 100.0);
        AnchorPane.setBottomAnchor(listView,  100.0);

        AnchorPane.setLeftAnchor(button, 100.0);
        AnchorPane.setRightAnchor(button, 100.0);
        AnchorPane.setBottomAnchor(button,  75.0);

        AnchorPane.setTopAnchor(listView, 100.0);
        AnchorPane.setLeftAnchor(button, 100.0);
        AnchorPane.setRightAnchor(button, 100.0);

        plane1.getChildren().addAll(options,hbox,listView,button);

        // Pane2 anchors
        AnchorPane plane2 = new AnchorPane();

        AnchorPane.setTopAnchor(back, 10.0);
        AnchorPane.setRightAnchor(back, 10.0);

        AnchorPane.setTopAnchor(toggleButton1, 10.0);
        AnchorPane.setLeftAnchor(toggleButton1, 10.0);

        AnchorPane.setTopAnchor(color, 10.0);
        AnchorPane.setLeftAnchor(color, 105.0);
        plane2.getChildren().addAll(back,toggleButton1);

        // Scene
        Scene scene1 = new Scene(plane1,1280, 720);
        Scene scene2 = new Scene(plane2,400, 200);

        // Listeners
        toggleButton1.setOnAction(e ->{
            if(toggle) {
                toggleButton1.setText("ON");
                toggle = false;
                plane2.getChildren().add(color);
            }
            else{
                toggleButton1.setText("OFF");
                toggle = true;
                plane2.getChildren().remove(2);
                BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                        CornerRadii.EMPTY, Insets.EMPTY);
                Background background = new Background(background_fill);
                plane1.setBackground(background);
                plane2.setBackground(background);
            }
        });
        // Colorpicker listner
        color.setOnAction(e ->{
            if(!toggle) {
                BackgroundFill background_fill = new BackgroundFill(color.getValue(),
                        CornerRadii.EMPTY, Insets.EMPTY);
                Background background = new Background(background_fill);
                plane1.setBackground(background);
                plane2.setBackground(background);
            }
        });

        // Button listener
        button.setOnAction(e -> {
            int i = 0;
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if(selectedDirectory != null){
                String folderpath = selectedDirectory.getAbsolutePath();
                File folder = new File(folderpath);
                File[] listOfFiles = folder.listFiles();
                System.out.println(folderpath);
                for (File file : listOfFiles) {
                    Video video = new Video(Name(file),filetype(file),
                            length(file),Size(file),resolution(file), i++);
                    video.vidFile(file);
                    Videos.add(video);
                    lib.addvids(video);
                }
                ArrayList<Video> Vids = new ArrayList();
                Vids = lib.getvids();
                for (Video vid : Vids) {
                    String name = vid.getName();
                    listView.getItems().add(name);
                }
            }
        });

        // Text listener
        text.setOnAction(e  -> {
            listView.getItems().clear();
            ArrayList<Integer> Intarr = new ArrayList();
            Video vid = new Video();
            Intarr = lib.searchLib(text.getText());
            for (int i : Intarr) {
                vid = Videos.get(i);
                listView.getItems().add(vid.getName());
            }
        });

        // ComboBox Listener
        comboBox.setOnAction(e -> {
            listView.getItems().clear();
            int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
            ArrayList<Video> strItems = new ArrayList();
            strItems = lib.sortLibBy(selectedIndex);
            for (Video vid : strItems) {
                listView.getItems().add(vid.getName());
            }
        });
        options.setOnAction(e -> {
            primaryStage.setScene(scene2);
        });
        back.setOnAction(e -> {
            primaryStage.setScene(scene1);
        });

        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // Reads the filename
    public String Name(File file) {
        String filename = "";
        if (file.isFile()) {
            filename = file.getName();
        }
        return filename;
    }

    // Reads the filetype
    public String filetype(File file) {
        String filetype = "";
        if (file.isFile()) {
            filetype = file.getName();
            filetype = filetype.substring(Math.max(filetype.length() - 3, 0));
        }
        return filetype;
    }

    // Outputs the size of the file in gb
    public static int Size(File file) {

        Path path = Paths.get(file.getAbsolutePath());
        int size = 0;
        try {
            // size of a file (in bytes)
            long bytes = Files.size(path);
            //Bytes to MB
            size = (int)(bytes * 0.000001);
            if(size >= 1000){
                //Bytes to GB
                size = (int)(bytes * 0.000000001);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }

    // Outputs the length  using the jaffree libs and ffmpeg
    public double length(File file) {
        String pathToVideo = file.getAbsolutePath();

        FFprobeResult result = FFprobe.atPath()
                .setShowStreams(true)
                .setInput(pathToVideo)
                .execute();

        Stream stream = result.getStreams().get(0);

        String duration = String.valueOf(stream.getDuration());
        double len;
        if (duration == "null"){
            len = -1;
        }
        else{
            len = Double.parseDouble(duration);

        }
        return Math.round(len * 100) / 100;
    }

    // Outputs the resolution using the jaffree libs and ffmpeg
    public int resolution(File file) {
        String pathToVideo = file.getAbsolutePath();

        FFprobeResult result = FFprobe.atPath()
                .setShowStreams(true)
                .setInput(pathToVideo)
                .execute();

        Stream stream = result.getStreams().get(0);

        String height = String.valueOf(stream.getCodedHeight());
        int res;
        if (height == "null"){
            res = -1;
        }
        else{
            res = Integer.parseInt(height);
        }
        return res;
    }
}