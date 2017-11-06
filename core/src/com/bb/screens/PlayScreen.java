package com.bb.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bb.imageloader.ImageLoader;
import com.bb.objects.Ball;
import com.bb.objects.Block;
import com.bb.objects.Cloud;

import static com.badlogic.gdx.utils.TimeUtils.millis;

/**
 * Created by fatih on 09-Feb-17.
 */

public class PlayScreen implements Screen, InputProcessor{

    private Game game;
    private SpriteBatch batch; // SpriteBatch is used for drawing to screen
    private Ball ball; // ball object
    private int BLOCK_SIZE_X; // Block x size
    private int BLOCK_SIZE_Y; // Block y size
    private int blockNumbers = 10; // number of blocks on the screen
    private Block[] blocks = new Block[blockNumbers]; // blocks array of Block objects
    public static int height = Gdx.graphics.getHeight(); // screen height
    public static int width = Gdx.graphics.getWidth(); // screen width
    private boolean collision = true; // Demonstrates collision of the ball and a block
    private boolean bouncable = true; // Demonstrates if the ball can bounce
    static float score = 0; // Player score
    private int[][] swipeCoords; // swipe[0][] --> touchdown, swipe[1][] --> touch up
    private float horizontalConstant, verticalConstant; // Horizontal and vertical speed multipliers
    private Vector2 velocity; // Vector keeps the swipe speed.
    private float gravity; // In-game gravity
    // Objects are moved by units instead of pixels to get the same movement on different screens.
    public static float unit = height/100; // unit length
    private float speedConst = 0.4f; // speed constant
    private float speed = unit * speedConst; //speed of the game

    private Cloud clouds[] = new Cloud[5]; // Clouds in the background
    static int level = 1; // Current level of the game
    private boolean levelUp = false; // Level up indicator
    private long time;


    PlayScreen(Game aGame, SpriteBatch batch){

        game = aGame;
        //stage = new Stage(new ScreenViewport());

        this.batch = batch;

        // Block sizes are adjusted here.
        BLOCK_SIZE_X = (int)(width / 3.7);
        BLOCK_SIZE_Y = height / 64;

        generateClouds();
        generateBlocks();	// Generates blocks
        generateBall();		// Generates the ball



        horizontalConstant = 0.0021f;	// Setting the horizontal speed multiplier
        verticalConstant = 0.0042f;		// Setting the vertical speed multiplier

        swipeCoords = new int [2][2];	// Swipe data is kept here
        velocity = new Vector2();		// Initial velocity of the ball is kept in this Vector2.

         // setting the gravity
        }


    @Override
    public void show() {
        Gdx.app.log("PlayScreen", "show");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.55f, 0.76f, 0.86f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //stage.act();
        //stage.draw();

        delta = Gdx.graphics.getDeltaTime() * 45;
        gravity = unit*delta*0.127f;

        batch.begin();

        // Regular game activity
        gameState(delta);

        batch.end();
    }

    private void gameState(float delta){

        moveClouds(delta);
        checkCollision();            // Collision is checked
        moveBlocks(batch, speed, delta);    // Blocks are moved
        moveBall(delta);                    // The ball is moved

        // Printing the score
        ImageLoader.scoreFont.setColor(1, 1, 1, 1);
        ImageLoader.scoreFont.draw(batch, "Score: " + (int) score, 50, height
                - height/10 // set this to height/40 for screen without banner. Default: height/10
        );


        // Move the ball if collision detected. Ball moves down with the block.
        if (!collision)
            ball.moveBall(velocity, gravity, delta);

        // Update the score
        score += (speed / unit) * delta * 0.2;

        time = millis();
        levelUp();

        // Checks if the ball fell down.
        if(ball.getBallSprite().getY() < 0){
            game.setScreen(new EndScreen(game, batch, score));     // Switch to end screen
        }

    }

    // Generates a random number within the specified range
    public static int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    // Ball generator function: generates the ball on the first block.
    private void generateBall(){
        ball = new Ball(blocks[0].getPosX()+blocks[0].getWidth()/2 - width/30,
                blocks[0].getPosY() + blocks[0].getHeight(), width/14, ImageLoader.ball);
        // The size of the ball is width/14
    }

    // Moves the ball with the block.
    private void moveBall(float delta){
        batch.draw(ball.getTextureRegion(), ball.getBallSprite().getX(),
                ball.getBallSprite().getY(),ball.getBallSize(), ball.getBallSize());

        if(collision){
            ball.getBallSprite().translateY(-speed * delta);
        }
    }

    // Generates blocks
    private void generateBlocks(){
        int posX, posY;
        posY = height;

        // Loop generates the blocks.
        for (int i = 0; i < blocks.length; i++){
            posX = randomWithRange(0, width-BLOCK_SIZE_X);
            blocks[i]= new Block(posX, posY, BLOCK_SIZE_X, BLOCK_SIZE_Y, ImageLoader.block);

            blocks[i].getSprite().setY(posY);
            blocks[i].getSprite().setX(posX);
            posY += height/blockNumbers;
        }
    }

    // Function moves the blocks with the specified game speed.
    private void moveBlocks(SpriteBatch batch, float speed, float delta){
        for (Block block : blocks) {
            batch.draw(block.getTextureRegion(), block.getSprite().getX(),
                    block.getSprite().getY(), BLOCK_SIZE_X, BLOCK_SIZE_Y);
            block.moveBlock(speed, delta);
        }
    }

    // Function checks collision of the ball and the blocks.
    // The function checks overlapping of the largest inner square of the circle and the bounding
    // rectangle of the blocks.

/*  ORIGINAL
    private void checkCollision() {
        if(!collision){
            Rectangle ballRec = ball.getBallSprite().getBoundingRectangle();
            for (int i = 0; i < blockNumbers; i++){ // Collision is checked with each block.
                // Width of the blocks' bounding rectangles are slightly reduced to make the ball
                // fall more realistically on the edges of the blocks.
                Rectangle blockRec = blocks[i].getSprite().getBoundingRectangle();
                blockRec.setWidth(blockRec.getWidth() - ballRec.getWidth()*0.75f);
                blockRec.setX(blockRec.getX() + ballRec.getWidth()*0.375f);
                blockRec.setHeight(blockRec.getHeight()*2);
                blockRec.setY(blockRec.getY() - blockRec.getHeight()/2);
                if (ballRec.overlaps(blockRec) && velocity.y < 0 &&
                        ballRec.getY() > blockRec.getY()){
                    ball.getBallSprite().setY(blockRec.getY()+ blockRec.getHeight());
                    collision = true; // now collision is detected
                    bouncable = true; // now the ball can bounce again
                }
            }
        }
    }
*/

    private void checkCollision(){
        if(!collision){
            Rectangle ballRec = ball.getBallSprite().getBoundingRectangle();
            float blockHeight = ballRec.getHeight();
            for (int i = 0; i < blockNumbers; i++){ // Collision is checked with each block.
                // Width of the blocks' bounding rectangles are slightly reduced to make the ball
                // fall more realistically on the edges of the blocks.
                Rectangle blockRec = blocks[i].getSprite().getBoundingRectangle();
                blockRec.setWidth(blockRec.getWidth() - ballRec.getWidth()*0.75f);
                blockRec.setX(blockRec.getX() + ballRec.getWidth()*0.375f);
                blockRec.setHeight(blockRec.getHeight()*3); // collision zone is larger

                blockRec.setY(blockRec.getY() - blockRec.getHeight()*2/3);
                if (ballRec.overlaps(blockRec) // Ball and block collision zone overlaps
                        && velocity.y < 0 // ball is falling
                        && ballRec.getY() > blockRec.getY() + 2*blockHeight/3

                        // Ball is above the block
                        ){
                    System.out.println("ballY: " + ballRec.getY() + "  blockY: " + blockRec.getY());
                    ball.getBallSprite().setY(blockRec.getY() + blockRec.getHeight());
                    collision = true; // now collision is detected
                    bouncable = true; // now the ball can bounce again
                }
            }
        }
    }


    private void generateClouds(){
        clouds[0] = new Cloud(ImageLoader.cloud1);
        clouds[1] = new Cloud(ImageLoader.cloud1);
        clouds[2] = new Cloud(ImageLoader.cloud1);
        clouds[3] = new Cloud(ImageLoader.cloud2);
        clouds[4] = new Cloud(ImageLoader.cloud2);
    }

    private void moveClouds(float delta) {
        for (Cloud cloud: clouds){
            cloud.moveCloud(batch, delta);
        }
    }


    private void levelUp(){
        // Increasing the speed
        if (score > level * 100){
            speedConst += 0.07f;
            speed = speedConst * unit;
            level++;
            System.out.println("Speed: " + speed);
            levelUp = true;
        }
        else if (score < level*100 -90 && level != 1) {
            ImageLoader.levelFont.draw(batch, "  Level " + level + "\nSpeed Up!", width * 0.6f,height - height/10);
        }

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
    public void dispose() {
        batch.dispose();
        ball.dispose();
        for (Block block : blocks) {
            block.dispose();
        }
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
        // Swipe coordinates are kept within 2 dimensional arrays.
        // swipe[0][] --> touchdown, swipe[1][] --> touch up
        swipeCoords[0][0] = screenX;
        swipeCoords[0][1] = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(bouncable){
            swipeCoords[1][0] = screenX;
            swipeCoords[1][1] = screenY;

            // Swipe data is recorded.
            int vectorY = swipeCoords[1][1] - swipeCoords[0][1];
            int vectorX = swipeCoords[1][0] - swipeCoords[0][0];

            // System.out.println("vectorY: " + vectorY + " vectorX: " + vectorX);

            // Vertical swipe is limited with the value -600.
            if(vectorY < -600) {
                vectorY = -600;
            }

            velocity.y = -vectorY * unit * verticalConstant; // Y axis speed value

            // X axis swipe limitations
            if(vectorX > 300)
                vectorX = 300;
            if(vectorX < -300)
                vectorX = -300;

            velocity.x = vectorX * unit * horizontalConstant; // X axis speed value

            // The ball can not jump again right after jumping.
            collision = false;
            bouncable = false;
        }
        return true;
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

}
