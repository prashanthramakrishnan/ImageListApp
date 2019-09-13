package com.prashanth.imageapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageSearchResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("search_id")
    private String searchId;

    @SerializedName("data")
    ArrayList<ImageDetails> imageDetailsArrayList;

    @Override
    public String toString() {
        return "ImageSearchResponse{" +
                "page=" + page +
                ", totalCount=" + totalCount +
                ", searchId='" + searchId + '\'' +
                ", imageDetailsArrayList=" + imageDetailsArrayList +
                '}';
    }
}
