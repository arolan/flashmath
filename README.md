# flashmath

This repository contains the source code for the Flash Math Android app. The app has a (very poorly built) backend [here](https://github.com/whoshuu/flashmathserver).

[![FlashMath](http://i.imgur.com/BhiuWgv.png)](http://github.com/arolan/flashmath) [![FlashMath](http://i.imgur.com/40qRQkK.png)](http://github.com/arolan/flashmath) [![FlashMath](http://i.imgur.com/Sjibrd4.png)](http://github.com/arolan/flashmath) [![FlashMath](http://i.imgur.com/IlT7TO4.png)](http://github.com/arolan/flashmath)

## License

* [MIT License](http://opensource.org/licenses/MIT)

## Building

This project uses gradle to build. There are currently no tests, so the two commands `gradle assemble` and `gradle build` currently both build the APK. In addition you'll need to set the `ANDROID_HOME` environment variable to the location of your SDK:

```bash
export ANDROID_HOME=/opt/tools/android-sdk
```

## Acknowledgments

This project makes use of the open source libraries:

* [ActiveAndroid](https://github.com/pardom/ActiveAndroid)
* [android-async-http](https://github.com/loopj/android-async-http)
* [android-oauth-handler](https://github.com/thecodepath/android-oauth-handler)
* [GraphView](https://github.com/jjoe64/GraphView)
* [scribe-java](https://github.com/fernandezpablo85/scribe-java)
* [Android-Universal-Image-Loader](https://github.com/nostra13/Android-Universal-Image-Loader)

## Contributing

Please fork this repository and contribute back using [pull requests](https://github.com/arolan/flashmath/pulls). Features can be requested using [issues](https://github.com/arolan/flashmath/issues). All code, comments, and critiques are greatly appreciated.
