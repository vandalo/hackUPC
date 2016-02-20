package com.noxer.games.wii;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Partida implements Screen{
	private PerspectiveCamera cam, cam2;
	static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;
    private Sprite background;
    private Batch batch;
	
	public Partida(){
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        
		cam = new PerspectiveCamera(40, 15, 15 * (h / w));
		cam2 = new PerspectiveCamera(40, 15, 15 * (h / w));
		
		//cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 0f, -20f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.rotate(-50, 1, 0, 0);
        cam.update();
        
        cam2.position.set(0f, 0f, -20f);
        cam2.lookAt(0,0,0);
        cam2.near = 1f;
        cam2.far = 300f;
        cam2.rotate(-50, 1, 0, 0);
        cam2.update();
        
        background = new Sprite(new Texture("fondo2.png"));
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);        
		//background.draw(batch);

	    /*upperStage.act(delta);  
	    bottomStage.act(delta);    */     
		cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
	    /*Upper Half*/          
	    //set the openGl viewport to half the screenheight and starting y from the     middle of the screen
	    Gdx.gl.glViewport(0,Gdx.graphics.getHeight()/2,
	    		30*Gdx.graphics.getWidth(),30*Gdx.graphics.getHeight()/2);      
	    background.draw(batch);
	    batch.end();

	    cam2.update();
        batch.setProjectionMatrix(cam2.combined);
	    batch.begin();
	    /*bottom Half*/     
	    //set the openGl viewport to half the screenheight and starting y from the     bottom of the screen
	    Gdx.gl.glViewport(0,0,
	    		30*Gdx.graphics.getWidth(),30*Gdx.graphics.getHeight()/2);    
	    background.draw(batch);
	    batch.end();

	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam2.viewportWidth = 30f;
        cam2.viewportHeight = 30f * height/width;
        cam.update();
        cam2.update();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
