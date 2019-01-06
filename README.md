risetek.archetypes
====================
* This is from: https://github.com/tbroyer/gwt-maven-archetypes
* �ڴ˱���ԭ���ߵľ��⣬���챾��Ŀ��Ŀ��ֻ��ϣ����һ���Լ���Ҫ�����

### ���ز����챾maven��������repo��
* git clone https://github.com/kerongbaby/risetek.archetypes.git
* cd risetek.archetypes && mvn clean install

### ������Ŀ��ȱʡmodule����ΪApp
	mvn archetype:generate -DarchetypeCatalog=local \
	 -DarchetypeGroupId=com.risetek.archetypes \
	 -DarchetypeVersion=HEAD-SNAPSHOT \
	 -DgroupId=com.risetek \
	 -DarchetypeArtifactId=modular-webapp

### ������Ŀ���Զ���module���ƣ�����demo
	mvn archetype:generate -DarchetypeCatalog=local \
	 -DarchetypeGroupId=com.risetek.archetypes \
	 -DarchetypeVersion=HEAD-SNAPSHOT \
	 -DgroupId=com.risetek \
	 -DarchetypeArtifactId=modular-webapp \
	 -Dmodule=demo

### ˵��
* -DarchetypeCatalog=local���������ڱ�����ѰarchetypeGroupId����˲����ʱ���˷��ڲ����ڵ�������Դ��Ѱ�ϡ�

### ��Ŀ����
* ��eclipse��import-> Maven -> Existing Maven Projects������ͬʱ��������Ŀ����eclipse���ɿ��������С�
* Ҳ���������ɵ���ĿĿ¼��ִ�У�mvn eclipse:eclipse����eclipse��Ŀ�ļ�.project

### ����
* ��һ���նˣ����ڣ�������: `mvn gwt:codeserver -pl client -am`
* ����һ���նˣ����ڣ�������: `mvn jetty:run -pl server -am -Denv=dev` ���ߣ�`mvn jetty:run -pl server -am -P env-dev`
* Chrome�������jetty����ĵ�ַ�����磺`http://127.0.0.1:8080`��������gwt:codeserver�ĵ�ַ

==================================

#### ������ԭʼ�ĵ�
    mvn archetype:generate \
       -DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/ \
       -DarchetypeGroupId=net.ltgt.gwt.archetypes \
       -DarchetypeArtifactId=<artifactId> \
       -DarchetypeVersion=1.0-SNAPSHOT

where the available `<artifactIds>` are:

* `modular-webapp`
* `modular-requestfactory`
* `guice-rf-activities`

This uses the snapshot deployed to Sonatype OSS. Alternatively, and/or if you want to
hack on / contribute to the archetypes, you can clone and install the project locally:

    git clone https://github.com/tbroyer/gwt-maven-archetypes.git
    cd gwt-maven-archetypes && mvn clean install

You'll then use the `mvn archetype:generate` command from above, except for the
`-DarchetypeCatalog` argument which you'll remove, as you now want to use your local
catalog.

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
