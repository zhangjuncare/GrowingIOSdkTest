### app_event_flow.txt保存了测试用例种每个步骤的event数据,每行对应数据意义如下：
1. 首次打开应用的visit事件，记录下session id，接下来每条都需要验证session id，需要验证字段p,t,b,ch,db,d
2. 首次打开应用的page事件，需要验证字段d,p,t,tl
3. 首次打开应用的impression事件，需要验证d,p,t字段和e字段里的所有xpath
4. 从Splash跳转到Main的page事件，需要验证d,p,t,rp,tl
5. MainActivity的impression事件，验证需求同3一样
