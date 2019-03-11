package me.curiosus.games.flappee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;

public class LoadingScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = GameScreen.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = GameScreen.WORLD_HEIGHT;
    private static final float PROGRESS_BAR_WIDTH = 100.0f;
    private static final float PROGRESS_BAR_HEIGHT = 25.0f;

    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Camera camera;
    private float progress = 0.0f;
    private final FlappeeGame flappeeGame;

    private int counter = 0;

    public LoadingScreen(FlappeeGame game) {
        flappeeGame = game;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2.0f, WORLD_HEIGHT / 2.0f, 0.0f);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        flappeeGame.getAssetManager().load("core/assets/flappee_assets.atlas", TextureAtlas.class);

//        flappeeGame.getAssetManager().load("core/assets/bg.png", Texture.class);
//        flappeeGame.getAssetManager().load("core/assets/flappee.png", Texture.class);
//        flappeeGame.getAssetManager().load("core/assets/flowerbottom.png", Texture.class);
//        flappeeGame.getAssetManager().load("core/assets/flowertop.png", Texture.class);
        flappeeGame.getAssetManager().finishLoading();

    }

    @Override
    public void render(float delta) {
        update();
        clearScreen();
        draw();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void update() {
        if (flappeeGame.getAssetManager().update()) {
            flappeeGame.setScreen(new GameScreen(flappeeGame));
        } else {
            progress = flappeeGame.getAssetManager().getProgress();
        }

    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect((WORLD_WIDTH - PROGRESS_BAR_WIDTH) / 2,
                (WORLD_HEIGHT - PROGRESS_BAR_HEIGHT / 2),
                progress * PROGRESS_BAR_WIDTH,
                PROGRESS_BAR_HEIGHT);
        shapeRenderer.end();
    }
}
