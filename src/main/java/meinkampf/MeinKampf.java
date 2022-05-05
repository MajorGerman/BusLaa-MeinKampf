package meinkampf;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import engine.IGameLogic;
import engine.Window;
import engine.graph.Mesh;

public class MeinKampf implements IGameLogic {

    private int[] direction = {0, 0, 0};
    private float[] color = {0.0f,0.0f,0.0f};
    private final Renderer renderer;
    private int change = 1;
    private Mesh mesh;
    
    public MeinKampf() {
        renderer = new Renderer();
    }
    
    @Override
    public void init() throws Exception {
    	renderer.init();
        float[] positions = new float[]{
        		
            0f, 0.8f, 0.0f,
            0.1f, 0f, 0.0f,
            -0.1f, 0f, 0.0f,
            
            -0.1f, 0.2f, 0.0f,
            -0.3f, 0f, 0.0f,
            -0.3f, 0.2f, 0.0f,
            
            0.1f, 0.2f, 0.0f,
            0.3f, 0.2f, 0.0f,
            0.3f, 0f, 0.0f,
            
            };
        int[] indices = new int[]{
            0,1,2,2,3,4,4,5,3,1,6,7,1,7,8
            //3,2,4,4,5,3
            };
        mesh = new Mesh(positions, indices);
     }
    
    @Override
    public void input(Window window) {
        if ( window.isKeyPressed(GLFW_KEY_UP) ) {
            direction[0] = 1;
        } else if ( window.isKeyPressed(GLFW_KEY_DOWN) ) {
            direction[0] = -1;
        } else if ( window.isKeyPressed(GLFW_KEY_LEFT) ) {
            direction[1] = 1;
        } else if ( window.isKeyPressed(GLFW_KEY_RIGHT) ) {
            direction[1] = -1;
        } else {
            direction[0] = (int) 0 ;
            //direction[1] = (int) 0 ;
            //direction[2] = (int) 0 ;
        }
        
        if (window.getMouseState(GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS) {
        	direction[2] = -1;
        } else if (window.getMouseState(GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
        	direction[2] = 1;
        } else if (window.getMouseState(GLFW_MOUSE_BUTTON_RIGHT) == GLFW_RELEASE) {
        	direction[2] = 0;
        } else if (window.getMouseState(GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
        	direction[2] = 0;
        }
    }

    @Override
    public void update(float interval) {
    	
    	mesh = mesh.move(direction[0] * 0.02f);
    	
//    	if (change == 1) {
//    		direction[0] = 1;
//    		if (color[0] >= 1) {
//    			change = 0;
//    		}
//    	}
//    	if (change == 0) {
//    		direction[0] = -1;
//    		if (color[0] <= 0) {
//    			change = 1;
//    		}
//    	}
    	
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
        renderer.render(window, mesh);
    }
    
    @Override
    public void cleanup() {
        renderer.cleanup();
        mesh.cleanUp();
    }
    
}