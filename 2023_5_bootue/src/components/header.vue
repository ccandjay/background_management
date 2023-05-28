<template>
  <div class="head" style="display: flex; justify-content: space-between">
    <div style="width: 170px; text-align: left">
      <span
        :class="collapseBtnClass"
        style="
          cursor: pointer;
          font-size: 25px;
          line-height: 60px;
          display: inline-block;
        "
        @click="collapse"
      ></span>

      <el-breadcrumb
        separator="/"
        style="display: inline-block; font-size: 17px; margin-left: 10px"
      >
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ pathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 100px">
      <img :src="userInfo.avatarUrl" class="img" />
      <span style="cursor: pointer">{{ userInfo.username }}</span>
      <i class="el-icon-arrow-down" style="margin-right: 15px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
          <span @click="$router.push('/person')">
            个人信息
          </span>
        </el-dropdown-item>
        <el-dropdown-item>
          <span @click="logout">退出</span></el-dropdown-item
        >
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: 'HeaderView',
  props: {
    collapseBtnClass: String
  },
  data () {
    return {
      pathName: '',
      userInfo: JSON.parse(localStorage.getItem('user')) || {}
    }
  },
  created () {},
  methods: {
    collapse () {
      this.$emit('collapse')
    },
    logout () {
      this.$router.replace('/login')
      localStorage.removeItem('user')
    }
  },
  watch: {
    // 此处监听variable变量，当期有变化时执行
    $route (item1, item2) {
      // item1为新值，item2为旧值
      // console.log(item1)
      this.pathName = item1.name
      if (item1.meta.title === '首页') {
        this.pathName = ''
      }
    },
    immediate: true // watch侦听操作内的函数会立刻被执行
  }
}
</script>

<style scoped lang="scss">
.img {
  width: 30px;
  border-radius: 50%;
  position: relative;
  top: 10px;
  right: 5px;
}
</style>
