package cn.fc.coolweather.util;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.fc.coolweather.db.City;
import cn.fc.coolweather.db.County;
import cn.fc.coolweather.db.Province;

public class Utility {
    /**
     *  解析和处理服务器返回的省级数据
     */

    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allprovinces = new JSONArray(response);
                for (int i = 0; i < allprovinces.length(); i++){
                    JSONObject provinceObject = allprovinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setPrivinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *  解析和出路服务器的返回市级数据
     */
    public static boolean handleCityResponse(String response, int provinceId){

        if (!TextUtils.isEmpty(response)){

            try{
                JSONArray allcities = new JSONArray(response);
                for (int i =0; i< allcities.length(); i++){
                    JSONObject cityObject = allcities.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     *  解析和处理服务器返回的县级数据
     */
    public static boolean handleCountyResponse(String response, int cityId){

        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounties = new JSONArray(response);
                for (int i=0; i< allCounties.length(); i++){
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

}
