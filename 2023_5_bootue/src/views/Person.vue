<template>
  <div class="main">
    <el-card class="card">
      <el-upload
        class="avatar-uploader"
        :action="$api + '/files/upload'"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :on-error="handleAvaterError"
      >
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form label-width="80px" size="small" style="margin-top: 10px">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="form.username" autocomplete="off"></el-input>
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
      <div style="text-align: right">
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import { userInfo, userUpdate } from '@/api/userApi'
export default {
  name: 'PersonView',
  data () {
    return {
      form: {},
      formLabelWidth: '100px',
      user: JSON.parse(localStorage.getItem('user')),
      imageUrl: ''
    }
  },
  created () {
    this.getInfo()
  },
  methods: {
    submit () {
      userUpdate(this.form).then((res) => {
        if (res.code === '200') {
          this.$message.success('更新成功')
          this.getInfo()
        } else {
          this.$message.success('更新失败')
        }
      })
    },
    getInfo () {
      userInfo({ username: this.user.username }).then((res) => {
        if (res.code === '200') {
          this.imageUrl = res.data.avatarUrl
          this.form = res.data
        }
      })
    },
    handleAvatarSuccess (response, file, fileList) {
      console.log(file)
    },
    handleAvaterError () {}
  }
}
</script>

<style scoped lang="scss">
.main{
  position: relative;
}
.card {
  width: 550px;
  text-align: center;
  margin: 5% auto;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 168px;
  height: 168px;
  display: block;
  border-radius: 20px;
}
</style>
