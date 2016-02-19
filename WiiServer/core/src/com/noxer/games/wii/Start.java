package com.noxer.games.wii;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Start extends ApplicationAdapter {
	
	@Override
	public void create () {
		Server s = new Server();
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
