<template>
  <div class="app-container">
    <el-card style="width:100%; margin:0 auto; padding-top: 20px">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="演员">
          <el-row :gutter="10">
            <el-col :span="6">
              <div class="grid-content">
                <el-select v-model="formMark.actor" placeholder="">
                  <el-option label="演员参演" value="actor" />
                  <el-option label="演员主演" value="mainActor" />
                </el-select>
              </div>
            </el-col>
            <el-col :span="12" v-if="formActorMark">
              <div class="grid-content">
                <el-input v-model="form.actor.actor" placeholder="请输入演员名称" />
              </div>
            </el-col>
            <el-col :span="12" v-if="formMainActorMark">
              <div class="grid-content">
                <el-input v-model="form.actor.mainActor" placeholder="请输入演员名称" />
              </div>
            </el-col>
            <el-col :span="2" :push="1" >
              <div class="grid-content">
                <el-button type="primary" @click="onSubmit">查询</el-button>
              </div>
            </el-col>
          </el-row>     
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="width:100%; margin:2% auto; height: 410px;">
      <el-row>
        <el-col :span="15"><div class="grid-content">
          <el-table
            :data="tableData"
            :stripe="true"
            height="330"
            :highlight-current-row='true'
            style="width: 95%; margin: 0; height: 330px;">
            <el-table-column
              prop="movie_id"
              label="电影ID"
              align='center'
              width="90">
            </el-table-column>
            <el-table-column
              prop="title"
              label="电影名称"
              align='center'
              width="300">
            </el-table-column>
            <el-table-column
              prop="runtime"
              label="电影时长"
              align='center'
              width="180"
              :formatter="runtimeFormatter">
            </el-table-column>
            <el-table-column
              prop="releasedate"
              label="发行日期"
              align='center'>
            </el-table-column>
          </el-table>
          <el-pagination
            layout="total"
            style="margin: 0 auto;"
            :total="tableData.length">
          </el-pagination>
        </div></el-col>

        <el-col :span="1"><div class="grid-content">
          <el-divider direction="vertical" ></el-divider>
        </div></el-col>

        <el-col :span="8"><div class="grid-content">
          <div id="myChart" :style="{width: '100%', height: '400px'}"></div>
        </div></el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      database:{
        mysqlbTime: 0,
        mysqlaTime: 0,
        hiveTime: 0,
        neo4jTime: 0,
      },
      form: {
        actor:{
          actor: '',
          mainActor: '',
        },
      },
      formMark:{
        actor: 'actor',
      },
      tableData: [],
      supposedToDraw: -1,
    }
  },
  computed:{
    formActorMark(){
      return this.formMark.actor=="actor";
    },
    formMainActorMark(){
      return this.formMark.actor=="mainActor";
    },
  },
  methods: {
    onSubmit() {
      if(this.formMark.actor=='actor'){
        this.$axios
          .get("/getMoviesByActorFromD1", {
            params: {
              name: this.form.actor.actor
            }
          })
          .then((response)=>{
            this.tableData = response.data.data;
            this.database.mysqlbTime=response.data.time;
            this.querySucceed("MySQL");
            // this.draw();
          })
          .catch(error => {
            ++this.supposedToDraw;
            this.queryFail("MySQL");
          });
        this.$axios
          .get("/getMoviesByActorFromD2", {
            params: {
              name: this.form.actor.actor
            }
          })
          .then((response)=>{
            this.database.mysqlaTime=response.data.time;
            this.querySucceed("MySQL(反范式)");
            // this.draw();
          })
          .catch(error => {
            ++this.supposedToDraw;
            this.queryFail("MySQL(反范式)");
          });
        this.$axios
          .get("/getMoviesByActorFromHive", {
            params: {
              name: this.form.actor.actor
            }
          })
          .then((response)=>{
            this.database.hiveTime=response.data.time;
            this.querySucceed("Hive");
            // this.draw();
          })
          .catch(error => {
            ++this.supposedToDraw;
            this.queryFail("Hive");
          });
      }
      else if(this.formMark.actor=='mainActor'){
        this.$axios
          .get("/getMoviesLeadingByActorFromD1", {
            params: {
              name: this.form.actor.mainActor
            }
          })
          .then((response)=>{
            this.tableData = response.data.data;
            this.database.mysqlbTime=response.data.time;
            this.querySucceed("MySQL");
            // this.draw();
          })
          .catch(error => {
            ++this.supposedToDraw;
            this.queryFail("MySQL");
          });
        this.$axios
          .get("/getMoviesLeadingByActorFromD2", {
            params: {
              name: this.form.actor.mainActor
            }
          })
          .then((response)=>{
            this.database.mysqlaTime=response.data.time;
            this.querySucceed("MySQL(反范式)");
            // this.draw();
          })
          .catch(error => {
            ++this.supposedToDraw;
            this.queryFail("MySQL(反范式)");
          });
        this.$axios
          .get("/getMoviesLeadingByActorFromHive", {
            params: {
              name: this.form.actor.mainActor
            }
          })
          .then((response)=>{
            this.database.hiveTime=response.data.time;
            this.querySucceed("Hive");
            // this.draw();
          })
          .catch(error => {
            ++this.supposedToDraw;
            this.queryFail("Hive");
          });
      }
      
    },
    draw(){
      // 初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: ''
        },
        tooltip: {},
        legend: {
          data: ['查询时间']
        },
        xAxis: {
          data: ['MySQL', 'MySQL(反范式)', 'HIVE']
        },
        yAxis: {},
        series: [
          {
            name: '查询时间',
            type:'bar',
            data: [this.database.mysqlbTime, this.database.mysqlaTime, this.database.hiveTime],
            itemStyle: {
							normal: {
								label: {
									show: true, //开启显示
									position: 'top', //在上方显示
									textStyle: { //数值样式
										color: 'black',
										fontSize: 10
									}
								}
							}
						}
          }
        ]
      };
      //防止越界，重绘canvas
      window.onresize = myChart.resize;
      myChart.setOption(option);//设置option
    },
    querySucceed(database) {
      this.$notify({
        title: '成功',
        message: "成功获取"+database+"的查询结果",
        type: 'success'
      });
    },
    queryFail(database) {
      this.$notify.error({
        title: '错误',
        message: "未能获取"+database+"的查询结果",
      });
    },
    runtimeFormatter(row,column){
      let runtime = row.runtime;
      if(runtime==0){
        return '-'
      }
      return runtime;
    },
  },
  watch:{
    database: {
      handler: function (newd, oldd) {
        ++this.supposedToDraw;
      },
      deep: true,
      immediate: true,
    },
    supposedToDraw: {
      handler: function(newd,oldd){
        if(this.supposedToDraw==3){
          this.draw();
          this.supposedToDraw=0;
        }
      },
      deep: true,
      immediate: true,
    }
  },
  mounted(){
    this.draw();
  }
}
</script>

<style lang="scss" scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .el-divider--vertical{
    display: inline-block;
    width: 1px;
    height: 31em;
    margin: 0 8px;
    vertical-align: middle;
    position: relative;
  } 
</style>
