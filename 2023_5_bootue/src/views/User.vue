<template>
  <div class="user">
    <div style="padding: 10px 0; display: flex; justify-content: space-between">
      <div>
        <el-input
          style="width: 300px"
          placeholder="请输入用户名"
          suffix-icon="el-icon-search"
          v-model="username"
        ></el-input>
        <el-button
          class="ml-5"
          type="primary"
          icon="el-icon-search"
          @click="load"
          >搜索</el-button
        >
      </div>
      <div>
        <el-input
          style="width: 300px"
          placeholder="请输入邮箱"
          suffix-icon="el-icon-message"
          v-model="email"
        ></el-input>
        <el-button
          class="ml-5"
          type="primary"
          icon="el-icon-message"
          @click="load"
          >搜索</el-button
        >
      </div>
      <div>
        <el-input
          style="width: 300px"
          placeholder="请输入地址"
          suffix-icon="el-icon-location-outline"
          v-model="address"
        ></el-input>
        <el-button
          class="ml-5"
          type="primary"
          icon="el-icon-location-outline"
          @click="load"
          >搜索</el-button
        >
      </div>

      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd"
        >新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-popconfirm
        class="ml-5"
        confirm-button-text="确定"
        cancel-button-text="取消"
        icon="el-icon-info"
        icon-color="red"
        title="确定批量删除这些用户吗?"
        @confirm="delsFn"
      >
        <el-button type="danger" slot="reference"
          >批量删除 <i class="el-icon-remove-outline"></i>
        </el-button>
      </el-popconfirm>
      <el-button type="primary" style="margin-left: 5px;"
        >导入 <i class="el-icon-bottom"></i>
      </el-button>
      <el-button type="primary">导出 <i class="el-icon-top"></i> </el-button>
    </div>
    <el-table
      :data="tableData"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" > </el-table-column>
      <el-table-column prop="id" label="id" width="90"> </el-table-column>
      <el-table-column prop="username" label="用户名" width="120">
      </el-table-column>
      <el-table-column prop="role" label="角色" width="120">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>
          <el-tag type="warning" v-if="scope.row.role === 'ROLE_TEACHER'">老师</el-tag>
          <el-tag type="success" v-if="scope.row.role === 'ROLE_STUDENT'">学生</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" width="130"> </el-table-column>
      <el-table-column prop="email" label="邮箱"  width="170"> </el-table-column>
      <el-table-column prop="phone" label="电话"  width="170"> </el-table-column>
      <el-table-column prop="address" label="地址"  width="170"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)" size="small" v-if="userInfo.role == 'ROLE_ADMIN'"
            >编辑 <i class="el-icon-edit"></i
          ></el-button>
          <el-button type="success" @click="showClass(scope.row)" size="small" v-if="scope.row.role=='ROLE_TEACHER'"
            >所带课程 <i class="el-icon-finished"></i
          ></el-button>
          <el-button type="success" @click="showClass(scope.row)" size="small"  v-if="scope.row.role=='ROLE_STUDENT'"
            >所选课程 <i class="el-icon-finished"></i
          ></el-button>
          <el-popconfirm
            class="ml-5"
            confirm-button-text="确定"
            cancel-button-text="取消"
            icon="el-icon-info"
            icon-color="red"
            title="确定删除该用户吗?"
            @confirm="handleDel(scope.row)"
          >
            <el-button slot="reference" type="danger" size="small" v-if="userInfo.role == 'ROLE_ADMIN'"
              >删除 <i class="el-icon-delete"></i
            ></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
        background
        layout="prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrent"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        :total="total"
      >
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="120px">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色选择" :label-width="formLabelWidth">
         <el-select clearable v-model="form.role" placeholder="请选择" style="width:100%">
            <el-option v-for="(item,index) in roles" :key="index" :label="item.name" :value="item.flag"></el-option>
         </el-select>
        </el-form-item>
        <el-form-item label="昵称" :label-width="formLabelWidth">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="课程信息" :visible.sync="stuVis" width="30%" >
      <el-table :data="stuCourses" border stripe>
        <el-table-column prop="name" label="课程名称"></el-table-column>
        <el-table-column prop="score" label="学分"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {
  userPage,
  userSave,
  userDelete,
  userUpdate,
  userDeles
} from '@/api/userApi'
import { roleList } from '@/api/roleApi'
export default {
  name: 'UserView',
  data () {
    return {
      tableData: [],
      dialogFormVisible: false,
      formLabelWidth: '100px',
      form: {},
      page: 1,
      pageSize: 10,
      username: '',
      email: '',
      address: '',
      total: 1,
      // 多选的选中信息
      multipleSelection: [],
      roles: [],
      entity: {},
      stuVis: false,
      stuCourses: [],
      userInfo: JSON.parse(localStorage.getItem('user')) || {}
    }
  },
  created () {
    this.load()
  },
  methods: {
    // 分页查询
    load () {
      const params = {
        page: this.page,
        pageSize: this.pageSize,
        username: this.username,
        address: this.address
      }
      userPage(params).then((res) => {
        console.log(res)
        if (res.code === '200') {
          this.total = res.data.total
          this.tableData = res.data.records
        } else {
          this.$message.error(res.msg)
        }
      })

      roleList().then(res => {
        if (res.code === '200') {
          this.roles = res.data
        }
      })
    },
    // 新增按钮
    handleAdd () {
      this.dialogFormVisible = true
      this.form = {}
    },
    // 批量删除
    delsFn () {
      userDeles({ ids: this.multipleSelection }).then((res) => {
        this.message(res, '批量删除成功', '批量删除失败')
      })
    },
    // 切换每页展示数
    handleSizeChange (val) {
      console.log(val)
    },
    // 下一页
    handleCurrent (val) {
      this.page = val
      this.load()
    },
    handleSelectionChange (val) {
      console.log(val)
      this.multipleSelection = val
      this.multipleSelection = this.multipleSelection.map((e) => e.id)
    },
    handleDel (row) {
      userDelete({ id: row.id }).then((res) => {
        this.message(res, '删除成功', '删除失败')
      })
    },
    handleEdit (row) {
      this.form = row
      this.dialogFormVisible = true
    },
    // 查看所带课程
    showClass (row) {
      this.stuVis = true
      this.stuCourses = row.courses
    },
    selectCourse (id) {

    },
    collapse () {
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {
        this.asideWidth = 50
        this.logoShow = false
        this.collapseBtnClass = 'el-icon-s-unfold'
      } else {
        this.asideWidth = 200
        this.logoShow = true
        this.collapseBtnClass = 'el-icon-s-fold'
      }
    },
    submit () {
      console.log(this.entity)
      if (this.form.id) {
        userUpdate(this.form).then((res) => {
          this.message(res, '修改成功', '修改失败')
        })
      } else {
        userSave(this.form).then((res) => {
          this.message(res, '添加成功', '添加失败')
        })
      }
      this.dialogFormVisible = false
    },
    reset () {
      this.username = ''
      this.email = ''
      this.address = ''
    },
    message (res, suc, wran) {
      if (res.code === '200') {
        this.$message.success(suc)
        this.load()
      } else {
        this.$message.warning(wran)
        this.load()
      }
    }
  }
}
</script>

<style scoped lang="scss">
</style>
