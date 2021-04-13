# Auto-FSG

For Fabric 1.16.1

[Made for the filteredseed program](https://github.com/hube12/FSG)   
Runs the program from inside minecraft, parses the output for a seed and a verification code.

**Note: This program runs an external executable in java, please make sure you know what's running before you run it.
It's highly recommended that you build it yourself using the instructions in the link**

[Demo video here(mic volume is low, sorry)](https://streamable.com/hcjgu3)

## Building

`./gradlew build` generates a jar

### Linux
You will have to add `export LD_LIBRARY_PATH=/path/to/filteredseed/libs` to the end of your ~/.profile in order for it to work  
You can also run the make_static.sh and it should work as well without any .profile change.

### Windows
Install WSL2 using the manual directions [here](https://docs.microsoft.com/en-us/windows/wsl/install-win10), restart a couple times, set up WSL,
build your exec using the instructions in the repo above and paste into mods/autofsg   

### Grabbing from replit  
A demo video is [here](https://streamable.com/bsrvdj) but basically you want to use https://bashupload.com/ to upload to 
yourself from the replit shell.  


## Running
Take the jar from `build/libs/..` after building, paste in your mod folder alongside the fabric modules. Paste a copy of your `seed` executable in `.minecraft/mods/autofsg`.  
Run minecraft, click AutoFSG, and wait.

I have copies of executable in [the releases](https://github.com/Kyu/auto-fsg/releases/latest), not sure if im even allowed to do that
but keep in mind they won't be the latest version.


## Editing

Clone the repo, then follow the [fabric setup instructions](https://fabricmc.net/wiki/tutorial:setup)  

