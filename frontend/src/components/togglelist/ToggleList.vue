<template>
  <div class="toggle-list">
    <div class="entry" @click.stop="click">
      <slot name="entry"/>
    </div>

    <div class="content" v-show="show">
      <slot name="content"/>
    </div>

  </div>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {Row} from "@/components/layouts"
const show = ref(false)
function click() {
  show.value = !show.value
}
</script>

<style lang="scss" scoped>
div.toggle-list {
  cursor: pointer;


  div.entry {
    //font-size: 36px;
  }

  div.content {
    //margin-left: 20px;
    border-left: 2px solid rgba(0, 0, 0, 0.3)
  }
}
</style>
