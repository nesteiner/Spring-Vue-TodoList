<template>
  <div class="card">
    <slot/>
  </div>
</template>

<style lang="scss" scoped>
div.card {
  width: 100%;
  font-size: 14px;
  line-height: 20px;
  font-weight: 400;

  cursor: pointer;
  margin: 8px 16px 8px 0;
}
</style>