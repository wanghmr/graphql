// 发送api请求
// query： graphql格式参数
// variables： 需要传递的变量
// bizHandle：业务处理方法
function send(query, variables, bizHandle) {
    $.ajax({
        url: "/graphql",
        data: JSON.stringify({
            query: query, // 这里的名称必须是query
            variables: variables
        }),
        accepts: {
            customType: 'application/json'
        },
        contentType: 'application/json',
        type: "post",
        cache: false,
        dataType: "json",
        async: true, //同步请求
        success: function (data) {
            bizHandle(data.data);
        },
        error: function (error) {
            console.log(error)
        }
    })
}