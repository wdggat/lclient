1. com.liu.bean.Message 里要区分uid和email吗

    Newmsg 时: email 带上，服务器返回uid.
    Replymsg 时: UID 带上，服务器返回email.

2. Timeline_listitem　显示啥,
    Uid or Email           time 
    msg_content_prefix15

3. time 统一用10位的timestamp

## 与receiver交互 ## 
1. 全通过RequestHelper包装成Request对象后发送,Message:用户所发消息类, Event:用户动作类，登陆，注册，忘记密码, 修改密码等.
2. Event类消息由receiver需直接返回结果,流量会偏少; Message类消息进入队列，由sender处理


## TODO ##

1. Message 改字段, sendByMe 改为 from,to  --done,2014-06-09
2. lreceiver处理Event型消息  --done
3. 怎样从Request中取出Event,或Message消息来.   --done
4. NettyResponse统一发送Response对象进行回复  --done,2014-06-09
5. nginx负载均衡
6. 增加一个NO ONE的功能，类似/dev/null，不发给任何人
7. REPLY,QUICK_MSG的from都是email，怎样保证接收方可以按uid或者email按需显示呢
    REPLY: [from: email,to: uid], 接收端: 只显示email
    QUICK_MSG: [from: email, to:email], 客户端要只显示 uid, 在lsender把from换成对应的 uid
               [from: email, to:uid], 客户端要只显示　email
8.每次进TimelineActivity时都会Depends.initAll一次，需改正.          --done,每次都会检查，然后再决定是否绑定的.
9.TimelineActivity里的item按时间倒序排序.     --done
10.邮件需加加粗黑体"消息主人uid:....\n我们只是信息的搬运工，注册whoami,贴上上面的Uid,可进行回复哦."    --done
11.打出带签名包的测试和错误log获取.   --done
12.uid策略改进，现在UUID的方式太难记，不够友好   --done
13.当有新消息push过来时，主动更新消息界面
14.在app的logo和消息列表里,显示未读消息数的红字.
15.当用户换一个设备登陆时,baidu_push的消息怎么处理，要换吗，或增加?
16.ME页面增加uid显示，注册成功后show一个uid的通知　。--done
17.ME页面字体整体偏淡，标题的字体应加黑     --done 2014-08-17
18.New_msg 后TimelineActivity会有多个.
19.baidu_uinfo发不出去(FATAL)   --done 2014-08-17
20.quick_msg里,输入有汉字时，框里不正常显示已经输入的内容,横竖屏切换后正常了.
21.quick_msg: 在quick_msg里发送消息 -> Timeline -> quick_msg, quick_msg里的消息数显示不全(主要是自己主动发出的消息); 时间可能不一致，应都按手机本地收到的时间为排序(增加字段: local_time); 消息发到，不显示(退回Timeline再回到quick_msg后显示出来了);新消息到来，不亮灯.
22.Timeline里的item应该也以localTime来排序.




#应用描述
一款半匿名社交工具，把您的消息以另一匿名身份转发给对方, 完成与对方的沟通交流.
1,当你想向暗恋对象表白，又担心对方拒绝时，它能先以匿名身份帮你与暗恋对象沟通.
2,当你想调戏身边某人时，它可以帮你把想说的话换成匿名身份转给他(她).


