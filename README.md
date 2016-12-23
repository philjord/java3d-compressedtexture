# java3d-compressedtexture

Some draft instructions to make this run

The classpath in the repo won't work straight from a clone it relies on non public projects.

jre1.8.0_66a, awtshim and awtgeom are projects for making java/android cross over code, but if you're just working on desktop you can:

In the Java build path
In the libraries tab replace the odd jre1.8.0_66a with the workspace default (e.g. jdk1.8.0_25)
In the Projects tab
Remove the awtgeom and awtshim projects, and change java3d-core-and for java3d 1.7.0pre1 here:
https://github.com/philjord/java3d-core/releases

(Make sure you also have the 1.7.0pre1 of utils, vecmath and examples too)

https://github.com/philjord/java3d-utils/releases

https://github.com/philjord/vecmath/releases

https://github.com/philjord/java3d-examples/releases


Use global find and replace to replace
import javaawt.
with import java.awt.

Note this will only work with the Gl2es2Pipeline as the pipeline method
updateTexture2DImage must support the incoming compressed formats
See the examples project for how to use this pipeline and how to use SimpleShaderAppearance to create shaders for rendering

Alternatively you can edit Java3d 1.6.0 final release 

https://github.com/hharrison/java3d-core/releases

JoglPipeline class updateTexture2DImage method to support the compressed textures without having to use shaders, though I'd suggest using the Gl2es2pipeline in 1.7.0 as it's much faster.


TextureRetained complains incorrectly about mip map levels; if you use a mipmapped compressed file (like most dds) that doesn't bother with the last single pixel high or wide mip maps

see here for what needs to be changed, it is very simple
https://jogamp.org/bugzilla/show_bug.cgi?id=1332



Finally, note this project contains un-attributed source code from other projects so please don't use it anywhere near a GPL project :) .
