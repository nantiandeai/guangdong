/**
 * 数字文化馆文件上传对象
 * @type {{}}
 */
WhgUploadFile = (function () {
    /** 内部对象构造函数- 文件上传对象 */
    function __obj_Construct(options){
        //请求参数
        this._options = $.extend({}, {
            basePath: './',                                          //
            uploadBtnId: 'fileUploadBtn1',                           //上传文件的按钮,
            hiddenFieldId: '',                                      //表单隐藏域ID
            // previewImgId: '',                           //预览图片元素的ID
            previewFileId: '',
            uploadFileType: 'file',                                 // 图片类型。img|video|audio|file
            needCut: false,                                          //是否裁剪图片
            cutWidth: 0,                                          //裁剪图片的宽度
            cutHeight: 0,                                         //裁剪图片的高度
            maxFileSize: '10mb',                                    //最大只能上传10mb的文件
            imgServerAddr: WhgComm.getImgServerAddr(),             //
            multiFile: true                                       //是否能一次上传多个文件
        }, options);

        //内部参数
        this._uploadURL = this._options.basePath+'/comm/upload';//上传文件的URL oss上传地址
        this._uploadFileName = "whgUploadFile";                  //服务端接收文件的名称
        this._uploadSuccFileURL = false;                          //已经上传成功的文件地址

        //初始
        this._init();
        var thisObj = this;

        //设置预览图片
        /*if( $('#'+this._options.previewImgId).size() ==1 ){x
            var initImgUrl = $('#'+this._options.hiddenFieldId).val();
            if(initImgUrl != ''){
                $('#'+thisObj._options.previewImgId).html('<img src="'+thisObj._options.imgServerAddr + initImgUrl+'" style="width: 100%; height: 100%;" />');
            }
        }*/
        //编辑预览
        if ($('#'+thisObj._options.hiddenFieldId).val()) {
            var filePaths = $('#'+thisObj._options.hiddenFieldId).val().split(",");
            var html = "";
            for (var i in filePaths) {
                var fileName = filePaths[i].substr(filePaths[i].lastIndexOf("/")+1);
                html += '<div class="singleFile"><a href="#">'+fileName+'</a><span class="cancelFileBtn" fileurl = "'+filePaths[i]+'" >取消</span></div>';
            }
            $("#"+thisObj._options.previewFileId).append(html);
        }
        $("#"+thisObj._options.previewFileId).on("click", ".cancelFileBtn", function () {
            var thisEle = $(this);
            var fileUrl = thisEle.attr("fileurl");
            thisObj._removeUploadSuccFile(fileUrl,function () {
                thisEle.parent().remove();
            });
        });
    }

    /**
     * 初始WebUpload
     * @private
     */
    __obj_Construct.prototype._init = function () {
        //this对象
        var thisObj = this;

        //使用plupload构造文件上传
        this._uploader = new plupload.Uploader({
            browse_button : this._options.uploadBtnId,
            multi_selection: this._options.multiFile,
            file_data_name: this._uploadFileName, //服务端接收文件的名称
            flash_swf_url : this._options.basePath+'/static/plupload/lib/plupload-2.1.2/js/Moxie.swf',
            silverlight_xap_url : this._options.basePath+'/static/plupload/lib/plupload-2.1.2/js/Moxie.xap',
            url : this._uploadURL,
            filters: {
                mime_types : [{ title : "files", extensions : "doc,docx,xls,zip,xlsx,pdf" }],
                max_file_size : this._options.maxFileSize, //最大只能上传10mb的文件
                prevent_duplicates : false //不允许选取重复文件
            },
            init: {
                FilesAdded: function(up, files) {
                    //在上传新文件时如果已经上传了文件，先删除
                    // if( thisObj._uploadSuccFileURL ){
                    //     thisObj._removeUploadSuccFile(function () {
                    //         thisObj._addFile(up, files);
                    //     });
                    // }else{
                        thisObj._addFile(up, files);
                    // }
                },
                BeforeUpload: function(up, file) {
                    thisObj._beforeUpload(file);
                },
                UploadProgress: function(up, file) {
                    var d = document.getElementById(file.id);
                    d.innerHTML = file.percent;
                },
                FileUploaded: function(up, file, info) {
                    if (info.status == 200){
                        //上传后的处理
                        var response = eval('('+info.response+')');
                        if(response.data.success == '1'){
                            //保存已上传文件的记录
                            thisObj._uploadSuccFileURL = response.data.url;

                            //设置隐藏域的值
                            var hiddenInp = $('#'+thisObj._options.hiddenFieldId);
                            if (hiddenInp.val()) {
                                var hiddenInpVal = hiddenInp.val().split(",");
                                hiddenInpVal.push(response.data.url);
                                hiddenInp.val(hiddenInpVal.join(","));
                            } else {
                                hiddenInp.val(response.data.url);
                            }
                            $("#"+file.id).parent().find(".cancelFileBtn").attr("fileurl",response.data.url);


                        }else{
                            $('#'+thisObj._options.uploadBtnId).parent('i').children('span').remove();
                            $.messager.alert('提示', '上传失败：'+response.data.errormsg+'！', 'warning');
                        }
                    } else {
                        $.messager.alert('提示', '上传失败：'+info.response+'！', 'warning');
                    }
                },
                Error: function(up, err) {
                    if (err.code == -600) {
                        $.messager.alert('提示', '您选择的文件超过大小限制！', 'warning');
                    }else if (err.code == -601) {
                        $.messager.alert('提示', '您选择的文件类型不允许上传！', 'warning');
                    }else if (err.code == -602) {
                        $.messager.alert('提示', '您选择的文件已经上传过了！', 'warning');
                    }else{
                        $.messager.alert('提示', '您选择的文件不允许上传：'+err.response+'！', 'warning');
                    }
                }
            }
        });
        this._uploader.init();
    }

    /**
     * 上传图片之前获取签名参数，传递给plupload
     * @private
     */
    __obj_Construct.prototype._beforeUpload = function (file) {
        this._uploader.setOption({
            'multipart_params': {
                uploadFileType: this._options.uploadFileType
            }
        });
    }

    /**
     * 删除已经上传的文件
     * @private
     */
    __obj_Construct.prototype._removeUploadSuccFile = function (fileUrl,fn) {
        var thisObj = this;
        var thisFileUrl = "";
        if (fileUrl && typeof fileUrl == "string") {
            thisFileUrl = fileUrl;
        } else {
            thisFileUrl = this._uploadSuccFileURL;
        }
        if(thisFileUrl){
            $.ajax({
                type: 'POST',
                cache: false,
                async: false,
                url: this._options.basePath+'/comm/delUpload',
                data: {uploadURL:thisFileUrl},
                success: function (data) {
                    if(data && data.success == '1'){
                        if(!thisObj._options.needCut){
                            //图片预览
                            $('#'+thisObj._options.previewImgId).html('');
                            //设置隐藏域
                            var hidenVal = $('#'+thisObj._options.hiddenFieldId).val().split(",");
                            for (var i in hidenVal) {
                                if (hidenVal[i] == thisFileUrl) {
                                    hidenVal.splice(i,1);
                                    break;
                                }
                            }
                            $('#'+thisObj._options.hiddenFieldId).val(hidenVal.join(","));
                        }

                        //保存已上传文件的记录
                        thisObj._uploadSuccFileURL = false;
                        $('#'+thisObj._options.uploadBtnId).parent('i').children('span').remove();
                        fn();
                    }else{
                        $.messager.alert('提示', '操作失败:'+data.errormsg+'！', 'error');
                    }
                }
            });
        }
    }

    /**
     * 添加文件
     * @param up
     * @param files
     * @private
     */
    __obj_Construct.prototype._addFile = function (up, files) {
        var thisObj = this;

        for(var i=0; i<files.length; i++){
            var file = files[i];
            // $('<span id="span_'+file.id+'">'+file.name+' 完成<b id="'+file.id+'"></b>%</span>').appendTo($('#'+this._options.uploadBtnId).parent('i'));
            var html = '<div class="singleFile"><a href="#">'+file.name+'</a> <span id="'+file.id+'"></span>% <span class="cancelFileBtn" fileurl = "'+thisObj._uploadSuccFileURL+'" >取消</span></div>';
            $("#"+this._options.uploadBtnId).parents(".whgff-row-input-filefile").prev().append(html);
            break;
        }
        up.start();
    }


    /**
     * 清空组件
     */
    __obj_Construct.prototype.clear = function () {
        var thisObj = this;

        //图片预览
        //$('#'+thisObj._options.previewImgId).html('');
        //设置隐藏域
        $('#'+thisObj._options.hiddenFieldId).val('');

        //保存已上传文件的记录
        thisObj._uploadSuccFileURL = false;
        $('#'+thisObj._options.uploadBtnId).parent('i').children('span').remove();
        $("#"+thisObj._options.previewFileId).html("");
        //重新初始
        if(thisObj._uploader){
            thisObj._uploader.destroy();
        }
        thisObj._init();
    }

    return {
        init: function (options) {
            return new __obj_Construct(options);
        }
    }
})();
