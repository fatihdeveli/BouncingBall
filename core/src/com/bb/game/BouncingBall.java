package com.bb.game;

import com.badlogic.gdx.Game;


class BouncingBall extends Game {

	@Override
	public void create() {
		this.setScreen(new com.bb.screens.MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {

	}

}