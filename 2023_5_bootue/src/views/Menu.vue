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
      row-key="id"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="id" width="140"> </el-table-column>
      <el-table-column prop="name" label="名称" width="120"></el-table-column>
      <el-table-column prop="path" label="路径" width="120"></el-table-column>
      <el-table-column prop="pagePath" label="页面路径" width="120"></el-table-column>
      <el-table-column label="图标" width="140" align="center">
        <template slot-scope="scope">
          <i  style="font-size: 20px;" :class="scope.row.icon"></i>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            @click="addChildren(scope.row.id)"
            v-if="!scope.row.pid && !scope.row.path"
            >新增子菜单 <i class="el-icon-circle-plus-outline"></i
          ></el-button>
          <el-button
            type="primary"
            @click="addChildren(scope.row.id)"
            disabled
            v-else
            >新增子菜单 <i class="el-icon-circle-plus-outline"></i
          ></el-button>
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

    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="120px">
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径" :label-width="formLabelWidth">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" :label-width="formLabelWidth">
          <el-select clearable v-model="form.icon" placeholder="请选择">
            <el-option
              v-for="(item, index) in options"
              :key="index"
              :label="item.name"
              :value="item.value"
            >
              <i :class="item.value"></i>{{ item.name }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  menuList,
  menuSave,
  menuDelete,
  menuUpdate,
  menuDeles,
  menuIcons
} from '@/api/menuApi'
export default {
  name: 'menuView',
  data () {
    return {
      tableData: [],
      dialogFormVisible: false,
      formLabelWidth: '100px',
      form: {},
      name: '',
      email: '',
      address: '',
      // 多选的选中信息
      multipleSelection: [],
      options: []
    }
  },
  created () {
    this.load()
  },
  methods: {
    // 分页查询
    load () {
      const params = {
        name: this.name
      }
      menuList(params).then((res) => {
        console.log(res)
        if (res.code === '200') {
          this.tableData = res.data
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
      menuDeles({ ids: this.multipleSelection }).then((res) => {
        this.message(res, '批量删除成功', '批量删除失败')
      })
    },
    handleCheckChange () {},
    // 下一页
    handleCurrent (val) {
      this.page = val
      this.load()
    },
    addChildren (id) {},
    handleSelectionChange (val) {
      console.log(val)
      this.multipleSelection = val
      this.multipleSelection = this.multipleSelection.map((e) => e.id)
    },
    handleDel (row) {
      menuDelete({ id: row.id }).then((res) => {
        this.message(res, '删除成功', '删除失败')
      })
    },
    handleEdit (row) {
      this.form = row
      this.dialogFormVisible = true
      menuIcons().then((res) => {
        if (res.code === '200') {
          this.options = res.data
        }
        console.log(res)
      })
    },
    // 提交分配角色
    submitMenu () {},
    submit () {
      if (this.form.id) {
        menuUpdate(this.form).then((res) => {
          this.message(res, '修改成功', '修改失败')
          this.$emit('getUserInfo')
        })
      } else {
        menuSave(this.form).then((res) => {
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

<style scoped lang="scss">
.el-select {
  width: 100%;
}
</style>
