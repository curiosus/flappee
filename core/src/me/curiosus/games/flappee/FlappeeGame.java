package me.curiosus.games.flappee;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class FlappeeGame extends Game {

    private final AssetManager assetManager;

    public FlappeeGame() {
        assetManager = new AssetManager();
    }

    @Override
    public void create() {
        setScreen(new StartScreen(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
