<template>
  <div class="dropdown-item" @click.stop="click">
    <slot/>
  </div>
</template>

<script lang="ts" setup>
import {inject, Ref} from "vue";

const emits = defineEmits(["click"])
const show = inject<Ref<boolean>>("show")!
function click() {
  emits("click")
  show.value = false
}
</script>

<style lang="scss" scoped>
div.dropdown-item {
  background: white;
  border-bottom: 1px solid black;
  cursor: pointer;
}
</style>