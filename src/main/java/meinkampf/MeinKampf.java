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
    	
    	
        float[] positions = new float[]{
        		

            0f, 0.8f, -1.05f,
            0.1f, 0f, -1.05f,
            -0.1f, 0f, -1.05f,
            
            -0.1f, 0.2f, -1.05f,
            -0.3f, 0f, -1.05f,
            -0.3f, 0.2f, -1.05f,
            
            0.1f, 0.2f, -1.05f,
            0.3f, 0.2f, -1.05f,
            0.3f, 0f, -1.05f,
            
        };
        
        int[] indices = new int[]{
        		0,1,2,2,3,4,4,5,3,1,6,7,1,7,8,
        };
        
        float[] textCoords = new float[] {
        		

        	0.5f, 0.3f, 0.2f,
        	0.5f, 0.4f, 0.6f,
        	0.5f, 0.3f, 0.2f,
        	
        	0.8f, 0.2f, 0.1f,
        	0.8f, 0.3f, 0.2f,
        	0.8f, 0.3f, 0.2f,
        	
        	0.1f, 0.3f, 0.2f,
        	0.1f, 0.1f, 0.3f,
        	0.1f, 0.1f, 0.9f,
        };
        
//        mesh = new Mesh(positions, textCoords, indices);
        
        positions = new float[]{
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
                
                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,

                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,

                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,

                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,  
            };
        
        textCoords = new float[]{           		
                0.0f, 0.0f,
                0.0f, 1f,
                1f, 1f,
                1f, 0.0f,
                
                0.0f, 0.0f,
                1f, 0.0f,
                0.0f, 1f,
                1f, 1f,
                
                // For text coords in top face
                0.0f, 0.0f,
                1f, 0.0f,
                0.0f, 1f,
                1f, 1f,

                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 1f,

                // For text coords in left face
                1f, 0.0f,
                1f, 1f,

                // For text coords in bottom face
                0.0f, 0.0f,
                1f, 0.0f,
                0.0f, 1f,
                1f, 1f,
            };
            
             indices = new int[]{
                     // Front face
                     0, 1, 3, 3, 1, 2,
                     // Top Face
                     8, 10, 11, 9, 8, 11,
                     // Right face
                     12, 13, 7, 5, 12, 7,
                     // Left face
                     14, 15, 6, 4, 14, 6,
                     // Bottom face
                     16, 18, 19, 17, 16, 19,
                     // Back face
                     4, 6, 7, 5, 4, 7,
            };
             
       Texture texture = new Texture("textures/Busel.png");
             
       mesh2 = new Mesh(positions, textCoords, indices, texture);
       
       GameItem gameItem = new GameItem(mesh);
       GameItem gameItem2 = new GameItem(mesh2);
       gameItem.setPosition(0, 0, -2);
       gameItem2.setPosition(0, 0, -3);
       gameItem2.setScale(10f);
       gameItems = new GameItem[] { gameItem2 };
       
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