<!--上传组件单独提出来，放到需要的页面的最后 需要自定义：_ID变量-->
<!-- */ 上传组件
     */ 注:文件先后顺序 S -->
<link rel="stylesheet" href="${ctx}/static/common/plug-in/plupload-2.3.6/jquery-1.8.9-ui.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/common/plug-in/plupload-2.3.6/js/jquery.ui.plupload/css/jquery.ui.plupload.css">
<style>
    .ui-progressbar .ui-progressbar-value{
        background-color:#5FB878;
    }
</style>
<script type="text/javascript" src="${ctx}/static/common/plug-in/plupload-2.3.6/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/common/plug-in/plupload-2.3.6/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/plug-in/plupload-2.3.6/js/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/plug-in/plupload-2.3.6/js/i18n/zh_CN.js"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<script>
    //上传组件初始化;
    $(function() {
        $("#uploader").plupload({
            // General settings
            runtimes: 'html5,flash,silverlight,html4',
            url: vmMain.sysPath + '/notice/upload',

            // User can upload no more then 20 files in one go (sets multiple_queues to false)
            max_file_count: 20,

            chunk_size: '0',

            filters: {
                // Maximum file size
                max_file_size: '10mb',
                // Specify what files to browse for
                mime_types: [
                    {title: "Image files", extensions: "jpg,gif,png"},
                    {title: "Doc files", extensions: "xls,xlsx,doc,docx,txt,ppt,pptx"},
                    {title: "Video files", extensions: "avi,mp4,rmvb,mp3"},
                    {title: "Zip files", extensions: "zip,rar"},
                    { title : "Pdf files", extensions : "pdf" }
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
            flash_swf_url: '../plug-in/plupload-2.3.6/js/Moxie.swf',

            // Silverlight settings
            silverlight_xap_url: '../plug-in/plupload-2.3.6/js/Moxie.xap',
            multipart_params: {
                id: _ID
            },

            init: {
                BeforeUpload:function (uploader) {
                    //判断事件是否存在
                    if(!_isNew){
                        $.ajax({
                            url:"${ctx}/notice/detail/exist/"+_ID,
                            type:"post",
                            async:false,
                            success:function (data) {
                                if(data.code!='0'){
                                    uploader.destroy();
                                    layer.msg(data.msg,function(){
                                        location.reload();
                                        return ;
                                    });
                                }
                            }
                        });
                    }
                    var flag = true;
                    $.ajax({
                        url:"${ctx}/notice/check/attachment?noticeId="+_ID,
                        type:"post",
                        async:false,
                        success:function (data) {
                            if(data.code!='0'){
                                layer.msg(data.msg);
                                flag = false;
                                uploader.stop();
                            };
                        }
                    });
                    if(!flag){
                        var fileAll = $('#uploader').plupload('getFiles');
                        uploader.splice(0, fileAll.length);
                        refleshEventFile();
                    }
                },
                UploadComplete: function (up, files) {
                    try {
                        if (typeof(eval(refleshEventFile)) == "function") {
                            refleshEventFile();
                        }
                        var fileAll = $('#uploader').plupload('getFiles');
                        up.splice(0, fileAll.length);
                    } catch (e) {
                    }
                },
                FilesAdded:function (uploader,files) {
                    for(i=0;i<files.length;i++){
                        var file = files[i];
                        if(file.name.length>50){
                            var popup = $(
                                '<div class="plupload_message">' +
                                '<span class="plupload_message_close ui-icon ui-icon-circle-close" title="关闭"></span>' +
                                '<p><span class="ui-icon"></span>' + '<strong>' + '文件名称长度超过50个字符' + '</strong><br />'+
                                '<i style="word-wrap:break-word;word-break: break-all;">' + file.name+'不符合要求' + '</i>'+
                                '</p>' +
                                '</div>'
                            );
                            var type='error';
                            popup
                                .addClass('ui-state-' + (type === 'error' ? 'error' : 'highlight'))
                                .find('p .ui-icon')
                                .addClass('ui-icon-' + (type === 'error' ? 'alert' : 'info'))
                                .end();

                            $('.plupload_header', this.container).append(popup);
                            $('.plupload_message_close').on('click', function(){
                                $(this).parent().remove();
                            });
                            uploader.removeFile(file);
                        }
                    }
                }
            }
        });
    });
</script>