## 🌸 Amaririsu

A synchronous, heavily customizable and efficient crawling library for ScribbleHub, created with developer experience in mind. Amaririsu is the successor 
to the old and cluster of mess [Amatsuki](https://github.com/ShindouMihou/Amatsuki) that was written years back.

#### 🍶 Asynchronous or Synchronous?

Amaririsu is intentionally a synchronous yet thread-safe library to give developers more flexibility in how they use the library whether 
they'd want it to be asynchronous with futures or coroutines or as a synchronous library by itself.

#### 🔮 Cachy

Amaririsu, by default, has no cache enabled but can support caching by setting the cache in the Amaririsu class which enables developers to bring their 
cache whether it'd be with Redis or whether it'd be a simple in-memory cache.

#### 🧰 Customizable

You can customize the library as much as possible from changing its cache module to changing how it sends requests to ScribbleHub. All customization
options will be available from the Amaririsu class. It's customizability also extends per-request with the ability to customize the connector to even 
excluding properties such as synopsis, tags and genres on series or excluding users or series from search results.

## 💝 Usage

Amaririsu's usage is straightforward and simple without any roundabouts. All methods should be self-explanatory and documented to help developers 
understand the need or use for the method.

> 👤 User Profiles
>
> Requesting a user's profile is as simple as running the following line:
>
> ```kotlin
> Amaririsu.user("https://www.scribblehub.com/profile/24680/mihou/")
> ```

> 🖋️ Series
>
> You can request a series' information by simply running the following line:
>
> ```kotlin
> Amaririsu.series("https://www.scribblehub.com/series/299262/the-vampire-empress/")
> ```
> 
> You can customize these requests to exclude properties such as synopsis, tags and genres to have a smaller memory footprint 
> by simply excluding them in the options such as:
> 
> ```kotlin
> Amaririsu.series("https://www.scribblehub.com/series/299262/the-vampire-empress/") { 
>   includeSynopsis = false
>   includeTags = false
>   includeGenres = false
> }
> ```

> 🔬 Searching
> 
> You can search for both series and users by simply running the following line:
> 
> ```kotlin
> Amaririsu.search("Mihou")
> ```
> 
> You can customize this to a certain extent such as only including users by excluding what should be excluded in the
> options such as:
> 
> ```kotlin
> Amaririsu.search("Mihou") { 
>   series.enabled = false
> }
> ```