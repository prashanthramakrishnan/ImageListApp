package com.prashanth.imageapp.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HugeThumb {

    @SerializedName("height")
    private int height;

    @SerializedName("width")
    private int width;

    @SerializedName("url")
    private String url;

    @Override
    public String toString() {
        return "HugeThumb{" +
                "height=" + height +
                ", width=" + width +
                ", url='" + url + '\'' +
                '}';
    }
}
