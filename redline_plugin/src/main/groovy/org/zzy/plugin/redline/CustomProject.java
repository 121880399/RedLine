package org.zzy.plugin.redline;

import com.android.builder.model.AndroidProject;
import com.android.tools.lint.client.api.LintClient;
import com.android.tools.lint.detector.api.Project;
import com.google.common.collect.Maps;

import java.io.File;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/7/10 16:16
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CustomProject extends Project {

//    public final Map<org.gradle.api.Project, AndroidProject> gradleProjects = Maps.newHashMap();


    org.gradle.api.Project mProject;

//    private  AndroidProject mAndroidProject;

//    public final Map<String,Project> namedProjects = Maps.newHashMap();
    /**
     * Creates a new Project. Use one of the factory methods to create.
     *
     * @param client
     * @param dir
     * @param referenceDir
     */
    protected CustomProject(LintClient client, File dir, File referenceDir, org.gradle.api.Project project) {
        this(client, dir, referenceDir);
        this.mProject = project;
//        mAndroidProject = getAndroidProject(project);
    }

    protected CustomProject(LintClient client, File dir, File referenceDir){
        super(client,dir,referenceDir);
    }



    @Override
    public int getBuildSdk() {
        return 29;
    }

//    public Project getProject(){
//        gradleProject = true;
//        mergeManifests = true;
//
//        AndroidProject androidProject = getAndroidProject(mProject);
//        JavaPluginConvention convention = mProject.getConvention()
//                .findPlugin(JavaPluginConvention.class);
//        if (convention == null) {
//            return;
//        }
//        SourceSetContainer sourceSets = convention.getSourceSets();
//        if (sourceSets != null) {
//            final List<File> sources = Lists.newArrayList();
//            final List<File> classes = Lists.newArrayList();
//            final List<File> libs = Lists.newArrayList();
//            final List<File> tests = Lists.newArrayList();
//            for (SourceSet sourceSet : sourceSets) {
//                if (sourceSet.getName().equals(SourceSet.TEST_SOURCE_SET_NAME)) {
//                    // We don't model the full test source set yet (e.g. its dependencies),
//                    // only its source files
//                    SourceDirectorySet javaSrc = sourceSet.getJava();
//                    if (javaSrc != null) {
//                        tests.addAll(javaSrc.getSrcDirs());
//                    }
//                    continue;
//                }
//
//                SourceDirectorySet javaSrc = sourceSet.getJava();
//                if (javaSrc != null) {
//                    // There are also resource directories, in case we want to
//                    // model those here eventually
//                    sources.addAll(javaSrc.getSrcDirs());
//                }
//                SourceSetOutput output = sourceSet.getOutput();
//                if (output != null) {
//                    for (File file : output.getClassesDirs()) {
//                        classes.add(file);
//                    }
//                }
//
//                libs.addAll(sourceSet.getCompileClasspath().getFiles());
//
//                // TODO: Boot classpath? We don't have access to that here, so for
//                // now EcjParser just falls back to the running Gradle JVM and looks
//                // up its class path.
//            }
//
//            File projectDir = mProject.getProjectDir();
//            final List<Project> dependencies = Lists.newArrayList();
//
//
//            directLibraries = dependencies;
//            javaSourceFolders = sources;
//            javaClassFolders = classes;
//            javaLibraries = libs;
//            testSourceFolders = tests;
//
//
//            // Dependencies
//            ConfigurationContainer configurations = mProject.getConfigurations();
//            Configuration compileConfiguration = configurations.getByName("compileClasspath");
//            for (Dependency dependency : compileConfiguration.getAllDependencies()) {
//                if (dependency instanceof ProjectDependency) {
//                    org.gradle.api.Project p =
//                            ((ProjectDependency) dependency).getDependencyProject();
//                    if (p != null) {
//                        Project lintProject = getProject(lintClient, p.getPath(), p,
//                                variantName);
//                        if (lintProject != null) {
//                            dependencies.add(lintProject);
//                        }
//                    }
//                } else if (dependency instanceof ExternalDependency) {
//                    String group = dependency.getGroup();
//                    String name = dependency.getName();
//                    String version = dependency.getVersion();
//                    if (name == null || group == null || version == null) {
//                        // This will be the case for example if you use something like
//                        //    repositories { flatDir { dirs 'myjars' } }
//                        //    dependencies { compile name: 'guava-18.0' }
//                        continue;
//                    }
//                    MavenCoordinatesImpl coordinates = new MavenCoordinatesImpl(group,
//                            name, version);
//                    Project javaLib = javaLibraryProjectsByCoordinate.get(coordinates);
//                    //noinspection StatementWithEmptyBody
//                    if (javaLib != null) {
//                        dependencies.add(javaLib);
//                    } else {
//                        // Else: Create wrapper here. Unfortunately, we don't have a
//                        // pointer to the actual .jar file to add (getArtifacts()
//                        // typically returns an empty set), so we can't create
//                        // a real artifact (and creating a fake one and placing it here
//                        // is dangerous; it would mean putting one into the
//                        // map that would prevent a real definition from being inserted.
//                    }
//                } else if (dependency instanceof FileCollectionDependency) {
//                    Set<File> files = ((FileCollectionDependency) dependency).resolve();
//                    if (files != null) {
//                        libs.addAll(files);
//                    }
//                }
//            }
//
//        }
//    }



//    @Nullable
//    private AndroidProject getAndroidProject(@NonNull org.gradle.api.Project gradleProject) {
//        AndroidProject androidProject = gradleProjects.get(gradleProject);
//        if (androidProject == null) {
//            androidProject = createAndroidProject(gradleProject);
//            if (androidProject != null) {
//                gradleProjects.put(gradleProject, androidProject);
//            }
//        }
//        return androidProject;
//    }
//
//    private static AndroidProject createAndroidProject(
//            @NonNull org.gradle.api.Project gradleProject) {
//        PluginContainer pluginContainer = gradleProject.getPlugins();
//        for (Plugin p : pluginContainer) {
//            if (p instanceof ToolingRegistryProvider) {
//                ToolingModelBuilderRegistry registry;
//                registry = ((ToolingRegistryProvider) p).getModelBuilderRegistry();
//                String modelName = AndroidProject.class.getName();
//                ToolingModelBuilder builder = registry.getBuilder(modelName);
//                assert builder.canBuild(modelName) : modelName;
//                return (AndroidProject) builder.buildAll(modelName, gradleProject);
//            }
//        }
//        return null;
//    }


}
