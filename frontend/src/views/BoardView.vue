<template>
  <Column class="task-board" cross-axis="start">
    <Column class="tasks">
      <Row class="head" cross-axis="center">
        <Expanded>
          <h3>{{currentTaskList.name}}</h3>
        </Expanded>
        <Dropdown>
          <DropdownItem @click="toggleRenameTaskList">Rename Tasklist</DropdownItem>
          <DropdownItem @click="toggleDeleteTaskList">Delete TaskList</DropdownItem>
        </Dropdown>
      </Row>

      <template v-for="(task, index) in data" :key="index">
        <Card class="task" @click="selectTask(task)">
          <Row>
            <input type="checkbox" :checked="task.isdone" @click.stop="clickCheckbox(task)"/>
            <div> {{task.name}} </div>

          </Row>
        </Card>
      </template>
    </Column>

    <Row class="operate" cross-axis="center" main-axis="center" v-show="currentTaskList.id !== 0">
      <input type="text" v-model="textAdd" placeholder="input tasklist name"/>
      <button @click="addTask">add</button>
    </Row>

    <Dialog v-model:show="showRenameDialog">
      <Column>
        <input type="text" v-model="textRename" placeholder="input tasklist name"/>
        <button @click="saveRename">save</button>
      </Column>
    </Dialog>

    <Dialog v-model:show="showDeleteDialog">
      <Row>
        <button @click="submitDelete">delete</button>
        <button @click="cancelDelete">cancel</button>
      </Row>
    </Dialog>

    <Drawer v-model:show="showDrawer">
      <input type="text" v-model="currentTask.name" placeholder="rename task"/>
      <button @click="saveEdit">save change</button>
      <button @click="cancelEdit">cancel</button>
      <button @click="deleteTask">delete</button>

      <Column>
        <template v-for="(fileitem, index) in filelist" :key="index">
          <Card>
            <Row cross-axis="center" main-axis="space-between">
              <div class="name">{{fileitem.name}}</div>
              <button @click="deleteFile(fileitem)">delete</button>
            </Row>
          </Card>
        </template>

        <Row cross-axis="center" main-axis="space-between">
          <input type="file" ref="inputFile"/>
          <button @click="uploadFile">upload</button>
        </Row>
      </Column>
    </Drawer>
  </Column>
</template>

<script lang="ts" setup>
import {defineEmits, inject, PropType, reactive, ref, watch} from "vue";
import {Column, Row, Expanded} from "@/components/layouts";
import Card from "@/components/Card.vue"
import {Dropdown, DropdownItem} from "@/components/dropdown";
import Dialog from "@/components/Dialog.vue";
import Drawer from "@/components/todolist/Drawer.vue";
import {FileItemService, TaskListService, TaskService} from "@/api";

const props = defineProps({
  data: {
    type: Array as PropType<Task[]>,
    required: true
  }
})

const currentTaskList = inject<TaskList>("current-tasklist")!
const emits = defineEmits(["update:data"])

const textAdd = ref("")
const textRename = ref("")
const showRenameDialog = ref(false)
const showDeleteDialog = ref(false)
const showDrawer = ref(false)
const currentTask = reactive<Task>({
  id: 0,
  name: "None",
  isdone: false,
  listid: 0
})

const inputFile = ref<HTMLInputElement>();
const filelist = ref<FileItem[]>([])
const listid = currentTaskList.id;

async function addTask() {
  // emits("add-task", textAdd.value);
  // textAdd.value = ""
  try {
    let task: Task = {
      name: textAdd.value,
      isdone: false,
      listid: currentTaskList.id!
    }

    let response = await TaskService.insertOne(task)
    let newtask = response.data
    let temparray = props.data
    temparray.push(newtask)
    emits("update:data", temparray)
  } catch (error) {
    alert(error);
  } finally {
    textAdd.value = "";
  }
}

async function selectTask(task: Task) {
  currentTask.id = task.id
  currentTask.name = task.name
  currentTask.isdone = task.isdone
  currentTask.listid = task.listid

  showDrawer.value = true

  currentTask.id = task.id
  currentTask.name = task.name
  currentTask.isdone = task.isdone
  currentTask.listid = task.listid
}

async function clickCheckbox(task: Task) {
  try {
    let response = await TaskService.updateOne({
      ...task,
      isdone: !task.isdone
    })
    let newtask = response.data
    let index = props.data.findIndex(x => x.id == task.id)
    if(index != -1) {
      let temparray = props.data
      temparray[index] = newtask
      emits("update:data", temparray)
    }
  } catch(error) {
    alert(error)
  } finally {

  }
}

function toggleRenameTaskList() {
  showRenameDialog.value = true
}

function toggleDeleteTaskList() {
  showDeleteDialog.value = true
}

async function saveRename() {
  // emits("rename-tasklist", textRename.value)
  // textRename.value = ""
  // showRenameDialog.value = false

  try {
    let response = await TaskListService.updateOne({
      ...currentTaskList,
      name: textRename.value
    })

    let newtasklist = response.data
    let index = props.data.findIndex(x => x.id == currentTaskList.id)
    if(index != -1) {
      let temparray = props.data
      temparray[index] = newtasklist;
      emits("update:data", temparray)
    }
  } catch (error) {
    alert(error);
  } finally {
    textRename.value = ""
    showRenameDialog.value = false
  }
}

async function submitDelete() {
  // emits("delete-tasklist")
  // showDeleteDialog.value = false

  try {
    await TaskListService.deleteOne(currentTaskList.id!)
    let index = props.data.findIndex(x => x.id == currentTaskList.id!)
    if (index != -1) {
      let temparray = props.data
      temparray.splice(index, 1)
      emits("update:data", temparray)
    }
  } catch(error) {
    alert(error);
  } finally {
    showDeleteDialog.value = false;
  }
}

function cancelDelete() {
  showDeleteDialog.value = false
}

async function saveEdit() {
  // emits("rename-task", {
  //   ...currentTask
  // })
  try {
    let response = await TaskService.updateOne({
      ...currentTask
    })

    let newtask = response.data
    let index = props.data.findIndex(x => x.id == currentTask.id)
    if(index != -1) {
      let temparray = props.data
      temparray[index] = newtask
      emits("update:data", temparray)
    }
  } catch(error) {
    alert(error);
  } finally {

  }
}

function cancelEdit() {
  showDrawer.value = false
}

async function deleteTask() {
  // emits("delete-task", {
  //   ...currentTask
  // })

  try {
    await TaskService.deleteOne(currentTask.id!);
    let index = props.data.findIndex(x => x.id == currentTask.id!)
    if (index != -1) {
      let temparray = props.data;
      temparray.splice(index, 1);
      emits("update:data", temparray)
    }
  } catch (error) {
    alert(error);
  } finally {
    showDrawer.value = false;
  }

}

async function deleteFile(fileitem: FileItem) {
  // emits("delete-file", fileitem)
  try {
    await FileItemService.deleteOne(fileitem.id!)
    let index = filelist.value.findIndex(x => x.id == fileitem.id!)
    if (index != -1) {
      filelist.value.splice(index, 1);
    }
  } catch(error) {

  } finally {

  }
}

async function uploadFile() {
  let files = inputFile.value?.files;
  if(files?.length == 0) {
    alert("no file selected")
    return;
  }
  // emits("upload-file", files?.item(0)!)

  try {
    let response = await FileItemService.insertOne(currentTask.id!, files?.item(0)!);
    let newfileitem = response.data
    filelist.value.push(newfileitem)
  } catch(error) {
    alert(error);
  } finally {

  }
}

watch(currentTask, async (newvalue, _) => {
  let id = newvalue.id!;
  try {
    let response = await FileItemService.findAll(id);
    let fileitems = response.data
    filelist.value = fileitems
  } catch (error) {
    alert(error);
  }
})
</script>

<style lang="scss" scoped>
div.task-board {
  position: absolute;
  right: 0;
  bottom: 0;
  left: 268px;

  top: 0;
  .tasks {
    margin-bottom: 20px;
    width: 100%;
    padding: 10px;

    .head {
      padding: 8px;
      width: 100%;
    }

    .task {
      border-bottom: 1px solid rgba(0, 0, 0, 0.6);
    }
  }
  .operate {
    height: 20px;
    position: absolute;
    bottom: 0;
    width: 100%;
  }
}
</style>