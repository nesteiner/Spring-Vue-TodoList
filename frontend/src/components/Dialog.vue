<template>
  <div class="dialog" v-show="show">
    <Column class="content-wrapper" cross-axis="center" main-axis="center">
      <div class="close" @click="close">close</div>
      <Column class="content" main-axis="center" cross-axis="center">
        <slot/>
      </Column>
    </Column>
  </div>
</template>

<script lang="ts" setup>
import {Column} from "@/components/layouts";

const props = defineProps({
  show: Boolean
})

const emits = defineEmits(["update:show"])
function close() {
  emits("update:show", false)
}
</script>

<style lang="scss" scoped>
div.dialog {
  z-index: 100;
  background: rgba(0, 0, 0, 0.6);
  //position: absolute;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;

  .content-wrapper {
    position: relative;
    background: white;
    width: 50%;
    height: 50%;
    box-shadow: 10px 5px 5px rgba(0, 0, 0, 0.6);
    div.close {
      cursor: pointer;
      position: absolute;
      top: 6px;
      right: 6px;
    }

    .content {
      margin-top: 12px;
    }
  }
}
</style>