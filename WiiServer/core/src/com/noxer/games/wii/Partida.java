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

public class Partida implements Screen{
	private PerspectiveCamera cam, cam2;
	static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;
    private Sprite background;
    private Batch batch;
    private TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private Sprite car;
    private ModelBatch modelBatch;
    private Model model;
    public ModelInstance instance;
    public Environment environment;
    public World world;
    public Array<Body> bodiesToDestroy = new Array<Body>(false, 16);
    Box2DDebugRenderer debugRenderer;
    Car carcito;
	
	public Partida(){
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        batch = new SpriteBatch();
        tiledMap = new TmxMapLoader().load("mapa.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        world = new World(new Vector2(0, 0),true);
		world.setContactListener(new ContListener(this));
		
		debugRenderer = new Box2DDebugRenderer();
        
        car = new Sprite(new Texture("cochehiperrealista-01.png"));
        car.setPosition(20*32/2, 10);
        //carcito = new Car(world, this, car, car, car, car, car);
        
        
        System.out.println(tiledMap.getProperties().get("width",Integer.class));
        
		cam = new PerspectiveCamera(60, 15, 15 * (h / w));
		cam2 = new PerspectiveCamera(60, 15, 15 * (h / w));
		
		//cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(20*32/2f, 0f, -50f);
        cam.lookAt(20*32/2,0,0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.rotate(-50, 1, 0, 0);
        cam.update();
        
        cam2.position.set(20*32/2f, 0f, -50f);
        cam2.lookAt(20*32/2,0,0);
        cam2.near = 0.1f;
        cam2.far = 300f;
        cam2.rotate(-50, 1, 0, 0);
        cam2.update();
        
        
        
        background = new Sprite(new Texture("fondo2.png"));
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        background.setPosition(0, 0);
        
        
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(50f, 50f, 50f, 
            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            Usage.Position | Usage.Normal);
        instance = new ModelInstance(model, new Vector3(20*32/2, 50,0));
        modelBatch = new ModelBatch();
        
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        /*ModelLoader loader = new ObjLoader();
        model = loader.loadModel(Gdx.files.internal("Ferrari2001.obj"));
        instance = new ModelInstance(model);*/
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
			//world.destroyBody(body);
			//body.getBody().destroyFixture(body);
			body.setActive(false);
			bodiesToDestroy.removeValue(body, true);
		}
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
        batch.begin();
        car.draw(batch);
	    batch.end();
	    

	    cam2.update();
        batch.setProjectionMatrix(cam2.combined);
        Gdx.gl.glViewport(0,0,
        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);    
	    batch.begin();
	    /*bottom Half*/     
	    //set the openGl viewport to half the screenheight and starting y from the     bottom of the screen
	    tiledMapRenderer.setView(cam2.combined, 0, 0,
        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
        tiledMapRenderer.render();
        car.draw(batch);
	    batch.end();
	    batch.begin();
        car.draw(batch);
        //carcito.draw(batch);
        debugRenderer.render(world, cam2.combined);
	    batch.end();

	    modelBatch.begin(cam2);
        modelBatch.render(instance, environment);;
        modelBatch.end();
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

}
