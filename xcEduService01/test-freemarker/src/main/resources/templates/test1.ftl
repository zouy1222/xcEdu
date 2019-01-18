<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
<#--单一数据-->
Hello ${name}!</br>
<#--对象数据-->
${stu1.name}--${stu1.age}--${stu1.money}</br>
<#--list集合-->
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list list as item>
        <tr>
            <#--索引获取,通过 别名_index,默认从0开始-->
            <td>${item_index+1}</td>
            <td>${item.name}</td>
            <td>${item.age}</td>
            <td>${item.money}</td>
        </tr>
    </#list>
</table>
<#--
    map集合
    map集合单个对象获取有两种方法
-->
name:${stuMap.stu1.name}
age:${stuMap['stu2'].age}
<#--map集合遍历-->
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#--?keys就是获取map中的key-->
    <#list stuMap?keys as k>
        <tr>
            <td>${k_index+1}</td>
            <td>${stuMap[k].name}</td>
            <td>${stuMap[k].age}</td>
            <td>${stuMap[k].money}</td>
        </tr>
    </#list>
</table>
<#--if指令,此指令可以使用在任何地方-->
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list list as item>
        <tr>
        <#--索引获取,通过 别名_index,默认从0开始-->
            <td <#if item.name=='小明'>style="background:red"</#if>>${item_index+1}</td>
            <td>${item.name}</td>
            <#--> gt, < lt, >= gte,<=lte-->
            <td <#if item.age gt 18>style="background:green"</#if>>${item.age}</td>
            <td>${item.money}</td>
        </tr>
    </#list>
</table>
<#--空值处理,freemark对出现空对象时直接报错,判断为空,对象??表示为非空-->
<#if stu??>
    <#list stu as s>
        ${s.age}
    </#list>
</#if>
<#--空值处理,填充法,不知为何报错-->
<#--<#list stu3 as s>
    ${s.age!''}
</#list>-->


<#--日期格式化-->
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
        <td>日期</td>
    </tr>
    <#list list as item>
        <tr>
            <td>${item_index+1}</td>
            <td>${item.name}</td>
            <td>${item.age}</td>
            <td>${item.money}</td>
            <#--
                ${item.birthday?datetime}                 默认格式
                ${item.birthday?string("yyyy年MM月dd日")} 自定义格式
                ${item.birthday?date}                     年与日
                ${item.birthday?time}                     时分秒
            -->
            <td>${item.birthday?date}</td>
        </tr>
    </#list>
</table>
<#--内建函数-->
没有使用内建函数c===${stuMap['stu1'].money}</br>
使用内建函数c=======${stuMap['stu1'].money?c}</br>
<#--json字符串转换为对象-->
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank} 账号：${data.account}
</body>
</html>