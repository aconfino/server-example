cd C:\workspace\sample-server-client\serverSavedStuff
ECHO Y | del *.*
cd C:\workspace\sample-server-client
mvn assembly:single & java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8001 -jar target/sample-server-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar