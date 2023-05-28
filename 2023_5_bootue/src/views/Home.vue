<template>
  <div>
    <el-row :gutter="10" :span="10" style="margin-bottom: 50px">
      <el-col :span="6">
        <el-card style="color: #409eff">
          <div><i class="el-icon-user-solid"></i> 用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #ff7800">
          <div><i class="el-icon-s-goods"></i> 销售总量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            10W件
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #9fe080">
          <div><i class="el-icon-s-data"> </i> 收益总额</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            ￥1000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #cf000d">
          <div><i class="el-icon-s-shop"></i> 门店总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            1021家
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row type="flex" justify="center" style="text-align: center">
      <el-col :span="12">
        <div id="main" style="height: 600px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pai" style="height: 600px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { echartsMembers } from '@/api/echartsApi'
export default {
  name: 'HomeView',
  data () {
    return {}
  },
  created () {},
  // 页面元素渲染之后
  mounted () {
    const chartDom = document.getElementById('main')
    const myChart = echarts.init(chartDom)
    // 柱状图
    const option = {
      title: {
        text: '各季度会员数量统计',
        subtext: '折线柱状统计图',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: ['第一季度', '第二季度', '第三季度', '第四季度']
      },
      yAxis: {
        type: 'value'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'XXX',
          data: [],
          type: 'line'
        },
        {
          name: 'XX',
          data: [],
          type: 'bar'
        },
        {
          name: 'XXX',
          data: [],
          type: 'line'
        },
        {
          name: 'XX',
          data: [],
          type: 'bar'
        }
      ]
    }
    echartsMembers().then((res) => {
      if (res.code === '200') {
        option.series[0].data = res.data
        option.series[1].data = res.data
        option.series[2].data = [5, 6, 7, 8]
        option.series[3].data = [5, 6, 7, 8]
        myChart.setOption(option)
      }
    })

    // 饼图
    const paiChartDom = document.getElementById('pai')
    const paiChart = echarts.init(paiChartDom)
    const option2 = {
      title: {
        text: '各季度用户注册统计',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '瑞幸',
          type: 'pie',
          radius: '50%',
          data: [],
          center: ['25%', '50%'],
          label: {
            // 饼图图形上的文本标签
            normal: {
              show: true,
              position: 'inner', // 标签的位置
              textStyle: {
                fontWeight: 300,
                fontSize: 16,
                color: '#fff'
              },
              formatter: '{d}%'
            }
          },
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        {
          name: '星巴克',
          type: 'pie',
          radius: '50%',
          data: [],
          center: ['75%', '50%'],
          label: {
            // 饼图图形上的文本标签
            normal: {
              show: true,
              position: 'inner', // 标签的位置
              textStyle: {
                fontWeight: 300,
                fontSize: 16,
                color: '#fff'
              },
              formatter: '{d}%'
            }
          },
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    }
    echartsMembers().then((res) => {
      console.log(res)
      if (res.code === '200') {
        option2.series[0].data = [
          { name: '第一季度', value: res.data[0] },
          { name: '第二季度', value: res.data[1] },
          { name: '第三季度', value: res.data[2] },
          { name: '第四季度', value: res.data[3] }
        ]
        option2.series[1].data = [
          { name: '第一季度', value: res.data[2] },
          { name: '第二季度', value: res.data[1] },
          { name: '第三季度', value: res.data[0] },
          { name: '第四季度', value: 5 }
        ]
        option.series[1].data = res.data
        paiChart.setOption(option2)
      }
    })
  },
  methods: {}
}
</script>

<style>
</style>
