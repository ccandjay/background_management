<template>
  <div class="user">
    <div style="padding: 10px 0; display: flex">
      <div>
        <el-input
          style="width: 300px"
          placeholder="名称"
          suffix-icon="el-icon-search"
          v-model="name"
        ></el-input>
        <el-button
          class="ml-5"
          type="primary"
          icon="el-icon-search"
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
        title="确定批量删除这些角色吗?"
        @confirm="delsFn"
      >
        <el-button type="danger" slot="reference"
          >批量删除 <i class="el-icon-remove-outline"></i>
        </el-button>
      </el-popconfirm>
    </div>
    <el-table
      :data="tableData"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="id" width="140"> </el-table-column>
      <el-table-column prop="name" label="名称" width="120"></el-table-column>
      <el-table-column prop="description" label="描述"> </el-table-column>
      <el-table-column prop="flag" label="唯一标识"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            slot="reference"
            type="info"
            @click="menuManger(scope.row)"
          >
            <i class="el-icon-s-grid"></i> 菜单分配
          </el-button>

          <el-button type="success" @click="handleEdit(scope.row)"
            >编辑 <i class="el-icon-edit"></i
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
            <el-button slot="reference" type="danger"
              >删除<i class="el-icon-delete"></i
            ></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
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

    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="120px">
        <el-form-item label="角色名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识" >
          <el-input v-model="form.flag" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="菜单分配"
      :visible.sync="menuDialogFormVisible"
      width="30%"
    >
      <el-tree
        :data="menuData"
        node-key="id"
        show-checkbox
        :props="{ label: 'name' }"
        ref="tree"
        :default-expanded-keys="expends"
        :default-checked-keys="checks"
      >
        <span class="custom-tree-node" slot-scope="{ data }">
          <span>
            <i :class="data.icon"> {{ data.name }}} </i>
          </span>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  rolePage,
  roleSave,
  roleDelete,
  roleUpdate,
  roleDeles,
  roleMenu,
  roleMenuId
} from '@/api/roleApi'
import { menuList } from '@/api/menuApi'
export default {
  name: 'RoleView',
  data () {
    return {
      tableData: [],
      dialogFormVisible: false,
      formLabelWidth: '100px',
      form: {},
      page: 1,
      pageSize: 5,
      name: '',
      email: '',
      address: '',
      total: 1,
      // 多选的选中信息
      multipleSelection: [],
      menuDialogFormVisible: false,
      menuData: [],
      expends: [],
      checks: [],
      roleId: -1
    }
  },
  created () {
    this.load()
  },
  methods: {
    // 分页查询
    load () {
      const params = {
        pageNum: this.page,
        pageSize: this.pageSize,
        name: this.name
      }
      rolePage(params).then((res) => {
        if (res.code === '200') {
          this.total = res.data.total
          this.tableData = res.data.records
        } else {
          this.$message.error(res.msg)
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
      roleDeles({ ids: this.multipleSelection }).then((res) => {
        this.message(res, '批量删除成功', '批量删除失败')
      })
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
      roleDelete({ id: row.id }).then((res) => {
        this.message(res, '删除成功', '删除失败')
      })
    },
    handleEdit (row) {
      this.form = row
      this.dialogFormVisible = true
    },
    // 节点选中状态发生变化时的回调
    handleCheckChange () {},
    // 分配角色按钮
    menuManger (row) {
      console.log(row)
      this.roleId = row.id
      this.menuDialogFormVisible = true
      // 菜单列表
      this.menuData = []
      menuList({ name: '' }).then((res) => {
        if (res.code === '200') {
          this.menuData = res.data
          // 把后台返回的菜单数据处理成 id数组
          this.expends = this.menuData.map((v) => v.id)
        }
      })
      // 选中的菜单id
      roleMenuId({ roleId: row.id }).then((res) => {
        if (res.code === '200') {
          this.checks = res.data.map(item => item.menuId)
          console.log(this.checks)
        }
      })
    },
    // 提交分配菜单
    submitMenu () {
      console.log(this.$refs.tree.getCheckedKeys())
      // 获取选中的 id,类型是数组
      let ids = this.$refs.tree.getCheckedKeys()
      ids = ids.toString()
      const params = {
        roleId: this.roleId,
        ids: ids
      }
      roleMenu(params).then((res) => {
        if (res.code === '200') {
          this.$message.success('提交成功')
        }
      })
      this.menuDialogFormVisible = false
    },
    submit () {
      if (this.form.id) {
        roleUpdate(this.form).then((res) => {
          this.message(res, '修改成功', '修改失败')
        })
      } else {
        roleSave(this.form).then((res) => {
          this.message(res, '添加成功', '添加失败')
        })
      }
      this.dialogFormVisible = false
    },
    reset () {
      this.name = ''
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

<style scoped>
</style>
