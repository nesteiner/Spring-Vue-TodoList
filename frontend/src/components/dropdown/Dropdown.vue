<template>
  <div class="dropdown" @click.stop="click">
    <svg t="1661609774203" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2480" width="200" height="200"><path d="M415.930119 223.790358c0-52.980346 43.003528-95.983874 95.983874-95.983874s95.983874 43.003528 95.983874 95.983874-43.003528 95.983874-95.983874 95.983874S415.930119 276.770704 415.930119 223.790358z" p-id="2481"></path><path d="M415.930119 511.741979c0-52.980346 43.003528-95.983874 95.983874-95.983874s95.983874 43.003528 95.983874 95.983874-43.003528 95.983874-95.983874 95.983874S415.930119 564.722325 415.930119 511.741979z" p-id="2482"></path><path d="M415.930119 799.865614c0-52.980346 43.003528-95.983874 95.983874-95.983874s95.983874 43.003528 95.983874 95.983874-43.003528 95.983874-95.983874 95.983874S415.930119 852.673946 415.930119 799.865614z" p-id="2483"></path></svg>
    <div class="items" v-show="show">
      <slot/>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {Row} from "@/components/layouts"
import {provide, ref} from "vue"

const show = ref(false)
provide("show", show)
function click() {
  show.value = !show.value
}
</script>

<style lang="scss" scoped>
.dropdown {
  width: 24px;
  height: 24px;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;

  svg {
    width: 100%;
    height: 100%;
  }

  div.items {
    position: absolute;
    width: 128px;
    top: 16px;
    z-index: 100;
    box-shadow: 10px 5px 5px rgba(0, 0, 0, 0.6);
  }
}
</style>