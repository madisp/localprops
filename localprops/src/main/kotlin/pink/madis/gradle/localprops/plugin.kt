package pink.madis.gradle.localprops

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.nio.charset.StandardCharsets
import java.util.Properties

class LocalPropertiesPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val propsFile = project.file("local.properties")

    val map = mutableMapOf<String, String>()

    if (propsFile.exists()) {
      val props = Properties()
      propsFile.reader(StandardCharsets.UTF_8).use { reader ->
        props.load(reader)
      }
      props.forEach { key, value ->
        map.put(key.toString(), value.toString())
      }
    }

    project.extensions.add("localprops", LocalProperties(map, project))
  }
}

class LocalProperties(
    internal val props: Map<String, String>,
    internal val project: Project) : Map<String, String> by props {
  fun file(key: String) = project.file(props[key])
}