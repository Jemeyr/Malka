package loader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ColladaLoader {


	
	public static HashMap<String, float[]> load(String filename){
		
		HashMap<String, float[]> values = new HashMap<String, float[]>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document d = null;
		try {
			db = dbf.newDocumentBuilder();
			d = db.parse(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Node mesh = d.getElementsByTagName("mesh").item(0);
		
		NodeList sources = mesh.getChildNodes();
		
		for(int i =0; i < sources.getLength(); i++){
			Node source = sources.item(i);
			if(source.getNodeName().equals("source")){
				NodeList sourceChildren = source.getChildNodes();
				for(int j = 0; j < sourceChildren.getLength(); j++){
					Node child = sourceChildren.item(j);
					
					if(child.getNodeName().equals("float_array")){
						String data = child.getTextContent();
						String[] vals = data.split(" ");
						float[] floats = new float[vals.length];
						
						for(int k = 0; k < vals.length; k++){
							floats[k] = Float.parseFloat(vals[k]);
						}
						values.put(child.getAttributes().item(1).getNodeValue(), floats);
						System.out.println(child.getAttributes().item(1).getNodeValue());
					}
				}	
			}
			else if(source.getNodeName().equals("polylist")){
				NodeList sourceChildren = source.getChildNodes();
				for(int j = 0; j < sourceChildren.getLength(); j++){
					Node child = sourceChildren.item(j);
					
					if(child.getNodeName().equals("p")){
						String data = child.getTextContent();
						String[] vals = data.split(" ");
						float[] floats = new float[vals.length];
						
						for(int k = 0; k < vals.length; k++){
							floats[k] = Float.parseFloat(vals[k]);
						}
						values.put("elements", floats);
						System.out.println("elements");
					}
				}	
			}
			
			
		}
		
		//NodeList nodes = libgeos.getElementsByTagName("library_geometries");
		
		return values;
	}
	
	public static HashMap<String, float[]>loadTemp(String fileName)
	{

		int indexCount = 0;
		
		float[] vertices;
		float[] elements;
		float[] normals;
		float[] texCoords;
		
		List<Vector3f> init_vertices = new ArrayList<Vector3f>();
		List<Vector3f> init_normals = new ArrayList<Vector3f>();
		List<Vector2f> init_texCoords = new ArrayList<Vector2f>();
		
		List<Vert[]> init_faces = new ArrayList<Vert[]>();
		
		List<Vert> unique_verts = new ArrayList<Vert>();
		List<Integer> unique_indices = new ArrayList<Integer>();
		
		List<Vector3f> output_vertices = new ArrayList<Vector3f>();
		List<Vector3f> output_normals = new ArrayList<Vector3f>();
		List<Vector2f> output_texCoords = new ArrayList<Vector2f>();
		
		String[] tokens = new String[4];
		
		Scanner fileScanner = null;
		
		
		try
		{
			fileScanner = new Scanner(new File(fileName));
		}catch (Exception e){System.out.println("File Error");}
		
		if(fileScanner == null)
		{
			System.out.println("File Error");
			return null;
		}

		String currLine = "";
	
		while(fileScanner.hasNext())
		{
			currLine = fileScanner.nextLine();

			tokens = currLine.split(" ");
			float x = 0;
			float y = 0;
			float z = 0;
			
			
			try{
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				z = Float.parseFloat(tokens[3]);
			}catch (Exception e){}
			
			if(currLine.startsWith("v "))
			{
				init_vertices.add(new Vector3f(x,y,z));
			}
			else if (currLine.startsWith("vn "))
			{
				init_normals.add(new Vector3f(x,y,z));
			}
			else if (currLine.startsWith("vt "))
			{
				init_texCoords.add(new Vector2f(x,y));
			}
			else if (currLine.startsWith("f "))
			{
				Vert[] face = new Vert[3];
				
				int j = 0;
				for(int i = 1; i < 4; i++)//why am I changing this?
				{
					String[] toks = tokens[i].split("/");
					face[j] = new Vert(indexCount++, toks[0], toks[1], toks[2]);
					j++;
				}
				init_faces.add(face);
			}
		}

		while(fileScanner.hasNext())
		{
			currLine = fileScanner.nextLine();

			tokens = currLine.split(" ");
			
			float x,y,z;
				
			if(currLine.startsWith("v "))
			{
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				z = Float.parseFloat(tokens[3]);
				init_vertices.add(new Vector3f(x,y,z));
			}
			else if (currLine.startsWith("vn "))
			{
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				z = Float.parseFloat(tokens[3]);
				init_normals.add(new Vector3f(x,y,z));
			}
			else if (currLine.startsWith("vt "))
			{
				x = Float.parseFloat(tokens[1]);
				y = Float.parseFloat(tokens[2]);
				init_texCoords.add(new Vector2f(x,y));
			}
			else if (currLine.startsWith("f "))
			{
				Vert[] face = new Vert[3];
				
				int j = 0;
				for(int i = 1; i < 4; i++)
				{
					String[] toks = tokens[i].split("/");
					face[j] = new Vert(indexCount++, toks[0], toks[1], toks[2]);
					j++;
				}
				init_faces.add(face);
			}
		}
		
		if(fileScanner != null)
		{
			fileScanner.close();
		}
		
		int uid = 0;
		
		//iterate over here and add it
		for(Vert[] f : init_faces)
		{
			for(int j = 0; j < 3; j++)
			{
				Vert v = f[j];
				boolean flag = true;
				int index = -1;
				
				for(Vert uvert : unique_verts)
				{
					if(uvert.vertexIndex == v.vertexIndex && uvert.textureIndex == v.textureIndex && 
							uvert.normalIndex == v.normalIndex)
					{
						index = uvert.index;
						flag = false;
						break;
					}
				}
				
				if(flag)
				{
					v.index = uid++;
					unique_verts.add(v);
					index = v.index;

					output_vertices.add(init_vertices.get(v.vertexIndex));
					output_texCoords.add(init_texCoords.get(v.textureIndex));
					output_normals.add(init_normals.get(v.normalIndex));

				}
				
				unique_indices.add(index);
	
				
			}
			
		}	
		
		elements = new float[unique_indices.size()];
		vertices = new float[3 * output_vertices.size()];
		texCoords = new float[2 * output_texCoords.size()];
		normals = new float[3 * output_normals.size()];
		
		
		int counter = 0;
		for(int i = 0; i < unique_indices.size(); i++)
		{
			elements[counter++] = unique_indices.get(i);
		}
		
		counter = 0;
		for(Vector3f v : output_vertices)
		{
			vertices[counter++] = v.x;
			vertices[counter++] = v.y;
			vertices[counter++] = v.z;	
		}
		
		counter = 0;
		for(Vector2f v : output_texCoords)
		{
			texCoords[counter++] = v.x;
			texCoords[counter++] = 1.0f - v.y;	
		}
		
		counter = 0;
		for(Vector3f v : output_normals)
		{
			normals[counter++] = v.x;
			normals[counter++] = v.y;
			normals[counter++] = v.z;	
		}
		
		HashMap<String, float[]> values = new HashMap<String, float[]>();
		values.put("positions", vertices);
		values.put("normals", normals);
		values.put("texCoords", texCoords);
		values.put("elements", elements);
		
		return values;
		//TODO: why use floats for element, do some OO you idiot.
		
		
	}
	
	

	protected static class Vert {	
		public int vertexIndex, normalIndex, textureIndex, index;
	
	
	public Vert(int index, int v , int t, int n)
	{
		this.index = index;
		this.vertexIndex = v - 1;
		this.textureIndex = t - 1;
		this.normalIndex = n - 1;
	}
	
	public Vert(int index, String v, String n, String t)
	{
		this(index, Integer.parseInt(v), Integer.parseInt(n), Integer.parseInt(t));
	}
}
	
}
