# ABOUT LUNA V0.01

定义变量

变量一经定义无法被修改

[variable name] = (right value)

定义函数（多行）

多行函数的最后一行表达式的左值将被作为函数返回值

或使用return关键字提前返回函数，剩余的语句将被跳过

函数的参数可以有任意个，其使用空格隔开，使用_表示占位符，_不可在函数体中被调用，此参数会被忽视

[function name] [param name] | _ = do

    (function body)
    
end

定义函数（单行）

[function name] = (right value) ｜ (function body)

函数调用

[function name] [param list]

嵌套式函数调用

[function name] ([function name] [param list]) [param list]

表达式定义

单独的数字和双引号引起的字符都可以作为表达式，
有字符串参与的表达式计算无法使用除+之外的操作符

EXPRESSION = ATOM ｜ ATOM OPERATOR ATOM | ATOM OPERATOR EXPRESSION | EXPRESSION OPERATOR EXPRESSION

ATOM = 0-9 ｜ "any characters"

OPERATOR = + ｜ - ｜ * ｜ /

关键字列表

if 声明条件判断语句开始
else 条件判断的分支语句
end 表明语句块结束
do 表明语句块开始
naive 表明此方法被内部实现