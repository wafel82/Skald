# Skald

Skald is simple, plugable logging framework for **Kotlin** :rocket:. It's designed to be super-light in the term of size and runtime performance impact. This makes it a good choice for Android platform. 

## How to get it?
Skald - and its core plugins - is available on jCenter repository. To get main Skald library - simply add following line to your build.gradle:
```gradle
implementation 'com.wojtek.wawerek:skald-kotlin:0.2.0' 
```

## How to use it?
Skald usage consists of two stages - configuration and adding actual logging entry points in you implementation.

### Configuration
Skald configuration leverages beautiful Kotlin's type-safe builders semantic to provide fluent, easy to use, understand and maintain configuration api.
Here's example Skald configuration statement:
```Kotlin
 skald {
    writeSaga {
        toStandardOut {  }
        withLevel { DEBUG }
        withPath { "com.wafel.skald.demo" }
        withPattern { "${it.simplePath} -> ${it.message}" }
    }

    writeSaga {
        ...
    }
 }
```
#### What does this code do?
Skald configuration consist of following main sections:
* **`skald{...}`** - This is where the whole magic begins. Such code block should be executed at the very beginning of your application. All logging statements executed before configuration definition will be silently disabled. If you are developing for Android platform - then Application's `onCreate` method is good place to put skald configuration. Notice that you do not have to maintain any Skald related instance - simply execute `skald {...}` method and you are ready to log your stuff.

* **`writeSaga {...}`** - This method defines what should be logged, where the logs should be placed and how the log messages should be constructed. Here are available configuration options:
  * **`withPath {...}`** - Use this method to provide path to be used with this saga. It will checked against each logger path - whenever logger path start with saga path then such saga will be used by this logger.
  * **`withLevel { DEBUG }`** - Use this method to provide log level to be used by this saga. It will checked against each log entry point level - whenever log entry level is equal or lower than saga level, then such saga will be used with this logger.
  * **`withPattern {}`** - Use this method to configure how the output log message should be constructed.
  * **`toStandardOut {  }`** - Use this method to redirect log messages to standard output. Other `to... {}` methods are available as Skald extensions plugins.
  

### Logging from your code
Once Skald is configured you are ready to log your messages. Here's simple example presenting how it usually looks like on Android:
```Kotlin
class MainActivity : AppCompatActivity() {
    private val logger = createLogger(this.javaClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logger.debug("Hello Skald!")
    }
}
```
First you need to create logger instance. Skald provides convenient method to create logger based on class using it. With this method, created logger will be initialized with path equal to fully qualified name of the class you provided. This path will be checked against each saga path - whenever logger path start with saga path then such saga will be used by this logger.

When logger is created, you can simply use it to log your messages. All log messages will be forwarded to path-matching saga's and logged according to their configuration.

## Plugins
Skald's main purpose is to provide lightweight and simple logging framework. To make it possible, Skald's core implementation needs to stay small and generic.
However, it's often good to have logs integrated in to platform specific loggers or industry standard frameworks. 
To address those needs, Skald provide plugable extensions. They can be simply added to your build configuration as separate libraries. Each Skald plugins add its specific logging capabilities together with additional configuration options available as methods able to be called in `skald {}` lambda body.

Here are list of available plugins:

| name | description | latest version |
|------|-------------|---------------|
| skald-logcat-plugin | Android specific plugin redirecting log messages to Logcat | 0.1.0 |







