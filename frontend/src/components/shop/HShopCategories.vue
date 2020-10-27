<template>
  <div class="breadcrumb">
    <div class="container">
      <h4>Shop > {{ category }}</h4>
    </div>
  </div>
</template>

<script>
export default {
  props: ["category"]
};
</script>

<style scoped>
ul {
  display: flex;
  list-style: none;
}
a.nav-link {
  color: rgba(255, 255, 255, 0.5);
}

a.nav-link.router-link-exact-active {
  color: white;
}
a:focus {
  font-weight: bold;
  text-decoration: underline;
}
</style>
