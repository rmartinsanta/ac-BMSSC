# Requires libcurl4-openssl-dev in most Ubuntu distributions, install before launching
set -e
mvn clean package
java \
-Xmx24G \
-Xms24G \
-jar target/BMSSC-0.21-SNAPSHOT.jar \
--instances.path.default=instances/tuning/instances \
--serializers.solution-json.enabled=true \
--serializers.solution-json.frequency=all \
--solver.experiments=FinalExperiment \
--solver.parallelExecutor=true \
--solver.nWorkers=32 \
--solver.metrics=true
