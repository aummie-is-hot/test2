package io.github.test_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.Random;
import com.badlogic.gdx.math.MathUtils;
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
   private SpriteBatch batch;
    private Texture image;
    private Texture wall1;
    private Texture wall2;
    private Label label;
    private Stage stage;
     private Texture evilturtle;
    private Random random = new Random(); 
     private Random random2 = new Random(); 
      public static final float VIRTUAL_WIDTH = 1700;
      public static final float VIRTUAL_HEIGHT = 864;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture sheet;
    private Animation<TextureRegion> animation;
    private float stateTime;
    int enemydead = 0;
    float x = 100;
    float y = 100;
     float xwall1 = -430;
    float ywall1 = -5;
    float xwall2 = 1620;
    float ywall2 = -5;
     float xwall3 = 0;
    float ywall3 = -430;
    float evilx = 200;
    float evily = 700;
    float speed = 300;
    float speed2 = 200;
    int timer = 0;
    double dash = 100;
    float randomFloat = MathUtils.random(0,1700); 
    boolean playing = false;
    float attackCooldown = 1.6f; // seconds between attacks
    float attackTimer = 0f;      // time until next attack allowed
    float swordx =x-70;
    float  swordy =y+50;
    int ishit = 0;
    @Override
    public void create() {
        batch = new SpriteBatch();
        
        image = new Texture("turtle.png");
        wall1 = new Texture("wallsides.png");
        wall2 = new Texture("wallupdown.png");
        evilturtle = new Texture("EvilTurtle.png");
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        stage = new Stage(viewport);
         sheet = new Texture("sword2.png"); // 4 frames in one row

        TextureRegion[][] tmp = TextureRegion.split(
                sheet,
                sheet.getWidth(),
                sheet.getHeight() / 8
        );
        
        TextureRegion[] frames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            frames[i] = tmp[i][0];
        }

        animation = new Animation<>(0.05f, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        stateTime = 0f;
        
    label = new Label("Starting text", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    label.setPosition(60,820);
    label.setFontScale(3f);
    stage.addActor(label);


    }
    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true);
    }



    @Override
    public void render() {
       
        // make start screen and when you get hit restart screen with an if statement using a varibale
        //  to decide if the game is
        //  running like if( running = 1) 
        // put all the code in the normal game, if (running=0) restart the timer and show the restart screen
        // make it have hearts so when the enemy doesnt die and hit the bottom you lose a heart as its your base. 
        // And add a boost meter that when pressing shift allows you to move alot faster but its limited 
        // and the boost regens a bit slow like 1 every second
       label.setText("Dash Meter: " +String.format("Value: %.2f", dash));
        float dt = Gdx.graphics.getDeltaTime();
        if (dash<100){
            dash = dash+0.01;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A))  x -= speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.W))    y += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.S))  y -= speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.A)&&Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)&& dash>1){
            x = x -10; dash = dash-0.5;
        }  
        if (Gdx.input.isKeyPressed(Input.Keys.D)&&Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)&& dash>1){
            x = x+10; dash = dash-0.5;
        } 
          if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && attackTimer <= 0f) {
            playing = true;
            stateTime = 0;
            attackTimer = attackCooldown;
            ishit = 0;
        }
        if (attackTimer > 0f) attackTimer -= dt;
        
        evily-=speed2*dt;
        timer = timer+1;
        if (timer>=1000 && timer<5000) {
            speed2 = 300;
        }
        if (timer>=5000) {
            speed2 = 700;
        }
        if(evily<=-100){
            randomFloat = MathUtils.random(0,1700);
evily =700;
enemydead =0;
        } 
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
         
        
       batch.begin();
        batch.draw(image, x, y);
        if (enemydead == 0){
            batch.draw(evilturtle,randomFloat, evily);
            
        }
       
        
        batch.draw(wall1, xwall1, ywall1);
        batch.draw(wall1, xwall2, ywall2);
         batch.draw(wall2, xwall3, ywall3);
        
    
        batch.end();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Rectangle bounds = new Rectangle(xwall1, ywall1, wall1.getWidth()-58, wall1.getHeight());
        Rectangle bounds2 = new Rectangle(xwall2, ywall2, wall1.getWidth()-58, wall1.getHeight());
        Rectangle bounds3 = new Rectangle(xwall3, ywall3, wall2.getWidth()-58, wall2.getHeight()-58);
        Rectangle player = new Rectangle(x, y, image.getWidth(), image.getHeight());
        Rectangle evilturtlerect = null;
        if (enemydead == 0) evilturtlerect = new Rectangle(randomFloat, evily, evilturtle.getWidth(), evilturtle.getHeight());
       
        if (playing) {
        int frameWidth = sheet.getWidth();
        int frameHeight = sheet.getHeight() / 8;
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
         Rectangle sword = new Rectangle(x-70, y+50, frameWidth, frameHeight);
        batch.begin();
        batch.draw(currentFrame, x-70,y+50 );
        
        //shapeRenderer shapeRenderer = new ShapeRenderer();
//shapeRenderer.setProjectionMatrix(camera.combined);
//shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//shapeRenderer.setColor(1, 0, 0, 1); // red
//shapeRenderer.rect(sword.x, sword.y, sword.width, sword.height-1000);
//shapeRenderer.end();
        batch.end();

        // stop once animation ends
        if (animation.isAnimationFinished(stateTime)) {
            playing = false;
        }
        if (ishit == 0 && evilturtlerect != null && sword.overlaps(evilturtlerect)){
          ishit = 1;
          enemydead =1;

        }
       

    }
        if (bounds.overlaps(player)) {
    x += speed * dt;
}
if (evilturtlerect != null && evilturtlerect.overlaps(player)) {
 //   System.exit(0);
}
if (bounds2.overlaps(player)) {
    x -= speed * dt;
}
if (bounds3.overlaps(player)) {
    y += speed * dt;
}

stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
