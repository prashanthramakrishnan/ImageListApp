package com.prashanth.imageapp.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDetails {

    @SerializedName("id")
    private String id;

    @SerializedName("aspect")
    private Double aspect;

    @SerializedName("assets")
    private Assets assets;

    @SerializedName("description")
    private String description;

    @Override
    public String toString() {
        return "ImageDetails{" +
                "id='" + id + '\'' +
                ", aspect=" + aspect +
                ", assets=" + assets +
                '}';
    }
}
