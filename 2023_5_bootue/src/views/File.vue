<template>
  <div v-loading="loading">
    <div style="margin: 10px 0">
      <div>
        <el-upload
          :action="$api + '/files/upload'"
          :show-file-list="showFileList"
          :on-success="handleFileUploadSuccess"
          :on-error="handleFileUploadError"
          :before-upload="handleBeforeFile"
          style="display: inline-block"
        >
          <el-button type="primary" class="ml-5"
            >上传文件<i class="el-icon-top"></i>
          </el-button>
        </el-upload>
        <el-popconfirm
          class="ml-5"
          confirm-button-text="确定"
          cancel-button-text="取消"
          icon="el-icon-info"
          icon-color="red"
          title="确定批量删除这些文件吗?"
          @confirm="delsFn"
        >
          <el-button type="danger" slot="reference"
            >批量删除 <i class="el-icon-remove-outline"></i>
          </el-button>
        </el-popconfirm>
      </div>
    </div>

    <el-table
      :data="tableData"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="id"> </el-table-column>
      <el-table-column prop="name" label="文件名称"> </el-table-column>
      <el-table-column prop="type" label="文件类型"> </el-table-column>
      <el-table-column prop="size" label="文件大小(kb)"> </el-table-column>
      <el-table-column label="下载">
        <template slot-scope="scope">
          <el-button
            type="primary"
            @click="download(scope.row)"
            v-if="scope.row.enable"
            >下载文件</el-button
          >
          <el-button type="primary" disabled v-else> 下载文件 </el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enable"
            active-color="#13ce66"
            inactive-color="#ccc"
            @change="changeStatic(scope.row)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-popconfirm
            class="ml-5"
            confirm-button-text="确定"
            cancel-button-text="取消"
            icon="el-icon-info"
            icon-color="red"
            title="确定删除该文件吗?"
            @confirm="handleDel(scope.row)"
          >
            <el-button
              slot="reference"
              type="danger"
              size="mini"
              v-if="scope.row.enable"
              >删除 <i class="el-icon-delete"></i
            ></el-button>
            <el-button
              slot="reference"
              type="danger"
              size="mini"
              v-else
              disabled
              >删除 <i class="el-icon-delete"></i
            ></el-button>
          </el-popconfirm>

          <el-popconfirm
            class="ml-5"
            confirm-button-text="确定"
            cancel-button-text="取消"
            icon="el-icon-info"
            icon-color="red"
            title="确定彻底删除该文件吗?删除后不可恢复"
            @confirm="completeDel(scope.row)"
          >
            <el-button
              slot="reference"
              type="danger"
              size="mini"
              v-if="scope.row.enable"
              >彻底删除 <i class="el-icon-delete"></i
            ></el-button>
            <el-button
              slot="reference"
              type="danger"
              size="mini"
              v-else
              disabled
              >彻底删除 <i class="el-icon-delete"></i
            ></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0; text-align: right">
      <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrent"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import {
  filesPage,
  filesDel,
  fileUpload,
  fileCompleteDel
} from '@/api/filesApi'
export default {
  name: 'FilesView',
  data () {
    return {
      tableData: [],
      name: '',
      showFileList: false,
      page: 1,
      pageSize: 5,
      total: 1,
      loading: false
    }
  },
  created () {
    this.load()
  },
  methods: {
    load () {
      const params = {
        pageNum: this.page,
        pageSize: this.pageSize,
        name: this.name
      }
      filesPage(params).then((res) => {
        if (res.code === '200') {
          this.total = res.data.total
          this.tableData = res.data.records
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    // 下载
    download (row) {
      console.log(row)
      window.open(this.$api + '/files/' + row.url)
    },
    // 上传成功的钩子
    handleFileUploadSuccess () {
      this.$message.success('文件上传成功')
      this.loading = false
      this.load()
    },
    // 失败的钩子
    handleFileUploadError () {
      this.$message.error('文件上传失败')
      this.loading = false
    },
    handleBeforeFile () {
      this.loading = true
    },
    delsFn () {},
    handleSelectionChange () {},
    changeStatic (row) {
      console.log(row)
      fileUpload(row).then((res) => {
        if (res.code === '200') {
          this.$message.success('更新成功')
          this.load()
        } else {
          this.$message.error('更新失败')
        }
      })
    },
    handleDel (data) {
      filesDel({ id: data.id }).then((res) => {
        if (res.code === '200') {
          this.$message.success('删除成功')
          this.load()
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    // 彻底删除
    completeDel (row) {
      console.log(row)
      fileCompleteDel(row).then((res) => {
        console.log(res)
        if (res.code === '200') {
          this.$message.success(res.data)
          this.load()
        } else {
          this.$message.error(res.data)
        }
      })
    },
    // 点击页数
    handleCurrent (val) {
      this.page = val
      this.load()
    }
  }
}
</script>

<style>
</style>
