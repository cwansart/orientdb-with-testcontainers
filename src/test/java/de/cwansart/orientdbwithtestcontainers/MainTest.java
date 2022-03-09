package de.cwansart.orientdbwithtestcontainers;

import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class MainTest {

    @Container
    GenericContainer orientContainer = new GenericContainer(DockerImageName.parse("orientdb:3.2.4"))
            .withExposedPorts(2424, 2480)
            .withEnv("ORIENTDB_ROOT_PASSWORD", "root");

    @Test
    void myDatabaseTest() {
        String host = orientContainer.getHost();
        Integer mappedPort = orientContainer.getMappedPort(2424);
        try (OrientDB orientDB = new OrientDB("remote:" + host + ":" + mappedPort, OrientDBConfig.defaultConfig())) {
            // do something
        }
    }
}