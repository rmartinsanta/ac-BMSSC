set -e
mvn clean package
java -jar target/BMSSC-0.18-SNAPSHOT.jar --instance-selector \
--instances.preliminar-output-path=instances/tuning \
--instances.for-selection=instances/allForClassification \
--instances.preliminar-percentage=0.15