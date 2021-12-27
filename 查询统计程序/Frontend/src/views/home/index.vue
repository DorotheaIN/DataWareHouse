<template>
  <div class="card">
    <Card>
      <p slot="title" class="card-title">
          <Icon type="home"></Icon>
          <span>数据仓库大项目</span>
      </p>
      <div>
        <h2>数据来源:</h2>
        <div class="margin-bottom-10" style="margin-left:20px">
          <ul>
            <li>从以下链接下载Amazon电影和TV评价数据：<a target="_blank" href="https://nijianmo.github.io/amazon/index.html">https://nijianmo.github.io/amazon/index.html</a></li>
            <li>选择 "Small" subsets for experimentation 中的 Movies and TV 5-core (3,410,019 reviews)数据集</li>
            <li>可以结合第三方数据源或自行处理，从数据集中过滤掉TV类评价数据，找出所有电影类的评价数据。</li>
          </ul>
        </div>
        <br>
        <h2>项目要求:</h2>
        <div class="margin-bottom-10" style="margin-left:20px">
          <ul>
            <li>针对电影及其周边信息，建立基于关系型数据仓库、分布式文件型数据仓库系统和图数据库的数据仓库系统，建立数据治理体系，并进行系统性能比对</li>
            <li>i.	能够从数据来源处获取数据，并进行数据预处理</li>
            <li>ii.	建立关系型数据仓库存储模型，存储数据</li>
            <li>iii.建立分布式文件系统存储模型，存储数据</li>
            <li>iv.	建立图数据库存储模型，存储数据</li>
            <li>v.  在数据展现的界面上能够执行数据应用中的查询，并将在三种不同存储模型上的执行时间以数值的方式和图表的方式显示在界面</li>
            <li>vi. 建立数据治理体系</li>
          </ul>
        </div>
        <br>
        <h2>数据来源:</h2>
        <div class="margin-bottom-10" style="margin-left:20px">
            <ul>
              <li>数据来源自Snap的文本文件和Amazon网站，数据包括但不限于以下信息：</li>
              电影ID，评论用户ID，评论用户ProfileName，评论用户评价Helpfulness，评论用户Score，评论时间Time，评论结论Summary，评论结论Text，电影演员，电影上映时间，电影风格，电影导演，电影主演，电影演员，电影版本等信息
            </ul>
        </div>
        <br>
        <h2>数据使用场景:</h2>
        <div class="margin-bottom-10" style="margin-left:20px">
            <ul>
              <li>i.	  按照时间进行查询及统计（例如XX年有多少电影，XX年XX月有多少电影，XX年XX季度有多少电影，周二新增多少电影等）</li>
              <li>ii.	  按照电影名称进行查询及统计（例如 XX电影共有多少版本等）</li>
              <li>iii.	按照导演进行查询及统计（例如 XX导演共有多少电影等）</li>
              <li>iv.   按照演员进行查询及统计（例如 XX演员主演多少电影，XX演员参演多少电影等）</li>
              <li>v.	  按照电影类别进行查询及统计（例如 Action电影共有多少，Adventure电影共有多少等）</li>              
              <li>vi.	  按照演员和导演的关系进行查询及统计（例如经常一起合作的演员有哪些，经常一起合作的导演和演员有哪些）</li>  
              <li>vii.	按照电影类别进行查询及统计（例如 Action电影共有多少，Adventure电影共有多少等）</li>  
              <li>viii. 按照用户评价进行查询及统计（例如用户评分3分以上的电影有哪些，用户评价中有正面评价的电影有哪些等）</li>
              <li>ix.	  按照上述条件的组合查询和统计</li>
            </ul>
        </div>
        <br>
      </div>
    </Card>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>
  .card{
    margin-right: 1%;
    margin-left:1% ;
    margin-top: 1%;
    margin-bottom: 1%;
  }
</style>