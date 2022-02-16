package edu.nju;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 16:15
 */
public class TuringMachine {
    DealingInstruction deal = new DealingInstruction();

    // 状态集合
    Set<String> Q = new HashSet<>();
    // 输入符号集
    Set<Character> S = new HashSet<>();
    // 磁带符号集
    Set<Character> G = new HashSet<>();
    // 初始状态
    String q = null;
    // 终止状态集
    Set<String> F = new HashSet<>();
    // 空格符号
    Character B = null;
    // 磁带数
    Integer tapeNum = null;
    // 迁移函数集
    Set<TransitionFunction> Delta = new HashSet<>();

    public TuringMachine(Set<String> Q, Set<Character> S, Set<Character> G, String q, Set<String> F, char B, int tapeNum, Set<TransitionFunction> Delta) {
        this.Q = Q;
        this.S = S;
        this.G = G;
        this.q = q;
        this.F = F;
        this.B = B;
        this.tapeNum = tapeNum;
        this.Delta = Delta;
    }

    //TODO
    public TuringMachine(String tm) {
        boolean f1=false,f2=false,f3=false,f4=false,f5=false,f6=false,f7 = false;
        int j = 0;
        String l = System.lineSeparator();
        ArrayList<String> Instructions = deal.InstructionSplit(tm);
        for(int i=0;i<Instructions.size();i++){
            if(Instructions.get(i).substring(0,2).equals("#Q")){
                this.Q = deal.InstructionAnalyzeQ(Instructions.get(i));f1=true;
            }
            else if(Instructions.get(i).substring(0,2).equals("#S")){
                this.S = deal.InstructionAnalyzeS(Instructions.get(i));f2=true;
            }
            else if(Instructions.get(i).substring(0,2).equals("#G")){
                this.G = deal.InstructionAnalyzeG(Instructions.get(i));f3=true;
            }
            else if(Instructions.get(i).substring(0,3).equals("#q0")){
                this.q = deal.InstructionAnalyzeq(Instructions.get(i));f4=true;
            }
            else if(Instructions.get(i).substring(0,2).equals("#F")){
                this.F = deal.InstructionAnalyzeF(Instructions.get(i));f5=true;
            }
            else if(Instructions.get(i).substring(0,2).equals("#B")){
                this.B = deal.InstructionAnalyzeB(Instructions.get(i));f6=true;
            }
            else if(Instructions.get(i).substring(0,2).equals("#N")){
                this.tapeNum = deal.InstructionAnalyzeN(Instructions.get(i));f7=true;
            }
            else if(Instructions.get(i).substring(0,2).equals("#D")){
                this.Delta.add(deal.InstructionAnalyzeD(Instructions.get(i)));
            }
            else{
                for(j=0;j<tm.split(System.lineSeparator()).length;j++){
                    if(tm.split(System.lineSeparator())[j].equals(Instructions.get(i))){
                        break;
                    }
                }
                System.err.printf("Error: %d%s",j+1,l);
            }
        }
        if(!f1){System.err.println("Error: lack Q");}
        if(!f2){System.err.println("Error: lack S");}
        if(!f3){System.err.println("Error: lack G");}
        if(!f4){System.err.println("Error: lack q0");}
        if(!f5){System.err.println("Error: lack F");}
        if(!f6){System.err.println("Error: lack B");}
        if(!f7){System.err.println("Error: lack N");}
        if(Delta.size()==0){System.err.println("Error: lack D");}
    }

    //TODO
    @Override
    public String toString() {
        String l = System.lineSeparator();
        String all = "";
        //输出Q
        int c1 = 1;
        all = all + "#Q = {";
        for(String s:Q){
            all = all + s;
            if(c1 < Q.size()) {
                all = all + ",";
            }
            else{
                all = all + "}" + l;
            }
            c1++;
        }
        //输出S
        int c2 = 1;
        all = all + "#S = {";
        for(Character c:S){
            String s = String.valueOf(c);
            all = all + s;
            if(c2 < S.size()) {
                all = all + ",";
            }
            else{
                all = all + "}" + l;
            }
            c2++;
        }
        //输出G
        int c3 = 1;
        all = all + "#G = {";
        for(Character c:G){
            String s = String.valueOf(c);
            all = all + s;
            if(c3 < G.size()) {
                all = all + ",";
            }
            else{
                all = all + "}"+l;
            }
            c3++;
        }
        //输出q0
        all = all +  "#q0 = " + q + l;
        //输出F
        int c4 = 1;
        all = all + "#F = {";
        for(String s:F){
            all = all + s;
            if(c4 < F.size()) {
                all = all + ",";
            }
            else{
                all = all + "}" + l;
            }
            c4++;
        }
        //输出B
        all = all + "#B = " + String.valueOf(B) + l;
        //输出tapenum
        all = all + "#N = " + String.valueOf(tapeNum) + l;
        //输出Delta
        int c = 1;
        for(TransitionFunction tf:Delta){
            if(c < Delta.size()) {
                all = all + "#D " + tf.getFromState() + " " + tf.getInput() + " " + tf.getOutput() + " " + tf.getDirection() + " " + tf.getToState() + l;
            }
            else{
                all = all + "#D " + tf.getFromState() + " " + tf.getInput() + " " + tf.getOutput() + " " + tf.getDirection() + " " + tf.getToState();
            }
            c++;
        }
        return all;
    }
}
