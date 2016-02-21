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
import com.noxer.games.entities.Box;
import com.noxer.games.entities.Car;
import com.noxer.games.entities.auxCar;

public class Partida implements Screen{
	private PerspectiveCamera cam, cam2;
	static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;
    private Sprite background;
    private Batch batch;
    private TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    //private Sprite car;
    private ModelBatch modelBatch;
    private Model model, model2, modelBox;
    public ModelInstance instance;
    public Environment environment;
    public World world;
    public Array<Body> bodiesToDestroy = new Array<Body>(false, 16);
    Box2DDebugRenderer debugRenderer;
    Car carcito, carcitoFerran;
    auxCar carDani, carFerran;
    Box boxes[];
    private float timeToStart;  
    private float lookX, lookY;
    Stage semafor;
    boolean start;
    boolean firstTime;
	private TextureAtlas atlas;
	protected Skin skin, skin2;
	private float xVRP, yVRP;
	final private int radio = 20;
	Vector3 vec;
	//xVRP = xObs + R * cos(angle);
    //zVRP = (zObs + R * sin(angle));
	
	public Partida(){
		/*vec = new Vector3(carFerran.trans.x + carFerran.bb.getWidth()*15/2-carFerran.trans.x + carFerran.bb.getWidth()*15/2, 
				carFerran.trans.y + carFerran.bb.getHeight()*15/2-carFerran.trans.y + carFerran.bb.getHeight()*15/2 - 50 ,
				5);*/
		vec = new Vector3();
		firstTime = true;
		start = false;
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        tiledMap = new TmxMapLoader().load("TILES/mapadefinitiu.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //skin = new Skin(gameUI);
        world = new World(new Vector2(0, 0),true);
		world.setContactListener(new ContListener(this));
		timeToStart = 5; 
		debugRenderer = new Box2DDebugRenderer();
		
		atlas = new TextureAtlas("skins/userInterface.pack");
		skin = new Skin(Gdx.files.internal("skins/userInterface.json"), atlas);
		
		semafor = new Stage(new StretchViewport(800, 480));
		world.setContactListener(new ContListener(this));
		
        
		
/////////////////////////////////////
		///COCHES
/////////////////////////////////////
        /*carcito = new Car(this, new Sprite(new Texture("cochehiperrealista-01.png")), new Sprite(new Texture("cochehiperrealista-01.png")),
        		new Sprite(new Texture("cochehiperrealista-01.png")), new Sprite(new Texture("cochehiperrealista-01.png")), 
        		new Sprite(new Texture("cochehiperrealista-01.png")), 0);
        carcito.setPosition(15*32/2, 10);
        carcito.scale(0.7f);
        //carcito.initBody(world);
        ///
        carcitoFerran = new Car(this, new Sprite(new Texture("cochehiperrealista-01.png")), new Sprite(new Texture("cochehiperrealista-01.png")),
        		new Sprite(new Texture("cochehiperrealista-01.png")), new Sprite(new Texture("cochehiperrealista-01.png")), 
        		new Sprite(new Texture("cochehiperrealista-01.png")), 1);
        carcitoFerran.setPosition(25*32/2, 10);
        carcitoFerran.scale(0.7f);*/
        //carcitoFerran.initBody(world);
        ////////
/////////////////////////////////////
/////////////////////////////////////
        
		cam = new PerspectiveCamera(60, 15, 15 * (h / w));
		cam2 = new PerspectiveCamera(60, 15, 15 * (h / w));
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
        carDani = new auxCar(this, model, new Vector3(54*32, (499-89)*32,0), 0);
        carFerran = new auxCar(this, model2, new Vector3(56*32, (499-89)*32,0), 1);
        carDani.transform.scale(15, 15, 15).rotate(1, 0, 0, -80);
		carFerran.transform.scale(15, 15, 15).rotate(1, 0, 0, -80);
		carDani.initBody(world);
		carFerran.initBody(world);
		
		modelBox = new ObjLoader().loadModel(Gdx.files.internal("Crate1.obj"));
		boxes = new Box[20];
		boxes[0] = new Box(this, modelBox, new Vector3(57*32, (499-82)*32,0));
		boxes[0].transform.scale(15, 15, 10);
		boxes[0].initBody(world);
		
		boxes[1] = new Box(this, modelBox, new Vector3(111*32/2, (499-82)*32,0));
		boxes[1].transform.scale(15, 15, 10);
		boxes[1].initBody(world);
		
		boxes[2] = new Box(this, modelBox, new Vector3(54*32, (499-82)*32,0));
		boxes[2].transform.scale(15, 15, 10);
		boxes[2].initBody(world);
		
		boxes[4] = new Box(this, modelBox, new Vector3(1787, 13182,0));
		boxes[4].transform.scale(15, 15, 10);
		boxes[4].initBody(world);


		boxes[5] = new Box(this, modelBox, new Vector3(2541f,14381.07f,0f));
		boxes[5].transform.scale(15, 15, 10);
		boxes[5].initBody(world);


		boxes[6] = new Box(this, modelBox, new Vector3(3699.228f, 14304.192f,0));
		boxes[6].transform.scale(15, 15, 10);
		boxes[6].initBody(world);


		boxes[7] = new Box(this, modelBox, new Vector3(4897.1953f, 14288.212f,0));
		boxes[7].transform.scale(15, 15, 10);
		boxes[7].initBody(world);


		boxes[8] = new Box(this, modelBox, new Vector3(5297.6426f, 13476.052f,0));
		boxes[8].transform.scale(15, 15, 10);
		boxes[8].initBody(world);


		boxes[9] = new Box(this, modelBox, new Vector3(5939.092f, 12515.612f,0));
		boxes[9].transform.scale(15, 15, 10);
		boxes[9].initBody(world);


		boxes[10] = new Box(this, modelBox, new Vector3(5888.771f, 11318.275f,0));
		boxes[10].transform.scale(15, 15, 10);
		boxes[10].initBody(world);


		boxes[11] = new Box(this, modelBox, new Vector3(5923.629f, 10119.802f,0));
		boxes[11].transform.scale(15, 15, 10);
		boxes[11].initBody(world);

		boxes[12] = new Box(this, modelBox, new Vector3(5896.0283f, 8921.152f,0));
		boxes[12].transform.scale(15, 15, 10);
		boxes[12].initBody(world);


		boxes[13] = new Box(this, modelBox, new Vector3(5928.9873f, 7725.534f,0));
		boxes[13].transform.scale(15, 15, 10);
		boxes[13].initBody(world);


		boxes[14] = new Box(this, modelBox, new Vector3(5242.9663f, 7327.282f,0));
		boxes[14].transform.scale(15, 15, 10);
		boxes[14].initBody(world);


		boxes[15] = new Box(this, modelBox, new Vector3(5221.812f, 8494.491f,0));
		boxes[15].transform.scale(15, 15, 10);
		boxes[15].initBody(world);


		boxes[16] = new Box(this, modelBox, new Vector3(4947.6924f, 9507.618f,0));
		boxes[16].transform.scale(15, 15, 10);
		boxes[16].initBody(world);


		boxes[17] = new Box(this, modelBox, new Vector3(4066.1902f, 8939.8545f,0));
		boxes[17].transform.scale(15, 15, 10);
		boxes[17].initBody(world);





		boxes[18] = new Box(this, modelBox, new Vector3(4241.8784f, 12573.542f,0));
		boxes[18].transform.scale(15, 15, 10);
		boxes[18].initBody(world);


		boxes[19] = new Box(this, modelBox, new Vector3(3340.651f, 10693.012f,0));
		boxes[19].transform.scale(15, 15, 10);
		boxes[19].initBody(world);


		boxes[3] = new Box(this, modelBox, new Vector3(1759.4908f, 10842.136f,0));
		boxes[3].transform.scale(15, 15, 10);
		boxes[3].initBody(world);
		
		vec.x = 0;
		//vec.y = cam2.position.set(carFerran.trans.x + carFerran.bb.getWidth()*15/2,
        		/*carFerran.trans.y + carFerran.bb.getHeight()*15/2 - 50, 
        		-20f);*/
        /*carDani.transform.scale(15, 15, 15).rotate(1, 0, 0, -70);
		carFerran.transform.scale(15, 15, 15).rotate(1, 0, 0, -70);
		carDani.initBody(world);
		carFerran.initBody(world);*/
		
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
			carDani.update(delta);
			carFerran.update(delta);
			world.step(1f/30f, 6, 2);
			for (Body body : bodiesToDestroy){
				body.setActive(false);
				bodiesToDestroy.removeValue(body, true);
			}			
			cam.rotate((float) Math.toDegrees(-carDani.angleGir), 0, 0, 1);
			batch.setProjectionMatrix(cam.combined);
			cam.update();
	        Gdx.gl.glViewport(0,Gdx.graphics.getHeight()/2,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);      
	        batch.begin();
	
	        tiledMapRenderer.setView(cam.combined, cam.position.x-1500, cam.position.y-1000,
	        		3000,2000);
	        tiledMapRenderer.render();
	        batch.end();	    
		    
		    modelBatch.begin(cam);
	        modelBatch.render(carDani, environment);
	        modelBatch.render(carFerran, environment);
	        for (int i = 0; i < boxes.length; i++){
	        	if (boxes[i].pintar)modelBatch.render(boxes[i], environment);
	        	boxes[i].update(delta);
	        }
	        modelBatch.end();

	        xVRP = (float) (carDani.trans.x + carDani.bb.getWidth()*15/2 - 50*Math.cos(carDani.angleGir - Math.PI/2));
	        yVRP = (float) (carDani.trans.y + carDani.bb.getHeight()*15/2 + 50*Math.sin(carDani.angleGir - Math.PI/2));
	        cam.position.set(xVRP,
	        		yVRP, 
	        		-20f);
	        cam.lookAt(carDani.trans.x + carDani.bb.getWidth()*15/2,
	        		carDani.trans.y + carDani.bb.getHeight()*15/2, 
	        		5f);
	        cam.rotate((float) Math.toDegrees(carDani.angleGir), 0, 0, 1);
	        
	        
	        /*cam.rotate((float) Math.toDegrees(carDani.angleGir), 0, 0, 1);
	        cam.position.set(carDani.trans.x + carDani.bb.getWidth()*15/2,
	        		carDani.trans.y-50, 
	        		-50f);
	        xVRP = (int) (carDani.trans.x + 20 * Math.cos(carDani.angleGir));
	        yVRP = (int) (carDani.trans.y + 20 * Math.sin(carDani.angleGir));
	        cam.lookAt(carDani.trans.x + carDani.bb.getWidth()*15/2,
	        		carDani.trans.y+10, 
	        		10);*/
	        
	        
	        
	        cam.near = 0.1f;
	        cam.far = 800f;
	        //cam.rotate(-55, 1, 0, 0);
	        cam.update();
	        ////
	        ///FINISH PANTALLA TOP
		    ///////////
	        cam2.rotate((float) Math.toDegrees(-carFerran.angleGir), 0, 0, 1);
	        batch.setProjectionMatrix(cam2.combined);
		    cam2.update();
	        Gdx.gl.glViewport(0,0,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);    
		    batch.begin();
		    /*bottom Half*/     
		    tiledMapRenderer.setView(cam2.combined, cam2.position.x-1500, cam2.position.y-1000,
	        		3000,2000);
	        tiledMapRenderer.render();
		    batch.end();
		    
		    debugRenderer.render(world, cam2.combined); 
		    modelBatch.begin(cam2);
	        modelBatch.render(carDani, environment);
	        modelBatch.render(carFerran, environment);
	        for (int i = 0; i < boxes.length; i++){
	        	if (boxes[i].pintar)modelBatch.render(boxes[i], environment);
	        	boxes[i].update(delta);
	        }
	        modelBatch.end();
		    
	        xVRP = (float) (carFerran.trans.x + carFerran.bb.getWidth()*15/2 - 50*Math.cos(carFerran.angleGir - Math.PI/2));
	        yVRP = (float) (carFerran.trans.y + carFerran.bb.getHeight()*15/2 + 50*Math.sin(carFerran.angleGir - Math.PI/2));
	        cam2.position.set(xVRP,
	        		yVRP, 
	        		-20f);
	        //xVRP = (int) (carFerran.trans.x + 20 * Math.cos(carFerran.angleGir-Math.PI/2));
	        //yVRP = (int) (carFerran.trans.y - 20 - 20 * Math.sin(carFerran.angleGir-Math.PI/2));
	        cam2.lookAt(carFerran.trans.x + carFerran.bb.getWidth()*15/2,
	        		carFerran.trans.y + carFerran.bb.getHeight()*15/2, 
	        		5f);
	        //carFerran.trans.y + carFerran.bb.getHeight()*15/2, 	
	        cam2.rotate((float) Math.toDegrees(carFerran.angleGir), 0, 0, 1);
	        
	        cam2.near = 0.1f;
	        cam2.far = 800f;
	        
	        //cam2.rotate(-55, 1, 0, 0);

	        cam2.update();
	        
	        
		}
		/////////////////////////
		////////////////////////
		//////////////////////
		else{
			
			batch.setProjectionMatrix(cam.combined);
			cam.update();
	        Gdx.gl.glViewport(0,Gdx.graphics.getHeight()/2,
	        		Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);      
	        batch.begin();
	
	        tiledMapRenderer.setView(cam.combined, cam.position.x-1500, cam.position.y-1000,
	        		3000,2000);
	        tiledMapRenderer.render();
	        batch.end();	    
		    
		    modelBatch.begin(cam);
	        modelBatch.render(carDani, environment);
	        modelBatch.render(carFerran, environment);
	        for (int i = 0; i < boxes.length; i++){
	        	if (boxes[i].pintar)modelBatch.render(boxes[i], environment);
	        	boxes[i].update(delta);
	        }
	        modelBatch.end();
	        
	      //cam.position.set(carcito.getX()+carcito.getWidth()/2, carcito.getY()-10, -50f);
	        //cam.lookAt(carcito.getX()+carcito.getWidth()/2,carcito.getY()-10,0);
	        //cam.position.set(carDani.trans.x+carDani.bb.getWidth()*15/2, carDani.trans.y-40, -50f);
	        //cam.lookAt(carDani.trans.x+carDani.bb.getWidth()*15/2, carDani.trans.y-45,0);
	        xVRP = (int) (carDani.trans.x + 20 * Math.cos(carDani.angleGir));
	        yVRP = (int) (carDani.trans.y + 20 * Math.sin(carDani.angleGir));
	        cam.position.set(carDani.trans.x+carDani.bb.getWidth()*15/2, carDani.trans.y-40, -50f);
	        cam.lookAt(carDani.trans.x+carDani.bb.getWidth()*15/2, carDani.trans.y-40,0);
	        //zVRP = (zObs + R * sin(angle));
	        cam.near = 0.1f;
	        cam.far = 800f;
	        cam.rotate(-55, 1, 0, 0);
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
		    tiledMapRenderer.setView(cam2.combined, cam2.position.x-1500, cam2.position.y-1000,
	        		3000,2000);
	        tiledMapRenderer.render();
		    batch.end();
		    debugRenderer.render(world, cam2.combined); 
		    modelBatch.begin(cam2);
	        modelBatch.render(carDani, environment);
	        modelBatch.render(carFerran, environment);
	        modelBatch.end();
	        
	        //xVRP = (int) (carFerran.trans.x + 20 * Math.cos(carFerran.angleGir));
	        //yVRP = (int) (carFerran.trans.y + 20 * Math.sin(carFerran.angleGir));
	        cam2.position.set(carFerran.trans.x+carFerran.bb.getWidth()*15/2, carFerran.trans.y-40, -50f);
	        cam2.lookAt(carFerran.trans.x+carFerran.bb.getWidth()*15/2, carFerran.trans.y-45,0);
	        	        
	        cam2.near = 0.1f;
	        cam2.far = 800f;
	        cam2.rotate(-55, 1, 0, 0);
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
