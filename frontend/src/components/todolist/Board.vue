<template>
  <Column class="task-board" cross-axis="start">
    <Column class="tasks">
      <Row class="head" cross-axis="center">
        <Expanded>
          <h3>{{name}}</h3>
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

    <Row class="operate" cross-axis="center" main-axis="center" v-show="listid !== 0">
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
import {defineEmits, PropType, reactive, ref} from "vue";
import {Column, Row, Expanded} from "@/components/layouts";
import Card from "@/components/Card.vue"
import {Dropdown, DropdownItem} from "@/components/dropdown";
import Dialog from "@/components/Dialog.vue";
import Drawer from "@/components/todolist/Drawer.vue";

defineProps({
  listid: {
    type: Number,
    required: true
  },

  data: {
    type: Array as PropType<Task[]>,
    required: true
  },

  name: {
    type: String,
    required: true
  },

  taskid: {
    type: Number,
    required: true
  },

  filelist: {
    type: Array as PropType<FileItem[]>,
    required: true
  }
})

const emits = defineEmits([
  "add-task",
  "toggle-task",
  "rename-tasklist",
  "delete-tasklist",
  "rename-task",
  "delete-task",
  "delete-file",
  "upload-file",
  "update:taskid"
])

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

function addTask() {
  emits("add-task", textAdd.value);
  textAdd.value = ""
}

async function selectTask(task: Task) {
  currentTask.id = task.id
  currentTask.name = task.name
  currentTask.isdone = task.isdone
  currentTask.listid = task.listid

  showDrawer.value = true
  emits("update:taskid", task.id);
  // let response = await FileItemService.findAll(currentTask.id!)
  // filelist.value = response.data
}

function clickCheckbox(task: Task) {
  emits("toggle-task", {
    ...task,
    isdone: !task.isdone
  })
}

function toggleRenameTaskList() {
  showRenameDialog.value = true
}

function toggleDeleteTaskList() {
  showDeleteDialog.value = true
}

function saveRename() {
  emits("rename-tasklist", textRename.value)
  textRename.value = ""
  showRenameDialog.value = false
}

function submitDelete() {
  emits("delete-tasklist")
  showDeleteDialog.value = false
}

function cancelDelete() {
  showDeleteDialog.value = false
}

function saveEdit() {
  emits("rename-task", {
    ...currentTask
  })
}

function cancelEdit() {
  showDrawer.value = false
}

function deleteTask() {
  emits("delete-task", {
    ...currentTask
  })

  showDrawer.value = false
}

function deleteFile(fileitem: FileItem) {
  emits("delete-file", fileitem)
}

function uploadFile() {
  let files = inputFile.value?.files;
  if(files?.length == 0) {
    alert("no file selected")
  } else {
    // let response = await FileItemService.insertOne(currentTask.id!, files?.item(0)!)
    // let fileitem = response.data
    // filelist.value.push(fileitem)
    emits("upload-file", files?.item(0)!)
  }
}
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