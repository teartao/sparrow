# maven archetype
maven 坐标样例工程

# 使用
https://blog.csdn.net/qq_26462567/article/details/100100470

1. pom.xml 加入archetype插件
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-archetype-plugin</artifactId>
    <version>2.2</version>
</plugin>
```
2. 生成archetype骨架项目
```bash
mvn archetype:create-from-project
```

3. 本地install
```bash
mvn install 
```

4. deploy上传私服
上传私服需要2步：pom.xml中添加私服信息 ； 执行`mvn deploy`

mvn deploy
