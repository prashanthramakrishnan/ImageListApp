package com.prashanth.imageapp.network;

import com.prashanth.imageapp.model.ImageSearchResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShutterstockAPI {

    @GET("/v2/images/search/")
    Observable<ImageSearchResponse> searchImageByCategory(@Query("category") String category,
                                                          @Query("query") String query,
                                                          @Query("search_id") String searchId,
                                                          @Query("page") int page,
                                                          @Query("per_page") int perPage);

}
