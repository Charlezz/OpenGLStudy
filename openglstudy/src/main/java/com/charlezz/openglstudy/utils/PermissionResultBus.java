package com.charlezz.openglstudy.utils;

public class PermissionResultBus extends RxEventBus<PermissionResultMsg> {

    private static PermissionResultBus instance = new PermissionResultBus();
    public static PermissionResultBus getInstance(){
        return instance;
    }
    private PermissionResultBus() { }

}