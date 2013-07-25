/*
 * This class will be responsible for loading/unloading 
 * our game resources (art, fonts, audio) it will also 
 * provide references to the most commonly needed objects 
 * (camera, context, engine, VertexBufferObjectManager) this
 *  class will use singleton holder, which means we will be
 *   able to access this class from the global level.
 */
package com.kiga.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

import com.kiga.GameActivity;

public class ResourcesManager {
	//---------------------------------
	//VARIABLES
	//---------------------------------
	private static final ResourcesManager INSTANCE= new ResourcesManager();
	public Engine engine;
	public GameActivity activity;
	public BoundCamera camera;
	public VertexBufferObjectManager vbom;
	
	//splash
	public BitmapTextureAtlas splashTextureAtlas;
	public ITextureRegion splash_region;
	
	//menu
	public ITextureRegion menu_background_region;
	public ITextureRegion menu_play_region;
	public ITextureRegion menu_options_region;
	public BuildableBitmapTextureAtlas menuTextureAtlas;
	public Font font;
	
	
	//---------------------------------
	//CLASS_LOGIC
	//---------------------------------
	public void loadMenuResources(){
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFont();
	}
	
	private void loadMenuFont() {
		// TODO Auto-generated method stub
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture=new BitmapTextureAtlas(activity.getTextureManager(),
				256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font=FontFactory.createStrokeFromAsset(activity.getFontManager(),
				mainFontTexture,activity.getAssets(),"font.ttf",50,true,Color.WHITE,2,Color.BLACK );
		font.load();
	}

	private void loadMenuAudio() {
		// TODO Auto-generated method stub
		
	}

	private void loadMenuGraphics() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuTextureAtlas=new BuildableBitmapTextureAtlas(activity.getTextureManager(),
				1024, 1024, TextureOptions.BILINEAR);
		menu_background_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				menuTextureAtlas, activity, "menu_background.png");
		menu_play_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				menuTextureAtlas, activity, "play.png");
		menu_options_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				menuTextureAtlas, activity, "options.png");
		try {
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,
					BitmapTextureAtlas>(0,1,0));
			this.menuTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			Debug.e(e);
		}
	}
	
	
	public void loadGameResources(){
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
	}

	private void loadGameAudio() {
		// TODO Auto-generated method stub
		
	}

	private void loadGameFonts() {
		// TODO Auto-generated method stub
		
	}

	private void loadGameGraphics() {
		// TODO Auto-generated method stub
		
	}
	
	//splash_textures
	public void loadSpalshScreen(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas=new BitmapTextureAtlas(activity.getTextureManager(),
				256,256,TextureOptions.BILINEAR);
		splash_region=BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,
				activity,"splash.png",0,0 );
		splashTextureAtlas.load();
	}
	
	public void unloadSplashScreen(){
		splashTextureAtlas.unload();
		splash_region=null;
	}
	
	//menu_textures
	public void loadMenuTextures(){
		menuTextureAtlas.load();
	}
	public void unloadMenuTextures(){
		menuTextureAtlas.unload();
	}
	
	//game_textures
	public void unloadGameTextures(){
		//todo
	}

	
	/*     * @param engine     * @param activity     * @param camera     * @param vbom     * 
	 *      * We use this method at beginning of game loading, to prepare Resources Manager properly,
	 *      * setting all needed parameters, so we can latter access them from different classes (eg. scenes)     
	*/
	
	public static ResourcesManager getInstance(){
		return INSTANCE;
	}
	
	public static void prepareManager(Engine engine,GameActivity activity,
			BoundCamera camera,VertexBufferObjectManager vbom){
		getInstance().engine=engine;
		getInstance().activity=activity;
		getInstance().camera=camera;
		getInstance().vbom=vbom;
	}

}
