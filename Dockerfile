FROM openjdk:8-jdk
ADD . /tmp/
WORKDIR /tmp
ENV CLASSPATH /tmp/src/
RUN javac -classpath .:resources/External_Libs/sqlite/sqlite-jdbc-3.16.1.jar:resources/External_Libs/miglayout/miglayout-4.0.jar:resources/External_Libs/miglayout/miglayout-4.0-swing.jar:resources/External_Libs/miglayout/miglayout-4.0-swt.jar ./src/au/com/eca/assignment/*/*.java
CMD ["java", "-classpath", "resources/External_Libs/sqlite/sqlite-jdbc-3.16.1.jar;resources/External_Libs/miglayout/miglayout-4.0.jar;resources/External_Libs/miglayout/miglayout-4.0-swing.jar;resources/External_Libs/miglayout/miglayout-4.0-swt.jar", ".au.com.eca.assignment.main.BJMain"]