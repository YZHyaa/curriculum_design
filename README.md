# curriculum_design
1）暴力破解密码  
1. 设计一个信息系统，该系统可为学籍管理系统、订餐系统、票务管理系统不限， 系统必须通过客户端录入账号口令远程登录；
 2. 系统内至少包含三个以上账号，密码为 6 位以上数字组成；
2. 设计程序在该系统所在服务器端进行暴力密码破解，破解后将账号密码通过套接字发送出去；
3. 在客户端用破解得到的账号口令进行登录，验证破解成功。 

3）认证审计系统 
1. 设计一个信息系统，系统必须通过客户端录入账号口令远程登录； 
5. 系统内至少包含三个以上账号； 
6. 某账号登录后服务器端可实时显示该账号登录的时间及 IP 信息； 
7. 服务器端可查询账号的历史登录信息。 

4）数据嗅探系统 

1. 设计一个信息系统，系统必须通过客户端录入账号口令远程登录； 
2. 登录后客户端可通过键盘输入向服务器发送数据； 
3. 服务器端设置嗅探关键字，如果客户端发送的数据包含该关键字，即将该数据显示出来。 

	举例： 
	服务器设置关键字：密码 
	客户端从键盘输入的数据 1：你好，我是张三 
	客户端从键盘输入的数据 2：我的密码是 password 
	服务器端显示：我的密码是 password

5）防火墙系统 
1. 设计一个信息系统，系统必须通过客户端录入账号口令远程登录； 
5. 系统内至少包含三个以上账号； 
6. 系统服务器端可设定禁止登录的 IP 地址和账号信息；
7.  如果客户端从禁止的 IP 地址登录或使用禁止的账号登录则显示不允许登录，并断开连 接。 

7）图形验证模拟 、
1. 开发一个手机锁屏的图形验证程序，以字符命令行形式来实现要求完成的功能有： 
9. 登录时输入用户名； 
10. 输入 4*4 坐标下的图形点位置，用字符方式输入； 
11. 输入完成后实现在服务器端对图形进行验证； 
12. 至少有三个以上的用户验证。 

举例： 
显示：请输入用户名 
输入：user 
显示：请输入验证图形 
<img src="https://img-blog.csdnimg.cn/20201228003058608.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkzNTkyNw==,size_16,color_FFFFFF,t_70#pic_center" width="50%">

输入：set（1,1） 显示： 
<img src="https://img-blog.csdnimg.cn/20201228003043129.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkzNTkyNw==,size_16,color_FFFFFF,t_70#pic_center" width="50%">

输入：set（1,2） 显示： 
<img src="https://img-blog.csdnimg.cn/2020122800322312.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkzNTkyNw==,size_16,color_FFFFFF,t_70#pic_center" width="50%">

中间过程省略 
显示：
<img src="https://img-blog.csdnimg.cn/20201228003442810.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkzNTkyNw==,size_16,color_FFFFFF,t_70#pic_center" width="50%">

输入：set（1,2） 
显示： 
<img src="https://img-blog.csdnimg.cn/20201228003529487.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkzNTkyNw==,size_16,color_FFFFFF,t_70#pic_center" width="50%">

输入：verify 
显示：登录成功