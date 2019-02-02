package com.charlezz.openglstudy.feature.main;

public class MainMenu {
    private Navigator navigator;
    private RendererType rendererType;

    public MainMenu(RendererType rendererType, Navigator navigator) {
        this.rendererType = rendererType;
        this.navigator = navigator;
    }

    public void onMenuClicked(){
        navigator.onMenuClicked(rendererType);
    }

    public int getName() {
        return rendererType.getName();
    }

    interface Navigator{
        void onMenuClicked(RendererType rendererType);
    }
}
