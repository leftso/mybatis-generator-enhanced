# Mybatis Generator Swagger 注解插件

## 使用说明
pom.xml引入
```xml
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.4.0</version>
                <dependencies>
                    <dependency>
                        <groupId>net.ifok.plugin</groupId>
                        <artifactId>mybatis-swagger-plugin</artifactId>
                        <version>1.0.0</version>
                    </dependency>
                </dependencies>
                <configuration>
                    <configurationFile>src/main/resources/mybatis-generator-configuration.xml</configurationFile>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>
            </plugin>
```
mybatis-generator-configuration.xml 配置
````xml
<plugin type="net.ifok.project.mybatis.plugin.MybatisSwaggerPlugin"/>
````
