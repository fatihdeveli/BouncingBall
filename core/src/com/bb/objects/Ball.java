package com.bb.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/*
* Class Ball
* The ball object represents the ball in the game.
*
* */
public class Ball {

    private TextureRegion ballRegion;
    private float posX, posY;
    private Sprite ballSprite;
    private int ballSize;

    public Ball(int posX, int posY, int ballSize, TextureRegion region) {
        this.ballRegion = region;
        this.posX = (float)posX;
        this.posY = (float)posY;
        this.ballSize = ballSize;
        ballSprite = new Sprite(ballRegion, posX, posY, ballSize, ballSize);
        ballSprite.setPosition(posX,posY);
    }

    public void moveBall(Vector2 ballVelocity, float gravity, float delta)
    {
        ballSprite.translateX(ballVelocity.x * delta);
        ballSprite.translateY(ballVelocity.y * delta);

        if (ballVelocity.y > -50) // vertical speed is limited
            ballVelocity.y -= gravity;

        // ball passes through left and right sides
        if(ballSprite.getX() + getBallSize()/2 > com.bb.screens.PlayScreen.width)
            ballSprite.setCenter(1, ballSprite.getY());

        if(ballSprite.getX() + getBallSize()/2 < 0)
            ballSprite.setCenter(com.bb.screens.PlayScreen.width, ballSprite.getY());
    }

    public int getBallSize() {
        return ballSize;
    }

    public Sprite getBallSprite() {
        return ballSprite;
    }

    public TextureRegion getTextureRegion() {
        return ballRegion;
    }


    public void dispose(){
    }
}

