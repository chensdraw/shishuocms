<#assign menu="system">
<#assign submenu="system_basic">
<#include "/admin/head.ftl">
<style type="text/css">

</style>
<!--main content start-->
	<section id="main-content">
		<section class="wrapper">
		<!-- page start-->
			<div class="row">
			<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading">
 					修改目录
				</header>
				<div class="panel-body">
					<form id="update_folder_form" method="post" class="form-horizontal tasi-form" autocomplete="off" action="${basePath}/admin/folder/update.json">
					<fieldset>
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">目录Id</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="folderId" value="${folder.folderId}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">目录名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="folderName" value="${folder.name}"
									placeholder="目录名称" id="folderName">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">英文名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control round-input" name="folderEname" value="${folder.ename}"
									placeholder="英文名称" id="folderEname">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">父级标签</label>
							<div class="col-sm-10">
								<label class="col-sm-2 col-sm-2 control-label">
									${fatherFolderName}
								</label>
								<select class="form-control input-lg m-bot15" style="font-size:15px;width: 300px;" name="fatherId">
									<option value="0">未分类</option>
									<#list folderAll as folder>
										<option value="${folder.folderId}">${folder.name}</option>
									</#list>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">目录类型</label>
							<div class="col-sm-10">
								<label class="col-sm-2 col-sm-2 control-label">
									${folder.type}
								</label>
								<select class="form-control input-lg m-bot15" style="font-size:15px;width: 300px;" name="type">
									<option value= "article">文章</option>
									<option value= "photo">图片</option>
									<option value= "download">下载</option>
									<option value= "shop">商品</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">目录状态</label>
							<div class="col-sm-10">
								<#if folder.status=="hidden">
								<input type="radio" name="status" value="hidden" checked="checked"/>隐藏
								<input type="radio" name="status" value="display"/>显示
								<#else>
								<input type="radio" name="status" value="hidden"/>显示
								<input type="radio" name="status" value="display" checked="checked"/>隐藏
								</#if>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">目录等级</label>
							<div class="col-sm-10">
								<label class="col-sm-2 col-sm-2 control-label">
									${folder.rank}
								</label>
								<select class="form-control input-lg m-bot15" style="font-size:15px;width: 300px;" name="rank">
									<option value= "everyone">everyone</option>
									<option value= "login">login</option>
									<option value= "vip">vip</option>
									<option value= "admin">admin</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">目录序列</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="sort" value="${folder.sort}"
									placeholder="序列" id="sort">
							</div>
						</div>
						<div class="form-group">
                        	<label class="col-sm-2 col-sm-2 control-label"></label>
                            <button class="btn btn-danger" type="submit">修改</button>
                        </div>
					</fieldset>
				</form>
				</div>
			</section>
		</div>
		</div>
<!-- page end-->
		</section>
	</section>
 <!--main content end-->
 <script type="text/javascript">
	$(function() {
		$('#update_folder_form').ajaxForm({
			dataType : 'json',
			success : function(data) {
				if (data.result) {
					bootbox.alert("保存成功，将刷新页面", function() {
						window.location.reload();
					});
				}else{
					showErrors($('#update_folder_form'),data.errors);
				}
			}
		});
	});	
</script>
<#include "/admin/foot.ftl">