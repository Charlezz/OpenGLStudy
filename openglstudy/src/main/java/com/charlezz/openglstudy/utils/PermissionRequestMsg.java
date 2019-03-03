package com.charlezz.openglstudy.utils;

public class PermissionRequestMsg {
    public static PermissionRequestMsg with(String[] permissions, int requestCode){
        return new PermissionRequestMsg(permissions, requestCode);
    }

    private String[] permissions;
    private int requestCode;

    private PermissionRequestMsg(String[] permissions, int requestCode){
        this.permissions = permissions;
        this.requestCode = requestCode;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
