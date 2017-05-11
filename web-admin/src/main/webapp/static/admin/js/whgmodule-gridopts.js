/**
 * Created by rbg on 2017/3/24.
 */


function Gridopts(config){
    this.grid = "#whgdg";

    if (config && typeof config =='object'){
        this.grid = config.grid || "#whgdg";
    }

    this.modeUrl = window.location.href;
    this.modeUrl = this.modeUrl.substring(0, this.modeUrl.indexOf('/view'));

    //console.info(this.modeUrl);
}

Gridopts.prototype = {

    __getGridRow: function(idx){
        return $(this.grid).datagrid("getRows")[idx];
    },

    __getGridSelectRows: function(){
        return $(this.grid).datagrid("getSelections");
    },

    __getGridSelectIds: function(){
        var rows = $(this.grid).datagrid("getSelections");
        if (rows.length){
            var ids = [];
            for(var i in rows){
                ids.push(rows[i].id);
            }
            return ids.join();
        }else{
            return '';
        }
    },

    __ajaxError: function(xhr, ts, e){
        $.messager.progress('close');
        $.messager.alert("提示信息", "操作处理发生错误", 'error');
        $.error("ajax error info : "+ts);
    },

    __ajaxSuccess: function(data){
        $(this.grid).datagrid('reload');
        if (!data.success || data.success != "1"){
            $.messager.alert("错误", data.errormsg||'操作失败', 'error');
        }
        $.messager.progress('close');
    },

    view: function(idx){
        var row = this.__getGridRow(idx);
        WhgComm.editDialog( this.modeUrl+'/view/add?targetShow=1&id='+row.id);
    },

    add: function(){
        //window.location.href = this.modeUrl+'/view/add';
        WhgComm.editDialog( this.modeUrl+'/view/add');
    },

    edit: function(idx){
        var row = this.__getGridRow(idx);
        WhgComm.editDialog( this.modeUrl+'/view/add?id='+row.id );
    },

    /**
     * 删除
     * @param idx
     */
    del: function(idx){
        var row = this.__getGridRow(idx);
        var confireStr = '确定要删除选中的项吗？'
        if (row.delstate == 1){
            confireStr = '此操作将会永久性删除数据！'+ confireStr;
        }
        var mmx = this;
        $.messager.confirm("确认信息", confireStr, function(r){
            if (r){
                $.messager.progress();
                $.ajax({
                    url: mmx.modeUrl+'/del',
                    data: {id: row.id},
                    type: 'post',
                    dataType: 'json',
                    success: function(data){ mmx.__ajaxSuccess(data) },
                    error: function(xhr, ts, e){ mmx.__ajaxError(xhr, ts, e) }
                });
            }
        })
    },

    /**
     * 还原删除
     * @param idx
     */
    undel: function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定要还原选中的项吗？", function(r){
            if (r){
                $.messager.progress();
                $.ajax({
                    url: mmx.modeUrl+'/undel',
                    data: {id: row.id},
                    type: 'post',
                    dataType: 'json',
                    success: function(data){ mmx.__ajaxSuccess(data) },
                    error: function(xhr, ts, e){ mmx.__ajaxError(xhr, ts, e) }
                });
            }
        })
    },

    __updateStateSend: function (ids, formstates, tostate, optTime) {
        $.messager.progress();
        var params = {ids: ids, formstates: formstates, tostate: tostate, optTime: optTime};
        var mmx = this;
        $.ajax({
            url: mmx.modeUrl+'/updstate',
            data: params,
            type: 'post',
            dataType: 'json',
            success: function(data){ mmx.__ajaxSuccess(data) },
            error: function(xhr, ts, e){ mmx.__ajaxError(xhr, ts, e) }
        });
    },

    /**
     * 发布 [2,4]->6
     * @param idx
     */
    publish: function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        /*$.messager.confirm("确认信息", "确定发布选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(row.id, "2,4", 6);
            }
        })*/

        var optDialog =$('<div></div>').dialog({
            title: '设置发布时间时段',
            width: 400,
            height: 200,
            cache: false,
            modal: true,
            content: '<div class="dialog-content-add" style="padding: 30px 30px;"></div>',
            onOpen: function(){
                var content = $(this).find('.dialog-content-add');
                content.append('发布时间：<input class= "easyui-datetimebox" data-options="width:200,required:true"/>');
                $.parser.parse(content);
                content.find(".easyui-datetimebox:eq(0)").datetimebox('setValue', new Date().Format("yyyy-MM-dd hh:mm:ss"));
            },
            buttons: [{
                text:'确认',
                iconCls: 'icon-ok',
                handler:function(){
                    $.messager.progress();
                    var valid = optDialog.find(".easyui-datetimebox:eq(0)").datetimebox('isValid');
                    if (!valid) {
                        $.messager.progress('close');
                        return;
                    }
                    var optTime = optDialog.find(".easyui-datetimebox:eq(0)").datetimebox('getValue');
                    mmx.__updateStateSend(row.id, "2,4", 6, optTime);
                    optDialog.dialog('close');
                }
            },{
                text:'取消',
                iconCls: 'icon-no',
                handler:function(){ optDialog.dialog('close')}
            }]
        });

    },

    /**
     * 取消发布 [6]->4
     * @param idx
     */
    publishoff: function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定撤销发布选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(row.id, 6, 4);
            }
        })
    },

    /**
     * 送审 [1,5]->9
     * @param idx
     */
    checkgo: function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定提交审核选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(row.id, "1,5", 9);
            }
        })
    },

    /**
     * 审通过 [9]->2
     * @param idx
     */
    checkon: function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定审核通过选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(row.id, 9, 2);
            }
        })
    },

    /**
     * 审不通过 [9]->1
     * @param idx
     */
    checkoff: function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定审核不通过选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(row.id, 9, 1);
            }
        })
    },

    /**
     * 打回到编辑 [9,2]->1
     * @param idx
     */
    back: function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定打回编辑选中的项吗？", function(r){
            if (r){
                mmx.__updateStateSend(row.id, "9,2", 1);
            }
        })
    },

    /**
     * 推荐
     */
    recommend: function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定设置推荐选中的项吗？", function(r){
            if (r){
                $.messager.progress();
                $.ajax({
                    url: mmx.modeUrl+'/recommend',
                    data: {id: row.id, recommend:1},
                    type: 'post',
                    dataType: 'json',
                    success: function(data){ mmx.__ajaxSuccess(data) },
                    error: function(xhr, ts, e){ mmx.__ajaxError(xhr, ts, e) }
                });
            }
        })
    },

    /**
     * 取消推荐
     * @param idx
     */
    recommendoff: function(idx){
        var row = this.__getGridRow(idx);
        var mmx = this;
        $.messager.confirm("确认信息", "确定取消推荐选中的项吗？", function(r){
            if (r){
                $.messager.progress();
                $.ajax({
                    url: mmx.modeUrl+'/recommend',
                    data: {id: row.id, recommend:0},
                    type: 'post',
                    dataType: 'json',
                    success: function(data){ mmx.__ajaxSuccess(data) },
                    error: function(xhr, ts, e){ mmx.__ajaxError(xhr, ts, e) }
                });
            }
        })
    },


    recommendVfun: function(idx){
        var row = this.__getGridRow(idx);
        return (row.state == 6 && row.delstate == 0 && (!row.recommend || row.recommend==0 ));
    },
    recommendOffVfun: function(idx){
        var row = this.__getGridRow(idx);
        return (row.state == 6 && row.delstate == 0 && (row.recommend && row.recommend==1 ));
    }
};
