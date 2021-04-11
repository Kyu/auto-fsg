# Auto-FSG

Runs a program from inside minecraft, parses the output for a seed and a verification code.  

*Note: This program runs an external executable in java, please make sure you know what's running before you run it*  

### Building  

`./gradlew build` generates a jar  
Requires `fabric-command-api-v1-xx.jar` and `fabric-api-base-xx.jar`, for one command, will be removed soon

#### Linux  
You will have to add `export LD_LIBRARY_PATH=/path/to/filteredseed/libs` to the end of your ~/.bashrc in order for it to work   

#### Windows  
Still figuring out how to compile for Windows...


### Editing  

Clone the repo, then follow the [fabric setup instructions](https://fabricmc.net/wiki/tutorial:setup)  

