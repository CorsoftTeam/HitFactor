import com.android.build.api.dsl.LibraryExtension
import com.corsoft.hitfactor.build_logic.convention.config.configureCompose
import com.corsoft.hitfactor.build_logic.convention.pluginId
import com.corsoft.hitfactor.build_logic.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ComposePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply(versionCatalog.findPlugin("android-library").pluginId)
            }

            extensions.configure<LibraryExtension> {
                configureCompose(this)
            }
        }
    }
}