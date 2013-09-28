package loader;

import graphics.compatibility.skeleton.Skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		
		Node geoNameNode = d.getElementsByTagName("geometry").item(0);
		String geoName = geoNameNode.getAttributes().item(0).getNodeValue();
		
		
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
						
						//strip name out
						String name = child.getAttributes().item(1).getNodeValue().replaceAll(geoName + "-", "");
						
						values.put(name, floats);
						System.out.println(name);
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
		
		//get skeleton information
		Skeleton skeleton;
		

		Node libControllersNode = d.getElementsByTagName("library_controllers").item(0);
		
		Node skinSourceNode = mesh.getFirstChild().getFirstChild();
		
		NodeList skinSourceNodeChildren = skinSourceNode.getChildNodes();
		
		Node vertexWeights;
		List<Node> skinSources = new ArrayList<Node>();
		for(int i = 0; i < skinSourceNodeChildren.getLength(); i++)
		{
			Node n = skinSourceNodeChildren.item(i);
			if(n.getNodeName().equals("source"))
			{
				skinSources.add(n);
			}
			if(n.getNodeName().equals("vertex_weights"))
			{
				vertexWeights = n;
			}
		}
		
		//next get the id one and use that to get a list of the bone ids.
		
		
		
		return rearrange(values);
	}
	
	private static HashMap<String, float[]> rearrange(HashMap<String, float[]> in){
		
		////
		
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
				
		
		float[] temp = in.get("positions-array");
		for(int i = 0; i < temp.length; i+= 3){
			init_vertices.add(new Vector3f(temp[i],temp[i+1],temp[i+2]));
		}

		temp = in.get("normals-array");
		for(int i = 0; i < temp.length; i+= 3){
			init_normals.add(new Vector3f(temp[i],temp[i+1],temp[i+2]));
		}
		
		temp = in.get("map-0-array");
		for(int i = 0; i < temp.length; i+= 2){
			init_texCoords.add(new Vector2f(temp[i],temp[i+1]));
		}
		
		temp = in.get("elements");
		for(int i = 0; i < temp.length;){
			Vert[] face = new Vert[3];
			for(int j = 0; j < 3; j++){
				face[j] = new Vert(indexCount++, (int)temp[i++], (int)temp[i++], (int)temp[i++]);					
			}
			init_faces.add(face);
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

					output_vertices.add(init_vertices.get(v.vertexIndex + 1));
					output_texCoords.add(init_texCoords.get(v.textureIndex + 1));
					output_normals.add(init_normals.get(v.normalIndex + 1));

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
		
		////
		
	}
	
	
	

	protected static class Vert {	
		public int vertexIndex, normalIndex, textureIndex, index;
	
	
		public Vert(int index, int v , int n, int t)
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
