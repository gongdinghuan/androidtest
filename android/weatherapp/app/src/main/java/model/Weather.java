package model;

/**
 * Created by jowang on 16/7/27.
 */
public class Weather {
    public Place place;
    public String iconData;
    public CurrentCondition currentCondition=new CurrentCondition();
    public Temperatue temperatue=new Temperatue();
    public Wind wind=new Wind();
    public Snow snow=new Snow();
    public Clouds clouds=new Clouds();
}
