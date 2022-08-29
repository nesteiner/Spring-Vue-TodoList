<template>
  <div class="test">
    <Row>
      <h3>Hello</h3>
      <Expanded>
        <h3>world</h3>
      </Expanded>
    </Row>

    <button @click="toggleDrawer">toggle drawer</button>
    <Drawer v-model:show="showDrawer">
      <h3>Hello World</h3>
    </Drawer>

    <input type="text" v-model="currentTask.name"/>
    <button @click="showCurrentTask">click</button>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import {Row, Expanded} from "@/components/layouts";
import {Drawer} from "@/components/todolist";

const checked = ref(false)
const showDrawer = ref(false)
const currentTask = reactive<Task>({
  id: 0,
  name: "None",
  isdone: false,
  listid: 0
})

function click() {
  checked.value = !checked.value
}

function toggleDrawer() {
  showDrawer.value = true
}

function showCurrentTask() {
  console.log({
    ...currentTask
  })
}

onMounted(() => {
  console.log({
    ...currentTask
  })
})
</script>

<style scoped>

</style>