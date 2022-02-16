package edu.nju;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-25 23:53
 */
public class Executor {

    ArrayList<Tape> tapes;
    TuringMachine tm;
    State q;
    int steps = 0;
    boolean canRun = true;

    public Executor(TuringMachine tm, ArrayList<Tape> tapes) {
        this.tm = tm;
        q = tm.getInitState();
        loadTape(tapes);
    }

    /**
     * TODO
     * 1. 检查能否运行
     * 2. 调用tm.delta
     * 3. 更新磁带
     * 4. 返回下次能否执行
     *
     * @return
     */
    public Boolean execute() {
        TransitionFunction tf = q.getDelta(snapShotTape());
        String s = snapShotTape();
        if(!tm.isStop(q,s)){
            updateTape(tf.getOutput());
            moveHeads(tf.getDirection());
            q=tm.getQSet().get(tf.getDestinationState().getQ());
            for(Tape tape:tapes){
                for(StringBuilder S: tape.tracks){
                    if(tape.getHead()>=S.length()){
                        S.append("_");
                    }
                    if(tape.getHead()==-1){
                        for(StringBuilder ss: tape.tracks){
                            ss.insert(0,"_");
                        }
                        tape.updateHead('r');
                    }
                }
            }
            canRun = !tm.isStop(q,snapShotTape());
            steps ++;
            return canRun;
        }
        else{
            return false;
        }
    }

    /**
     * TODO
     * 1. 检查磁带的数量是否正确 ( checkTapeNum )
     * 2. 检查磁带上的字符是否是输入符号组的 ( checkTape )
     *
     * @param tapes
     */
    public void loadTape(ArrayList<Tape> tapes) {
        this.tapes = tapes;
        Set<Character> characterSet = new HashSet<>();
        for(Tape tape:tapes){
            for(StringBuilder track:tape.tracks){
                for(int i=0;i< track.length();i++){
                    if(!characterSet.contains(track.charAt(i))) {
                        characterSet.add(track.charAt(i));
                    }
                }
            }
        }
        if(!tm.checkTape(characterSet)){
            System.err.println("Error: 1");
        }
        if(!tm.checkTapeNum(tapes.size())){
            System.err.println("Error: 2");
        }
    }

    /**
     * TODO
     * 获取所有磁带的快照，也就是把每个磁带上磁头指向的字符全都收集起来
     *
     * @return
     */
    private String snapShotTape() {
        String s = "";
        for(Tape tape:tapes){
            s = s + tape.snapShot();
        }
        return s;
    }

    /**
     * TODO
     * 按照README给出当前图灵机和磁带的快照
     *
     * @return
     */
    public String snapShot() {
        String str = "";

        //找出最长的前缀
        int maxlength = 0;
        int max=0;
        for(Tape tape:tapes){
            if(tape.tracks.size()>max){
                max = tape.tracks.size();
            }
        }
        maxlength = String.valueOf(max-1).length() + 5;
        str = str + modify("Step",maxlength) + " ";
        str = str + String.valueOf(steps) + System.lineSeparator();
        for(int i=0;i< tapes.size();i++){
            //对于每个磁带获得它的最长
            int indexS = tapes.get(i).tracks.get(0).length()-1;
            int indexE = 0;
            boolean ifAllBlank = true;
            for(StringBuilder S:tapes.get(i).tracks){
                int IS = tapes.get(i).tracks.get(0).length()-1;
                int IE = 0;
                for(int j=0;j<S.length();j++){
                    if(S.charAt(j)!='_'){
                        ifAllBlank = false;
                        IS = j;
                        if(IS < indexS){
                            indexS = IS;
                        }
                        break;
                    }
                }
                if(!ifAllBlank) {
                    for (int j = 0; j < S.length(); j++) {
                        if (S.charAt(S.length() - 1 - j) != '_') {
                            IE = S.length() - 1 - j;
                            if (IE > indexE) {
                                indexE = IE;
                            }
                            break;
                        }
                    }
                }
                if(!ifAllBlank){
                    if(tapes.get(i).getHead()>indexE || tapes.get(i).getHead()<indexS){
                        if(tapes.get(i).getHead()>indexE){
                            indexE = tapes.get(i).getHead();
                        }
                        if(tapes.get(i).getHead()<indexS){
                            indexS = tapes.get(i).getHead();
                        }
                    }
                }
                else{
                    indexS = tapes.get(i).getHead();
                    indexE = tapes.get(i).getHead();
                }
            }
            //必要操作
            str = str + modify("Tape"+String.valueOf(i),maxlength)+ System.lineSeparator();
            str = str + modify("Index"+String.valueOf(i),maxlength)+" ";
            str = str + numString(indexS,indexE);
            for(int j=0;j<tapes.get(i).tracks.size();j++){
                str = str + modify("Track"+String.valueOf(j),maxlength)+" ";
                str = str + addSpace(tapes.get(i).tracks.get(j).substring(indexS,indexE+1),indexS);
            }
            str = str + modify("Head"+String.valueOf(i),maxlength) + " ";
            str = str + String.valueOf(tapes.get(i).getHead())+ System.lineSeparator();

        }
        str = str + modify("State",maxlength)+ " " + q.getQ();
        return str;
    }

    public String modify(String str,int max){
        String mod = str.substring(0);
        for(int i=0;i<max-str.length();i++){
            mod = mod + " ";
        }
        return mod + ":";
    }

    public String numString(int start,int end){
        String s = "";
        for(int i=start;i<=end;i++){
            if(i!=end){
                s = s + String.valueOf(i) + " ";
            }
            else{
                s = s + String.valueOf(i) + System.lineSeparator();
            }
        }
        return s;
    }

    public String addSpace(String str,int index){
        String s = "";
        for(int i=0;i<str.length();i++){
            s = s + str.substring(i,i+1);
            if(i!=str.length()-1){
                if(index + i<10) {
                    s = s + " ";
                }
                else{
                    s = s + "  ";
                }
            }
            else{
                s = s + System.lineSeparator();
            }
        }
        return s;
    }



    /**
     * TODO
     * 不断切割newTapes，传递给每个Tape的updateTape方法
     *
     * @param newTapes
     */
    private void updateTape(String newTapes) {
        for(Tape tape:tapes){
            tape.updateTape(newTapes.substring(0,tape.tracks.size()));
            newTapes = newTapes.substring(tape.tracks.size());
        }
    }

    /**
     * TODO
     * 将每个direction里的char都分配给Tape的updateHead方法
     *
     * @param direction
     */
    private void moveHeads(String direction) {
        for(int i=0;i<tapes.size();i++){
            tapes.get(i).updateHead(direction.charAt(i));
        }
    }



}
