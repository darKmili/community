$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    $("#publishModal").modal("hide");
    let title = $("#recipient-name").val();
    let content = $("#message-text").val();
    $.post(gloabl_context + "/discuss/upload",
        {"title": title, "content": content},
        function (data) {
            // TODO 已经是js对象了，不用解析了
            // let obj = JSON.parse(data.data);
            $("#hintBody").text(data.message);
            $("#hintModal").modal("show");

            setTimeout(function () {
                $("#hintModal").modal("hide");
                if (data.code===200){
                    window.location.reload();
                }
            }, 2000);

        }
    )

}