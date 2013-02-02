cd C:\workspace\sample-server-client\serverSavedStuff
ECHO Y | del *.*
cd C:\workspace\sample-server-client
mvn assembly:single & java -jar target/sample-server-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar