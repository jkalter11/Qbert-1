package qBert;
import java.util.*;
import java.io.*;

// mat file parser
public class ObjectMat {
	private Map<String,Mtl> materials = new  HashMap<String,Mtl>();//map of materilas and names
	Mtl currMaterial = null;
	String CurrName;
	
	
	public void Load(String fileName){
		Reader inFile = null;
		try{
			String s;
			inFile = new FileReader(fileName);
			BufferedReader in = new BufferedReader(inFile);
			while (in.ready()){
				s=in.readLine();
				parseLine(s);
			}
			materials.put(CurrName.toLowerCase(),currMaterial);
		}
		catch (IOException e){}
		
	}
	
	public void parseLine(String s){
		String[] line = s.split(" ");
		if (line[0].equalsIgnoreCase("newmtl")){
			if (currMaterial != null){
				materials.put(CurrName.toLowerCase(),currMaterial);
			}
			currMaterial = new Mtl();
			CurrName = line[1];
			}
		if (currMaterial != null){
			currMaterial.parseLine(line);
		}
		
	}
	//sets the active materail
	public void setMaterial(String name){
		currMaterial = materials.get(name.toLowerCase());
	}
	
	public float[] getKa(){return currMaterial.getKa();}
	public float[] getKd(){return currMaterial.getKd();}
	public float[] getKs(){return currMaterial.getKs();}
	
	public float getD(){return currMaterial.getD();}
	public float getTr(){return currMaterial.getTr();}
	public float getNs(){return currMaterial.getNs();}
	public int getIllum(){return currMaterial.getIllum();}
	
}
