<template>
  <div class="drawer" v-show="show">
    <Column class="content" cross-axis="center">
      <Row class="close" main-axis="end">
        <button @click="click">close</button>
      </Row>
      <slot/>
    </Column>
  </div>
</template>

<script lang="ts" setup>
import {Column, Row} from "@/components/layouts";
const props = defineProps({
  show: Boolean
});

const emits = defineEmits(["update:show"]);
function click() {
  emits("update:show", false);
}
</script>

<style lang="scss" scoped>
div.drawer {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);

  .content {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    background: white;

    width: 30%;

  }
}
</style>