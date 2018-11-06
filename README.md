# Sample project showing AST Transformation bug

this is a sample project that is meant to illustrate the lack of AST Transformations
being applied with griffon framework 2.15

Steps to reproduce

1. create project with lazybones

    ```bash
    lazybones create  griffon-javafx-groovy ast-bug
    ```

2. add validateable plugin to `build.gradle`

    ```groovy
    ...
    dependencies {
       compile "org.codehaus.griffon:griffon-guice:${griffon.version}"
       // add validation plugin
       griffon 'org.codehaus.griffon.plugins:griffon-validation-plugin:2.0.0'
    ```
    
3. add `@griffon.transform.Validateable` AST transformation to the model class 
   as shown in the [plugin guide](http://griffon-plugins.github.io/griffon-validation-plugin/#_the_validatable_ast_transformation)
   
   ```groovy
    @ArtifactProviderFor(GriffonModel)
    @griffon.transform.Validateable
    class AstBugModel {
        @FXObservable String clickCount = "0"
    }
    ```

4. launch the app via 

    ```bash
    ./gradlew run
    ```
    
## Expected 

App runs normally 

## Actual

App crashes with 
```
[JavaFX-Launcher] INFO org.codehaus.griffon.runtime.core.AbstractApplicationBootstrapper - Griffon 2.15.0
[JavaFX-Launcher] INFO org.codehaus.griffon.runtime.core.AbstractApplicationBootstrapper - Build: 2018-06-01T09:23:01.065+0200
[JavaFX-Launcher] INFO org.codehaus.griffon.runtime.core.AbstractApplicationBootstrapper - Revision: 7a518f1303ae26688437516128c295fd02f547c9
[JavaFX-Launcher] INFO org.codehaus.griffon.runtime.core.AbstractApplicationBootstrapper - JVM: 1.8.0_181 (Oracle Corporation 25.181-b13)
[JavaFX-Launcher] INFO org.codehaus.griffon.runtime.core.AbstractApplicationBootstrapper - OS: Mac OS X 10.14 x86_64
[JavaFX-Launcher] INFO griffon.util.AnnotationUtils - resource bundle loader class with instance org.codehaus.griffon.runtime.util.ClassResourceBundleLoader@68076fc9 evicted by org.codehaus.griffon.runtime.groovy.util.GroovyScriptResourceBundleLoader@23badcda
[griffon-pool-1-thread-1] INFO griffon.javafx.JavaFXGriffonApplication - Initializing all startup groups: [astBug]
[griffon-pool-1-thread-1] ERROR griffon.core.GriffonExceptionHandler - Uncaught Exception. Stacktrace was sanitized. Set System property 'griffon.full.stacktrace' to 'true' for full report.
griffon.exceptions.InstanceNotFoundException: Could not find an instance of type org.example.AstBugModel
        at griffon.javafx.AbstractJavaFXGriffonApplication.startup(AbstractJavaFXGriffonApplication.java:433)
        at griffon.javafx.JavaFXGriffonApplication.lambda$afterStart$0(JavaFXGriffonApplication.java:93)
Caused by: com.google.inject.ProvisionException: Unable to provision, see the following errors:

1) Error notifying InjectionListener org.codehaus.griffon.runtime.injection.GuiceInjectorFactory$$Lambda$57/1832630697@1e158cd1 of org.example.AstBugModel.
 Reason: griffon.exceptions.InstanceMethodInvocationException: An error occurred while invoking instance method org.example.AstBugModel.postConstructInitialization()
  while locating org.example.AstBugModel

1 error
        at com.google.inject.internal.InternalProvisionException.toProvisionException(InternalProvisionException.java:226)
        at com.google.inject.internal.InjectorImpl$1.get(InjectorImpl.java:1053)
        at com.google.inject.internal.InjectorImpl.getInstance(InjectorImpl.java:1086)
        ... 2 more
Caused by: griffon.exceptions.InstanceMethodInvocationException: An error occurred while invoking instance method org.example.AstBugModel.postConstructInitialization()
        at griffon.util.GriffonClassUtils.lambda$invokeAnnotatedMethod$0(GriffonClassUtils.java:3280)
        at griffon.util.GriffonClassUtils.invokeAnnotatedMethod(GriffonClassUtils.java:3272)
        at com.google.inject.internal.MembersInjectorImpl.notifyListeners(MembersInjectorImpl.java:131)
        at com.google.inject.internal.ConstructorInjector.provision(ConstructorInjector.java:125)
        at com.google.inject.internal.ConstructorInjector.access$000(ConstructorInjector.java:32)
        at com.google.inject.internal.ConstructorInjector$1.call(ConstructorInjector.java:98)
        at com.google.inject.internal.ProvisionListenerStackCallback$Provision.provision(ProvisionListenerStackCallback.java:112)
        at com.google.inject.internal.ProvisionListenerStackCallback$Provision.provision(ProvisionListenerStackCallback.java:120)
        at com.google.inject.internal.ProvisionListenerStackCallback.provision(ProvisionListenerStackCallback.java:66)
        at com.google.inject.internal.ConstructorInjector.construct(ConstructorInjector.java:93)
        at com.google.inject.internal.ConstructorBindingImpl$Factory.get(ConstructorBindingImpl.java:306)
        at com.google.inject.internal.InjectorImpl$1.get(InjectorImpl.java:1050)
        ... 3 more
Caused by: java.lang.NullPointerException: Cannot invoke method evaluate() on null object
        at org.example.AstBugModel.postConstructInitialization(AstBugModel.groovy)
        at griffon.util.GriffonClassUtils.lambda$invokeAnnotatedMethod$0(GriffonClassUtils.java:3276)
        ... 14 more
        ```
