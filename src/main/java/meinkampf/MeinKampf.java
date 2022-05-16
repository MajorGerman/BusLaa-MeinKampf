package meinkampf;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.GameItem;
import engine.IGameLogic;
import engine.MouseInput;
import engine.Window;
import engine.graph.Camera;
import engine.graph.Mesh;
import engine.graph.OBJLoader;
import engine.graph.Texture;

public class MeinKampf implements IGameLogic {

    private int[] direction = {0, 0, 0};
    private float[] color = {0.0f,0.0f,0.0f};
    
    private static final float MOUSE_SENSITIVITY = 0.2f;
    
    
    private static final float CAMERA_POS_STEP = 0.05f;
    
    
    private final Renderer renderer;
    
    private final Vector3f cameraInc;
    
    private final Camera camera;

    private Mesh mesh;
    private Mesh mesh2;
    
    private int displxInc = 0;
    private int displyInc = 0;
    private int displzInc = 0;
    private int scaleInc = 0;
    
    private GameItem[] gameItems;
    
    public MeinKampf() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f();
    }
    
    @Override
    public void init(Window window) throws Exception {
    	
    	renderer.init(window);
    	
    	Mesh mesh = OBJLoader.loadMesh("/models/model.obj");
    	
       Texture texture = new Texture("textures/Busel.png");
             
       //mesh2 = new Mesh(positions, textCoords, indices, texture);
       mesh.setTexture(texture);
       GameItem gameItem = new GameItem(mesh);
       gameItem.setPosition(0, 0, -2);
       gameItems = new GameItem[] { gameItem };
       
     }
    
    @Override
    public void input(Window window, MouseInput mouseInput) {
    	cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
    	
    	camera.movePosition(cameraInc.x * CAMERA_POS_STEP,
    	        cameraInc.y * CAMERA_POS_STEP,
    	        cameraInc.z * CAMERA_POS_STEP);

    	    // Update camera based on mouse            
    	    if (mouseInput.isRightButtonPressed()) {
    	        Vector2f rotVec = mouseInput.getDisplVec();
    	        camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
    	    }
    	    
    	for (GameItem gameItem : gameItems) {
            // Update position
            Vector3f itemPos = gameItem.getPosition();
            float posx = itemPos.x + displxInc * 0.02f;
            float posy = itemPos.y + displyInc * 0.02f;
            float posz = itemPos.z + displzInc * 0.02f;
            gameItem.setPosition(posx, posy, posz);
            
            // Update scale
            float scale = gameItem.getScale();
            scale += scaleInc * 0.05f;
            if ( scale < 0 ) {
                scale = 0;
            }
            gameItem.setScale(scale);
            
            // Update rotation angle
            float rotation = gameItem.getRotation().z + 1.5f;
            if ( rotation > 360 ) {
                rotation = 0;
            }
            gameItem.setRotation(rotation, rotation, rotation);     
       }
    	
    	for (int i = 0; i < color.length; i++) {
            color[i] += direction[i] * 0.05f;
            if (color[i] > 1) {
                color[i] = 1.0f;
            } else if ( color[i] < 0 ) {
                color[i] = 0.0f;
            }    		
    	}
    }

    @Override
    public void render(Window window) {

        window.setClearColor(color[0], color[1], color[2], 0.0f);
        renderer.render(window, camera ,gameItems);
        
    }
    
    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
    
}