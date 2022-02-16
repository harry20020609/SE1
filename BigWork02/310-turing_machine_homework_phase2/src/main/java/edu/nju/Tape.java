package edu.nju;

import java.util.ArrayList;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 19:37
 */
public class Tape {

    ArrayList<StringBuilder> tracks;
    private final char B;
    private int head;

    public Tape(ArrayList<StringBuilder> tracks, int head, char B) {
        this.tracks = tracks;
        this.head = head;
        this.B = B;
    }

    public String snapShot() {
        String s = "";
        for(StringBuilder track:tracks){
            s = s + track.charAt(head);
        }
        return s;
    }

    public void updateHead(char c) {
        if(c=='r'){
            head = head + 1;
        }
        else if(c == 'l') {
            head = head - 1;
        }
    }

    public int getHead(){return head;}

    public void updateTape(String newTape) {
        for(int i=0;i< tracks.size();i++){
            tracks.get(i).replace(head,head+1,newTape.substring(i,i+1));
        }
    }
}
