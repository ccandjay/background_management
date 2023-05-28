<template>
  <div class="main">
    <img src="../assets/404.jpg" alt="" srcset="" />
    <p>错误:404 --- 您要访问的页面不存在 ---</p>
    <p @click="$router.push('/')">点击返回首页</p>
  </div>
</template>

<script>
export default {
  name: 'NotFound'
}
</script>

<style scoped lang="scss">
.main {
  width: 100vw;
  height: 100vh;
  text-align: center;
  background-color: #f6f6f6;
  img {
    width: 400px;
    height: 400px;
    margin-top: 150px;
  }
  font-size: 23px;
  font-weight: 900;
  p:nth-of-type(2) {
    margin-top: 20px;
    text-decoration: underline;
    cursor: pointer;
  }
  p:nth-of-type(2):hover {
    color: #ff7800;
  }
}
</style>
