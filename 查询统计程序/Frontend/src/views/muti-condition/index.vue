<template>
  <div class="app-container">
    <el-card style="width:70%; margin:0 auto; height:90%" >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="时间">
          <el-row :gutter="10">
            <el-col :span="6">
              <div class="grid-content">
                <el-select v-model="formMark.time" placeholder="">
                  <el-option label="年" value="year" />
                  <el-option label="年/月" value="month" />
                  <el-option label="年/季度" value="quarter" />
                </el-select>
              </div>
            </el-col>
            <el-col :span="12" v-if="this.formMark.time=='year'">
              <div class="grid-content">
                <el-input v-model="form.time.year" placeholder="请输入年份" />
              </div>
            </el-col>
            <el-col :span="6" v-else>
              <div class="grid-content">
                <el-input v-model="form.time.year" placeholder="请输入年份" />
              </div>
            </el-col>
            <el-col :span="6" v-if="formMonthMark">
              <div class="grid-content">
                <el-input v-model="form.time.month" placeholder="请输入月份" />
              </div>
            </el-col>
            <el-col :span="6" v-if="formQuarterMark">
              <div class="grid-content">
                <el-input v-model="form.time.quarter" placeholder="请输入季度" />
              </div>
            </el-col>
          </el-row>     
        </el-form-item>

        <el-form-item label="电影名">
          <el-row>
            <el-col :span="18"><div class="grid-content">
              <el-input v-model="form.name" placeholder="请输入电影名"/>
            </div></el-col>
          </el-row>        
        </el-form-item>

        <el-form-item label="导演">
          <el-row>
            <el-col :span="18"><div class="grid-content">
              <el-input v-model="form.director" placeholder="请输入导演名"/>
            </div></el-col>
          </el-row>        
        </el-form-item>

        <el-form-item label="演员">
          <el-row :gutter="10">
            <el-col :span="18">
              <div class="grid-content">
                <el-input v-model="form.actor" placeholder="请输入演员名称" />
              </div>
            </el-col>
          </el-row>     
        </el-form-item>

        <el-form-item label="电影类别">
          <el-row>
            <el-col :span="18"><div class="grid-content">
              <el-input v-model="form.genre" placeholder="请输入电影类别" />
              <!-- <el-autocomplete
                class="inline-input"
                v-model="form.genre"
                :fetch-suggestions="querySearch"
                placeholder="请输入电影类别"
                clearable                
                style="width:100%"
              ></el-autocomplete> -->
            </div></el-col>
          </el-row>        
        </el-form-item>

        <el-form-item label="评分">
          <el-row :gutter="10">
            <el-col :span="18">
              <div class="grid-content">
                <el-input-number 
                v-model="form.score"  
                size="medium" 
                style="width:100%" 
                :min="1" 
                :max="5" 
                label="描述文字" />
              </div>
            </el-col>
          </el-row>     
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmit" >查询</el-button>
          <!-- <el-button @click="onCancel">Cancel</el-button> -->
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog
      title="查询结果"
      :visible.sync="dialogVisible"
      width="80%"
      @open="open">
        <el-row>
          <el-col :span="13"><div class="grid-content">
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
                width="250">
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
            <el-pagination
              layout="total"
              style="margin: 0 auto;"
              :total="tableData.length">
            </el-pagination>
          </div></el-col>

          <el-col :span="1"><div class="grid-content">
            <!-- <el-divider direction="vertical" ></el-divider> -->
          </div></el-col>

          <el-col :span="10"><div class="grid-content">
            <div id="myChart" ref="b" :style="{width: '100%', height: '400px'}"></div>
          </div></el-col>
        </el-row>
      <!-- <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span> -->
    </el-dialog>
    
  </div>
</template>

<script>
export default {
  data() {
    return {
      database: {
        mysqlbTime: 0,
        mysqlaTime: 0,
        hiveTime: 0,
        neo4jTime: 0
      },
      form: {
        time: {
          year: null,
          month: null ,
          quarter: null ,
        },
        name: null ,
        director: null ,
        actor: null ,
        genre: null ,
        score: null ,
      },
      formMark:{
        time: 'year',
        actor: 'actor',
      },
      genres:[],
      tableData: [],
      dialogVisible: false,
      supposedToDraw: -1,

    }
  },
  computed:{
    formMonthMark(){
      return this.formMark.time=="month";
    },
    formQuarterMark(){
      return this.formMark.time=="quarter";
    },
    formActorMark(){
      return this.formMark.actor=="actor";
    },
    formMainActorMark(){
      return this.formMark.actor=="mainActor";
    },
  },
  methods: {
    onSubmit() {
      this.dialogVisible=true;
      this.$axios
        .get("/getMoviesByCombinationFromD1", {
          params: {
            actor: this.form.actor,
            director: this.form.director,
            genres: this.form.genre,
            month: this.form.time.month,
            quarter: this.form.time.quarter,
            score: this.score,
            title:this.form.name,
            year: this.form.time.year
          }
        })
        .then((response)=>{
          this.tableData = response.data.data;
          console.log(this.tableData);
          this.database.mysqlbTime=response.data.time;
          this.querySucceed("MySQL");
          // console.log(this.database.mysqlbTime);
          // this.draw();
        })
        .catch(error => {
          ++this.supposedToDraw;
          this.queryFail("MySQL");
        });
      this.$axios
        .get("/getMoviesByCombinationFromD2", {
          params: {
            title:this.form.name,
            actor: this.form.actor,
            director: this.form.director,
            genres: this.form.genre,
            month: this.form.time.month,
            quarter: this.form.time.quarter,
            score: this.score,
            year: this.form.time.year
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
      // this.$axios
      //   .get("/getMoviesByCombinationFromHive", {
      //     params: {
      //       title:this.form.name,
      //       actor: this.form.actor,
      //       director: this.form.director,
      //       genres: this.form.genre,
      //       month: this.form.time.month,
      //       quarter: this.form.time.quarter,
      //       score: this.score,
      //       year: this.form.time.year
      //     }
      //   })
      //   .then((response)=>{
      //     this.database.hiveTime=response.data.time;
      //     this.querySucceed("Hive");
      //     // this.draw();
      //   })
      //   .catch(error => {
      //     ++this.supposedToDraw;
      //     this.queryFail("Hive");
      //   });
    },
    // onCancel() {
    //   this.$message({
    //     message: 'cancel!',
    //     type: 'warning'
    //   })
    // },
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
      return [
        { "value": "Action" },
        { "value": "Adventure",},
      ];
    },
    handleSelect(item) {
      console.log(item);
    },
    draw(){
      // 初始化echarts实例
      let myChart = this.$echarts.init(this.$refs.b);
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
    open(){
      this.$nextTick(() => {
        //  执行echarts方法
          this.draw();
        })

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
        if(this.supposedToDraw==2){
          this.draw();
          this.supposedToDraw=0;
        }
      },
      deep: true,
      immediate: true,
    }
  },
  mounted() {
    this.genres = this.loadAll();
  }
}
</script>

<style scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .line{
    text-align: center;
  }
  .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 4px;
  }
  
</style>
