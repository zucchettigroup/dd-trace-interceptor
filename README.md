[![Java CI with Maven](https://github.com/zucchettigroup/dd-trace-interceptor/actions/workflows/maven.yml/badge.svg)](https://github.com/zucchettigroup/dd-trace-interceptor/actions/workflows/maven.yml)

# dd-trace-interceptor

This project aims to extend the database tags of a query type span in Datadog. We would show which and how a single service uses the resources of a shared SQL Cluster.

To achieve the goal, we need to do the following tasks for each query span:
1. Convert a DNS alias (*peer.hostname*) into the real SQL cluster name (*db.cluster*)
2. Add the name of the LocalRootSpan service (*db.service*)

The entrypoint of the interceptor is the method *lifecycleEvent* called by Apache Tomcat during startup:

```
void com.zucchetti.ztracer.interceptor.tomcat.TraceInterceptorLifecycleListener.lifecycleEvent(LifecycleEvent event)
```

How to configure Apache Tomcat:

```
cp ztracer-interceptor-jdk-1.8.jar $CATALINA_HOME/lib/
vi $CATALINA_HOME/conf/server.xml
<Listener className="com.zucchetti.ztracer.interceptor.tomcat.TraceInterceptorLifecycleListener" queryExtraInfo="true" debugMode="false" />
```

Datadog database span tags after adding *db.service* and *db.cluster*

```
db {	
  cluster xxxxx
  instance yyyyyy
  service /mycontext
  type sqlserver
  user myuser
}
peer {	
  hostname yyyyy.xxxx.zucchetti.com
}
```

Debug tags added on the LocalRootSpan of a query span:

```
dc-interceptor-queryextrainfo {	
  exectime 2
  spancount 530
  thread http-nio-8081-exec-10
}
```
