# dd-trace-interceptor

This project aims to extend the database tags of a query type span in Datadog. We would show which and how a single service uses the resources of a shared SQL Cluster.

To achieve the goal, we need to do the following tasks for each query span:
1. Convert a DNS alias into the real SQL cluster name
2. Add to the query spans the name of the LocalRootSpan service

How to configure in Apache Tomcat:

```
cp ztracer-interceptor-jdk-1.8.jar /opt/tomcat_b/lib/
vi /opt/<tomcat_name>/conf/server.xml
<Listener className="com.zucchetti.ztracer.interceptor.tomcat.TraceInterceptorLifecycleListener" queryExtraInfo="true" debugMode="false" />
```

Database span tags after adding *db.service* and *db.cluster*

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
