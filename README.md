risetek.archetypes
====================
* This is from: https://github.com/tbroyer/gwt-maven-archetypes
* 在此表达对原作者的敬意，构造本项目的目的只是希望有一个自己需要的组合

### Supported archtypes
* modular-webapp:
* empty-webapp: GWT基本界面，以及RPC实现的Greeting服务
* empty-frame: requestFactory
* empty-frame-shiro: requestFactory, login/logout Place and Apache shiro
* dagger-guice-rf-activities
* gwtp-frame-shiro: GWTP and Shiro combination

### 下载并构造本maven包到本地repo中
* git clone https://github.com/cdrisetek/risetek.archetypes.git
* cd risetek.archetypes && mvn clean install

### 说明
* 如果server代码修改后，jetty没有重新开始（restart）,请检查Eclispe的设置，Project->Build Automatically应该为选中。
* -DarchetypeCatalog=local参数限制在本地搜寻archetypeGroupId，因此不会把时间浪费在不存在的网络资源搜寻上。
* 由于GWTP使用的是GIN，因此没有在dagger上下功夫。

### 项目构造
* 在eclipse下import-> Maven -> Existing Maven Projects，可以同时将三个项目纳入eclipse集成开发环境中。
* 也可以在生成的项目目录下执行：mvn eclipse:eclipse构造eclipse项目文件.project

### 调试
* 在一个终端（窗口）中运行: `mvn gwt:codeserver -pl client -am`
* 在另一个终端（窗口）中运行: `mvn -Djetty.port=8081 jetty:run -pl server -am -Denv=dev` 或者：`mvn -Djetty.port=8081 jetty:run -pl server -am -P env-dev`
* Chrome浏览器打开jetty服务的地址，比如：`http://127.0.0.1:8081`，而不是gwt:codeserver的地址
* 需要注意的是，gwt:codeserver存在退出后没有释放java进程的问题。比如关闭了bash窗口，java进程还存在，而且占用了调试端口，这个时候需要在`任务管理器`里找到这个java进程，手动清除，否则下一次运行gwt:codeserver会存在端口占用问题而无法执行。
* 调试脚本:
```
#!/bin/bash
set -e
mvn clean package
git-bash.exe -c "mvn jetty:run -pl server -am -Penv-dev" &
git-bash.exe -c "mvn gwt:codeserver -pl client -am" &
```

dagger-guice-rf-activities (A combination of dagger, Guice and GWT requestFactory, activities)
====================
#### 构造dagger-guice-rf-activities项目，需要输入自定义module名称，比如risetek.demo
```
mvn archetype:generate -DarchetypeCatalog=local -DarchetypeGroupId=com.risetek.archetypes \
  -DarchetypeVersion=HEAD-SNAPSHOT -DgroupId=com.risetek -DarchetypeArtifactId=dagger-guice-rf-activities
```
#### 增添新的requestFactory服务
#### server
* 实现Response对应的数据结构（Entity）
* 实现返回Response的服务

##### shared
* ${module}Factory中增加对应的服务上下文: RequestContext.
* ResponseProxy对应于服务端（server）代码中的Entity
* 实现RequestContext的extends，里面包含服务名，并返回Request<T>，<T>与ResponseProxy相关

#### 几个特殊文件的作用
* (client) AuthAwareRequestTransport.java
* (server) AjaxAuthenticationFilter.java
* User.java CurrentUser.java & ServerUser.java

#### 增添新的Place
* 实现一个Place的扩展
* ${module}PlaceHistortMapper需要更新，在@WithTokenizers中引入新增的Place对应的class
* MainActivityMapper需要增加处理该Place活动的代码

gwtp-frame-shiro (A combination of GWT, GWTP and Shiro)
====================
#### TODO
* 服务端AotoLoadModule的处理在增加一个新的模块时会失效，开发者必须重新编译整个项目，而不能依赖于jetty的restart过程。
* 如何实现一种模板配置的方式，通过声明需要的package来组织成初始代码。

#### User Subject and Project
* User: 用户
* Subject: The currently executing user, called a Subject.
* Project: 项目

#### Host Project
* 项目本身的设定
* 固定的角色设置，开发者需要在源码级别扩展

#### 构造gwtp-frame-shiro项目，需要输入自定义module名称，比如risetek.tools
```
mvn archetype:generate -DarchetypeCatalog=local -DarchetypeGroupId=com.risetek.archetypes \
 -DarchetypeArtifactId=gwtp-frame-shiro  \
 -DarchetypeVersion=HEAD-SNAPSHOT -DgroupId=com.risetek
```

#### NOTES
* client中的module.gwt.xml有<set-configuration-property name="gin.ginjector.modules" value="com.risetek.entry.ClientModule"/> 构建运行时（GWT compiler 运行时）采用google gin进行依赖注入。
* ClientModule在GWT compiler运行时install各种GinModule。这些被安装的GinModule又会进行其自身领域的依赖注入。
* 除了GWTP必要的GinModule外，开发者的AbstractPresenterModule也需要进行install。这部分如何实现自动化的安装呢？
* bind MyBootstrapper使得GWTP项目在浏览器载入界面运行的初期得以运行在浏览器中，它会启动与后台的用户授权信息同步过程。
* CurrentUser作为一个Singleton被及早实例化，以提供其它需求。
* 服务端shiro/MyAuthorizingRealm是管理用户权限的DAO（数据访问目标），如果后台需要实现不同的管理提供，需要修改这个部分的代码。

#### PresenterModuleMavenProcesser NOTES
* 设计PresenterModuleMavenProcesser是为了自动化转载用户编写的各种PresenterModule，这使得用户构建的新项目可以通过删除不需要的PresenterModule代码来达到自由组合的需要。
* 至少有一个用户的PresidentModule 需要 AutoLoadPresenterModule annotation。否则没有机会生成 MavenProcessedPresenterModuleLoader，造成构建失败。 

#### UI:Infinity Card List
* 实现了一个无止境的卡片布局容器，InfinityCardList只能实现一列的卡片布局方式。
* Card需要按照CSS响应式设计方式，当width发生变化的时候，通过变更height来适应外部Panel的width变化。这种单个Card的height变化造成的List布局变化会由InfinityCardList通过调整TOP值消化。

#### Authorization

#### Client handler Exception from Server
* 服务端执行Action产生的ActionException需要通过ActionExceptionMapper转换成可序列化的ActionException才能通过RPC过程传递到客户端。
* 客户端调用Action执行服务端程序如果出现异常，会回调onFailuer函数，ServerExceptionHandler帮助对这些服务端异常的通常处理，比如onFailure得到的服务端异常类型是ActionAuthenticationException，那么就前往UnauthorizedPlace，通常这是一个Login界面。
* 可序列化的ActionException在xxx.share.exception包中。
* ServerExceptionHandler类在client的utils.ServerExceptionHandler。

#### Service side initialization
* DevOpsTask提供了一种跟踪，记录服务端初始化的办法。
* 特别地，如果服务端没有提供合适的账户进行后续的作业，可以进入/services页面处理。

#### 部署
##### nginx
* 服务端需要得到浏览器访问的URL，因此部署时要考虑将这些信息真实地传递到服务端，比如在使用nginx的情况下，一个实际的配置例子：

```
server {
  listen 80;
  server_name devops.yun74.com;
  location / {
    proxy_pass http://localhost:19000;
    port_in_redirect off;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header Host $http_host;
  }
}
```
##### Docker
* 如果用Docker进行部署，部署服务器需要安装docker.io和docker-compose
* 用于部署的服务器账户需要具有sudo能力
* 用于部署的服务器账户执行sudo时如果需要输入密码，那么在部署期间需要多次输入这个密码。请阅读以下<strong>参考</strong>的内容以选择合适的方案。
* 部署完成后，在部署的工作区（WORKSPACE，由环境变量.env定义），存留完整的docker-compose.yml，可以用于管理。

##### 运行时
* 我们需要用上/.database目录，这是用于存放数据库文件的目录
* /config目录，是用于提供给microprofile额外（用户自定义）配置文件的目录
* 在没有创建账户的情况下，需要配置文件 /config/application.properties 的介入。用部署账户登录后，请及时创建有效的管理账户，然后删除配置文件中的deploy账户，以保障安全。如果出现忘记管理账户的情况，也可以通过临时加入deploy的方式加以挽救。

```
deploy.account=deploy
deploy.password=deploy
```
##### 参考
* 关于ssh remote 的sudo需要密码问题，请参考https://www.shell-tips.com/linux/sudo-no-tty-present-and-no-askpass-program-specified/

modular-webapp
======
#### 构造项目，自定义module名称，比如demo
```
mvn archetype:generate -DarchetypeCatalog=local \
 -DarchetypeGroupId=com.risetek.archetypes \
 -DarchetypeVersion=HEAD-SNAPSHOT \
 -DgroupId=com.risetek \
 -DarchetypeArtifactId=modular-webapp \
 -Dmodule=demo
```

empty-webapp
======
#### 构造项目，自定义module名称，比如demo
```
mvn archetype:generate -DarchetypeCatalog=local \
 -DarchetypeGroupId=com.risetek.archetypes \
 -DarchetypeVersion=HEAD-SNAPSHOT \
 -DgroupId=com.risetek \
 -DarchetypeArtifactId=empty-webapp \
 -DgroupId=com.risetek
```

Origin Document
==================================

### Start the development mode

Change directory to your generated project and issue the following commands:

1. In one terminal window: `mvn gwt:codeserver -pl *-client -am`
2. In another terminal window: `mvn tomcat7:run -pl *-server -am -Denv=dev`

The same is available with `tomcat6` instead of `tomcat7`.

Or if you'd rather use Jetty than Tomcat, use `cd *-server && mvn jetty:start -Denv=dev` instead of `mvn tomcat7:run`.

Note that the `-pl` and `-am` are not strictly necessary, they just tell Maven not to
build the client module when you're dealing with the server one, and vice versa.


### Profiles

There's a special profile defined in the POM file of server modules:
`env-dev`, which is used only when developping. It configures the Tomcat and Jetty
plugins and removes the dependency on the client module (declared in the `env-prod`
profile, active by default.)

To activate the `env-dev` profile you can provide the `-Denv=dev` system property, or
use `-Penv-dev`.
