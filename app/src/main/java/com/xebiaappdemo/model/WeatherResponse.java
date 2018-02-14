package com.xebiaappdemo.model;

import java.util.List;

/**
 * Created by Administrator on 2/14/2018.
 */

public class WeatherResponse {
    private String message;

    private String cnt;

    private String cod;

    public List<WeatherList> getList() {
        return list;
    }

    public void setList(List<WeatherList> list) {
        this.list = list;
    }

    private List<WeatherList> list;

    private City city;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getCnt ()
    {
        return cnt;
    }

    public void setCnt (String cnt)
    {
        this.cnt = cnt;
    }

    public String getCod ()
    {
        return cod;
    }

    public void setCod (String cod)
    {
        this.cod = cod;
    }

    public City getCity ()
    {
        return city;
    }

    public void setCity (City city)
    {
        this.city = city;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", cnt = "+cnt+", cod = "+cod+", list = "+list+", city = "+city+"]";
    }

}
