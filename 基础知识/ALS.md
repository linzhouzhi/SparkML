# 交替最小二乘法
ALS（alternating least squares）

例如：
将用户(user)对商品(item)的评分矩阵分解为两个矩阵：一个是用户对商品隐含特征的偏好矩阵，另一个是商品所包含的隐含特征的矩阵
ALS旨在找到两个低维矩阵X(m×k)和矩阵Y(n×k)，来近似逼近R(m×n)
<img src="./formulary/als/1.png" />
为了找到使低秩矩阵X和Y尽可能地逼近R，需要最小化下面的平方误差损失函数：
<img src="./formulary/als/2.png" />
其中xu(1×k)表示示用户u的偏好的隐含特征向量，yi(1×k)表示商品i包含的隐含特征向量, rui表示用户u对商品i的评分, 向量xu和yi的内积xuTyi是用户u对商品i评分的近似
由于变量xu和yi耦合到一起，这个问题并不好求解，所以我们引入了ALS，也就是说我们可以先固定Y（例如随机初始化X），然后利用公式（2）先求解X，然后固定X，再求解Y，如此交替往复直至收敛，即所谓的交替最小二乘法求解法。
具体步骤
先固定Y,  将损失函数L(X,Y)对xu求偏导，并令导数=0
然后再固定X



参考：
http://www.cnblogs.com/hxsyl/p/5032691.html