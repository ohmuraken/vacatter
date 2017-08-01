# Android-Kotlin architecture
In this sample app we use the [Clean Architecture](https://blog.moove-it.com/the-cilean-architecture-on-android/) approach on Android, using Kotlin as a programming language.

It is based on [Uncle Bob's sample app](https://github.com/android10/Android-CleanArchitecture) — and on [Moove-it's sample app](https://github.com/moove-it/android-arquitecture-template) — with some structural changes:

- Uncle Bob uses one entity per layer. Here, each entity is commonly shared by the three layers. We believe that our approach suits projects that don't require such level of bureaucracy.
- As in Uncle Bob's proposal, we divided the folder structure by layers. However, we decided to subdivide the presentation folder by functionality. We believe that this approach makes it easier to find the implementation of a particular use case or functionality.

## Contributing
See the [contribution guide](CONTRIBUTING.md).
