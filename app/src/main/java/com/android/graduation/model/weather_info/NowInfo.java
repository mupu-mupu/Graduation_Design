package com.android.graduation.model.weather_info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/11.
 * Email: 18814182294@163.com
 * Describe ：当前天气描述
 *
 *   体感温度        f1   : 28°
 *   湿度(%)         hum
 *   降雨量(mm)      pcpn
 *   气压            pres
 *   当前温度        tmp
 *   能见度km        vis
 *   风向(方向)      dir
 *   风力等级        sc
 */

public class NowInfo implements Serializable {


    @SerializedName("fl")    private String fl;
    @SerializedName("hum")   private String hum;
    @SerializedName("pcpn")  private String pcpn;
    @SerializedName("pres")  private String pres;
    @SerializedName("tmp")   private String tmp;
    @SerializedName("vis")   private String vis;
    @SerializedName("wind")  private WindEntity wind;
    @SerializedName("cond")  private CondEntity cond;

        private static class CondEntity implements Serializable {
            //@SerializedName("code") private String code;
            @SerializedName("txt") private String txt;
            public String getTxt() {
                return txt;
            }

        }
        public static class WindEntity implements Serializable {

            @SerializedName("dir") private String dir;
            @SerializedName("sc") private String sc;
            public String getDir() {
                return dir;
            }
            public String getSc() {
                return sc;
            }

        }


    public CondEntity getCond() {
        return cond;
    }
    public String getFl() {
        return fl;
    }
    public String getHum() {
        return hum;
    }
    public String getPcpn() {
        return pcpn;
    }
    public String getPres() {
        return pres;
    }
    public String getTmp() {
        return tmp;
    }
    public String getVis() {
        return vis;
    }
    public WindEntity getWind() {
        return wind;
    }
}
