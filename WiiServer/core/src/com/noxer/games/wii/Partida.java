package com.noxer.games.wii;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Partida implements Screen{
	private PerspectiveCamera cam, cam2;
	static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;
    private Sprite background;
    private Batch batch;
    private TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    
	
	public Partida(){
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        batch = new SpriteBatch();
        tiledMap = new TmxMapLoader().load("mapa.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        System.out.println(tiledMap.getProperties().get("width",Integer.class));
        
		cam = new PerspectiveCamera(60, 300, 15 * (h / w));
		cam2 = new PerspectiveCamera(30, 15, 15 * (h / w));
		
		//cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(20*32/2f, 0f, -50f);
        cam.lookAt(20*32/2,0,0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.rotate(-50, 1, 0, 0);
        cam.update();
        
        cam2.position.set(15f, 15f, -20f);
        cam2.lookAt(15,15,0);
        cam2.near = 0.1f;
        cam2.far = 300f;
        cam2.rotate(-50, 1, 0, 0);
        cam2.update();
        
        
        
        background = new Sprite(new Texture("fondo2.png"));
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        background.setPosition(0, 0);
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
        Gdx.gl.glViewport(0,Gdx.graphics.getHeight()/2,
        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);      
        batch.begin();
	    /*Upper Half*/          
	    //set the openGl viewport to half the screenheight and starting y from the     middle of the screen
	    //background.draw(batch);
	    //tiledMapRenderer.setView(camera);
        tiledMapRenderer.setView(cam.combined, 0, 0,
        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
        tiledMapRenderer.render();
	    batch.end();
	    

	    cam2.update();
        batch.setProjectionMatrix(cam2.combined);
        Gdx.gl.glViewport(0,0,
        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);    
	    batch.begin();
	    /*bottom Half*/     
	    //set the openGl viewport to half the screenheight and starting y from the     bottom of the screen
	    background.draw(batch);
	    batch.end();

	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = 100f;
        cam.viewportHeight = 30f * height/width;
        cam2.viewportWidth = 20f;
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
