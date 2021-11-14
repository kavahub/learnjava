https://www.baeldung.com/java-authentication-authorization-service

$ mvn clean package
$ java -Djava.security.manager net.learnjava.jaas.JaasAuthorization -Djava.security.policy=src/main/resources/jaas/jaas.policy -Djava.security.auth.login.config=src/main/resources/jaas/jaas.login.config -classpath target/core-java-security-1.0.0-SNAPSHOT.jar 