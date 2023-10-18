set -e
mvn clean package
java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.rmi.port=9010 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false \
-Djava.rmi.server.hostname=192.168.10.114 \
-Xmx24G \
-Xms24G \
-jar target/BMSSC-0.19-SNAPSHOT.jar \
--solver.experiments=FinalExperiment \
--solver.parallelExecutor=true \
--solver.nWorkers=8