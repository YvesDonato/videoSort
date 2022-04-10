package com.video.videosort;

import java.io.File;

public class Video implements Comparable<Video> {
    private String name;
    private String fileType;
    private String length;
    private int size;
    private int resolution;
    private int id;

    private String sort;
    File file;

    public Video(){}
    // Video Constructor
    public Video(String name, String fileType, double length, int size,
                 int resolution, int id){
        this.name = name;
        this.fileType = fileType;
        this.length = String.valueOf((int)length);
        this.size = size;
        this.resolution = resolution;
        this.id = id;
    }
    public void vidFile (File file){
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getFileType() {
        return fileType;
    }

    public String getLength() {
        return length;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    public int getResolution() {
        return resolution;
    }

    public void sortby(String sort){
        this.sort = sort;
    }

    // CompareTo for sorting
    @Override
    public int compareTo(Video o) {
        int com = 0;
        if (sort == "name") {
            com = name.compareTo(o.name);
        }
        else if (sort == "file") {
            com = fileType.compareTo(o.fileType);
        }
        else if (sort == "length") {
            com = length.compareTo(o.length);
        }
        else if (sort == "size") {
            com = Integer.compare(size, o.size);
        }
        else if (sort == "res"){
            com = Integer.compare(resolution, o.resolution);
        }
        else if (sort == "default"){
            com = Integer.MAX_VALUE;
        }
        return com;
    }
}
