package com.noxer.games.wii;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.noxer.games.entities.Car;
import com.noxer.games.entities.auxCar;

public class Partida implements Screen{
	private PerspectiveCamera cam, cam2, camModel;
	static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;
    private Sprite background;
    private Batch batch;
    private TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    //private Sprite car;
    private ModelBatch modelBatch;
    private Model model, model2;
    public ModelInstance instance;
    public Environment environment;
    public World world;
    public Array<Body> bodiesToDestroy = new Array<Body>(false, 16);
    Box2DDebugRenderer debugRenderer;
    Car carcito, carcitoFerran;
    auxCar carDani, carFerran;
    private float timeToStart;  
    private float lookX, lookY;
    Stage semafor;
    boolean start;
    boolean firstTime;
	private TextureAtlas atlas, gameUI;
	protected Skin skin, skin2;
	
	public Partida(){
		firstTime = true;
		start = false;
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        batch = new SpriteBatch();
        tiledMap = new TmxMapLoader().load("mapa-cercle.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //skin = new Skin(gameUI);
        world = new World(new Vector2(0, 0),true);
		world.setContactListener(new ContListener(this));
		timeToStart = 5; 
		debugRenderer = new Box2DDebugRenderer();
		
		atlas = new TextureAtlas("skins/userInterface.pack");
		gameUI = new TextureAtlas("skins/gameUI.pack");
		skin = new Skin(Gdx.files.internal("skins/userInterface.json"), atlas);
		
		semafor = new Stage(new StretchViewport(800, 480));
		
        
		
/////////////////////////////////////
		///COCHES
/////////////////////////////////////
        carcito = new Car(this, new Sprite(new Texture("cochehiperrealista-01.png")), new Sprite(new Texture("cochehiperrealista-01.png")),
        		new Sprite(new Texture("cochehiperrealista-01.png")), new Sprite(new Texture("cochehiperrealista-01.png")), 
        		new Sprite(new Texture("cochehiperrealista-01.png")), 0);
        carcito.setPosition(15*32/2, 10);
        carcito.scale(0.7f);
        carcito.initBody(world);
        ///
        carcitoFerran = new Car(this, new Sprite(new Texture("cochehiperrealista-01.png")), new Sprite(new Texture("cochehiperrealista-01.png")),
        		new Sprite(new Texture("cochehiperrealista-01.png")), new Sprite(new Texture("cochehiperrealista-01.png")), 
        		new Sprite(new Texture("cochehiperrealista-01.png")), 1);
        carcitoFerran.setPosition(25*32/2, 10);
        carcitoFerran.scale(0.7f);
        carcitoFerran.initBody(world);
        ////////
/////////////////////////////////////
/////////////////////////////////////
        
		cam = cam2 = new PerspectiveCamera(60, 15, 15 * (h / w));
		//cam2 = new PerspectiveCamera(60, 15, 15 * (h / w));

        background = new Sprite(new Texture("fondo2.png"));
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        background.setPosition(0, 0);
        
        modelBatch = new ModelBatch();
        
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        model2 = new ObjLoader().loadModel(Gdx.files.internal("car-formula-violet.obj"));
        model = new ObjLoader().loadModel(Gdx.files.internal("car-formula-white.obj"));
        //instance = new ModelInstance(model2, new Vector3(20*32/2f, 150,0));
        //carDani = new auxCar(this, model, new Vector3(52*32/2f, 100+90*32/2f,0), 0);
        //carFerran = new auxCar(this, model2, new Vector3(54*32/2f, 100+90*32/2f,0), 0);
        carDani = new auxCar(this, model, new Vector3(15*32/2, 100,0), 0);
        carFerran = new auxCar(this, model2, new Vector3(18*32/2, 100,0), 1);
        carDani.transform.scale(15, 15, 15).rotate(1, 0, 0, -70);
		carFerran.transform.scale(15, 15, 15).rotate(1, 0, 0, -70);
		carDani.initBody(world);
		carFerran.initBody(world);
		//instance.transform.scale(20, 20, 20).rotate(1, 0, 0, -90);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		delta = Math.min(0.06f, delta);
		timeToStart -= delta;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); 
		showSemafor();
		if(start){
			world.step(1f/30f, 6, 2);
			for (Body body : bodiesToDestroy){
				body.setActive(false);
				bodiesToDestroy.removeValue(body, true);
			}			
			batch.setProjectionMatrix(cam.combined);
			cam.update();
	        Gdx.gl.glViewport(0,Gdx.graphics.getHeight()/2,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);      
	        batch.begin();
	
	        tiledMapRenderer.setView(cam.combined, 0, 0,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
	        tiledMapRenderer.render();
	        batch.end();	    
		    
		    modelBatch.begin(cam);
	        modelBatch.render(carDani, environment);
	        modelBatch.render(carFerran, environment);
	        modelBatch.end();
	        
	      //cam.position.set(carcito.getX()+carcito.getWidth()/2, carcito.getY()-10, -50f);
	        //cam.lookAt(carcito.getX()+carcito.getWidth()/2,carcito.getY()-10,0);
	        cam.position.set(carDani.trans.x+carDani.bb.getWidth()*15/2, carDani.trans.y-40, -50f);
	        cam.lookAt(carDani.trans.x+carDani.bb.getWidth()*15/2, carDani.trans.y-40,0);
	        cam.near = 0.1f;
	        cam.far = 3000f;
	        cam.rotate(-50, 1, 0, 0);
	        cam.update();
	        ////
	        ///FINISH PANTALLA TOP
		    ///////////
	        batch.setProjectionMatrix(cam2.combined);
		    cam2.update();
	        Gdx.gl.glViewport(0,0,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);    
		    batch.begin();
		    /*bottom Half*/     
		    tiledMapRenderer.setView(cam2.combined, 0, 0,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
	        tiledMapRenderer.render();
		    batch.end();
		    debugRenderer.render(world, cam2.combined); 
		    modelBatch.begin(cam2);
	        modelBatch.render(carDani, environment);
	        modelBatch.render(carFerran, environment);
	        modelBatch.end();
	        
	        cam2.position.set(carFerran.trans.x+carFerran.bb.getWidth()*15/2, carFerran.trans.y-40, -50f);
	        cam2.lookAt(carFerran.trans.x+carFerran.bb.getWidth()*15/2, carFerran.trans.y-40,0);
	        	        
	        cam2.near = 0.1f;
	        cam2.far = 3000f;
	        cam2.rotate(-50, 1, 0, 0);
	        cam2.update();
	        
	        carDani.update(delta);
			carFerran.update(delta);
		}
		else{
			
			batch.setProjectionMatrix(cam.combined);
			cam.update();
	        Gdx.gl.glViewport(0,Gdx.graphics.getHeight()/2,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);      
	        batch.begin();
	
	        tiledMapRenderer.setView(cam.combined, 0, 0,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
	        tiledMapRenderer.render();
	        batch.end();	    
		    
		    modelBatch.begin(cam);
	        modelBatch.render(carDani, environment);
	        modelBatch.render(carFerran, environment);
	        modelBatch.end();
	        
	      //cam.position.set(carcito.getX()+carcito.getWidth()/2, carcito.getY()-10, -50f);
	        //cam.lookAt(carcito.getX()+carcito.getWidth()/2,carcito.getY()-10,0);
	        cam.position.set(carDani.trans.x+carDani.bb.getWidth()*15/2, carDani.trans.y-40, -50f);
	        cam.lookAt(carDani.trans.x+carDani.bb.getWidth()*15/2, carDani.trans.y-45,0);
	        
	        //cam.position.set((float) -Math.cos(carDani.angleGir)*20+carDani.body.getPosition().x,(float) -Math.sin(carDani.angleGir)*20+carDani.body.getPosition().y,0f);
	        //cam.lookAt(carDani.trans.x+carDani.bb.getWidth()*15/2, carDani.trans.y-45,0);
	        
	        cam.near = 0.1f;
	        cam.far = 300f;
	        cam.rotate(-50, 1, 0, 0);
	        cam.update();
	        ////
	        ///FINISH PANTALLA TOP
		    ///////////
	        batch.setProjectionMatrix(cam2.combined);
		    cam2.update();
	        Gdx.gl.glViewport(0,0,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);    
		    batch.begin();
		    /*bottom Half*/     
		    tiledMapRenderer.setView(cam2.combined, 0, 0,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
	        tiledMapRenderer.render();
		    batch.end();
		    debugRenderer.render(world, cam2.combined); 
		    modelBatch.begin(cam2);
	        modelBatch.render(carDani, environment);
	        modelBatch.render(carFerran, environment);
	        modelBatch.end();
	        
	        cam2.position.set(carFerran.trans.x+carFerran.bb.getWidth()*15/2, carFerran.trans.y-40, -50f);
	        cam2.lookAt(carFerran.trans.x+carFerran.bb.getWidth()*15/2, carFerran.trans.y-45,0);
	        	        
	        cam2.near = 0.1f;
	        cam2.far = 300f;
	        cam2.rotate(-50, 1, 0, 0);
	        cam2.update();
	        
	        if(firstTime){
	        	carDani.update(delta);
	        	carFerran.update(delta);
	        }        
	        firstTime = false;
	        batch.begin();	
	        	semafor.draw();	
	        batch.end();
		}
			
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = 100f;
        cam.viewportHeight = 30f * height/width;
        cam2.viewportWidth = 100f;
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
	
	private void showSemafor() {
		
		
		if(timeToStart < 1)start = true;
		
		Table table = new Table(skin);
		
		if(timeToStart > 4)table.setBackground(new Image(new Sprite(new Texture("semaforo1.png"))).getDrawable());
		else if(timeToStart > 3)table.setBackground(new Image(new Sprite(new Texture("semaforo2.png"))).getDrawable());
		else if(timeToStart > 2)table.setBackground(new Image(new Sprite(new Texture("semaforo3.png"))).getDrawable());
		else if(timeToStart > 1)table.setBackground(new Image(new Sprite(new Texture("semaforo4-01.png"))).getDrawable());
		else table.setBackground(new Image(new Sprite(new Texture("semaforo4-01.png"))).getDrawable());
		//inventary.setBackground(new Image(game.background.getTexture()).getDrawable());
		
		table.setBounds(170, 60, 550, 330);
				
		semafor.addActor(table);
		
		table.getColor().mul(1, 1, 1, 0.85f);
//		table.add(backArrow).align(Align.topLeft).pad(10);*/
	}
	

}
