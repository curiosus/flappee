package me.curiosus.games.flappee;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StartScreen extends ScreenAdapter {

    private Stage stage;
    private Texture backgroundTexture;
    private Texture playTexture;
    private Texture playPressTexture;
    private Texture titleTexture;
    private FlappeeGame game;

    public StartScreen(FlappeeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        backgroundTexture = new Texture(Gdx.files.internal("core/assets/bg.png"));
        Image background = new Image(backgroundTexture);

        titleTexture = new Texture(Gdx.files.internal("core/assets/title.png"));
        Image title = new Image(titleTexture);
        title.setPosition(GameScreen.WORLD_WIDTH / 2.0f, 3.0f * GameScreen.WORLD_HEIGHT / 4.0f,
                Align.center);

        playTexture = new Texture(Gdx.files.internal("core/assets/play.png"));
        playPressTexture = new Texture(Gdx.files.internal("core/assets/playpressed.png"));

        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)),
                new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        play.setPosition(GameScreen.WORLD_WIDTH / 2.0f, GameScreen.WORLD_HEIGHT / 4.0f, Align.center);
        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new LoadingScreen(game));
                dispose();
            }
        });


        stage = new Stage(new FitViewport(GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        stage.addActor(background);
        stage.addActor(play);
        stage.addActor(title);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        playTexture.dispose();
        playPressTexture.dispose();
        titleTexture.dispose();
    }
}
