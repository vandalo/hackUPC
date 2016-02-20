package com.noxer.games.entities;


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
import com.badlogic.gdx.utils.Array;
import com.noxer.games.entities.Car;
import com.noxer.games.wii.ContListener;

public class auxPartida implements Screen{
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
    private float lookX, lookY;
	
	public auxPartida(){
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        batch = new SpriteBatch();
        tiledMap = new TmxMapLoader().load("mapa.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        world = new World(new Vector2(0, 0),true);
		//TODO world.setContactListener(new ContListener(this));
		
		debugRenderer = new Box2DDebugRenderer();
        
		
/////////////////////////////////////
		///COCHES
/////////////////////////////////////
        /*carcito = new Car(this, new Sprite(new Texture("cochehiperrealista-01.png")), new Sprite(new Texture("cochehiperrealista-01.png")),
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
        carcitoFerran.initBody(world);*/
		
		
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
        //TODO carcito = new Car(this, model, new Vector3(17*32/2f, 150,0), 0);
        //TODO carcitoFerran = new Car(this, model, new Vector3(23*32/2f, 150,0), 0);
      //TODO carcito.transform.scale(20, 20, 20).rotate(1, 0, 0, -90);
		//TODO carcitoFerran.transform.scale(20, 20, 20).rotate(1, 0, 0, -90);
		
		carcito.initBody(world);
		carcitoFerran.initBody(world);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		delta = Math.min(0.06f, delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);  
		world.step(1f/30f, 6, 2);
		for (Body body : bodiesToDestroy){
			body.setActive(false);
			bodiesToDestroy.removeValue(body, true);
		}
		
		cam.update();
        batch.setProjectionMatrix(cam.combined);
        Gdx.gl.glViewport(0,Gdx.graphics.getHeight()/2,
        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);      
        batch.begin();

        tiledMapRenderer.setView(cam.combined, 0, 0,
        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
        tiledMapRenderer.render();
        batch.end();
        modelBatch.begin(cam);
        //TODO modelBatch.render(carcito, environment);
        //carcitoFerran.draw(batch);
	    //batch.end();
        modelBatch.end();
	    
	    /*modelBatch.begin(cam2);
        modelBatch.render(instance, environment);
        modelBatch.end();*/
        
        ////
        ///FINISH PANTALLA TOP
	    ///////////

	    cam2.update();
        batch.setProjectionMatrix(cam2.combined);
        Gdx.gl.glViewport(0,0,
        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);    
	    batch.begin();
	    /*bottom Half*/     
	    tiledMapRenderer.setView(cam2.combined, 0, 0,
        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
        tiledMapRenderer.render();
        //car.draw(batch);
        /*carcito.draw(batch);
        carcitoFerran.draw(batch);*/
	    batch.end();
	    debugRenderer.render(world, cam2.combined);
	    batch.begin();
        //car.draw(batch);      
	  //TODO  carcito.draw(batch);
	  //TODO  carcitoFerran.draw(batch);
	    batch.end();

	    modelBatch.begin(cam2);
        modelBatch.render(instance, environment);
        modelBatch.end();
        
      //TODO   cam.position.set(carcito.getX()+carcito.getWidth()/2, carcito.getY()-10, -50f);
      //TODO   cam.lookAt(carcito.getX()+carcito.getWidth()/2,carcito.getY()-10,0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.rotate(-50, 1, 0, 0);
        cam.update();
        
      //TODO   cam2.position.set(carcitoFerran.getX()+carcitoFerran.getWidth()/2, carcitoFerran.getY()-10, -50f);
      //TODO   cam2.lookAt(carcitoFerran.getX()+carcitoFerran.getWidth()/2,carcitoFerran.getY()-10,0);
        cam2.near = 0.1f;
        cam2.far = 300f;
        cam2.rotate(-50, 1, 0, 0);
        cam2.update();
        
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
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}
