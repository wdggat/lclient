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

