<template>
  <div class="home">
    <el-container style="height: 100%; overflow: hidden">
      <el-aside
        :width="asideWidth + 'px'"
        style="
          background-color: rgb(238, 241, 246);
          overflow: hidden;
          max-width: 300px;
        "
      >
        <Menu :isCollapse="isCollapse" :logoShow="logoShow"></Menu>
      </el-aside>

      <el-container>
        <el-header
          style="
            text-align: right;
            line-height: 60px;
            font-size: 15px;
            border-bottom: 1px solid #000;
          "
        >
          <Header
            :collapseBtnClass="collapseBtnClass"
            @collapse="collapse"
          ></Header>
        </el-header>

        <el-main>
          <router-view @getUserInfo="getUserInfo"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Menu from '@/components/Aside.vue'
import Header from '@/components/header.vue'
export default {
  components: {
    Menu,
    Header
  },
  name: 'HomeView',
  data () {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      asideWidth: 200,
      logoShow: true,
      userInfo: JSON.parse(localStorage.getItem('user')) || []
    }
  },
  created () {
  },
  methods: {
    getUserInfo () {
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
    }
  }
}
</script>

<style lang="scss">
.home {
  height: 100%;
}
</style>
