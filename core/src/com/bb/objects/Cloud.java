package com.bb.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.bb.screens.PlayScreen.height;
import static com.bb.screens.PlayScreen.randomWithRange;
import static com.bb.screens.PlayScreen.unit;
import static com.bb.screens.PlayScreen.width;

/**
 * Created by fatih on 15-Feb-17.
 */

public class Cloud {

    private TextureRegion cloudRegion;
    private int posX, posY;
    private int size;

    public Cloud(TextureRegion region) {
        cloudRegion = region;
        size = randomWithRange(width/4, width/2);
        posX = randomWithRange(-size, width);           // Anywhere between the sides
        posY = randomWithRange(height, 2 * height);     // and above the screen
    }

    // Method moveCloud draws the cloud and updates its position.
    public void moveCloud(SpriteBatch batch, float delta) {
        batch.draw(cloudRegion, posX, posY, size, size/2);
        int movement = (int)(size * delta * unit * 0.0005);
        if (movement<1)
            movement = 1;
        posY -= movement;
        if (posY < -size){ // Position is randomized again after disappearing.
            posX = randomWithRange(-size/2, width-size/2);
            posY = randomWithRange(height, (int)(1.5 * height));
        }
    }

    public int getPosY(){
        return posY;
    }
}
