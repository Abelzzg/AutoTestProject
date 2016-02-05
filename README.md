# AutoTestProject
自己写的android自动化测试工具SDK，懒得导成jar，自动生成测试报告，自动截图，自动获取控制台log，自动记录每一步操作。解决测试报告不友好、不能自动截图和报告分析的问题。


##1.测试报告生成路径可以自行修改，整个测试报告没有进行压缩，产生截图多时可能会占用设备控件。


##2.测试工具功能：

###2.1当测试脚本运行失败时，工具会自动截图，测试报告里相应失败步骤会产生html超级链接，点击链接会看到失败时的截图；
                
                
###2.2当测试脚本运行时，程序突然崩溃，工具会记录异常栈信息和截图，两者也是一html超级链接的形式来展现；
                
                
###2.3测试开发编写测试脚本，测试工具会根据脚本的具体调用产生详细的步骤描述，并体现在测试报告中，方便开发人员解决问题。

##3.测试报告


测试报告首页

![image](https://raw.githubusercontent.com/Abelzzg/AutoTestProject/master/Screenshots/QQ20160205-0@2x.png)

点击各测试case标题可以查看各具体用例执行情况

![image](https://raw.githubusercontent.com/Abelzzg/AutoTestProject/master/Screenshots/QQ20160205-3@2x.png)

点击case标题可以查看执行case时控制台日志

![image](https://raw.githubusercontent.com/Abelzzg/AutoTestProject/master/Screenshots/QQ20160205-2@2x.png)

当测试步骤中有状态为异常或者失败时，点击状态栏，可以查看运行时失败截图

![image](https://raw.githubusercontent.com/Abelzzg/AutoTestProject/master/Screenshots/QQ20160205-4@2x.png)

如果执行测试用例时，程序出现崩溃，工具会记录崩溃日志。点击异常日志即可查看崩溃异常栈和一些崩溃时设备信息，方便开发人员分析崩溃原因。

![image](https://raw.githubusercontent.com/Abelzzg/AutoTestProject/master/Screenshots/QQ20160205-5@2x.png)

#关于作者
邮箱：abelzzg90@gmail.com 724210540@qq.com(非诚勿扰)  脉脉：Abelzzg  

