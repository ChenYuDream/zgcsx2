<style>
    .plupload_message {
        position : absolute;
        top : -1px;
        left : -1px;
        height : 100%;
        width : 90%;
    }
</style>
<!--上传文件tab-->
<div ms-controller="upload" class="ms-controller">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                <#--<span>文件类型：</span>-->
                    <select ms-duplex="@opt.uploadFileType" class="form-control input-sm js-uploadFileType">
                        <option value="">文件类型:</option>
                        <option :for="(index,el) in @uploadFileTypes" :attr="{value:el.itemValue}" :if="el.itemValue < 100">{{el.itemText}}</option>
                    </select>
                </li>
                <li>
                <#--<span>上传对象：</span>-->
                    <select ms-duplex="@opt.uploadType" class="form-control input-sm js-uploadType">
                        <option value="">上传对象:</option>
                        <option :for="(index,el) in @uploadTypes" :attr="{value:el.id}">{{el.value}}</option>
                    </select>
                </li>
            </ul>
            <div class="toolList pull-left">
                <ul class="clearfix">
                    <li>
                        <a :click="@next()" class="item-btn" href="javascript:void(0);">
                            <i class="iconfont"></i>确认
                        </a>
                    </li>
                    <li>
                        <a :click="@clear()" class="item-btn" href="javascript:void(0);">
                            <i class="iconfont icon-qingkong"></i>重置
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- 上传模块-->
    <div id="upload_up"></div>

</div>

<script type="text/javascript">
    var _opt = {
        uploadFileType : '', //文件类型
        uploadType : ''     //上传对象（1、年级  2、班级）
    };
    var vmUpload = avalon.define({
        $id: 'upload',
        initOpt : {
            clazzId : '',
            gradeId : '',
            courseId : '',
            optionalCourseId : '',
            createType : '2'   //操作人类型:1、学生  2、教师
        },
        opt: _opt,
        uploadFileTypes : [],
        uploadTypes  : [{id:1, value:"年级"}, {id:2, value:"班级"}],
        showUpload : false, //是否显示上传控件
        loadCodeMap : function () { //文件类型
            $.ajax({
                url: "${ctx}/api/codemap",
                data : {code : "sys_fileType"},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmUpload.uploadFileTypes = data;
                }
            });
        },
        isShow : function (boolean) {
            vmUpload.showUpload = boolean;
            $(".js-uploadFileType").attr("disabled", boolean);
            $(".js-uploadType").attr("disabled", boolean);
        },
        next : function () {
            if(this.opt.uploadFileType && this.opt.uploadType){ //值不为空
                if(vmUpload.$model.showUpload === false){ //未被
                    vmUpload.isShow(true);
                    initUpload();
                }
            }else{
                vmUpload.isShow(false);

                if(!this.opt.uploadFileType){
                    return msgFn.msg(false, "请选择文件类型");

                }
                if(!this.opt.uploadType){
                    return msgFn.msg(false, "请选择上传对象");

                }
            }
        },
        clear : function () {
            vmUpload.opt = _opt;
            vmUpload.isShow(false);

            $upload.remove();
        },
        loadOpt : function(){ //加载参数
            if(vmCourseResource){
                vmUpload.initOpt.clazzId = vmCourseResource.$model.opt.clazzId;
                vmUpload.initOpt.gradeId = vmCourseResource.$model.opt.gradeId;
                vmUpload.initOpt.courseId = vmCourseResource.$model.opt.courseId;
                vmUpload.initOpt.optionalCourseId = vmCourseResource.$model.opt.optionalCourseId;
            }
        }
    });

    vmUpload.loadOpt();
    vmUpload.loadCodeMap();
</script>
<script type="text/javascript">
    //上传组件初始化;
    var $upload;
    function initUpload(){
        var $div = "<div id=\"uploader\"><p>你的浏览器没有Flash,Silverlight或HTML5支持。</p></div>";
        $("#upload_up").append($div);
        $upload = $("#uploader").plupload({
            // General settings
            runtimes: 'html5,flash,silverlight,html4',
            url: '${ctx}/courseResource/upload/resource',
            //指定文件上传时文件域的名称
            file_data_name : "files",

            // User can upload no more then 20 files in one go (sets multiple_queues to false)
            max_file_count: 20,

            chunk_size: '0',

            filters: {
                // Maximum file size
                max_file_size: '10mb',
                // Specify what files to browse for
                mime_types: [
                    {title: "Image files", extensions: "jpg,jpeg,gif,png"},
                    {title: "Doc files", extensions: "xls,xlsx,doc,docx,txt,ppt,pptx,pdf"},
                    {title: "Video files", extensions: "avi,mp4,rmvb,mp3"},
                    {title: "Zip files", extensions: "zip,rar"}
                ],
                //不能选择重复文件;
                prevent_duplicates: true
            },

            // Rename files by clicking on their titles
            rename: true,

            // Sort files
            sortable: true,

            // Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
            dragdrop: true,

            // Views to activate
            views: {
                list: true,
                thumbs: true, // Show thumbs
                active: 'thumbs'
            },
            // Flash settings
            flash_swf_url: '${ctx}/static/common/plug-in/plupload-2.3.6/js/Moxie.swf',

            // Silverlight settings
            silverlight_xap_url: '${ctx}/static/common/plug-in/plupload-2.3.6/js/Moxie.xap',
            multipart_params : {
                uploadFileType : vmUpload.$model.opt.uploadFileType,
                uploadType : vmUpload.$model.opt.uploadType,
                clazzId : vmUpload.$model.initOpt.clazzId,
                gradeId : vmUpload.$model.initOpt.gradeId,
                courseId : vmUpload.$model.initOpt.courseId,
                optionalCourseId : vmUpload.$model.initOpt.optionalCourseId,
                createType : vmUpload.$model.initOpt.createType
            },
            init: {
                UploadComplete: function (uploader, files) {
                    var count = 0;
                    for(var i = 0;i < files.length; i++ ){
                        if(files[i].percent == 100){
                            count++;
                        }
                    }
                    if(count > 0) {
                        msgFn.msg(true, "您已成功上传"+files.length+"个文件");
                    }
                    $("#uploader_buttons").html("<p>已上传 "+count+"/"+files.length+" 个文件</p>");
                },
                FilesAdded:function (uploader,files) {
                    for(var i=0;i<files.length;i++){
                        var file = files[i];
                        if(file.name.length > 50){
                            msgFn.msg(false, "文件名称长度超过50个字符");
                            uploader.removeFile(file);
                        }
                    }
                },
                Error:function(uploader,errObject){
                    //msgFn.msg(false, "上传出现异常");
                }
            }
        });
    }
</script>