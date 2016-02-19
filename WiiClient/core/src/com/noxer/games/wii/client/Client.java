package com.noxer.games.wii.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


public class Client extends Game {
	public static int height, width;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		this.setScreen(new LogoScreen(this));
	}

	@Override
	public void render () {
		//boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		super.render();
		
		//Gdx.app.log("Aviable: ", ""+available);
	}
	
	@Override
	public void resize (int width, int height) { 
		super.resize(width, height);
	}
	
	
}
