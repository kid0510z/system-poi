# System_POI
## POI项目
> 项目说明

####        		这是一个专门将Excel里面的题目生成Word的一个小软件。

  1. 你需要选择你的Excel题目文件
  2. 然后指定生成的Sheet名称或者编号（编号是从下标0开始）
  3. 接下来你需要选择一个单选框，指明你上面那个输入框的```Sheet是否是根据名称输入的```，```yes```表示是根据Sheet名称，```no```表示是根据下标生成的
  4. 什么都不选择默认，读取第一个Sheet，由于里面模板固定，所以只支持下面这个格式（包括Excel头标题的每一个字符必须完全一致）
  5. 然后就是生成存放Word的一个目录，文件名默认根据Excel文件的名称生成，格式为 .docx
> 如何用?
  
  因为该项目是基于java编写的，首先需要java jdk支持，然后下载jar包，进入同级目录，打开cmd，执行 java -jar ```jar包名称``` 回车即可,下载源码放入Idea里面，启动Main方法即可,另外测试的excel文件和本人自己再项目打成的jar包在下面链接  <a href="https://pan.baidu.com/s/1O4cad1lrO6ye2Kb2__FE5w">点我获取</a>


> Excel源文件图片



![excel](assets\excel.png)



> Word生成图片

![word](assets\word.png)


> 项目不足

  由于种种原因，该项目没有很完善，比如说，当路径，sheet等信息输错，仅仅是窗口显示异常，
用户体验不好，还有应该根据用户选择的sheet取列出Sheet，然后就是 格式模板固定太死了


