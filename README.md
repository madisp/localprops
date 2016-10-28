Gradle Local Properties Plugin
==============================

A tiny Gradle plugin that enables adding a Android-plugin-like
`local.properties` file into the project.

It was pretty much created because I'm tired of writing a
variation of the following code over and over again:

```groovy
File propsFile = project.file('local.properties')
propsFile.withReader('UTF-8') { reader ->
  Properties props = new Properties()
  props.load(reader)
  props.forEach { key, value ->
    project.properties.put(key.toString(), value.toString())
  }
}
```

Usage
-----

Applying the plugin:

```groovy
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath 'pink.madis.gradle:localprops:0.0.2'
  }
}

apply plugin: 'pink.madis.gradle.localprops'
```

Example task printing out some properties:

```groovy
task props {
  doFirst {
    // prints all 
    project.localprops.each { key, value ->
      println "Local property '$key' is '$value'"
    }
    
    // helper method that gives you a File or null...
    println "SDK is at ${project.localprops.file('sdk.dir')}"
    
    // another example, these two blocks of code are essentially equivalent
    def file = project.localprops.file('filePath')
    
    def file = null
    if (project.localprops.filePath != null) {
      file = project.file(project.localprops.filePath)
    }
  }
}
```