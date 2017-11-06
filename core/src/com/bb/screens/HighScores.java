package com.bb.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bb.imageloader.ImageLoader;

import static com.bb.imageloader.ImageLoader.font2;

/**
 * Created by fatih on 18-Feb-17.
 */

class HighScores implements Screen{
    private Stage stage;
    private Game game;
    private int height, width;
    private SpriteBatch batch;

    HighScores(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();

        Label title = new Label("High Scores", ImageLoader.skin);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);



        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = ImageLoader.skin.getDrawable("Button08");
        textButtonStyle.font = font2;

        // PLAY button
        TextButton playButton = new TextButton("PLAY", textButtonStyle);
        playButton.setWidth(width/2);
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

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){

        stage.act();
        stage.draw();
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
