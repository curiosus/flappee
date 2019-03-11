package me.curiosus.games.flappee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Flower {

    private static final float COLLISION_RECTANGLE_WIDTH = 13F;
    private static final float COLLISION_RECTANGLE_HEIGHT = 447F;
    private static final float COLLISION_CIRCLE_RADIUS = 33f;
    private static final float MAX_SPEED_PER_SECOND = 100F;
    private static final float HEIGHT_OFFSET = -300F;
    private static final float DISTANCE_BETWEEN_FLOOR_AND_CEILING = 480;

    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;

    private final Circle ceilingCollisionCircle;
    private final Rectangle ceilingCollisionRectangle;
    private final Circle floorCollisionCircle;
    private final Rectangle floorCollisionRectangle;
    private boolean pointScored;

    private float x;
    private float y;

    public Flower() {
        y = MathUtils.random(HEIGHT_OFFSET);
        floorCollisionRectangle = new Rectangle(x, y, COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
        floorCollisionCircle = new Circle(x + floorCollisionRectangle.width / 2, y + floorCollisionRectangle.height, COLLISION_CIRCLE_RADIUS);

        ceilingCollisionRectangle = new Rectangle(
                x,
                floorCollisionCircle.y + DISTANCE_BETWEEN_FLOOR_AND_CEILING,
                COLLISION_RECTANGLE_WIDTH,
                COLLISION_RECTANGLE_HEIGHT
        );
        ceilingCollisionCircle = new Circle(
                x + ceilingCollisionRectangle.width / 2,
                ceilingCollisionRectangle.y,
                COLLISION_CIRCLE_RADIUS
        );
    }

    public void setPosition(float x) {
        this.x = x;
        updateCollisionRectangle();
        updateCollisionCircle();
    }

    public void update(float delta) {
        setPosition(x - MAX_SPEED_PER_SECOND * delta);

    }

    private void updateCollisionRectangle() {
        floorCollisionRectangle.setX(x);
        ceilingCollisionRectangle.setX(x);
    }

    private void updateCollisionCircle() {
        floorCollisionCircle.setX(x + floorCollisionRectangle.width / 2);
        ceilingCollisionCircle.setX(x + ceilingCollisionRectangle.width / 2);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(floorCollisionCircle.x,
                             floorCollisionCircle.y,
                             floorCollisionCircle.radius);

        shapeRenderer.rect(floorCollisionRectangle.x,
                           floorCollisionRectangle.y,
                           floorCollisionRectangle.width,
                           floorCollisionRectangle.height);

        shapeRenderer.circle(ceilingCollisionCircle.x,
                             ceilingCollisionCircle.y,
                             ceilingCollisionCircle.radius);

        shapeRenderer.rect(ceilingCollisionRectangle.x,
                           ceilingCollisionRectangle.y,
                           ceilingCollisionRectangle.width,
                           ceilingCollisionRectangle.height);





    }

    public void draw(SpriteBatch batch, TextureRegion flowerBottom, TextureRegion flowerTop) {
        batch.draw(flowerBottom,
                   floorCollisionRectangle.x,
                   floorCollisionRectangle.y,
                   floorCollisionRectangle.width,
                   floorCollisionRectangle.height);

        batch.draw(flowerBottom, ceilingCollisionRectangle.x,
                                 ceilingCollisionRectangle.y,
                                 ceilingCollisionRectangle.width,
                                 ceilingCollisionRectangle.height);

        batch.draw(flowerTop, floorCollisionCircle.x - floorCollisionCircle.radius,
                             floorCollisionCircle.y - floorCollisionCircle.radius,
                             2 * floorCollisionCircle.radius,
                             2 * floorCollisionCircle.radius);

        batch.draw(flowerTop,
                   ceilingCollisionCircle.x - ceilingCollisionCircle.radius,
                   ceilingCollisionCircle.y - ceilingCollisionCircle.radius,
                   2 * ceilingCollisionCircle.radius,
                   2 * ceilingCollisionCircle.radius);
    }

    public float getX() {
        return x;
    }

    public boolean isFlappeeColliding(Flappee flappee) {
        return Intersector.overlaps(flappee.getCollisionCircle(), ceilingCollisionCircle) ||
                    Intersector.overlaps(flappee.getCollisionCircle(), floorCollisionCircle) ||
                    Intersector.overlaps(flappee.getCollisionCircle(), ceilingCollisionRectangle) ||
                    Intersector.overlaps(flappee.getCollisionCircle(), floorCollisionRectangle);
    }

    public boolean isPointScored() {
        return pointScored;
    }

    public void setPointScored() {
        pointScored = true;
    }
}
