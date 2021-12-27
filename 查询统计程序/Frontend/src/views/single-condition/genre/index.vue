<template>
  <div class="app-container">
    <el-card style="width:100%; margin:0 auto; padding-top: 20px">
      <el-form label-width="120px" style="margin-top:-1%">          
        <el-row>
          <el-col :span="7" :push='1'><div class="grid-content">
            <el-radio v-model="radio" label="1">根据类别查询电影</el-radio>
          </div></el-col>
          <el-col :span="5" :push='1'><div class="grid-content">
            <el-autocomplete
                class="inline-input"
                v-model="form.genre"
                :fetch-suggestions="querySearch"
                placeholder="请输入类别"
                clearable
                style="width:100%"
              ></el-autocomplete>
          </div></el-col>
        </el-row>
        <el-col :span="7" :push='1'><div class="grid-content">
        </div></el-col>
        <el-row>
        </el-row>           
        <el-row>
          <el-col :span="24" :push='1'><div class="grid-content">
            <el-radio v-model="radio" label="2">统计各个类别电影数量的Top榜</el-radio>
          </div></el-col>
        </el-row> 
        <el-row style="margin-top:-1%">
          <el-col :span="24" :push='22'><div class="grid-content">
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </div></el-col>
        </el-row>
      </el-form>
    </el-card>
    <el-card style="width:100%; margin:2% auto; height: 410px;">
      <el-row>
        <el-col :span="13"><div class="grid-content">          
          <el-table
            :data="tableData"
            :stripe="true"
            height="330"
            :highlight-current-row='true'
            style="width: 95%; margin: 0; height: 330px;"
            v-if="radiolabel==1">
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
              width="90"
              :formatter="runtimeFormatter">
            </el-table-column>
            <el-table-column
              prop="releasedate"
              label="发行日期"
              align='center'>
            </el-table-column>
          </el-table>
          <el-table
            :data="tableData"
            :stripe="true"
            height="330"
            :highlight-current-row='true'
            style="width: 95%; margin: 0; height: 330px;"
            v-if="radiolabel==2">
            <el-table-column
              prop="genres"
              label="类别"
              align='center'
              width="300">
            </el-table-column>
            <el-table-column
              prop="num"
              label="电影数"
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

        <el-col :span="10"><div class="grid-content">
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
      form:{
        genre:'',
      },      
      genres:[],
      tableData: [],
      radio: '',
      radiolabel: 1,
      num: "",
      supposedToDraw: -1,
    }
  },
  methods: {
    onSubmit() {
      this.tableData = [];
      if(this.radio==1){
        this.$axios
          .get("/getMoviesByGenresFromD2", {
            params: {
              genres: this.form.genre
            }
          })
          .then((response)=>{
            this.tableData = response.data.data;
            console.log(this.tableData);
            console.log(response.data);
            this.database.mysqlaTime=response.data.time;
            this.querySucceed("MySQL(反范式)");
          })
          .catch(error => {
            ++this.supposedToDraw;
          });
        this.$axios
          .get("/getMoviesByGenresFromD1", {
            params: {
              genres: this.form.genre
            }
          })
          .then((response)=>{
            this.database.mysqlbTime=response.data.time;
            this.querySucceed("MySQL");
            // this.draw()
          })
          .catch(error => {
            ++this.supposedToDraw;
          });
        // this.$axios
        //   .get("/getMoviesByGenresFromHive", {
        //     params: {
        //       genres: this.form.genre
        //     }
        //   })
        //   .then((response)=>{
        //     this.database.hiveTime=response.data.time;
        //     this.querySucceed("Hive");
        //     // this.draw()
        //   })
        //   .catch(error => {
        //     ++this.supposedToDraw;
        //   });
        this.radiolabel=1;
      }
      else if(this.radio==2){
        this.$axios
          .get("/getGenresRankingFromD2")
          .then((response)=>{
            this.tableData = response.data.data;
            this.database.mysqlaTime=response.data.time;
            this.querySucceed("MySQL(反范式)");
            // this.draw();
          })
          .catch(error => {
            ++this.supposedToDraw;
            this.queryFail("MySQL(反范式)");
          });
        this.$axios
          .get("/getGenresRankingFromD1")
          .then((response)=>{
            this.database.mysqlbTime=response.data.time;
            this.querySucceed("MySQL");
            // this.draw();
          })
          .catch(error => {
            ++this.supposedToDraw;
            this.queryFail("MySQL");
          });
        // this.$axios
        //   .get("/getGenresRankingFromHive")
        //   .then((response)=>{
        //     this.database.hiveTime=response.data.time;
        //     this.querySucceed("Hive");
        //     // this.draw();
        //   })
        //   .catch(error => {
        //     ++this.supposedToDraw;
        //     this.queryFail("Hive");
        //   });
        this.radiolabel=2;
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
          data: ['MySQL', 'MySQL(反范式)']
        },
        yAxis: {},
        series: [
          {
            name: '查询时间',
            type: 'bar',
            data: [this.database.mysqlbTime, this.database.mysqlaTime],
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
    querySearch(queryString, cb) {
      var genres = this.genres;
      var results = queryString ? genres.filter(this.createFilter(queryString)) : genres;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (genre) => {
        return (genre.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    loadAll() {
      this.$axios
        .get("/getGenresRankingFromD2")
        .then((response)=>{
          this.genres=[];
            for(let i = 0; i < response.data.data.length; ++i){
              this.genres.push({
                "value":  response.data.data[i].genres
              })
            }
          })
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
        if(this.supposedToDraw==2){
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
    this.loadAll();
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
