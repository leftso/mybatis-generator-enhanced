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
                        <artifactId>mybatis-generator-enhanced</artifactId>
                        <version>1.0.2</version>
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

实体类生成springfox的字段属性注解
````xml
<plugin type="net.ifok.mybatis.plugins.SwaggerPlugin"/>
````

根据数据库comment生成Java注释插件
````xml
<plugin type="net.ifok.mybatis.plugins.CommentPlugin"/>
````

生成mapper新增返回主键id注解(注意：仅支持单表单主键)
````xml
<plugin type="net.ifok.mybatis.plugins.PrimaryKeyPlugin"/>
````

## 弃用说明
1.0.2版本开始，弃用artifactId `mybatis-swagger-plugin`，使用新的artifactId `mybatis-generator-enhanced`

## LICENSE

Apache Software License, Version 2.0
