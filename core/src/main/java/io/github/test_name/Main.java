package io.github.test_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;
import com.badlogic.gdx.math.MathUtils;
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
   private SpriteBatch batch;
    private Texture image;
    private Texture wall1;
    private Texture wall2;
     private Texture evilturtle;
    private Random random = new Random(); 
     private Random random2 = new Random(); 
      public static final float VIRTUAL_WIDTH = 800;
      public static final float VIRTUAL_HEIGHT = 600;
    private OrthographicCamera camera;
    private Viewport viewport;
    float x = 100;
    float y = 100;
     float xwall1 = -430;
    float ywall1 = -5;
    float xwall2 = 560;
    float ywall2 = -5;
     float xwall3 = 0;
    float ywall3 = -430;
    float evilx = 200;
    float evily = 700;
    float speed = 300;
    float speed2 = 200;
    int timer = 0;
    float randomFloat = MathUtils.random(0,500); 
     
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("turtle.png");
        wall1 = new Texture("wallsides.png");
        wall2 = new Texture("wallupdown.png");
        evilturtle = new Texture("EvilTurtle.png");
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
    }
    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true);
    }



    @Override
    public void render() {
        // make start screen and when you get hit restart screen with an if statement using a varibale to decide if the game is
        //  running like if( running = 1) 
        // put all the code in the normal game, if (running=0) restart the timer and show the restart screen
        float dt = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.A))  x -= speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.W))    y += speed * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.S))  y -= speed * dt;
        evily-=speed2*dt;
        timer = timer+1;
        if (timer>=1000 && timer<5000) {
            speed2 = 300;
        }
        if (timer>=5000) {
            speed2 = 700;
        }
        if(evily<=-100){
            randomFloat = MathUtils.random(0,500);
evily =700;
        } 
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
       batch.begin();
        batch.draw(image, x, y);
        batch.draw(evilturtle,randomFloat, evily);
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
        Rectangle evilturtlerect = new Rectangle(randomFloat, evily, evilturtle.getWidth(), evilturtle.getHeight());
        
        if (bounds.overlaps(player)) {
    x += speed * dt;
}
if (evilturtlerect.overlaps(player)) {
 //   System.exit(0);
}
if (bounds2.overlaps(player)) {
    x -= speed * dt;
}
if (bounds3.overlaps(player)) {
    y += speed * dt;
}
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
