本应用在windows7 + java8 + mysql + chrome环境下运行正常

采用spring boot + thymeleaf + spring data JPA技术，数据库为mysql.

安装运行步骤:
1. 安装jdk1.8;

2. 安装maven;

3. 安装mysql(设置用户名root, 密码aya01);

4. 在mysql中创建railway数据库, 编码格式utf-8;

5. git clone应用到指定目录;

6. 打开指定目录运行mvn spring-boot:run;

7. 导入src/main/resources/db/populateDB.sql;

8. 打开网站localhost:8080/login访问(普通用户test/test, 管理员账户admin/admin)
