let logPage = 1;
let logPageSize = 10;

function renderLogs(pageInfo) {
    $("#log-tbody").html("");
    if (pageInfo && pageInfo.list && pageInfo.list.length > 0) {
        $.each(pageInfo.list, function (idx, log) {
            const tr = '<tr>' +
                '<td>' + (log.id || '') + '</td>' +
                '<td>' + (log.ip || '') + '</td>' +
                '<td class="text-break">' + (log.url || '') + '</td>' +
                '<td class="text-break">' + (log.method || '') + '</td>' +
                '<td class="text-break text-start" style="max-width: 260px;">' + (log.args || '') + '</td>' +
                '<td>' + (log.operateTime || '') + '</td>' +
                '</tr>';
            $("#log-tbody").append(tr);
        });
    } else {
        $("#log-tbody").append('<tr><td colspan="6" class="text-center text-secondary">暂无数据</td></tr>');
    }
    $("#currentPage").text(pageInfo ? pageInfo.pageNum : 1);
    $("#totalPage").text(pageInfo ? pageInfo.pages : 1);
    $("#totalCount").text(pageInfo ? pageInfo.total : 0);
}

function listLogs(page, pageSize) {
    $.ajax({
        url: "/logs",
        type: "get",
        data: {
            page: page,
            pageSize: pageSize
        },
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                renderLogs(result.data);
            } else {
                $("#tooltip-msg").html(result.message);
                $("#tooltip-open").click();
            }
        }
    });
}

$(function () {
    listLogs(logPage, logPageSize);

    $("#prePage").click(function () {
        const current = parseInt($("#currentPage").text());
        if (current > 1) {
            logPage = current - 1;
            listLogs(logPage, logPageSize);
        }
    });

    $("#nextPage").click(function () {
        const current = parseInt($("#currentPage").text());
        const total = parseInt($("#totalPage").text());
        if (current < total) {
            logPage = current + 1;
            listLogs(logPage, logPageSize);
        }
    });
});

