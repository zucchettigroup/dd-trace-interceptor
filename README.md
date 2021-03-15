# dd-trace-interceptor

```
cp ztracer-interceptor-jdk-1.8.jar /opt/tomcat_b/lib/
vi /opt/<tomcat_name>/conf/server.xml
```

```
<Listener className="com.zucchetti.ztracer.interceptor.tomcat.TraceInterceptorLifecycleListener" queryExtraInfo="true"  />
```


```
<Listener className="com.zucchetti.ztracer.interceptor.tomcat.TraceInterceptorLifecycleListener" queryExtraInfo="true" debugMode="true" />
```

```
dc-interceptor-queryextrainfo {	
  exectime 2
  spancount 530
  thread http-nio-8081-exec-10
}
```

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

```
yyyyy.xxxx.zucchetti.com -> xxxxx
```
 
# Datadog links

Di seguito i link principali della documentazione Datadog per estendere la piattaforma con  _Custom Instrumentation_  e _Trace Interceptor_
- [Java Custom Instrumentation](https://docs.datadoghq.com/tracing/setup_overview/custom_instrumentation/java)
- [Java Open Standards](https://docs.datadoghq.com/tracing/setup_overview/open_standards/java/)
 
This is the [Markdown Guide](https://www.markdownguide.org)