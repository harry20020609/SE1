package edu.nju;

import java.util.*;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 16:15
 */
public class TuringMachine {
    DealingInstruction deal = new DealingInstruction();

    // 状态集合
    private final Map<String, State> Q;
    // 输入符号集
    private Set<Character> S = new HashSet<>();
    // 磁带符号集
    private Set<Character> G = new HashSet<>();
    // 初始状态
    private String q0 = null;
    // 终止状态集
    private Set<String> F = new HashSet<>();
    // 空格符号
    private Character B = null;
    // 磁带数
    private Integer tapeNum;
    private Set<TransitionFunction> Delta = new HashSet<>();

    public TuringMachine(Set<String> Q, Set<Character> S, Set<Character> G, String q, Set<String> F, char B, int tapeNum, Set<TransitionFunction> Delta) {
        this.S = S;
        this.G = G;
        this.F = F;
        this.B = B;
        this.q0 = q;
        this.Q = new HashMap<>();
        for (String state : Q) {
            State temp = new State(state);
            temp.setQ(state);
            this.Q.put(state, temp);
        }
        this.tapeNum = tapeNum;
        for (TransitionFunction t : Delta) {
            this.Q.get(t.getSourceState().getQ()).addTransitionFunction(t);
        }
    }

    /**
     * TODO
     * is done in Lab1 ~
     *
     * @param tm
     */
    public TuringMachine(String tm) {
        this.Q = new HashMap<>();
        Set<String> Q0 = new HashSet<>();
        boolean f1=false,f2=false,f3=false,f4=false,f5=false,f6=false,f7 = false;
        int j = 0;
        String l = System.lineSeparator();
        ArrayList<String> Instructions = deal.InstructionSplit(tm);
        for(int i=0;i<Instructions.size();i++){
            if(Instructions.get(i).substring(0,2).equals("#Q")){
                Q0 = deal.InstructionAnalyzeQ(Instructions.get(i));f1=true;
                for (String state : Q0) {
                    State temp = new State(state);
                    temp.setQ(state);
                    this.Q.put(state, temp);
                }
            }
            else if(Instructions.get(i).substring(0,2).equals("#S")){
                this.S = deal.InstructionAnalyzeS(Instructions.get(i));f2=true;
            }
            else if(Instructions.get(i).substring(0,2).equals("#G")){
                this.G = deal.InstructionAnalyzeG(Instructions.get(i));f3=true;
            }
            else if(Instructions.get(i).substring(0,3).equals("#q0")){
                this.q0= deal.InstructionAnalyzeq(Instructions.get(i));f4=true;
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
                int lineno = i;
                resolverTransitionFunction(Instructions.get(i),lineno);
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
        for (TransitionFunction t : Delta) {
            this.Q.get(t.getSourceState().getQ()).addTransitionFunction(t);
        }
        for(String s:F){
            if(!Q.keySet().contains(s)){
                System.err.println("Error: 3");
                break;
            }
        }
        if(S.contains(B)){
            System.err.println("Error: 4");
        }
        if(!G.contains(B)){
            System.err.println("Error: 5");
        }
        for(Character c:S){
            if(!G.contains(c)){
                System.err.println("Error: 6");
                break;
            }
        }
        if(!f1){System.err.println("Error: lack Q");}
        if(!f2){System.err.println("Error: lack S");}
        if(!f3){System.err.println("Error: lack G");}
        if(!f4){System.err.println("Error: lack q0");}
        if(!f5){System.err.println("Error: lack F");}
        if(!f6){System.err.println("Error: lack B");}
        if(!f7){System.err.println("Error: lack N");}
    }

    public State getInitState() {
        return Q.get(q0);
    }

    public Map<String,State> getQSet(){
        return Q;
    }

    /**
     * TODO
     * 停止的两个条件 1. 到了终止态 2. 无路可走，halts
     *
     * @param q Z
     * @return
     */
    public boolean isStop(State q, String Z) {
        if(F.contains(q.getQ())){
            return true;
        }
        else{
            if(q.getDelta(Z)!=null){
                return false;
            }
            else{
                return true;
            }
        }
    }

    public boolean checkTape(Set<Character> tape) {
        boolean flag = true;
        for(Character c:tape){
            if(!S.contains(c) && c!=B){
                flag = false;
            }
        }
        return flag;
    }

    public boolean checkTapeNum(int tapeNum) {
        if(this.tapeNum != tapeNum){
            return false;
        }
        else {
            return true;
        }
    }

    public Character getB() {
        return B;
    }


    /**
     * TODO
     * 检查迁移函数是否符合要求
     * @param s
     * @param lineno
     */
    private void resolverTransitionFunction(String s, int lineno) {
        int errorContents = 0;
        String[] s1 = s.split(" ");
        State SourceState = new State(s1[1]);
        State DState = new State(s1[5]);
        if (s1.length == 6){
            if (s1[2].length()==s1[3].length()){
                if (!(Q.keySet().contains(SourceState.getQ()) && Q.keySet().contains(DState.getQ()))){
                    System.err.println("Error: 7");
                    errorContents += 1;
                }
                if (! G.contains(s1[2].charAt(0))) {
                    System.err.println("Error: 8");
                    errorContents += 1;
                }
                if(!G.contains(s1[3].charAt(0))){
                    System.err.println("Error: 8");
                    errorContents += 1;
                }
            }
            else
                System.err.println("Error: "+lineno+1);
        }
        else
            System.err.println("Error: "+lineno+1);
        for (TransitionFunction t: Delta) {
            if ((SourceState.getQ().equals(t.getSourceState().getQ()) && s1[2].equals(t.getInput())) || (s1[2].substring(0,1).equals(t.getInput().substring(0,1)) && SourceState.getQ().equals(t.getSourceState().getQ()) && s1[2].substring(0,1).equals("1") && s1[2].substring(0,1).equals("0"))){
                if (!DState.getQ().equals(t.getDestinationState().getQ()) || !s1[3].equals(t.getOutput()) || !s1[4].equals(t.getDirection())){
                    System.err.println("Error: 9");
                    errorContents += 1;
                }
            }
        }
        if (errorContents == 0)
            Delta.add(new TransitionFunction(SourceState,DState,s1[2],s1[3],s1[4]));
    }


    /**
     * TODO
     * is done in lab1 ~
     *
     * @return
     */
    @Override
    public String toString() {
        String l = System.lineSeparator();
        String all = "";
        //输出Q
        int c1 = 1;
        all = all + "#Q = {";
        for(String s:Q.keySet()){
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
        all = all +  "#q0 = " + q0 + l;
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
        return all;
    }

}
