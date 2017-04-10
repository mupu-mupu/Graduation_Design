package com.android.graduation.model.weather_info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/11.
 * Email: 18814182294@163.com
 * Describe ：天气预报Json数据
 *
 *       未来七天的天气预报
 *      温度 tmp : max   ：35°
 *                 min   ：26°
 *      天气情况  cond   ：晴
 */

public class ForecastInfo implements Serializable {


    @SerializedName("tmp") private TmpEntity tmp;
    @SerializedName("cond") private CondEntity cond;
    @SerializedName("astro") private AstroEntity astro;
    @SerializedName("wind") private WindEntity wind;

    @SerializedName("date") private String date;

    public TmpEntity getTmp() {
        return tmp;
    }
    public CondEntity getCond() {
        return cond;
    }
    public AstroEntity getAstro() {
        return astro;
    }
    public WindEntity getWind() {
        return wind;
    }

    public String getDate() {
        return date;
    }





        public static class CondEntity implements Serializable {
            @SerializedName("txt_d") private String txtD;
            @SerializedName("code_d") private int codeD;
            public int getCodeD() {return codeD;}
            public String getTxtD() {
                return txtD;
            }
        }

        public static class TmpEntity implements Serializable {
            @SerializedName("max") private String max;
            @SerializedName("min") private String min;
            public String getMin() {
                return min;
            }
            public String getMax() {
                return max;
            }
        }

        public static class WindEntity implements Serializable{
            @SerializedName("dir") private String dir;
            @SerializedName("sc") private String sc;

            public String getDir() {
                return dir;
            }

            public String getSc() {
                return sc;
            }

        }

    public static class AstroEntity implements Serializable{
        @SerializedName("sr") private String sr;
        @SerializedName("ss") private String ss;
        public String getSr() {
            return sr;
        }

        public String getSs() {
            return ss;
        }

    }

}
