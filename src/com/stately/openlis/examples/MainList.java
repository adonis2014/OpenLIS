/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.examples;

import jssc.SerialPortList;

/**
 *
 * @author Edwin
 */
public class MainList {
 
    public static void main(String[] args) {
        //Method getPortNames() returns an array of strings. Elements of the array is already sorted.
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            System.out.println(portNames[i]);
        }
    }
}
