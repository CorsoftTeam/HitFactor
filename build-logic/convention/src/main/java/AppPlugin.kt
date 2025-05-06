import com.android.build.api.dsl.ApplicationExtension
import com.corsoft.hitfactor.build_logic.convention.config.configureApp
import com.corsoft.hitfactor.build_logic.convention.pluginId
import com.corsoft.hitfactor.build_logic.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AppPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply(versionCatalog.findPlugin("android-application").pluginId)
                apply(versionCatalog.findPlugin("kotlin-android").pluginId)
            }

            extensions.configure<ApplicationExtension> {
                configureApp(this)
            }
        }
    }
}