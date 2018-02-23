package com.mygdx.platformer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.platformer.PlatformManGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.width = 1067;
            config.height = 600;
            config.vSyncEnabled = true;
            new LwjglApplication(new PlatformManGame(), config); 
	}
}
