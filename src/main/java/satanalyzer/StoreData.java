package satanalyzer;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.driver.v1.*;
public class StoreData {
	
	public void createDB(){
		Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "neo4j" ) );
		Session session = driver.session();
	try {
		
		session.run( "CREATE (a:Person {name:'Arthur', title:'King'})" );

		StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title" );
		while ( result.hasNext() )
		{
		    Record record = result.next();
		    System.out.println( record.get( "title" ).asString() + " " + record.get("name").asString() );
		}
		
		
	} finally {
		// TODO: handle finally clause
		session.close();
		driver.close();
	}

		
	}

}
