/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis;

/**
 *
 * @author Administrator
 * @param <T> Type of message/data expected
 */
public interface DataCallback<T> 
{
    void onData(T  newDataReady);
}
