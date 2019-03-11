package me.curiosus.games.flappee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {

    public static final float WORLD_WIDTH = 2 * 640;
    public static final float WORLD_HEIGHT = 2 * 480;

    private static final float GAP_BETWEEN_FLOWERS = 400F;

    private ShapeRenderer shapeRenderer;

    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private Flappee flappee;
    private Array<Flower> flowers;
    private int score;

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    private TextureRegion background;
    private TextureRegion flowerBottom;
    private TextureRegion flowerTop;
    private TextureRegion flappeeTexture;
    private FlappeeGame flappeeGame;


    public GameScreen(FlappeeGame game) {
        flappeeGame = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        System.out.println("GameScreen " + flappeeGame.hashCode());
        TextureAtlas textureAtlas = flappeeGame.getAssetManager().get("core/assets/flappee_assets.atlas");
        flowers = new Array<Flower>();
        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout();
        flappee = new Flappee(textureAtlas.findRegion("flappee"));
        flappee.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        background = textureAtlas.findRegion("bg");
        flowerBottom = textureAtlas.findRegion("flowerbottom");
        flowerTop = textureAtlas.findRegion("flowertop");
    }

    private void drawScore() {
        String displayScore = Integer.toString(score);
        glyphLayout.setText(bitmapFont, displayScore);
        bitmapFont.draw(batch,
                displayScore,
                100.0f,
                100.0f);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        draw();
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        flappee.drawDebug(shapeRenderer);
        drawFlowers();
        shapeRenderer.end();
        update(delta);
    }

    private void draw() {
       batch.totalRenderCalls = 0;
       batch.setProjectionMatrix(camera.projection);
       batch.setTransformMatrix(camera.view);
       batch.begin();
       batch.draw(background, 0, 0, GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT);
       flappee.draw(batch);
       drawScore();
        for (Flower flower : flowers) {
            flower.draw(batch, flowerBottom, flowerTop);
        }
       batch.end();
       Gdx.app.log("Debug", String.valueOf(batch.totalRenderCalls));
    }

    private void update(float delta) {
        flappee.update(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            flappee.flyUp();
            enforceBoundries();
        }
        updateFlowers(delta);
        if (checkForCollision()) {
            restart();
        }
        registerScore();
    }

    private void registerScore() {
        for (Flower flower : flowers) {
            if (flappee.getX() > flower.getX() && !flower.isPointScored()) {
                score++;
                flower.setPointScored();
                ;
                System.out.println(score);
            }
        }
    }

    private void restart() {
        flappee.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        flowers.clear();
        score = 0;
    }

    private void updateFlowers(float delta) {
        for (Flower flower : flowers) {
            flower.update(delta);
        }
        checkIfNewFlowerNeeded();
        removeFlowerIfPassed();
    }

    private void drawFlowers() {
        for (Flower flower : flowers) {
            flower.drawDebug(shapeRenderer);
        }
    }

    private void createNewFlower() {
        Flower flower = new Flower();
        flower.setPosition(WORLD_WIDTH + Flower.WIDTH);
        flowers.add(flower);
    }

    private void checkIfNewFlowerNeeded() {
        if (flowers.size == 0) {
            createNewFlower();
        } else {
            Flower flower = flowers.peek();
            if (flower.getX() < WORLD_WIDTH - GAP_BETWEEN_FLOWERS) {
                createNewFlower();
            }
        }
    }

    private void removeFlowerIfPassed() {
        if (flowers.size > 0) {
            Flower firstFlower = flowers.first();
            if (firstFlower.getX() < -Flower.WIDTH) {
                flowers.removeValue(firstFlower, true);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void enforceBoundries() {
        flappee.setPosition(flappee.getX(), MathUtils.clamp(flappee.getY(), 0, WORLD_HEIGHT));
    }

    public boolean checkForCollision() {
        for (Flower flower : flowers) {
            if (flower.isFlappeeColliding(flappee)) {
                return true;
            }
        }
        return false;
    }

}