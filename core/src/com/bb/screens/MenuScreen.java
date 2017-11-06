package com.bb.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bb.imageloader.ImageLoader;

import static com.bb.imageloader.ImageLoader.font2;

/**
 * Created by fatih on 02-Feb-17.
 */

public class MenuScreen implements Screen{

    private Stage stage;
    private Game game;
    private int height, width;
    private SpriteBatch batch;

    public MenuScreen(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        ImageLoader.load();

        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();



        // PLAY button
        TextButton.TextButtonStyle playButtonStyle = new TextButton.TextButtonStyle();
        playButtonStyle.up = ImageLoader.skin.getDrawable("Button09");
        playButtonStyle.font = font2;

        TextButton playButton = new TextButton("PLAY", playButtonStyle);
        playButton.setWidth(width*0.6f);
        playButton.setHeight(height/15);
        playButton.setPosition(width/2 - playButton.getWidth()/2,
                height/2 - playButton.getHeight());

        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayScreen(game, batch));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);

/*
        // High scores button
        TextButton scoresButton = new TextButton("High Scores", playButtonStyle);
        scoresButton.setWidth(width*0.6f);
        scoresButton.setHeight(height/15);
        scoresButton.setPosition(width/2 - playButton.getWidth()/2,
                height/2 - 2.5f*playButton.getHeight());

        scoresButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HighScores(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(scoresButton);


        // About button
        TextButton aboutButton = new TextButton("About", playButtonStyle);
        aboutButton.setWidth(width*0.6f);
        aboutButton.setHeight(height/15);
        aboutButton.setPosition(width/2 - playButton.getWidth()/2,
                height/2 - 4*playButton.getHeight());

        aboutButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HighScores(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(aboutButton);

*/


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.827f, 0.847f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        stage.getBatch().begin();
        //                                    x pos          y pos                  x scale     y scale
        stage.getBatch().draw(ImageLoader.logo, 0, height-height/12-(int)(width/1.6), width, (int)(width/1.6));

        // (height/12) is approximately the height of the smart banner, which can have height
        // value of 32/50/90 dp
        // (int)(width/1.6) is the height of the logo after scaling.
        stage.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    @Override
    public void hide() {

    }


    @Override
    public void dispose(){
        stage.dispose();
        ImageLoader.dispose();
    }
}
