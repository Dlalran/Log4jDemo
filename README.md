# Log4j

​		Apache的开源项目[Log4j](https://logging.apache.org/log4j/2.x/)是一个功能强大的日志组件,提供方便的日志记录。

---

## 加入依赖

​		通过maven引入依赖或加入jar包。

```xml
<dependency>
	<groupId>log4j</groupId>
	<artifactId>log4j</artifactId>
	<version>${log4j.version}</version>
</dependency>
```

​		如果通过slf4j结合log4j进行使用，则添加以下依赖代替上面的依赖

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>${slf4j-log4j12.version}</version>
</dependency>
```

---

## 配置

​		Log4j的默认配置文件是`log4j.properties`，其中对于日志的输出级别、输出格式、输出位置等信息进行配置。

​		第一个配置内容是`log4j.rootLogger=[日志级别],[附加器1],[附加器2]...`(旧的使用方法是`log4j.rootCategory`)，其指定需要输出的最低日志级别(即设置级别之上的日志信息才会被输出)，以及使用的附加器名字，附加器的设置在其下一一给出，且名字必须准确对应。

#### 级别

​		为了方便对于日志信息的输出显示，对日志内容进行了分级管理，便于控制日志信息输出内容及输出位置。日志级别由高到低，共分 8个级别(一般使用以下中间的四个)：

- ~~OFF~~（关闭）
- FATAL（导致系统无法继续运行的致命错误）
- ERROR（不影响系统运行的错误）
- WARN（潜在的错误信息）
- INFO（运行过程中的重要信息）
- DEBUG（对调试有帮助的运行信息）
- TRACE（每个行动都被记录）
- ~~ALL~~（全部）

#### 附加器

​		附加器(appender)即对于日志的记录附加输出设置，其本质是一个接口，通过`log4j.appender`进行配置，有下列4中配置内容：

- `log4j.appender.[附加器名]` 指定输出实现类，有以下四种实现类

  - `org.apache.log4j.ConsoleAppender`：输出到控制台

  - `org.apache.log4j.FileAppender`：输出到文件

  - `org.apache.log4j.RollingFileAppender`：超出一定大小则产生一个新日志文件

    ​		可以通过`MaxFileSize`设置最大日志文件大小，`MaxBackupIndex`设置保存最大日志数量。

  - `org.apache.log4j.DailyRollingFileAppender`：每天产生一个日志文件(*不能设置上述两个属性*)

  - `org.apache.log4j.WriterAppender`：以流格式输出到任意地方

- `log4j.appender.[附加器名].File` 指定输出日志文件的路径名与文件名

- `log4j.appender.[附加器名].layout` 指定输出的布局类型

  - `org.apache.log4j.HTMLLayout`：以 HTML 表格形式布局
  - `org.apache.log4j.SimpleLayout`：简单布局，包含日志信息的级别和信息字符串
  - `org.apache.log4j.TTCCLayout`：包含日志产生的时间、线程、类别等等信息
  - `org.apache.log4j.PatternLayout`：格式化输出，通过`ConversionPattern `指定输出格式，有如下参数
    - `%m`：输出代码中指定的消息
    - `%M`: 输出执行方法
    - `%p`：输出优先级
    - `%r`：输出自应用启动到输出该 log 信息耗费的毫秒数
    - `%c`：输出定义该Logger的类的全类名
    - `%C`：输出调用该Logger的类的全类名
    - `%t`：输出产生该日志事件的线程名
    - `%n`：输出一个回车换行符
    - `%d`：输出日志时间点的日期或时间，默认格式为 ISO8601，也可以在其后指定格式，如`%d{yyy MMM dd HH:mm:ss , SSS}`
    - `%l`： 输出日志事件的发生位置，包括类目名、发生的线程，以及在代码中的行数。
    - 在%和字符中间还可以指定该信息的长度，如`%20c`则长度小于20则左对齐，`%-20c`则是右对齐，`%.20`则是超过20部分截去，`%c{2}`则是仅显示最内层算起2层的类名。*详细见[参数说明](https://www.cnblogs.com/cnsdhzzl/p/7442144.html)*

###### 示例配置

```properties
log4j.rootLogger=INFO, console, file

log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d %p [%c] - %m%n

log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=logs/log.log
log4j.appender.file.MaxFileSize=1024KB
log4j.appender.file.MaxBackupIndex=10
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d %p [%c] - %m%n
```

---

## 使用

#### 获得Logger

- 通过log4j直接获得log4j的Logger类

```java
private static final Logger logger = Logger.getLogger(TestLog4j.class);
```

- 通过slf4j的工厂类获得slf4j的Logger类，该类实际上通过log4j进行日志操作

``` java
private static final Logger logger = LoggerFactory.getLogger(TestLog4j.class);
```

#### 输出日志信息

```java
public static void main(String[] args) {
    logger.debug("Debug message");
    logger.info("Info message");
    logger.warn("Warn message");
    logger.error("Error message");
}
```

​		*通过slf4j获得的Logger可以通过占位符方式对日志内容进行凭拼接，在需要拼接变量的地方用`{}`代替，例如*

```java
String msg = "Test message";
logger.info("The message is {}", msg);
```

