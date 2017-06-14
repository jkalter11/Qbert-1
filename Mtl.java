package qBert;

public class Mtl {
		private float[] Ka = {0.2f,0.2f,0.2f};
		private float[] Kd = {0.8f,0.8f,0.8f};
		private float[] Ks = {1.0f,1.0f,1.0f};
		
		private float d = 1.0f;
		private float Tr = 1.0f;
		private float Ns = 0.0f;
		private int illum = 0;
		
		public void setKa(float r, float g, float b){
			float [] f = {r,g,b};
			Ka = f;
		}
		
		public void setKd(float r, float g, float b){
			float [] f = {r,g,b};
			Kd = f;
		}
		
		public void setKs(float r, float g, float b){
			float [] f = {r,g,b};
			Ks = f;
		}
		
		public void setD(float f){ d= f;};
		public void setTr(float f){ Tr= f;};
		public void setNs(float f){ Ns= f;};
		public void setIllum(int f){ illum= f;};
		
		public float[] getKa(){return Ka;}
		public float[] getKd(){return Kd;}
		public float[] getKs(){return Ks;}
		
		public float getD(){return d;}
		public float getTr(){return Tr;}
		public float getNs(){return Ns;}
		public int getIllum(){return illum;}
		
		//converts  a string array to a float array
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
		
		public void parseLine(String[] s){
			if (s[0].equalsIgnoreCase("ka")){
				Ka = get3fFromStr(s);
			}
			
			if (s[0].equalsIgnoreCase("ks")){
				Ks = get3fFromStr(s);
			}
			
			if (s[0].equalsIgnoreCase("kd")){
				Kd = get3fFromStr(s);
			}
			
			if (s[0].equalsIgnoreCase("d")){
				d = Float.parseFloat(s[1]);
			}
			
			if (s[0].equalsIgnoreCase("illum")){
				illum = Integer.parseInt(s[1]);
			}
			
			if (s[0].equalsIgnoreCase("Ns")){
				Ns = Float.parseFloat(s[1]);
			}
			
			if (s[0].equalsIgnoreCase("tr")){
				Tr = Float.parseFloat(s[1]);
			}
		}
		
}
