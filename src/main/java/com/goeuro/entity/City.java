package com.goeuro.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by phanindra on 7/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    private long _id;
    private String name,type;
    private GeoPosition geo_position;

    public GeoPosition getGeo_position() {
        return geo_position;
    }

    public void setGeo_position(GeoPosition geo_position) {
        this.geo_position = geo_position;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    City(long _id, String name, String type, GeoPosition geo_position){
//        this._id = _id;
//        this.type = type;
//        this.geo_position = geo_position;
//    }
//
    public City(){

    }
}
