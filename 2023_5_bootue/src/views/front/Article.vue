<template>
  <div style="color: #666">
    <div style="margin: 10px 0">
      <el-input size="small" style="width: 300px" placeholder="请输入名称" suffix-icon="el-icon-search"
                v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load" size="small">搜索</el-button>
      <el-button type="warning" @click="reset" size="small">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <div style="padding: 10px 0; border-bottom: 1px dashed #ccc" v-for="item in tableData" :key="item.id">
        <div class="pd-10" style="font-size: 20px; color: #3F5EFB; cursor: pointer"
             @click="$router.push('/front/articleDetail?id=' + item.id)">{{ item.name }}
        </div>
        <div style="font-size: 14px; margin-top: 10px">
          <i class="el-icon-user-solid"></i> <span>{{ item.user }}</span>
          <i class="el-icon-time" style="margin-left: 10px"></i> <span>{{ item.time }}</span>
        </div>
      </div>
    </div>

    <div style="padding: 10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>

import axios from 'axios'
import { artivleDel, artivleDels, artivlePage, artivleSaveOrUpdate } from '@/api/artivleApi'

export default {
  name: 'ArticleView',
  data () {
    return {
      form: {},
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      dialogFormVisible: false,
      teachers: [],
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {},
      content: '',
      viewDialogVis: false
    }
  },
  created () {
    this.load()
  },
  methods: {
    view (content) {
      this.content = content
      this.viewDialogVis = true
    },
    // 绑定@imgAdd event
    imgAdd (pos, $file) {
      const $vm = this.$refs.md
      // 第一步.将图片上传到服务器.
      const formData = new FormData()
      formData.append('file', $file)
      axios({
        url: this.$api + '/file/upload',
        method: 'post',
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' }
      }).then((res) => {
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        $vm.$img2Url(pos, res.data)
      })
    },
    load () {
      const params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.name
      }
      artivlePage(params).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.records
          this.total = res.data.total
        }
      })
    },
    changeEnable (row) {
      artivleSaveOrUpdate(row).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error('保存失败')
        }
      })
    },
    handleAdd () {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit (row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    del (id) {
      artivleDel({ id: id }).then(res => {
        if (res.code === '200') {
          this.$message.success('删除成功')
          this.load()
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    handleSelectionChange (val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch () {
      const ids = this.multipleSelection.map(v => v.id) // [{}, {}, {}] => [1,2,3]
      artivleDels({ ids: ids }).then(res => {
        if (res.code === '200') {
          this.$message.success('批量删除成功')
          this.load()
        } else {
          this.$message.error('批量删除失败')
        }
      })
    },
    save () {
      artivleSaveOrUpdate(this.form).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error('保存失败')
        }
      })
    },
    reset () {
      this.name = ''
      this.load()
    },
    handleSizeChange (pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange (pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    download (url) {
      window.open(url)
    }
  }
}
</script>

<style scoped>

</style>
