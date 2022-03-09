package de.cwansart.orientdbwithtestcontainers;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

public class Main {
    public static void main(String[] args) {
        try (OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
             ODatabaseSession db = orient.open("testdb", "root", "root")) {
            initDb(db);

            OResultSet query = db.query("MATCH {class:Person, as:a} RETURN a.firstname, a.lastname");
            while (query.hasNext()) {
                OResult next = query.next();
                String firstname = next.getProperty("firstname");
                String lastname = next.getProperty("lastname");
                System.out.println("Firstname=" + firstname);
                System.out.println("Lastname=" + lastname);
            }
        }
    }

    private static void initDb(ODatabaseSession db) {
        if (db.getClass("Person") == null) {
            db.createVertexClass("Person");
        }
        if (db.getClass("FriendOf") == null) {
            db.createEdgeClass("FriendOf");
        }

        OResultSet query = db.query("MATCH {class:Person, as:a} RETURN a.firstname, a.lastname");
        if (query.hasNext()) {
            OVertex person = db.newVertex("Person");
            person.setProperty("firstname", "Maria");
            person.setProperty("lastname", "Musterfrau");
            person.save();
        }
    }
}
