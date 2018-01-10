package com.mattsmith.demigodstocklevel;

/**
 * Created by mattb_000 on 23/12/2017.
 */

public interface ProductListItem_Delegate {
    void onClickIncrement(String name);
    void onClickDecrease(String name);
    void onCountManuallyUpdated(String name, String newCount);
}
