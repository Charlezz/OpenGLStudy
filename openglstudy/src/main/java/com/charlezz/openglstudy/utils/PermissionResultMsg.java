package com.charlezz.openglstudy.utils;

public class PermissionResultMsg {


    public static PermissionResultMsg with(int requestCode, String[] permissions, int[] grantResults){
        return new PermissionResultMsg(requestCode, permissions, grantResults);
    }

    private int requestCode;
    private String[] permissions;
    private int[] grantResults;

    private PermissionResultMsg(int requestCode, String[] permissions, int[] grantResults){
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public int[] getGrantResults() {
        return grantResults;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
