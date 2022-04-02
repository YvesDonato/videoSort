package com.video.videosort;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VideoSortMain extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        //ComboBox
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("File Type");
        comboBox.getItems().add("Length");
        comboBox.getItems().add("Size");
        comboBox.getItems().add("Resolution");

        //listView
        ListView listView = new ListView();
        listView.setMaxSize(150,150);

        //DirectoryChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        //Button
        Button options = new Button("Options");
        Button back = new Button("Return");
        Button button = new Button("Select Directory");

        //Boxes
        VBox vbox1 = new VBox(options,comboBox,listView,button);
        VBox vbox2 = new VBox(back);
        vbox1.setAlignment(Pos.BASELINE_CENTER);
        vbox2.setAlignment(Pos.BASELINE_CENTER);

        //Scene
        Scene scene1 = new Scene(vbox1,400, 400);
        Scene scene2 = new Scene(vbox2,400, 400);

        //listeners
        button.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);

            System.out.println(selectedDirectory.getAbsolutePath());
        });
        options.setOnAction(e -> {
            primaryStage.setScene(scene2);
        });
        back.setOnAction(e -> {
            primaryStage.setScene(scene1);
        });

        //stages
        primaryStage.setScene(scene1);
        primaryStage.show();
        //for file name for testing proposes
        File folder = new File("C:/Users/draon/Desktop/Test/");
        File[] listOfFiles = folder.listFiles();

        //for testing proposes
        for (File file : listOfFiles) {
            System.out.println(Name(file));
            System.out.println(Size(file));
            System.out.println(filetype(file));
            System.out.println(length(file));
        }

    }

    public static void main(String[] args) {
        launch();
    }
    //reads the filename
    public String Name(File file) {
        String filename = "";
        if (file.isFile()) {
            filename = file.getName();
        }
        return filename;
    }
    //read the filetype
    public String filetype(File file) {
        String filetype = "";
        if (file.isFile()) {
            filetype = file.getName();
            filetype = filetype.substring(Math.max(filetype.length() - 3, 0));
        }
        return filetype;
    }
    //outputs the size of the file in gb
    public static double Size(File file) {

        Path path = Paths.get(file.getPath());
        double GB = 0.0;
        try {

            // size of a file (in bytes)
            long bytes = Files.size(path);
            GB = bytes * 0.000000001;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return GB;
    }
    //Were having trouble with extracting length and resolution 90% of time
    //spent was trying to figure this out
    //outputs the length
    public Double length(File file) {
        Double filelength = 0.0;
        return filelength;
    }
    //outputs the resolution
    public Double resolution(File file) {
        Double fileresolution = 0.0;
        return fileresolution;
    }

}