# Hot reload

- build.gradle

```
developmentOnly("org.springframework.boot:spring-boot-devtools")
```

- application.properties
```
# Enable/disable restart functionality
spring.devtools.restart.enabled=true
# Enable/disable LiveReload server
spring.devtools.livereload.enabled=true
```

- Enable option: File / Settings / Advanced Settings / Compiler / Allow auto-make to start... 
- Create a new run configuration: Edit cofigurations / new / gradle / bootRun