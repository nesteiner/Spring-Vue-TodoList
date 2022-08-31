<template>
  <div class="taskview">
    <SidebarView class="sidebar" v-model:data="listgroups" v-model:map="tasklistmap"/>
    <Expanded>
      <BoardView class="board" v-model:data="tasks"/>
    </Expanded>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, provide, reactive, ref, watch} from "vue";
import {Row} from "@/components/layouts"
import SidebarView from "@/views/SidebarView.vue";
import BoardView from "@/views/BoardView.vue";
import {ListGroupService, TaskListService, TaskService} from "@/api";
import Expanded from "@/components/layouts/Expanded.vue";

const currentTaskList = reactive({
  id: 0,
  name: "None",
  groupid: 0
});

provide('current-tasklist', currentTaskList);

const listgroups = ref<ListGroup[]>([]);
const tasklistmap = ref<Map<number, TaskList[]>>(new Map());
const tasks = ref<Task[]>([])

watch(currentTaskList, async (newvalue, _) => {
  let id = newvalue.id!;
  try {
    let response = await TaskService.findAll(id);
    tasks.value = response.data
  } catch (error) {
    alert(error);
  }
})

onMounted(async () => {
  try {
    let response = await ListGroupService.findAll();
    listgroups.value = response.data;

    let groupids = listgroups.value.map(x => x.id)
    for(let groupid of groupids) {
      let response = await TaskListService.findAll(groupid!)
      tasklistmap.value.set(groupid!, response.data)
    }

    response = await TaskListService.findAll(0);
    tasklistmap.value.set(0, response.data)

  } catch (error) {
    alert(error)
  }
})
</script>

<style lang="scss" scoped>
.taskview {
  .sidebar {
    width: 20%;
  }

  .board {
    width: 80%;
  }
}
</style>