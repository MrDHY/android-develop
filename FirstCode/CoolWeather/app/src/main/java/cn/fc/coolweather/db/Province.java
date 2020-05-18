package cn.fc.coolweather.db;

import org.litepal.crud.DataSupport;

public class Province extends DataSupport {

    private int id;

    private String provinceName;

    private int privinceCode;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getProvinceName(){
        return provinceName;
    }

    public void setProvinceName(String name){
        this.provinceName = name;
    }

    public int getPrivinceCode(){
        return privinceCode;
    }

    public void setPrivinceCode(int code){
        this.privinceCode = code;
    }
}
