package satanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.management.AttributeList;

public class MainClass {

	public static void main(String args[]){
		WriteToXML propertyxml = new WriteToXML("ConfigurationArtefactFile.xml");
		ReadPropertyFile pf=new ReadPropertyFile("config.properties");
		HashMap<String, String> map=pf.load();
		propertyxml.convertToXML(map);
		
		WriteToXML configurationText = new WriteToXML("ConfigurationFile.xml");
		ReadPropertyFile conf=new ReadPropertyFile("configurations.txt");
		HashMap<String, String> map2=conf.load();
		configurationText.convertToXML(map2);

		XmlToArray x = new XmlToArray();
		ArrayList<AttributeList> list1 = x.parseXML("ConfigurationArtefactFile.xml");
		ArrayList<AttributeList> list2 = x.parseXML("ConfigurationFile.xml");
		Map<String,ArrayList<AttributeList>> mapList = CreateTraceability.compareList(list1, list2);
		
		CreateRelationFile relation = new CreateRelationFile("Relations.xml");
		relation.createRelations(mapList);
		
		StoreData sd = new StoreData();
		sd.createDB();
		
	}
}
