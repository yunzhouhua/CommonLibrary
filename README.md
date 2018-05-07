# CommonLibrary使用说明书

该工具类主要结合日常项目中的需求，对于一些项目中比较通用的，常用的功能做了归类整理，封装在这个CommonLibrary中，方便后期项目的快速开发。

该工具库中包含一下功能：

1. 网络请求
2. 图片异步加载
3. 路由跳转
4. 缓存
5. 常用工具库
   1. 本地数据清理
   2. 日期转换
   3. 加密（MD5,Base64）
   4. 文件操作
   5. 图片操作
   6. 应用包信息获取
   7. 尺寸转换
   8. 正则匹配
   9. 屏幕尺寸获取
   10. 字符串操作
   11. Toast操作
6. 常用View组件




## 使用配置

```groovy
// 主工程的build.gradle
allprojects {
    repositories {
        ...
        maven {
            url "https://jitpack.io"
        }
        ...
    }
}

// module中的build.gradle
dependencies {
    ...
    implementation 'com.txws.sdk:wxview:版本号'
}
```



下面具体介绍下各个功能的使用以及注意事项：

## 一.网络请求

网络请求是基于OKHttp3做了二次封装，提取了Request,Response中共性的东西，方便扩展去适配不同的业务需求，并且使用链式变成的方式，可用一行代码即可实现网络请求的功能。

使用前需要对网络请求功能有一个初始化的工作

#### 初始化网络请求

```java
Http.init(context);
```



目前提供了Get与Post的请求方式

#### Get请求

get请求只支持key=value&key=value的参数请求方式，因此用法比较单一，如下所示：

```java
Http.get()
    .url(url路径)
    .addHeader(key, value)	// 添加请求头
    .addParam(key, value)	// 添加请求参数
   	.execute(new StringCallBack() {	//返回结果用String接收
       @Override
       protected void onFailed(@NonNull HttpError error) {
			// todo 请求失败
       }
       
       @Override
       protected void onSuccess(long id, String result) {
			// todo 请求成功
       }
   });
```

#### Post请求

post请求的请求参数类型比较多样，可能是key=value&key=value的形式，也可能是json的形式，也可能是普通文本，甚至是二进制流。本库提供的PostRequest支持了各种情况的请求需求，配置比较灵活，本库将post的请求的请求数据归类为如下三种形式

​	1.key=value

​	2.单一数据流

​	3.复合数据流



##### key=value

key=value的请求方式与Get请求基本一致，还是通过addParam(key, value)的方式去添加请求参数，代码如下：

```java
Http.post()
    .url(url路径)
    .addHeader(key, value)	// 添加请求头
    .addParam(key, value)	// 添加请求参数
   	.execute(new StringCallBack() {	//返回结果用String接收
       @Override
       protected void onFailed(@NonNull HttpError error) {
			// todo 请求失败
       }
       
       @Override
       protected void onSuccess(long id, String result) {
			// todo 请求成功
       }
   });
```



##### 单一数据流

单一数据流可以是json，二进制流等等，通过设置type来指定数据的类型，如果不设置type类型，则默认使用**“text/plain;charset=UTF-8”**来处理，代码如下：

```java
Http.post()
    .url(url路径)
    .stream(数据流)	// 数据流可以为String,byte[],File
   	.execute(null);
```


***注意：stream方法只可使用一次，多次使用，后面的流会覆盖前面的流，且设置了stream方法后，addParam与addPart方法设置的内容都无效**



##### 复合数据流

复合数据流是对前两种情况的整合，当请求参数中同时存在普通key=value,以及key=value中的value是数据流的情况时，使用这种方式进行请求，底层其实就是将请求参数添加到MultipartBody中

```java
Http.post()
    .url(url路径)
    .addPart(key, value) // value可以为String,byte[],File, 数据流默认为“text/plain;charset=UTF-8”
    .addPart(key, value, type)
   	.execute(null);
```



#### 使用场景

##### post提交Json数据

现在有些后天提供的接口需要使用json来发送请求数据，上述示例可以通过如下改进，实现json请求
```java
Http.post()
    .url(url路径)
    .stream("application/json;charset=UTF-8", 数据流)	// 数据流可以为String,byte[],File
   	.execute(null);
```



##### 文件下载（可监听下载进度）

库中实现了文件下载的回调FileCallBack，可实现文件下载请求的处理，同时也包含了进度监听的功能

```
Http.get()
    .url(文件网络路径)
    .execute(new FileCallBack(文件本地路径, 文件名) {
        @Override
        protected void onFailed(@NonNull HttpError error) {
        	// todo 文件下载失败
        }

        @Override
        protected void onSuccess(long id, File result) {
        	// todo 文件下载成功
        }

        @Override
        public void updateProgress(long id, long current, long total) {
        	// todo 进度监听
        	// 如果需要监听下载进度，实现这个方法，进度为current/total
        	// 如果不需要监听下载进度，不需要实现这个方法
        }
    });
```



##### 文件上传（可监听上传进度）

```
Http.post()
    .url(HOST + "fileUploadPage")
    .stream(file)
    .execute(new StringCallBack() {
        @Override
        protected void onFailed(@NonNull HttpError error) {
        	// todo 文件上传失败
        }

        @Override
        protected void onSuccess(long id, String result) {
        	// todo 文件上传成功
        }

        @Override
        public void updateProgress(long id, long current, long total) {
        	// todo 进度监听
        	// 如果需要监听上传进度，实现这个方法，进度为current/total
        	// 如果不需要监听上传进度，不需要实现这个方法
        }
    });
```



#### 定制化

##### cookie管理

cookie使用三种管理方式,默认使用CookieType.FILE

| 常量              | 描述                                 |
| ----------------- | ------------------------------------ |
| CookieType.NONE   | 不使用Cookie                         |
| CookieType.MEMORY | Cookie保存在内存中                   |
| CookieType.FILE   | Cookie保存在文件中，ShredPreferences |

设置cookie管理方式，需要在Http请求功能初始化的时候操作

```java
HttpConfig config = new HttpConfig();
config.setConnectTimeOut(CookieType.MEMORY);	// 设置Cookie存储在内存中

Http.init(this, new OKHttpExecutor(), config);
```



##### 超时管理

超时管理有以下三种情况，分别为链接超时，读超时，写超时

| 超时           | 描述     | 默认值 |
| -------------- | -------- | ------ |
| readTimeOut    | 读超时   | 10s    |
| writeTimeOut   | 写超时   | 10s    |
| connectTimeOut | 链接超时 | 10s    |

###### 全局设置

全局设置即对所有网络请求有效，需要在请求功能初始化的时候进行设置

```java
HttpConfig config = new HttpConfig();
config.setReadTimeOut(30000);		// 设置读超时，30s
config.setWriteTimeOut(30000);		// 设置写超时，30s
config.setConnectTimeOut(30000);	// 设置链接超时，30s

Http.init(this, new OKHttpExecutor(), config);
```

###### 局部设置

局部设置仅对当前设置的网络请求有效

```java
Http.post()
    .url(请求路径)
    .setConnectTimeout(30000)	// 设置读超时，30s
    .setReadTimeout(30000)		// 设置写超时，30s
    .setWriteTimeout(30000)		// 设置链接超时，30s
    .execute(null);
```



##### SSL管理

###### 单向认证

```java
// 假设证书存放于res/raw目录下
InputStream ca = getResources().openRawResource(R.raw.ca);
SSLParams sslParams = new SSLParams(new InputStream[]{ca}, null, null);

HttpConfig config = HttpConfig.getDefaultConfig();
config.setSslParams(sslParams);

Http.init(this, new OKHttpExecutor(), config);
```

###### 双向认证

```java
InputStream ca = getResources().openRawResource(R.raw.ca);
InputStream bks = getResources().openRawResource(R.raw.bks);
String password = "123456";
SSLParams sslParams = new SSLParams(new InputStream[]{ca}, bks, password);

HttpConfig config = HttpConfig.getDefaultConfig();
config.setSslParams(sslParams);

Http.init(this, new OKHttpExecutor(), config);
```

###### 全局设置

全局设置同上述单向认证/双向认证是一致的

###### 局部设置

局部设置是的他对当前单个请求有效

```java
// 假定这边使用的是双向认证
InputStream ca = getResources().openRawResource(R.raw.ca);
InputStream bks = getResources().openRawResource(R.raw.bks);
String password = "123456";
SSLParams sslParams = new SSLParams(new InputStream[]{ca}, bks, password);

Http.post()
    .url("")
    .ssl(sslParams)
    .execute(null);
```



## 二.图片异步加载

图片异步加载使用的是Glide3，由于Glide的链式编程实现已经让图片异步请求很简单了，一行代码就能搞定，所以，没有再对Glide做进一步的封装，唯一修改的是Glide的网络请求方式，替换成了OkHttp3，因此使用该库，可以通过常规习惯使用Glide就可以了。

再图片加载中我们经常会使用到一些图片转换的功能，比如圆角图片，黑白图片，高斯模糊等等，下面推荐一个比较全面的针对Glide的Transform库

https://github.com/wasabeef/glide-transformations





## 三.路由跳转

路由跳转是自己对Activity跳转的封装，遵循链式编程的规则，使用一行代码即可支持支持显式的Activity跳转，同时也支持遵循scheme协议的跳转。

##### 显式Activity跳转

```java
RouteManager.target(context, Activity.class).go();
```

##### shceme跳转

```java
String scheme = "scheme";
String host = "host";
String path = "/path";

// 方式一
RouteManager.target(context, scheme, host, path).go();
// 方式二
Uri uri = Uri.parse(scheme + "://" + host + path);
RouteManager.target(context, uri).go();
```

##### startForResult

```JAVA
RouteManager.target(this, Activity.class)
    .forResult(requestCode)	// 设置请求码
    .go();
```

##### 参数传递

```java
RouteManager.target(this, Activity.class)
    .addInt("int", 12)					// int类型
    .addFloat("float", 12.6f)			// float类型
    .addLong("long", 123l)				// long类型
    .addDouble("dounle", 345d)			// double类型
    .addBoolean("boolean", true)		// boolean类型
    .addSerializable("serializable", null)	// serializable类型
    .addString("string", null)			// String类型
    .addBundle(bundle)					// bundle
    .go();
```

##### Flags

对于一些有特定需求的Intent跳转，会有需要设置Flag的情况

```java
RouteManager.target(this, Activity.class)
    .addFlags(FLAG_ACTIVITY_NEW_TASK)	// 增加Flag
    .go();

RouteManager.target(this, Activity.class)
    .setFlags(FLAG_ACTIVITY_NEW_TASK)	// 设置Flag
    .go();
```

##### 设置过场动画

路由跳转模块同时也支持设置页面跳转的转场动画设置。

```java
RouteManager.target(this, Activity.class)
    .pendingTransition(animIn, animOut)	// animIn,animOut都是对应的xml资源Id
    .go();
```

模块中同时定义了几种比较常用的页面跳转动画

```java
// 实现从左往右平移Activity跳转
RouteManager.target(this, Activity.class).goLeftSlide();

// 实现从右往左平移Activity跳转
RouteManager.target(this, Activity.class).goRightSlide();

// 任务过场进行Activity跳转
RouteManager.target(this, Activity.class).goSilence();
```



这边路由跳转只是对android中startActivity，startActivityForResult做了简单的封装，如果项目中使用了模块化的思想，这样的路由跳转还是依赖于context,以及目标Activity的.class对象的引用，耦合度还是比较高的。因此这边也强力推荐以下阿里巴巴出品的ARouter，**真的很好用！**

https://github.com/alibaba/ARouter



## 四.缓存

对于缓存功能，这边主要提供了两种方式，一个是ACache(前几年比较火的本地持久化缓存框架)，另一个是基于Android原生提供的SharedPreferences的封装

#### ACache

```java
ACache cache = ACache.get(this);
cache.put("acache", "acache");
String result = cache.getAsString("acache");
```

Acache的用法很简单，通过ACache的静态方法get获取一个ACache对象，然后调用对象的put/get方法就可以实现数据的存储与读取操作了。



#### SharedPreferenceUtils

SharedPreferenceUtils是对Android API中的SharedPreferences的封装，它内部对数据进行了分类，分为全局以及非全局，做这样子的区分是遇到过这样子的业务场景，当App更新的时候，部分sharedPreferences中存储的信息需要被清空，而部分又需要保留下来，这时候这种全局以及非全局的分类就可以很好的满足这样的业务场景。

##### 存储

```java
// 非全局存储
SharedPreferenceUtils.save(context, key, value);
SharedPreferenceUtils.save(context, key, value, false);

// 全局存储
SharedPreferenceUtils.save(context, key, value, true);
```

##### 获取

```java
// 非全局获取
SharedPreferenceUtils.get(context, key, value);
SharedPreferenceUtils.get(context, key, value, false);

// 全局获取
SharedPreferenceUtils.get(context, key, value, true);
```

##### 删除

```java
// 删除某个key的记录
// 非全局
SharedPreferenceUtils.remove(context, key);
SharedPreferenceUtils.remove(context, key, false);

// 全局
SharedPreferenceUtils.remove(context, key, true);


// 清空所有
// 非全局
SharedPreferenceUtils.clean(context);
SharedPreferenceUtils.clean(context, false);

// 全局
SharedPreferenceUtils.clean(context, true);
```



## 五.常用工具库

参考com.yunzhou.libcommon.utils中的代码





## 六.常用View组件

参考com.yunzhou.libcommon.views中的代码

