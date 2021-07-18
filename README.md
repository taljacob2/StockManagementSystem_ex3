# rolling_ex_3 notes

## How to import the project:

1. Import this project from Git.

2. Open settings by pressing Ctrl+Alt+S and navigate to:
     Build, Execution, Deployment | Build Tools | Maven | Importing

3. Check JDK for Importer and change the exact version of JDK (example : 1.8.0.250)
    installed on your machine from the drop-down.
    The Maven "web" project should be recognized now.
    
4. Right click the "pom.xml" file and press "Maven -> Reimport".

5. Go to the Maven-bar and press "install".

6. The Project is now set!
   
______________
## Packaging:
> The project packages the application into a ".war" file named "web.war".

### Choose a location to the packaged "web.war" file:

 - _Package directly to your local Tomcat "webapps" folder:_
   > If you have a local Tomcat server, and wish to automatically copy the "web.war" file to its "webapps" folder, you can do so.
     - Navigate to the "pom.xml" file.
     - Change the path in line 138 to the path of your local Tomcat "webapps" folder, like so:
       ```sh
                    <outputDirectory>I:\Tal\Apache-Tomcat\Installation\webapps
                        <!-- Tomcat webapps location--></outputDirectory>
       ```
       - Afterwards, press CTRL+SHIFT+O to Load Maven Changes.
    
 - _Package to the project path of: `web/target/web.war`:_
   > If you wish to package the "web.war" file to an output folder in the project.
      - Navigate to the "pom.xml" file.
      - Disable the plugin in line 138, by commenting-out the line, like so:
        ```sh
        <!--                    <outputDirectory>I:\Tal\Apache-Tomcat\Installation\webapps-->
        <!--                        &lt;!&ndash; Tomcat webapps location&ndash;&gt;</outputDirectory>-->
        ```
        - Afterwards, press CTRL+SHIFT+O to Load Maven Changes.
   
### How to package:

Go to the Maven-bar and press "install" or "package" in order to package the project to the "web.war".
    
______________

Once the server is running, the URL path to the application is:

```sh
localhost:8080/web
```
______________
## Optional, but very recommended for production:
### Set dependencies for the "web" module:
> This would help you navigate to the ".java" files in the non-maven modules while coding.

- Navigate to: Project Structure | web 'module' | Dependencies 'tab' |
- Press the "+" in the top right corner, then select "Module Dependency..."
- Select the 3 modules: "engine", "ui", "javafx", and press "OK".
- Then, do as the following picture, and afterwards press "OK":

![web dependencies](dependencies.jpg?raw=true "web dependencies")
______________
## Known Issues Solutions:

#### Issue 1:
> Fixing issue when after importing of project from Git, there are some
  classes that are unrecognized in the "web" module:

_SOLUTION:_

1. Go to the class that is unrecognized.
2. Change the name of the class to something else, and then return it back to
    the original name.
3. Now all the imports are working perfectly!

  [FORUM LINK - liuyong](https://stackoverflow.com/questions/34778279/intellij-cant-find-classes-in-same-package-when-compiling) 

______________
###Compiling Non-Maven Modules:

#### Issue 2:
![compile non-maven](compile-non-maven.png?raw=true "compile non-maven")

![annotation processing solution](annotation-processing-solution.png?raw=true "annotation processing solution")

  
______________
#### Issue 3:
> Fixing issue when after making a dependency such that a non-maven .jar is
  dependent on the "web"-maven module.
  But the classes in the "web" module are still, unrecognized in the non-maven
  module :

_SOLUTION:_

1. Press twice: `CTRL CTRL` to show the "Run Anything" pop-up window.
2. Type the command: `mvn idea:idea` and run it. This would re-compile the
   `.idea` folder.
3. Now all the imports are working perfectly!

  [FORUM LINK - daemon_nio](https://stackoverflow.com/questions/20137020/package-doesnt-exist-error-in-intellij/61716663#61716663) 

______________
