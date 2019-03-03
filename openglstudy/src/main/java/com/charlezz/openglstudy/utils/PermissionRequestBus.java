package com.charlezz.openglstudy.utils;

public class PermissionRequestBus extends RxEventBus<PermissionRequestMsg> {

    private static PermissionRequestBus instance = new PermissionRequestBus();
    public static PermissionRequestBus getInstance(){
        return instance;
    }
    private PermissionRequestBus() { }




}