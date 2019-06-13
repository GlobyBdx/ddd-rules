Install JAR file for annotations in local **Maven** repository.

```console
mvn install:install-file -Dfile=./ddd-annotations-1.0.0.jar -DgroupId=fr.ubordeaux.ddd -DartifactId=ddd-annotations -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
```

Install JAR file for rules in local **Maven** repository.

```console
mvn install:install-file -Dfile=./ddd-rules-1.0.0.jar -DgroupId=fr.ubordeaux.ddd -DartifactId=ddd-rules -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
```