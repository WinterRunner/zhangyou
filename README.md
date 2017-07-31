# zhangyou

关键技术：

1.利用DES3和BASE64对登录账号和密码进行加密，保存或者向服务器传送。加密串：借助NDK保存到C中，防止反编译。

2.每次访问对应的URL的时候，我们都需要对请求参数进行sign的MD5签名，到服务器端进行相同的加密算法，对比sign，如果相同则正常返回值。
  否则视为被拦截，篡改了参数。

3.对rxjava2+retrofit2的二次封装，更简洁的请求方式；打印请求头和请求json，响应头和响应json,一目了然；cookie维持用户登录状态的管理

4.背景使用了陀螺仪效果（https://github.com/WinterRunner/gyroscope）

5.使用路由进行模块化开发,解耦

6.统一的ContentLayout对加载成功，失败，空，加载中...的页面进行统一管理




项目整体架构描述：

1.三个基本library：function(功能)，uilibrary(视图UI)，router(模块间交互)

2.网络：rxJava2+Retrofit2

3.模块化开发，MVP模式开发

4.一个模块一个module，比如：登录模块（module_login）(跳转使用，请参考https://github.com/WinterRunner/xRouter)

5.AppManager管理整个app的存储，设置，信息缓存等，通过工厂类，用户工厂，设置类工厂，数据库工厂，分别实现相应的功能。

6.利用最顶层视图ContentLayout对所有页面的显示进行控制（成功，失败，空，加载...）

7.利用rxjave2+rxlifecycle2将网络请求和生命周期进行绑定.