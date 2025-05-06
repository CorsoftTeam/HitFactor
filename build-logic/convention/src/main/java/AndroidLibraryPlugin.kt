import com.android.build.api.dsl.LibraryExtension
import com.corsoft.hitfactor.build_logic.convention.config.configureAndroidLibrary
import com.corsoft.hitfactor.build_logic.convention.pluginId
import com.corsoft.hitfactor.build_logic.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply(versionCatalog.findPlugin("android-library").pluginId)
                apply(versionCatalog.findPlugin("kotlin-android").pluginId)
            }

            extensions.configure<LibraryExtension> {
                configureAndroidLibrary(this)
            }
        }
    }
}