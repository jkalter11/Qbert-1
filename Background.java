package qBert;

import java.io.*;

import javax.media.opengl.*;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.*;

// draes an background, which is a texture map rectangle behing the cene
public class Background {
	private File f;
	private Texture m_text;
	
	public Background(String textureName){
		f = new File(textureName);
		try {
			m_text = TextureIO.newTexture(f,true);
		} catch (GLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DrawBackgeound(GL gl){
		m_text.bind();
		TextureCoords tc = m_text.getImageTexCoords();
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(tc.left(), tc.top());
		gl.glVertex3f(-650,-650,-1000);
		gl.glTexCoord2f(tc.left(),tc.bottom());
		gl.glVertex3f(-650,650,-1000);
		gl.glTexCoord2f(tc.right(),tc.bottom());
		gl.glVertex3f(650,650,-1000);
		gl.glTexCoord2f(tc.right(),tc.top());
		gl.glVertex3f(650,-650,-1000);
		
		gl.glEnd();
		m_text.disable();
	}

}
