package qBert;

import java.io.*;
import java.util.*;

import javax.media.opengl.GL;
//loads and displays an onj model
import qBert.ObjectMat;
public class ObjObject {
	private List<float[]> vertices = new ArrayList<float[]>();//array list of vertices
	private List<float[]> normals = new ArrayList<float[]>();//array lost of notmals
	private List<int[][]> faces = new ArrayList<int[][]>();//array list of faces
	
	ObjectMat matLst = new ObjectMat();
	private Map<Integer,String> materials = new  HashMap<Integer,String>();//holds a mapping of face number and the material to use from that face number and on
	
	private float[] translateBefore = {0,0,0};
	private float[] translateAfter = {0,0,0};
	private float[] scale= {1,1,1};
	private float[] rotate= {0,0,0};
	private float[] rotateFirst= {0,0,0};
	
	boolean hasNormal=false;
	boolean hasTexture = false;
	
	private float xCm,yCm,zCm;
	private float scaleFactor;
	//conver a string array  into a float array
	private float[] get3fFromStr(String[] s){
		float[] f = new float[3];
		int pos=0;
		for (int i=1;i<s.length;i++){
			if  (s[i] == "")
				continue;
			f[pos] = Float.parseFloat(s[i]);
			pos++;
			if (pos >= 3) break;
		}
		return f;
	}
	
	//parse face parameters
	private int[][] parseFace(String[] s){
		String tmp[] = s[2].split("/");
		if (tmp.length >=2)
			hasTexture = true;
		if (tmp.length >=3)
			hasNormal = true;
		int[][] face = new int[s.length-2][tmp.length];
		for (int i=2;i<s.length;i++){
			String[] verts = s[i].split("/");
			for (int j=0;j<verts.length;j++)
				face[i-2][j] = Integer.parseInt(verts[j]);
		}
		return face;
			
		}
	//parse a line from the obj file
	public void parseLine(String str){
		String[] s = str.split(" ");
		if (s[0].equals("v")){
			float[] f = get3fFromStr(s);
			vertices.add(f);
		}
		if (s[0].equals("vn")){
			float[] f = get3fFromStr(s);
			normals.add(f);
		}
		
		if (s[0].equals("f")){
			int[][] face = parseFace(s);
			faces.add(face);
		}
		
		if (s[0].equalsIgnoreCase("mtllib")){
			matLst.Load(Main.DIR+s[1]);
		}
		
		if (s[0].equals("usemtl")){
			materials.put(faces.size()+1, s[1]);
		}
	}
	
	public void rotateFirst(float dx,float dy,float dz){
		rotateFirst[0] = dx;
		rotateFirst[1] = dy;
		rotateFirst[2] = dz;
	}
	
	public void rotate(float dx,float dy,float dz){
		rotate[0] = dx;
		rotate[1] = dy;
		rotate[2] = dz;
	}
	
	public void scale(float dx,float dy,float dz){
		scale[0] = dx;
		scale[1] = dy;
		scale[2] = dz;
	}
	
	public void translateBeforeRotate(float dx,float dy,float dz){
		translateBefore[0] = dx;
		translateBefore[1] = dy;
		translateBefore[2] = dz;
	}
	
	public void translateAfterRotate(float dx,float dy,float dz){
		translateAfter[0] = dx;
		translateAfter[1] = dy;
		translateAfter[2] = dz;
	}
	
	public void Draw(GL gl, Render render, int lookSide){
		///////////save the old material
		float[] oldAmbient= new float[4];
		float[] oldDiffuse = new float[4];
		float[] oldSpecular = new float[4];
		float[] oldIllum = new float[1]; 
		
		gl.glGetMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, oldAmbient, 0);
		gl.glGetMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, oldDiffuse, 0);
		gl.glGetMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, oldSpecular, 0);
		gl.glGetMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, oldIllum, 0);
		
		gl.glPushMatrix();
		gl.glLoadIdentity();
	
		gl.glTranslatef(translateAfter[0], translateAfter[1], translateAfter[2]);
		
		gl.glRotatef(45, 1.0f, 0.0f, 0.0f);
		/////////////////////////////////////////////////
		gl.glRotatef(rotate[2], 0.0f, 0.0f, 1.0f);
		gl.glRotatef(rotate[1], 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotate[0], 1.0f, 0.0f, 0.0f);
		/////////////////////////////////////////////////
		
		gl.glTranslatef(translateBefore[0], translateBefore[1], translateBefore[2]);
		
		
		gl.glRotatef(90*lookSide, 0.0f, 1.0f, 0.0f);
		
		gl.glRotatef(rotateFirst[2], 0.0f, 0.0f, 1.0f);
		gl.glRotatef(rotateFirst[1], 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotateFirst[0], 1.0f, 0.0f, 0.0f);
		
		gl.glScalef(scale[0], scale[1], scale[2]);
		
		gl.glEnable(GL.GL_NORMALIZE);
		
		int count =0;
		for (Iterator<int[][]> iter = faces.iterator();iter.hasNext();){
			int[][] face = iter.next();
			count++;// holds the current face number
			//check if we need to change the material wer'e using
			if (materials.containsKey(count)){
				matLst.setMaterial(materials.get(count));
				gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, matLst.getKa(), 0);
			    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, matLst.getKd(), 0);
			    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, matLst.getKs(), 0);
			    float[] shine = {(float)matLst.getIllum()};
			    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, shine, 0);
			}
			//render the current face
			gl.glBegin(GL.GL_POLYGON);
			for (int i=0;i<face.length;i++){
				if (hasNormal){
					float[] norm = null;
					if (face[i][2] < normals.size()){
						norm = normals.get(face[i][2]-1);
					}else{
						 norm = normals.get(1);
					}
					gl.glNormal3d(norm[0],norm[1], norm[2]);
				}
				
				float[] vert =  vertices.get(face[i][0]-1);
				gl.glVertex3d(vert[0], vert[1], vert[2]);
			 }
			gl.glEnd();
	
			
		}
		
		emptyArr(translateBefore);
		emptyArr(translateAfter);
		emptyArr(rotate);
		emptyArr(rotateFirst);
		
		gl.glLoadIdentity();
		gl.glPopMatrix();
		
		gl.glPopMatrix();
		gl.glNormal3d(0.0f,0.0f, 1.0f); 
		 // restore the old materail
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, oldAmbient, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, oldDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, oldSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, oldIllum, 0);
	}
	
	private void emptyArr(float[] arr){
		arr[0] = 0;
		arr[1] = 0;
		arr[2] = 0;
	}
	// centers the object around (0,0,0) ,  and scales it to unity (that way we can swap between models more conveintly
	private void calcCenterOfMassAndScale(){
		float minX,minY,minZ,maxX,maxY,maxZ;
		maxX=maxY=maxZ = Float.MIN_VALUE;
		minX=minY=minZ=Float.MAX_VALUE;
		int num=0;
		for (Iterator<int[][]> iter = faces.iterator();iter.hasNext();){
			int[][] face = iter.next();
			for (int i=0;i<face.length;i++){
			 
				
				float[] vert =  vertices.get(face[i][0]-1);
				xCm += vert[0];
				yCm += vert[1];
				zCm += vert[2];
				num++;
				
				if (vert[0] < minX)
					minX=vert[0];
				if(vert[0] > maxX)
					maxX=vert[0];
				
				if (vert[1] < minY)
					minY = vert[1];
				if(vert[1] > maxY)
					maxY=vert[1];
				
				if (vert[2] < minZ)
					minZ=vert[2];
				if(vert[2] > maxZ)
					maxZ=vert[2];			
			 }			
		}
		
		xCm /= num;
		yCm /= num;
		zCm /= num;
		
		float xScale = maxX-minX;
		float yScale = maxY-minY;
		float zScale = maxZ-minZ;
		
		scaleFactor = xScale;
		if (yScale > scaleFactor)
			scaleFactor = yScale;
		if(zScale > scaleFactor)
			scaleFactor = zScale;
		
		List<float[]> tmpVertices = new ArrayList<float[]>();
		
		for (Iterator<float[]> iter = vertices.iterator();iter.hasNext();){
			float[] vert = iter.next();
			vert[0] -= xCm;
			vert[1] -= yCm;
			vert[2] -= zCm;
			
			vert[0] /= scaleFactor;
			vert[1] /= scaleFactor;
			vert[2] /= scaleFactor;
			
			tmpVertices.add(vert);
			
			}
		
		vertices = tmpVertices;
		
	}
	//loads the obj file
	public void Load(String fileName){
		Reader inFile = null;
		try{
			String s;
			inFile = new FileReader(fileName);
			BufferedReader in = new BufferedReader(inFile);
			while (in.ready()){
				s=in.readLine();
				//takee care of split lines
				while (s.contains("\\")){
					String t = in.readLine();
					s=s.replace("\\", " ");
					s=s.concat(t);
				}
				parseLine(s);
			}
		}
		catch (IOException e){}
		calcCenterOfMassAndScale();
	}
	
}
