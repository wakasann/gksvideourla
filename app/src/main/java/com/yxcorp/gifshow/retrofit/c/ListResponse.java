package com.yxcorp.gifshow.retrofit.c;
import java.util.List;

public interface ListResponse<MODEL> {
    List<MODEL> getItems();

    boolean hasMore();

}
