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

