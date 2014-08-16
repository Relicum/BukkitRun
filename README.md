Minecraft TimeUnit
==================

**Licensed under GNU GENERAL PUBLIC LICENSE Version 3**

This a custom implementation of the Java TimeUnit class. It allows the conversion of different
unit of time. It has been modified to include a minecraft Tick as a unit of time.
>The intended use is to define delays and periods of time when using the Bukkit scheduler and a BukkitRunnable
 
Included is a modified version of BukkitRunnable called BukkitRun (To help avoid name conflicts)
 
THIS IS NOT A PLUGIN you will need to shard it if using maven or change the package names and include in your plugin.

Maven Dependency
----

```XML
    <dependency>
        <groupId>org.codemine</groupId>
        <artifactId>BukkitRun</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```

Maven Repo
----

```XML
    <repository>
        <id>relicum-snapshot</id>
        <url>http://repository-relicum.forge.cloudbees.com/snapshot/</url>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository
```

JavaDocs
---
[JAVA DOCS](https://relicum.ci.cloudbees.com/job/BukkitRun/javadoc/)

Jenkins
---
[JENKINS CI](https://relicum.ci.cloudbees.com/job/BukkitRun/)

**Pull requests gladly accepted**
 
  
  
