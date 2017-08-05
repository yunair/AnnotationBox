package com.air.transannotation;

/**
 * Created by air on 3/29/17.
 */

public interface Consumer<T> {
    void call(T t);
}
