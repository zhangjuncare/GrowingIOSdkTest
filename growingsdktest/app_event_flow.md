### app_event_flow.txt保存了测试用例种每个步骤的event数据,每行对应数据意义如下：
1. 首次打开应用的visit事件，记录下session id，接下来每条都需要验证session id，需要验证字段p,t,b,ch,db,d
2. 首次打开应用的page事件，需要验证字段d,p,t,tl
3. 首次打开应用的impression事件，需要验证d,p,t字段和e字段里的所有xpath
4. 从Splash跳转到Main的page事件，需要验证d,p,t,rp,tl
5. MainActivity的impression事件
6. LayoutChange触发的impression事件
7. 点击ChangeText按钮触发的click事件，需要验证字段d,p,t和xpath
8. 点击按钮改变文本触发的impression事件
9. 再次点击ChangeText按钮触发的click事件，这次不改变文字
10. 点击ActionBar上的显示Drawer按钮触发click事件
11. 点击Drawer上的第一个按钮“Click button”，触发Drawer收起动画，扫描时统计到Drawer的impression（这是一个bug，应该在10点击时就统计到）
12. 11中点击触发的click事件
13. 点击RadioButton触发的click事件
14. 启动ListViewActivity的page事件
15. ListViewActivity的impression事件
16. 点击ListView的第三行触发的click事件
17. 点击ListView第一行触发的click事件，跳转到RecyclerListActivity
18. RecyclerListActivity的page事件
19. RecyclerListActivity的impression事件 
20. 点击Recycler第一行触发的click事件
21. 点击Recycler第三行出发的click事件
