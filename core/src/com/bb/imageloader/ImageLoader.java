package com.bb.imageloader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/*
* Class ImageLoader is used to load and use the fonts on the game screen.
* */

public class ImageLoader {
    public static BitmapFont font2, scoreFont, endFont, levelFont;
    public static TextureRegion ball, block, cloud1, cloud2, logo, button1;
    public static Skin skin;

    public static void load(){

        skin = new Skin();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assets.pack"));
        skin.addRegions(atlas);
        ball = atlas.findRegion("sphere-23");
        block = atlas.findRegion("block_blue");
        cloud1 = atlas.findRegion("SingleCloud");
        cloud2 = atlas.findRegion("LargeCloud");
        logo = atlas.findRegion("logo");


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("BRLNSR.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = Gdx.graphics.getHeight()/20;
        params.color = Color.WHITE;
        font2 = generator.generateFont(params);

        params.size = Gdx.graphics.getHeight()/20;
        params.color = Color.WHITE;
        params.shadowOffsetX = 4;
        params.shadowOffsetY = 4;
        scoreFont = generator.generateFont(params);

        params.size = Gdx.graphics.getHeight()/25;
        params.color = Color.WHITE;
        params.shadowOffsetX = 4;
        params.shadowOffsetY = 4;
        levelFont = generator.generateFont(params);

        params.size = Gdx.graphics.getHeight()/14;
        params.color = Color.WHITE;
        endFont = generator.generateFont(params);
    }

    public static void dispose(){
        font2.dispose();
        scoreFont.dispose();
        endFont.dispose();
    }

}


