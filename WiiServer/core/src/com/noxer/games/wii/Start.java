package com.noxer.games.wii;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Start extends Game {
	public boolean primerMandoConectado;
	private Stage stage;
	private Table table;
	private ImageButton backArrow;
	private TextureAtlas atlas;
	private Skin skinButtons;
	protected Skin skin;
	protected Sprite background;
	public int num_Jugadores;
	public static Server s;
	
	
	@Override
	public void create () {
		s = new Server();
		setScreen(new MainMenuScreen(this));
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
