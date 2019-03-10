package me.curiosus.games.flappee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Flappee {

    private static final float COLLISION_RADIUS = 48;
    private static final float DIVE_ACCEL = .30F;
    private static final float FLY_ACCEL = 5.0F;
    private static final int TILE_WIDTH = 66;
    private static final int TILE_HEIGHT = 66;
    private static final float FRAME_DURATION = 0.25f;


    private Circle collisionCircle;
    private float x;
    private float y;
    private float ySpeed = 0f;
    private final Animation animation;
    private float animationTimer;


    public Flappee(Texture flappeeTexture) {
        TextureRegion [][] textures =
                new TextureRegion(flappeeTexture).split(TILE_WIDTH, TILE_HEIGHT);
        animation = new Animation(FRAME_DURATION, textures[0][0], textures[0][1]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        collisionCircle = new Circle(x, y, COLLISION_RADIUS);

    }


    public void update(float delta) {
        animationTimer += delta;
        ySpeed -= DIVE_ACCEL;
        setPosition(x, y + ySpeed);
    }

    public void flyUp() {
        ySpeed = FLY_ACCEL;
        setPosition(x, y + ySpeed);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
    }

    public void draw(Batch spriteBatch) {
       TextureRegion texture = (TextureRegion) animation.getKeyFrame(animationTimer);
       float x = collisionCircle.x -  texture.getRegionWidth() / 2.0f;
       float y = collisionCircle.y -  texture.getRegionHeight() / 2.0f;
       spriteBatch.draw(texture, x, y);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateCollisionCircle();
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x);
        collisionCircle.setY(y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public final Circle getCollisionCircle() {
       return collisionCircle;
    }
}