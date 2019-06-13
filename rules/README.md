Create and install JAR file for annotations in local **Maven** repository.

```console
cd ./target/classes/

jar cvf ddd-annotations-1.0.0.jar ./fr/ubordeaux/ddd/annotations/

mvn install:install-file -Dfile=./ddd-annotations-1.0.0.jar -DgroupId=fr.ubordeaux.ddd -DartifactId=ddd-annotations -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
```

Create and install JAR file for rules in local **Maven** repository.

```console
cd ./target/classes/

jar cvf ddd-rules-1.0.0.jar ./fr/ubordeaux/ddd/rules/ ./fr/ubordeaux/ddd/tests/

mvn install:install-file -Dfile=./ddd-rules-1.0.0.jar -DgroupId=fr.ubordeaux.ddd -DartifactId=ddd-rules -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
```