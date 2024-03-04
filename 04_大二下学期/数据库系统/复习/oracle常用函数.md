[TOC]



| 数据定义 |     创建     |    删除    |    修改     |
| :------: | :----------: | :--------: | :---------: |
|    表    | CREATE TABLE | DROP TABLE | ALTER TABLE |
|   视图   | CREATE VIEW  | DROP VIEW  |             |
|   索引   | CREATE INDEX | DROP INDEX |             |



## 1. 正则

**regexp_like(expression, regexp)**

返回值为一个布尔值。如果第一个参数匹配第二个参数所代表的正则表达式，那么将返回真，否则将返回假。

**regexp_instr(expression, regexp, startindex, times)**

返回找到的匹配字符串的位置。

**regexp_substr(expression, regexp)**

返回第一个字符串参数中，与第二个正则表达式参数相匹配的子字符串。

**regexp_replace(expression, regexp, replacement)**

将expression中的按regexp匹配到的部分用replacement代替.

## 2. 字符串

**instr(expression,value)**

查找value第一次出现的位置

**substr(string,a[,b])**

截取子串，返回从**a位置**开始的**b个字符长**的string的一个子字符串，**第一个字符的位置是1**. 

**concat(value1,value2..)**

拼接字符串

**replace(col_name, last_str, new_str)**

替换子串

## 3. 小数

**round(expression[,num])**

保留num位小数，默认保留0位

## 4. 时间

```bash
# Mysql
# (单位允许microsecond、second、minute、hour、day、week、month、year)
adddate(now(), interval 1 day) # 添加一天
subdate(now(), interval 1 day) # 减少一天


```



## 5. 索引

### 创建索引

```sql
oracle:
create index idx1 on table_name(column_name)
```

- **字符串前缀索引**

  ```sql
  create index idx1 on table1(substr(name,1,1));
  ```

  > 若没有使用字符串前缀索引，则可用like来使用字符串索引



### 删除索引

```sql
oracle:
drop index idx1

mysql:
drop index idx1 on table_name
```



## 6. 权限

### 赋予权限

```sql
grant select/update/delete/insert on table1 to user1
grant all on table1 to user1
```

### 移除权限

```sql
revoke select/update/delete/insert on table1 from user1
```

































