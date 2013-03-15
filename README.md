xiaoao-turntable
================

笑傲微江湖转盘抽奖

<p>介绍：</p>
    用于笑傲江湖OL的手机APP-笑傲微江湖的转盘抽奖。通过HTTP发送请求到服务器，可以自己手动指定账号的token和每次运行的间隔时间

<p>编译：</p>
    mvn clean package

<p>运行：</p>
    java -jar xiaoao-turntable-{version}.jar -a -b -c -d
    说明：
        version：程序版本号
        -a：获取积分，必须在jar包目录下包含a.txt文件
        -b：获取商品列表，必须在jar包目录下包含b.txt文件
        -c：获取商品，必须在jar包目录下包含c.txt文件
        -d：兑换积分，必须在jar包目录下包含d.txt文件
