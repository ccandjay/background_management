<template>
  <div v-loading="loading">
    <div style="margin: 10px 0">
      <div>
        <el-button type="primary" class="ml-5" @click="addFn"
          >新增课程<i class="el-icon-circle-plus-outline ml-5"></i>
        </el-button>
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
      <el-table-column prop="name" label="课程名称"> </el-table-column>
      <el-table-column prop="score" label="课程学时"> </el-table-column>
      <el-table-column prop="teacher" label="课程老师"> </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.state"
            active-color="#13ce66"
            inactive-color="#ccc"
            :active-value="1"
            :inactive-value="0"
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
            title="确定删除该课程吗?"
            @confirm="handleDel(scope.row)"
          >
            <el-button
              slot="reference"
              type="danger"
              size="mini"
              v-if="userInfo.role == 'ROLE_ADMIN'"
              >删除 <i class="el-icon-delete"></i
            ></el-button>
          </el-popconfirm>

          <div class="ml-5 dis-in-b">
            <el-button
              slot="reference"
              type="primary"
              size="mini"
              v-if="userInfo.role == 'ROLE_ADMIN'"
              >编辑 <i class="el-icon-delete"></i
            ></el-button>
          </div>

          <el-button type="primary" @click="selectCourse(scope.row.id)" size="small"  v-if="userInfo.role == 'ROLE_STUDENT'"> 选课 </el-button>
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

    <div>
      <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form label-position="right" label-width="120px">
          <el-form-item label="名称">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="学分">
            <el-input v-model="form.score" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="课时">
            <el-input v-model="form.times" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="老师">
            <el-select clearable v-model="form.attribute" placeholder="请选择">
              <el-option
                v-for="item in teachters"
                :key="item.id"
                :label="item.nickname"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  coursePage,
  courseDel,
  courseCompleteDel,
  courseSaveOrUpdate,
  setCourseStudent
} from '@/api/courseApi'
import { getTeachers } from '@/api/userApi'
export default {
  name: 'courseView',
  data () {
    return {
      tableData: [],
      name: '',
      showcourseList: false,
      page: 1,
      pageSize: 5,
      total: 1,
      loading: false,
      dialogFormVisible: false,
      dialogFormVisible2: false,
      form: {},
      teachters: [],
      userInfo: JSON.parse(localStorage.getItem('user')) || {}
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
      coursePage(params).then((res) => {
        console.log(res)
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
      window.open(this.$api + '/file/' + row.url)
    },
    addFn () {
      getTeachers({ role: 'ROLE_TEACHER' }).then((res) => {
        console.log(res)
        if (res.code === '200') {
          this.teachters = res.data
        }
      })
      this.dialogFormVisible = true
    },
    save () {
      courseSaveOrUpdate(this.form).then((res) => {
        if (res.code === '200') {
          this.$message.success('新增成功')
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.warning('新增失败')
        }
      })
    },
    handleBeforecourse () {
      this.loading = true
    },
    // 选课
    selectCourse (id) {
      setCourseStudent({ courseId: id, studentId: this.userInfo.id }).then(res => {

      })
    },
    delsFn () {},
    handleSelectionChange () {},
    changeStatic (row) {
      console.log(row)
      courseSaveOrUpdate(row).then((res) => {
        if (res.code === '200') {
          this.$message.success('更新成功')
          this.load()
        } else {
          this.$message.error('更新失败')
        }
      })
    },
    handleDel (data) {
      courseDel({ id: data.id }).then((res) => {
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
      courseCompleteDel(row).then((res) => {
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
