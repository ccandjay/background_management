<template>
  <div class="menu">
    <el-menu
      :default-openeds="opends"
      style="height: 100%"
      background-color="rgb(48,65,86)"
      text-color="#fff"
      :collapse-transition="false"
      :collapse="isCollapse"
      @select="handleSelect"
      router
    >
      <div
        style="
          height: 60px;
          color: #fff;
          display: flex;
          align-items: center;
          justify-content: center;
        "
        v-show="logoShow"
      >
        <img
          style="margin-right: 5px"
          src="@/assets/logo.png"
          width="20"
          alt=""
        />
        <b>后台管理系统</b>
      </div>

      <div v-for="(item, index) in menus" :key="index">
        <div v-if="item.path">
          <el-menu-item :index="item.path">
            <template slot="title"
              ><i :class="item.icon"></i> <span>{{ item.name }}</span>
            </template>
          </el-menu-item>
        </div>

        <div v-else>
          <el-submenu :index="item.id.toString()">
            <template slot="title">
              <i :class="item.icon"></i>
              <span>{{ item.name }}</span>
            </template>
          <el-menu-item
            v-for="(item2, index2) in item.children"
            :key="index2"
            :index="item2.path"
          >
            <i :class="item2.icon"></i>
            <span slot="title">{{ item2.name }}</span>
          </el-menu-item>
        </el-submenu>

        </div>
      </div>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'AsideView',
  props: {
    isCollapse: Boolean,
    logoShow: Boolean
  },
  data () {
    return {
      menus: JSON.parse(localStorage.getItem('menus')) || [],
      opends: JSON.parse(localStorage.getItem('menus')).map(v => v.id + '') || []
    }
  },
  methods: {
    handleSelect () {
      // this.$nextTick(() => {
      //   console.log('route', this.$route)
      //   console.log(this.$route.meta)
      // })
    }
  }
}
</script>

<style scoped lang="scss">
.menu {
  height: 100%;
}
</style>
