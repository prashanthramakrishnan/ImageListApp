package com.prashanth.imageapp.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Assets {

    @SerializedName("huge_thumb")
    private HugeThumb hugeThumb;

    @Override
    public String toString() {
        return "Assets{" +
                "hugeThumb=" + hugeThumb +
                '}';
    }
}
