package edu.nju;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DealingInstruction {
    String[] ins;

    public ArrayList<String> InstructionSplit(String tm){
        this.ins = tm.split(System.lineSeparator());
        ArrayList<String> Instructions = new ArrayList<String>();
        for(String str:tm.split(System.lineSeparator())){
            if(!(str.equals("") || str.substring(0,1).equals(";"))){
                Instructions.add(str.trim());
            }
        }
        return Instructions;
    }

    public Set<String> InstructionAnalyzeQ(String str){
        Set<String> Qset = new HashSet<>();
        int i = 0;
        if(!(str.contains("{") && str.contains("}"))){
            for(i=0;i<ins.length;i++){
                if(ins[i].equals(str)){
                    break;
                }
            }
            System.err.printf("Error: %d\n",i+1);
        }
        else {
            String[] s = str.split("\\{");
            if(!(s[0].equals("#Q = "))){
                System.err.printf("Error: %d\n",i+1);
            }
            else{
                String[] ss = s[1].split("}")[0].split(",");
                for(int j=0;j<ss.length;j++){
                    Qset.add(ss[j].trim());
                }
            }
        }
        return Qset;
    }

    public Set<Character> InstructionAnalyzeS(String str){
        Set<Character> Sset = new HashSet<>();
        int i = 0;
        for(i=0;i<ins.length;i++){
            if(ins[i].equals(str)){
                break;
            }
        }
        if(!(str.contains("{") && str.contains("}"))){
            System.err.printf("Error: %d\n",i+1);
        }
        else {
            String[] s = str.split("\\{");
            if(!(s[0].equals("#S = "))){
                System.err.printf("Error: %d\n",i+1);
            }
            else{
                String[] ss = s[1].split("}")[0].split(",");
                for(int j=0;j<ss.length;j++){
                    if(ss[j].charAt(0) == ' '||ss[j].charAt(0) == ','||ss[j].charAt(0) == ';'||ss[j].charAt(0) == '{'||ss[j].charAt(0) == '}'||ss[j].charAt(0) == '*') {
                        //
                    }
                    else{
                        Sset.add(ss[j].charAt(0));
                    }
                }
            }
        }
        return Sset;
    }

    public Set<Character> InstructionAnalyzeG(String str){
        Set<Character> Gset = new HashSet<>();
        int i = 0;
        if(!(str.contains("{") && str.contains("}"))){
            for(i=0;i<ins.length;i++){
                if(ins[i].equals(str)){
                    break;
                }
            }
            System.err.printf("Error: %d\n",i+1);
        }
        else {
            String[] s = str.split("\\{");
            if(!(s[0].equals("#G = "))){
                System.err.printf("Error: %d\n",i+1);
            }
            else{
                String[] ss = s[1].split("}")[0].split(",");
                for(int j=0;j<ss.length;j++){
                    if(ss[j].charAt(0) == ' '||ss[j].charAt(0) == ','||ss[j].charAt(0) == ';'||ss[j].charAt(0) == '{'||ss[j].charAt(0) == '}'||ss[j].charAt(0) == '*') {
                        System.err.printf("Error: %d\n",i+1);
                    }
                    else{
                        Gset.add(ss[j].charAt(0));
                    }
                }
            }
        }
        return Gset;
    }

    public String InstructionAnalyzeq(String str){
        int i = 0;
        if(str.substring(0,6).equals("#q0 = ")){
            return str.substring(6);
        }
        else{
            for(i=0;i<ins.length;i++){
                if(ins[i].equals(str)){
                    break;
                }
            }
            System.err.printf("Error: %d\n",i+1);
        }
        return null;
    }

    public Set<String> InstructionAnalyzeF(String str){
        Set<String> Fset = new HashSet<>();
        int i = 0;
        if(!(str.contains("{") && str.contains("}"))){
            for(i=0;i<ins.length;i++){
                if(ins[i].equals(str)){
                    break;
                }
            }
            System.err.printf("Error: %d\n",i+1);
        }
        else {
            String[] s = str.split("\\{");
            if(!(s[0].equals("#F = "))){
                System.err.printf("Error: %d\n",i+1);
            }
            else{
                String[] ss = s[1].split("}")[0].split(",");
                for(int j=0;j<ss.length;j++){
                    Fset.add(ss[j]);
                }
            }
        }
        return Fset;
    }

    public Character InstructionAnalyzeB(String str){
        int i = 0;
        if(str.substring(0,5).equals("#B = ")){
            return str.charAt(5);
        }
        else{
            for(i=0;i<ins.length;i++){
                if(ins[i].equals(str)){
                    break;
                }
            }
            System.err.printf("Error: %d\n",i+1);
        }
        return null;
    }

    public Integer InstructionAnalyzeN(String str) {
        int i = 0;
        if (str.substring(0, 5).equals("#N = ")) {
            return Integer.valueOf(str.substring(5));
        } else {
            for (i = 0; i < ins.length; i++) {
                if (ins[i].equals(str)) {
                    break;
                }
            }
            System.err.printf("Error: %d\n", i + 1);
        }
        return null;
    }
}
