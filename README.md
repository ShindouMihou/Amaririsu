# 🌸 Amaririsu
The successor of the [Amatsuki](https://github.com/ShindouMihou/Amatsuki) library with a more updated, faster and simpler codebase.
Amaririsu or Amaryllis is an asynchronous, reflective simple scraper for ScribbleHub written in Java.

## 🍱 Purpose
Amaririsu's purpose is to be able to scrape public data out of ScribbleHub with the prime exception of chapters. You are allowed to
 use Amaririsu for any non-malicious purposes under the Apache 2.0 license, the developers and contributors are not responsible for
 any creations that uses this library.

## 🥪 Usage
Amaririsu aims to be as simple as possible and following after [Jaikan](https://github.com/ShindouMihou/Jaikan), also aims to be 
less resource-intensive as possible by allowing custom models to be used to only contain what is needed by your application. An example of a custom 
model would be the [Story.class](https://github.com/ShindouMihou/Amaririsu/tree/master/src/main/java/pw/mihou/amaririsu/models/Story.java).

> 🔴 Amaririsu is still under development and is not ready to be used. Please come again later once completed.

## 🍜 Contributing
You can contribute to Amaririsu as you wish, but ensure that the quality of the code is sufficient. Amaririsu follows a simple facade-core 
structure wherein any static or public methods has to be exposed only in the facade and any internal methods has to be inside the core. Please also 
remember that Amaririsu makes heavy use of Reflections, if you are not knowledgeable of Reflections then it is recommended to learn about it first before
 contributing to the library.

Amaririsu will also use the following libraries:
- **__JSoup__**: For parsing and connecting to ScribbleHub.
- **__SLF4J__**: For logging data.

You are also encouraged to know about multi-threading, asynchronous and lambdas as Amaririsu's internal code will utilize them heavily.

## 📚 License
Amaririsu follows Apache 2.0 license which allows the following permissions:
- ✔ Commercial Use
- ✔ Modification
- ✔ Distribution
- ✔ Patent use
- ✔ Private use

The contributors and maintainers of Amaririsu are not to be held liability over any creations that uses Amaririsu. We also forbid trademark use of
the library and there is no warranty as stated by Apache 2.0 license. You can read more about the Apache 2.0 license on [GitHub](https://github.com/ShindouMihou/Amaririsu/blob/master/LICENSE).