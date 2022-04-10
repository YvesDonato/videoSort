package com.video.videosort;

import java.util.ArrayList;
import java.util.Collections;

public class Library {

    // Arraylist for searchlib, sortliBy, and orginal Videos
    private static ArrayList<Video> OVideos = new ArrayList();
    private static ArrayList<Video> Videos1 = new ArrayList();
    private static ArrayList<Video> Videos2 = new ArrayList();

    private String name = "";

    // SearchLib
    public ArrayList searchLib(String text){
        ArrayList<Integer> Intarr = new ArrayList();// arraylist for retrieving
        // index's
        Videos2 = OVideos;
        for (Video vid : Videos2) {
            name = vid.getName();
            if(name.contains(text)){
                Intarr.add(vid.getId());
            }
        }
        System.out.println();
        return Intarr;
    }

    // SortLibBy
    public ArrayList sortLibBy(int by){
        Videos1 = OVideos;

        if(by == 0 && !Videos1.isEmpty()) {
            for (Video vid : Videos1) {
                vid.sortby("name");
            }
            Collections.sort(Videos1);
        }
        else if(by == 1 && !Videos1.isEmpty()) {
            for (Video vid : Videos1) {
                vid.sortby("file");
            }
            Collections.sort(Videos1);
        }
        else if(by == 2 && !Videos1.isEmpty()) {
            for (Video vid : Videos1) {
                vid.sortby("length");
            }
            Collections.sort(Videos1);
        }
        else if(by == 3 && !Videos1.isEmpty()) {
            for (Video vid : Videos1) {
                vid.sortby("size");
            }
            Collections.sort(Videos1);
        }
        else if(by == 4 && !Videos1.isEmpty()){
            for (Video vid : Videos1) {
                vid.sortby("res");
            }
            Collections.sort(Videos1);
        }
        else{
            for (Video vid : Videos1) {
                vid.sortby("default");
            }
        }
        return Videos1;
    }

    // Addvids & Getvids (Setters & Getters)
    public void addvids(Video video){
        OVideos.add(video);
    }

    public ArrayList getvids(){
        return OVideos;
    }
}
