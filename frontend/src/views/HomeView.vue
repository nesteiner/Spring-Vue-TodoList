<template>
  <Row class="home" main-axis="start">
    <Sidebar class="sidebar"
             :data="listgroups"
             :map="tasklistmap"
             @select-tasklist="selectTaskList"
             @add-tasklist="addTaskList"
             @add-listgroup="addListGroup"
             @rename-listgroup="renameListGroup"
             @cancel-listgroup="cancelListGroup"
    />


      <Board class="board"
             v-model:taskid="currentTaskId"
             :listid="currentTaskListId"
             :name="currentTaskListName"
             :data="tasks"
             :filelist="filelist"
             @add-task="addTask"
             @toggle-task="toggleTask"
             @delete-tasklist="deleteTaskList"
             @rename-tasklist="renameTaskList"
             @rename-task="renameTask"
             @delete-task="deleteTask"
             @delete-file="deleteFile"
             @upload-file="uploadFile"
      />


  </Row>
</template>

<script lang="ts" setup>
import {Sidebar, Board} from "@/components/todolist";
import {Row} from "@/components/layouts"
import {onMounted, ref, watch} from "vue";
import {FileItemService, ListGroupService, TaskListService, TaskService} from "@/api";


const listgroups = ref<ListGroup[]>([]);
const tasklistmap = ref<Map<number, TaskList[]>>(new Map())

const currentTaskListId = ref(0)
const currentTaskListName = ref("")
const currentTaskListGroupid = ref(0)
const tasks = ref<Task[]>([])
const currentTaskId = ref(0)
const filelist = ref<FileItem[]>([])
enum Status {
  Ok = "Ok",
  Err = "Err"
}

function selectTaskList(tasklist: TaskList) {
  // update tasks
  currentTaskListId.value = tasklist.id!
}

async function addTaskList(text: string) {
  let response = await TaskListService.insertOne({
    name: text,
    groupid: 0
  });

  let tasklist = response.data;
  if(tasklistmap.value.has(0)) {
    let tasklists = tasklistmap.value.get(0)!
    tasklists.push(tasklist)
  } else {
    tasklistmap.value.set(0, [tasklist])
  }
}

async function addListGroup(text: string) {
  let response = await ListGroupService.insertOne({
    name: text
  });

  let listgroup = response.data
  listgroups.value.push(listgroup)
}

async function renameListGroup(arg: {id: number, name: string}) {
  let response = await ListGroupService.updateOne(arg)
  let listgroup = response.data
  let index = listgroups.value.findIndex(x => x.id == arg.id)

  if(index != -1) {
    listgroups.value[index] = listgroup
  }
}

async function cancelListGroup(id: number) {
  let response = await ListGroupService.deleteOne(id);
  let index = listgroups.value.findIndex(x => x.id == id)
  if(index != -1) {
    listgroups.value.splice(index, 1);
    let tasklists = tasklistmap.value.get(id)!;
    tasklistmap.value.delete(id);
    let defaultTaskLists = tasklistmap.value.get(0)!
    defaultTaskLists.push(...tasklists)
  }
}

async function addTask(text: string) {
  let response = await TaskService.insertOne({
    name: text,
    listid: currentTaskListId.value,
    isdone: false
  })

  if(response.code == Status.Ok) {
    let task = response.data
    tasks.value.push(task)
  } else {
    alert(response.message)
  }

}

async function toggleTask(task: Task) {
  let response = await TaskService.updateOne(task);
  let newtask = response.data;
  let index = tasks.value.findIndex(x => x.id == task.id);
  if(index != -1) {
    tasks.value[index] = newtask;
  }
}

async function deleteTaskList() {
  let response = await TaskListService.deleteOne(currentTaskListId.value)
  console.log(response.data)

  currentTaskListId.value = 0
}

async function renameTaskList(text: string) {
  let response = await TaskListService.updateOne({
    id: currentTaskListId.value,
    name: text,
    groupid: currentTaskListGroupid.value
  });

  if(response.code == Status.Ok) {
    let tasklist = response.data;
    let tasklists = tasklistmap.value.get(currentTaskListGroupid.value)!;
    let index = tasklists.findIndex(x => x.id = currentTaskListId.value);
    if (index != -1) {
      tasklists[index] = tasklist
      currentTaskListName.value = tasklist.name
    }
  } else {
    alert(response.message)
  }
}

async function renameTask(task: Task) {
  let response = await TaskService.updateOne(task);
  if(response.code == Status.Ok) {
    let _task = response.data
    let index = tasks.value.findIndex(x => x.id == task.id)
    if (index != -1) {
      tasks.value[index] = _task
    }
  } else {
    alert(response.message)
  }
}

async function deleteTask(task: Task) {
  await TaskService.deleteOne(task.id!);
  let index = tasks.value.findIndex(x => x.id == task.id);
  if(index != -1) {
    tasks.value.splice(index, 1);
  }
}


async function deleteFile(fileitem: FileItem) {
  await FileItemService.deleteOne(fileitem.id!)
  let index = filelist.value.findIndex(x => x.id == fileitem.id!)
  if(index != -1) {
    filelist.value.splice(index, 1)
  }
}

async function uploadFile(file: File) {
  let response = await FileItemService.insertOne(currentTaskId.value, file);
  if(response.code == Status.Ok) {
    let fileitem = response.data
    filelist.value.push(fileitem)
  } else {
    alert(response.message)
  }
}

watch(currentTaskListId, async (newvalue, _) => {
  if(newvalue == 0) {
    return;
  }

  let response = await TaskService.findAll(currentTaskListId.value)
  tasks.value = response.data

  response = await TaskListService.findOne(newvalue);
  let task = response.data
  currentTaskListName.value = task.name
  currentTaskListGroupid.value = task.groupid

})

watch(currentTaskId, async (newvalue, _) => {
  if(newvalue == 0) {
    return;
  }

  let response = await FileItemService.findAll(newvalue);
  filelist.value = response.data
})
onMounted(async () => {
  let response = await ListGroupService.findAll();
  listgroups.value = response.data;

  let groupids = listgroups.value.map(x => x.id)
  for(let groupid of groupids) {
    let response = await TaskListService.findAll(groupid!)
    tasklistmap.value.set(groupid!, response.data)
  }

  response = await TaskListService.findAll(0);
  tasklistmap.value.set(0, response.data)


})

</script>

<style lang="scss" scoped>
.home {
  .sidebar {
    width: 268px;
    border-right: 1px solid rgba(0, 0, 0, 0.6);
  }

  .board {
    width: 75%;
  }
}
</style>