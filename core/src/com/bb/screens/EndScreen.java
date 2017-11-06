package com.bb.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bb.imageloader.ImageLoader;

/**
 * Created by fatih on 02-Feb-17.
 */

class EndScreen extends Game implements Screen, InputProcessor {

    private SpriteBatch batch;
    private Game game;
    private int[] touch = new int[2];
    private float score;
    private int high1;
    private Preferences prefs;

    EndScreen(Game game, SpriteBatch batch, float score){
        this.game = game;
        this.batch = batch;
        this.score = score;

        prefs = Gdx.app.getPreferences("com.bb.game.score");
        this.high1 = prefs.getInteger("high1", 0);

        if (score > high1) {
            prefs.putInteger("high1", (int)score);
            prefs.flush();
        }

    }

    @Override
    public void create(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.2f, 0, 0.478f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        batch.begin();
        ImageLoader.endFont.draw(batch, "GAME OVER", PlayScreen.width/2, 2* PlayScreen.height/3, 1/2, 1, true);
        ImageLoader.scoreFont.draw(batch, "Score: " +(int)score + " (Level " + PlayScreen.level +")"
                + "\nHigh Score: " + prefs.getInteger("high1")
                + "\n\nTap to play again",
                PlayScreen.width/2, 2* PlayScreen.height/3 - PlayScreen.height/6, 1/2, 1, true);
        batch.end();
    }





    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch[0] = screenX; touch[1] = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!(Math.abs(screenX - touch[0]) > 200 || Math.abs(screenY - touch[1]) > 200)){
            PlayScreen.score = 0;
            game.setScreen(new PlayScreen(game, batch));
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void dispose(){
        batch.dispose();
        ImageLoader.dispose();
    }

}
