package tech.lovelycheng.learning.juc.designpattern.future;

/**
 * @author chengtong
 * @date 2020/1/6 15:26
 */
public class RealData implements Data{

    String content;

    @Override
    public String getContent() {
        return content;
    }


    public void setContent(String s){
        this.content = s;
    }



}
