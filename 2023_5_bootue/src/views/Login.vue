<template>
  <div class="login">
    <div class="box">
      <div class="tit">
        <b>登 录</b>
      </div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input
            size="medium"
            prefix-icon="el-icon-user"
            v-model="user.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            size="medium"
            prefix-icon="el-icon-lock"
            show-password
            v-model="user.password"
          ></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button
            type="warning"
            size="small"
            autocomplete="off"
            @click="$router.push('/register')"
            >注册</el-button
          >
          <el-button
            type="primary"
            size="small"
            autocomplete="off"
            @click="login"
            @keyup.enter="login"
            >登录</el-button
          >
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<script>
import { userLogin } from '@/api/userApi'
import { setRouter } from '@/router'
export default {
  name: 'LoginView',
  data () {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created () {},
  methods: {
    login () {
      if (this.user.username === undefined || this.user.password === undefined) {
        this.$message.error('用户名或密码不能为空!')
        return
      }
      userLogin(this.user).then(res => {
        localStorage.setItem('user', JSON.stringify(res.data))
        localStorage.setItem('menus', JSON.stringify(res.data.menus))
        if (res.code === '200') {
          this.$router.push('/')
          this.$message.success('登录成功')
          setRouter()
        } else {
          this.$message.error('用户名或密码错误')
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.login {
  height: 100vh;
  width: 100vw;
  background-image: linear-gradient(to bottom right, #fc466b, #3f5efb);
  overflow: hidden;
  .box {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    background-color: #fff;
    width: 350px;
    height: 280px;
    padding: 20px;
    border-radius: 10px;
  }
  .tit {
    margin: 20px 0;
    text-align: center;
    font-size: 24px;
  }
}
</style>
