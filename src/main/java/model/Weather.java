package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Weather {
    private String city_name;
    private String country_code;
    private double temp;
    private int clouds;
    private double wind_spd;
    private String wind_cdir_full;

    public String cityToString() {
        return String.valueOf(this.getCity_name());
    }
    public String tempToString() {
        return String.valueOf(this.getTemp());
    }
    public String windToString() {
        return String.valueOf(this.getWind_spd());
    }
    public String cloudsToString() {
        return String.valueOf(this.getClouds());
    }
    public String directionToString() {
        return String.valueOf(this.getWind_cdir_full());
    }

    public String[] toArray() {
        return new String[]{cityToString(), //0 - city string
                tempToString(), //1 - temp double C
                cloudsToString(),//2- clouds int %
                windToString(), //3 - wind speed double m/s
                directionToString()};//4 - wind direction string
    }

}
