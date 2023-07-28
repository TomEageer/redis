# Redis

## 常见命令

判断key是否存在：

```C
exists key
  //返回0 表示不存在
  //返回1 表示存在
```

删除key：

```C
del key
  //返回0 无操作
  //返回1 删除成功
```

判断key的类型：

```c
type key
  /*返回类型：
    String(字符串)
    List（列表）
    Set（集合）
    Hash（哈希）
    Sorted Set（有序集合）
  */
```

查看key的存活时间

```C
ttl key
  //返回 剩余时间（秒）
  //返回 -2 不存在 
```

## 五大结构

### String结构

#### 介绍

1. 存储字符串类型的key-value

2. 最大存储512m

#### 应用场景

   1. 短信验证码
   2. 商品购买联系下单

#### 常用命令

##### set、get

设置和获取key-value

```C
set key value
  //返回ok 设置成功 再次设置则覆盖
get key
  //返回null 不存在key
 	//返回“对应key的value”
set key value ex time 
  //ex:设置过期时间
  //time:单位秒
```

##### mset、mget

批量设置和获取key-value

```c
mset key1 value1 key2 value2
  //返回数字，数字代表设置成功数量
mget key1 key2
  //返回对应key的值
```

##### incr

对key的值+1，若不存在key，则创建一个默认为0的key再进行加

```c
incr key
  //返回key的value自增1之后的值
```

##### incrby

对key的值加约定的值，若不存在key，则创建一个默认为0的key再进行加

```c
incrby key num
  //返回key的value自增1之后的值
```

##### setex

设置一个有过期时间的value

```c
setex key seconds value
  //seconds填入秒
	//返回ok
  //过期后get key返回nil
```

##### setnx

若有key不进行任何操作，若无key和set一样

```c
setnx key value
  //成功返回1
```

##### getset

设置key的新值，并返回原先的值

```c
getset key value
  //返回被更新之前的值
```

### list结构

#### 介绍

1. 字符串列表，按照插入顺序排序
2. 双向链表，插入时间复杂度O(1)块，O(n)慢
3. 通常添加一个元素到头部(左)或者尾部(右)
4. 存储的都是String字符串类型
5. 支持分页，高并发项目中，第一页数据都是来源于list，第二页和更多信息则来源于数据库加载

#### 应用场景

1. 简单的队列
2. 最新评论列表
3. 非实时的排行榜，如某某日销榜

#### 常用命令

##### lpush

将一个或者多个值插入到头部（左）

```c
lpush key value
  //返回当前key中被插入后的值的数量，如果不存在key，则创建一个key
lpush key value1 value2 value3 ... valueN
  //返回当前key中被插入后的值的数量，如果不存在key，则创建一个key
```

##### rpush

将一个或者多个值插入到尾部（右）

```c
rpush key value
  //返回当前key中被插入后的值的数量，如果不存在key，则创建一个key
rpush key value1 value2 value3 ... valueN
  //返回当前key中被插入后的值的数量，如果不存在key，则创建一个key
```

##### lpop

移除并获取列表中最左（头）一个元素

```c
lpop key count
  //移除key中count个值，若没写count，则默认一个
```

##### rpop

移除并获取列表中最右（尾）一个元素

```c
rpop key
  //移除key中count个值，若没写count，则默认一个
```

##### brpop

移除并获取最右（尾）一个元素，若列表为空，则阻塞列表，直到获取到元素或者超时为止，redis关闭后，阻塞也对应关闭。

```c
brpop key timeout
	//timeout:超时时间
  //返回两行
 	//第一行：key
  //第二行：被移除的值
  
```

##### llen

获取当前key的长度

```c
llen key
  //返回key的长度
```

##### lindex

获取当前key的序号对应的值，顺序从0开始（头部开始）

```c
lindex key Num
```

##### lrange

获取预设值范围的所有元素的值，0 表示第一个，-1表示倒数第一个,若两个数字相等，则获取该序号的值,顺序是从头部开始。

```c
lrange key startNum endNum
```

##### lrem

​	当 count 大于 0 时：从列表的头部开始，向列表的尾部搜索，移除最多 count 个值为 value 的元素。

​	当 count 小于 0 时：从列表的尾部开始，向列表的头部搜索，移除最多 count 绝对值个值为 value 的元素。

​	当 count 等于 0 时：移除所有值为 value 的元素。

```c
lrem key count value
  //返回移除的个数
```

### hash结构

#### 介绍

1. String类型的field和value的映射表，hash特别适用于存储对象。
2. 每个hash可以存储最多40多亿个键值对

#### 应用场景

1. 购物车
2. 用户个人信息
3. 商品详情

#### 常用命令

##### hset

设置key指定的哈希集中指定字段的值

```c
hset key field1 value1 field2 value2
  //返回设置成功的个数
```

##### hmset

设置key指定的哈希集汇总的一个或多个字段的值

```c
hmset key filed1 value1 filed2 value2
  //成功返回ok
```

##### hget

返回指定的hash值中该字段所关联的值

```c
hget key field
  //返回对应的值
```

##### hmget

获取key指定的哈希集汇总的一个或多个字段的值

```c
hmget key filed1 filed2
  //返回对应的值
```

##### hgetall

返回指定的hash值中所有字段所关联的值

```c
hgetall key
  //返回key全部的值
```

##### hdel

从key中指定的hash集中移除指定的域

```c
hdel key field1 field2 fieldN
  //返回删除成功的个数
```

##### hexists

返回hash里面的field是否存在

```c
hexists key field
  //存在返回1
  //不存在返回0
```

##### hincrby

增加key指定的hash集中指定字段的数值，如果是-1则是递减

```c
hincrby key filed num
  //返回filed增加num后的值
```

### set结构

#### 介绍

1. 将一个或者多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略
2. set（集合）是通过哈希表来实现的

#### 应用场景

1. 去重
2. 社交应用关注、粉丝、共同好友
3. 统计网站里的pv、uv、ip
4. 大数据中的用户画像标签去重

#### 常用命令

##### sadd

添加一个或者多个制定的member元素到集合到key中

```c
sadd key:key member1 member2
  //返回添加成功个数
```

##### scard

返回集合存储的key的基数(数量)

```c
scard key
  //返回key中的个数
```

##### sdiff

返回第一个集合与后续集合中第一个集合的差集

```c
stiff key1 key2 keyN
  //返回key1与key2与keyN，key1的差集
```

##### sinter

返回第一个集合与后续集合中第一个集合的交集

```c
sinter key1 key2 keyN
  //返回key1与key2与keyN，key1的交集
```

##### smembers

返回key集合中所有的元素

```c
smembers key
  //返回key中所有的元素
```

##### srem

移除key集合中指定的元素，若不是key中的元素，则忽略

```c
srem key value1 value2 valueN
  //返回移除的个数
```

##### sunion

返回多个key集合的并集中的所有元素

```c
sunion key1 key2 keyN
  //返回ke1,key2,keyN 之间的并集
```

### Sorted Set结构

#### 介绍

1. Set的有序集合
2. 用于将一个或者多个成员元素及其分数值加入到有序集中

#### 应用场景

1. 实时排行榜
2. 优先级任务、队列
3. 朋友圈、文章点赞-取消（统计一篇文章被点赞了多少次，可以直接去取里面有多少个成员）

#### 常用命令

##### zadd

向有序集合添加一个或者多个成员，或更新已存在的成员的分数

```c
zadd key:key score1 member1 score2 member2 scoreN memberN
  //score:数字
  //返回key:key添加成功个数
```

##### zcard

获取有序集合的成员数

```c
zcard key:key
  //返回key:key中包含的成员数量
```

##### zcount

计算再有序集合中指定区间的成员数量

```c
zcount key:key minScore maxScore
 	//返回minScore到maxScore之间的成员的数量 
```

##### zincrby

增加有序集合中指定成员的分数

```c
zincrby key:key increment member
  //返回key中member增加increment后的值，若没有member，则新建一个member
```

##### zrange

从小到大排序有序集合的制定区间内的成员

```c
zrange key:key start end
  //从大到小返回排序key:key中score为start开始，end结束中的所有成员
```

##### zrevrange

从大到小排序有序集合的制定区间内的成员

```c
zrange key:key start end withscores
    //从大到小返回排序key:key中score为start开始，end结束中的所有成员
```

##### zrank

以从小到大的顺序返回有序集合中指定成员的排名

```c
zrank key:key member
  //以从小到大的顺序返回key:key中member的排名
```

##### zrevrank

以从大到小的顺序返回有序集合中指定成员的排名

```c
zrevrank key:key member
    //以从大到小的顺序返回key:key中member的排名
```

##### zrem

移除有序集合中的一个或者多个成员

```c
zrem key:key member1 member2 memberN
  //返回删除成功的数量
```

##### zscore

返回有序集合中指定成员的分数值

```c
zscore key:key member
  //返回key:key的分数值
```

