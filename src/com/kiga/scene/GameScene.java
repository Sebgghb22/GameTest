package com.kiga.scene;

import java.io.IOException;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.SAXUtils;
import org.andengine.util.color.Color;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kiga.manager.ResourcesManager;
import com.kiga.manager.SceneManager;
import com.kiga.manager.SceneManager.SceneType;

public class GameScene extends BaseScene{

	private HUD gameHUD;
	private Text scoreText;
	private int score=0;
	private FixedStepPhysicsWorld physicsWorld;
	
	//level_xmlfile_attrs
	private static final String TAG_ENTITY="entity";
	private static final String TAG_ENTITY_X="x";
	private static final String TAG_ENTITY_Y="y";
	private static final String TAG_ENTITY_TYPE="type";
	private static final Object TAG_ENTITY_TYPE_PLATFORM1="platform1";
	private static final Object TAG_ENTITY_TYPE_PLATFORM2="platform2";
	private static final Object TAG_ENTITY_TYPE_PLATFORM3="platform3";
	private static final Object TAG_ENTITY_TYPE_COIN="coin";

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createHUD();
		createPhysics();
		loadLevel();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		SceneManager.getInstance().loadMenuScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		camera.setHUD(null);
		camera.setCenter(camera.getWidth()/2, camera.getHeight()/2);
	}
	
	private void createBackground(){
		setBackground(new Background(Color.GREEN));
	}
	
	private void createHUD(){
		gameHUD=new HUD();

		scoreText = new Text(20, 420, resourcesManager.font, 
				"Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setPosition(0, 0) ;  
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);

		camera.setHUD(gameHUD);
	}
	
	private void addScore(int i){
		score+=i;
		scoreText.setText("Score:"+score);
	}
	
	private void createPhysics(){
		physicsWorld=new FixedStepPhysicsWorld(60, new Vector2(0,-17), false);
		registerUpdateHandler(physicsWorld);
	}
	
	private void loadLevel(){
		final SimpleLevelLoader levelLoader=new SimpleLevelLoader(vbom);
		final FixtureDef FIXTURE_DEF=PhysicsFactory.createFixtureDef(0, 0.1f, 0.2f);
		levelLoader.registerEntityLoader(new 
				EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL){

			@Override
			public IEntity onLoadEntity(String pEntityName, IEntity pParent,
					Attributes pAttributes,
					SimpleLevelEntityLoaderData pEntityLoaderData)
					throws IOException {
				// TODO Auto-generated method stub

				final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
				final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);

				return GameScene.this;
			}
			
		});

		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY)
		    {
		        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
		        {
		            final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_X);
		            final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_Y);
		            final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_TYPE);
		            
		            final Sprite levelObject;
		            
		            if (type.equals(TAG_ENTITY_TYPE_PLATFORM1))
		            {
		                levelObject = new Sprite(x, y, resourcesManager.platform1_region, vbom);
		                PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF).setUserData("platform1");
		            } 
		            else if (type.equals(TAG_ENTITY_TYPE_PLATFORM2))
		            {
		                levelObject = new Sprite(x, y, resourcesManager.platform2_region, vbom);
		                final Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
		                body.setUserData("platform2");
		                physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
		            }
		            else if (type.equals(TAG_ENTITY_TYPE_PLATFORM3))
		            {
		                levelObject = new Sprite(x, y, resourcesManager.platform3_region, vbom);
		                final Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
		                body.setUserData("platform3");
		                physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
		            }
		            else if (type.equals(TAG_ENTITY_TYPE_COIN))
		            {
		                levelObject = new Sprite(x, y, resourcesManager.coin_region, vbom)
		                {
		                    @Override
		                    protected void onManagedUpdate(float pSecondsElapsed) 
		                    {
		                        super.onManagedUpdate(pSecondsElapsed);
		                        
		                        /**                          * TODO                         * we will later check if player collide with this (coin)                         * and if it does, we will increase score and hide coin                         * it will be completed in next articles (after creating player code)                         */
		                    }
		                };
		                levelObject.registerEntityModifier((IEntityModifier) new LoopEntityModifier(new ScaleModifier(1, 1, 1.3f)));
		            }            
		            else
		            {
		                throw new IllegalArgumentException();
		            }
		
		            levelObject.setCullingEnabled(true);
		
		            return levelObject;
		        }
		    });
		
		  levelLoader.loadLevelFromAsset(activity.getAssets(), "level/"+"1.xml");
		  }
	
}
