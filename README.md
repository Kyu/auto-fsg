# Auto-FSG  

[Made for the filteredseed program](https://github.com/Kyu/filteredseed)   
The link above is my fork of AndyNovo's work, I just extracted out the files from repl.it for easier local compilation. I haven't changed anything.

Runs the program from inside minecraft, parses the output for a seed and a verification code.  

**Note: This program runs an external executable in java, please make sure you know what's running before you run it**  

## Building  

`./gradlew build` generates a jar  

### Linux  
You will have to add `export LD_LIBRARY_PATH=/path/to/filteredseed/libs` to the end of your ~/.profile in order for it to work   

### Windows  
Still figuring out how to compile for Windows...  

## Running  
Take the jar from `build/libs/..` after building, paste in your mod folder alongside the fabric modules. Paste a copy of your `seed` executable in `.minecraft/mods/autofsg`.  
Run minecraft, click AutoFSG, and wait.


## Editing  

Clone the repo, then follow the [fabric setup instructions](https://fabricmc.net/wiki/tutorial:setup)  

