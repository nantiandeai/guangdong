/**
 * 数字文化馆图片上传对象
 * @type {{}}
 */
WhgUploadImg = (function () {
    /** 内部对象构造函数- 文件上传对象 */
    function __obj_Construct(options){
        //请求参数
        this._options = $.extend({}, {
            basePath: './',                                          //
            uploadBtnId: 'imgUploadBtn1',                           //上传文件的按钮,
            hiddenFieldId: '',                                      //表单隐藏域ID
            previewImgId: 'previewImg1',                           //预览图片元素的ID
            uploadFileType: 'img',                                 // 图片类型。img|video|audio|file
            needCut: true,                                          //是否裁剪图片
            cutWidth: 750,                                          //裁剪图片的宽度
            cutHeight: 500,                                         //裁剪图片的高度
            maxFileSize: '4mb',                                    //最大只能上传10mb的文件
            imgServerAddr: WhgComm.getImgServerAddr(),             //
            multiFile: false                                       //是否能一次上传多个文件
        }, options);

        //内部参数
        this._uploadURL = this._options.basePath+'/comm/upload';//上传图片的URL oss上传地址
        this._uploadFileName = "whgUploadFile";                  //服务端接收文件的名称
        this._uploadSuccFileURL = false;                          //已经上传成功的图片地址

        //初始
        this._init();
        var thisObj = this;

        //设置预览图片
        if( $('#'+this._options.previewImgId).size() ==1 ){
            var initImgUrl = $('#'+this._options.hiddenFieldId).val();
            if(initImgUrl != ''){
                $('#'+thisObj._options.previewImgId).html('<img src="'+thisObj._options.imgServerAddr + initImgUrl+'" style="width: 100%; height: 100%;" />');
            }
        }
    }

    /**
     * 初始WebUpload
     * @private
     */
    __obj_Construct.prototype._init = function () {
        //this对象
        var thisObj = this;

        //使用plupload构造图片上传
        this._uploader = new plupload.Uploader({
            browse_button : this._options.uploadBtnId,
            multi_selection: this._options.multiFile,
            file_data_name: this._uploadFileName, //服务端接收文件的名称
            flash_swf_url : this._options.basePath+'/static/plupload/lib/plupload-2.1.2/js/Moxie.swf',
            silverlight_xap_url : this._options.basePath+'/static/plupload/lib/plupload-2.1.2/js/Moxie.xap',
            url : this._uploadURL,
            filters: {
                mime_types : [{ title : "Image files", extensions : "jpg,gif,png,bmp" }],
                max_file_size : this._options.maxFileSize, //最大只能上传10mb的文件
                prevent_duplicates : false //不允许选取重复文件
            },
            init: {
                FilesAdded: function(up, files) {
                    //在上传新文件时如果已经上传了图片，先删除
                    if( thisObj._uploadSuccFileURL ){
                        thisObj._removeUploadSuccFile(function () {
                            thisObj._addFile(up, files);
                        });
                    }else{
                        thisObj._addFile(up, files);
                    }
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
                            //保存已上传图片的记录
                            thisObj._uploadSuccFileURL = response.data.url;
                            //是否需要裁剪图片
                            if(thisObj._options.needCut){
                                var imgurl = thisObj._options.imgServerAddr + response.data.url;
                                thisObj._goCutImg(imgurl);
                            }else{//不需要裁剪图片
                                //图片预览
                                $('#'+thisObj._options.previewImgId).html('<img src="'+thisObj._options.imgServerAddr + response.data.url+'" style="width: 100%; height:100%;" />');

                                //设置隐藏域的值
                                $('#'+thisObj._options.hiddenFieldId).val(response.data.url);
                            }
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
     * 进入图片裁剪界面
     * @private
     */
    __obj_Construct.prototype._goCutImg = function (imgUrl) {
        var thisObj = this;
        var _wh = $(window).height();
        var _ww = $(window).width();
        _ww = _ww > 850 ? 850 : (_ww-40);
        _wh = _wh > 650 ? 650 : (_wh-40);

        var id_dialog = "whgDialog-ImgCut";
        var cut_img_url = this._options.basePath+"/comm/cutImg?w="+this._options.cutWidth+"&h="+this._options.cutHeight+"&imgurl="+encodeURIComponent(imgUrl)+"&ww="+_ww+"&wh="+_wh;
        if( $('#'+id_dialog).size() > 0 ){
            $('#'+id_dialog).dialog('destroy');
            $('#'+id_dialog).remove();
        }
        if( $('#Btn_'+id_dialog).size() > 0 ){
            $('#Btn_'+id_dialog).remove();
        }
        var dialogHTML = '<div id="'+id_dialog+'" style="overflow:hidden"></div>'+
        '<div id="Btn_'+id_dialog+'" style="text-align: center; display: none">'+
        '    <div style="display: inline-block; margin: 0 auto">'+
        '        <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-submit" iconCls="icon-save" id="'+id_dialog+'_ok">确 认</a>'+
        '        <a href="javascript:void(0)" class="easyui-linkbutton whgff-but-clear" iconCls="icon-cancel" id="'+id_dialog+'_no">取 消</a>'+
        '    </div>'+
        '</div>';
        $(dialogHTML).appendTo($("body"));
        $.parser.parse('#Btn_'+id_dialog);
        var _iframe = '<iframe id="iframe_'+id_dialog+'" name="iframe_'+id_dialog+'" scrolling="auto" frameborder="0"  src="' + cut_img_url+ '" style="width:100%; height:100%;"></iframe>';
        $('#'+id_dialog).dialog({
            title: '裁剪图片',
            closable: false,
            width: _ww,
            height: _wh,
            border: false,
            modal: true,
            content: _iframe,
            buttons: '#Btn_'+id_dialog
        });
        $('#'+id_dialog+'_no').on("click", function () {
            thisObj._removeUploadSuccFile(function () {});
            $('#'+id_dialog).dialog('close');
        });
        $('#'+id_dialog+'_ok').on("click", function () {
            var cutObj = $("#iframe_"+id_dialog)[0].contentWindow.JcropApi.tellSelect();
            $.ajax({
                type: 'POST',
                cache: false,
                async: false,
                url: thisObj._options.basePath+'/comm/uploadCut',
                data: {imgurl:thisObj._uploadSuccFileURL,x:cutObj.x, y:cutObj.y, x2:cutObj.x2, y2:cutObj.y2, w:thisObj._options.cutWidth, h:thisObj._options.cutHeight},
                success: function (data) {
                    if(data && data.success == '1'){
                        var _url = data.data.url;
                        //保留值
                        thisObj._uploadSuccFileURL = _url;
                        //图片预览
                        $('#'+thisObj._options.previewImgId).html('<img src="'+thisObj._options.imgServerAddr + _url+'" style="width: 100%; height: 100%;" />');

                        //设置隐藏域的值
                        $('#'+thisObj._options.hiddenFieldId).val(_url);
                        //关闭弹出层
                        $('#'+id_dialog).dialog('close');
                    }else{
                        $.messager.alert('提示', '操作失败:'+data.errormsg+'！', 'error');
                    }
                }
            });
        });
    }

    /**
     * 上传图片之前获取签名参数，传递给plupload
     * @private
     */
    __obj_Construct.prototype._beforeUpload = function (file) {
        this._uploader.setOption({
            'multipart_params': {
                uploadFileType: this._options.uploadFileType,
                needCut: this._options.needCut,
                cutWidth: this._options.cutWidth,
                cutHeight: this._options.cutHeight
            }
        });
    }

    /**
     * 删除已经上传的图片
     * @private
     */
    __obj_Construct.prototype._removeUploadSuccFile = function (fn) {
        var thisObj = this;
        if(this._uploadSuccFileURL){
            $.ajax({
                type: 'POST',
                cache: false,
                async: false,
                url: this._options.basePath+'/comm/delUpload',
                data: {uploadURL:this._uploadSuccFileURL},
                success: function (data) {
                    if(data && data.success == '1'){
                        if(!thisObj._options.needCut){
                            //图片预览
                            $('#'+thisObj._options.previewImgId).html('');
                            //设置隐藏域
                            $('#'+thisObj._options.hiddenFieldId).val('');
                        }

                        //保存已上传图片的记录
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
     * 添加图片文件
     * @param up
     * @param files
     * @private
     */
    __obj_Construct.prototype._addFile = function (up, files) {
        for(var i=0; i<files.length; i++){
            var file = files[i];
            $('<span id="span_'+file.id+'">'+file.name+' 完成<b id="'+file.id+'"></b>%</span>').appendTo($('#'+this._options.uploadBtnId).parent('i'));
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
        $('#'+thisObj._options.previewImgId).html('');
        //设置隐藏域
        $('#'+thisObj._options.hiddenFieldId).val('');

        //保存已上传图片的记录
        thisObj._uploadSuccFileURL = false;
        $('#'+thisObj._options.uploadBtnId).parent('i').children('span').remove();

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
