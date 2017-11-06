package com.bb.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bb.screens.PlayScreen;

/*
* Class Block
* Block objects represent the blocks in the game.
*
* */

public class Block {

    private int posX, posY, width, height;
    private Sprite sprite;
    private TextureRegion region;

    public Block(int x, int y, int width, int height, TextureRegion region)
    {
        posX = x;
        posY = y;
        this.width = width;
        this.height = height;
        this.region = region;
        sprite = new Sprite(region, posX, posY, width, height);
    }

    public void moveBlock(float gameSpeed, float delta){
        sprite.translateY(-gameSpeed * delta);

        if(sprite.getY() < 0) {
            sprite.setY(PlayScreen.height);
            sprite.setX((float)Math.random()*(PlayScreen.width-width));
        }
    }


    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TextureRegion getTextureRegion() {
        return region;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void dispose(){

    }

}

