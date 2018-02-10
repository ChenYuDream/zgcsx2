<div class="dialogBox" ms-important="evenAddress">
	<div class="row">
		<!-- tableTitle S-->
		<div class="col-sm-2">
			<div class="tabTitle ">
				<ul>
					<li :for="(index,el) in @firstDataList" 
						:click="@changeFn(index,el,0)" 
						:class="[(@firstActive == index ? 'active':'')]">{{el.name}}</li>
				</ul>
			</div>
		</div>
		<div class="col-sm-2">
			<div class="tabTitle ">
				<ul>
					<li :for="(index,el) in @secondDataList"
						:click="@changeFn(index,el,1)" 
						:class="[(@secondActive == index ? 'active':'')]">{{el.name}}</li>
				</ul>
			</div>
		</div>
		<div class="col-sm-2">
			<div class="tabTitle ">
				<ul>
					<li :for="(index,el) in @thirdDataList"
						:click="@changeFn(index,el,2)" 
						:class="[(@thirdActive == index ? 'active':'')]">{{el.name}}</li>
				</ul>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="tabTitle ">
				<ul>
					<li :for="(index,el) in @fourthDataList"
						:click="@changeFn(index,el,3)" 
						:class="[(@fourthActive == index ? 'active':'')]">{{el.name}}</li>
				</ul>
			</div>
		</div>
		<!-- tableTitle E-->
	</div>
</div>
<script type="text/javascript">
var vmAddress = avalon.define({
	$id:'evenAddress',
	port:{},
	firstDataList:[],
	secondDataList:{},
	thirdDataList:{},
	fourthDataList:{},
	firstActive:0,
	secondActive:0,
	thirdActive:0,
	fourthActive:0,
	loadListFn:function(){
		$.ajax({
			url:this.port.url,
			type: 'POST',
            dataType: 'json',
            success:function(data){
            	vmAddress.firstDataList = data.result;
            	vmAddress.secondDataList = data.result[vmAddress.firstActive].subTree;
            	vmAddress.thirdDataList = data.result[vmAddress.firstActive].subTree[vmAddress.secondActive].subTree;
            	vmAddress.fourthDataList = data.result[vmAddress.firstActive].subTree[vmAddress.secondActive].subTree[vmAddress.thirdActive].subTree;
            	var _dz1 =  data.result[vmAddress.firstActive].name,
            		_dz2 =  data.result[vmAddress.firstActive].subTree[vmAddress.secondActive].name,
            		_dz3 =  data.result[vmAddress.firstActive].subTree[vmAddress.secondActive].subTree[vmAddress.thirdActive].name,
            		_dz4 =  data.result[vmAddress.firstActive].subTree[vmAddress.secondActive].subTree[vmAddress.thirdActive].subTree[vmAddress.fourthActive].name;
            		vmAddress.port.echoData = _dz1 + ' > ' + _dz2 + ' > ' + _dz3 + ' > ' + _dz4;
            }
		});
	},
	changeFn:function(index,el,num){
		vmAddress.loadListFn(vmAddress.firstActive);
		switch(num){
			case 0:
				this.firstActive = index;
                this.secondActive = 0;
                this.thirdActive = 0 ;
                this.fourthActive = 0;
				break;
			case 1:
				this.secondActive = index;
                this.thirdActive = 0 ;
                this.fourthActive = 0;
				break;
			case 2:
				this.thirdActive = index;
                this.fourthActive = 0;
				break;
			case 3:
				this.fourthActive = index;
				break;
		}
	}
});
</script>