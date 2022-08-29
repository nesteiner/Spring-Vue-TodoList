<template>
  <Column class="task-sidebar" main-axis="start" cross-axis="center">
    <div class="listgroups">
      <template v-for="(listgroup, index1) in data" :key="index1">
        <ToggleList>
          <template v-slot:entry>
            <Row cross-axis="center">
              <Expanded>
                <Card class="entry">
                  <Row main-axis="start">{{listgroup.name}}</Row>
                </Card>
              </Expanded>
              <Dropdown>
                <DropdownItem @click="toggleAddOrDeleteTaskList(listgroup)">Add Or Delete TaskList</DropdownItem>
                <DropdownItem @click="toggleRenameListGroup(listgroup)">Rename ListGroup</DropdownItem>
                <DropdownItem @click="toggleCancelListGroup(listgroup)">Cancel ListGroup</DropdownItem>
              </Dropdown>
            </Row>
          </template>

          <template v-slot:content>
            <template v-for="(tasklist, index2) in map.get(listgroup.id) || []" :key="index2">
              <ToggleListItem @click="selectTaskList(tasklist)">
                <Card class="tasklist">{{tasklist.name}}</Card>
              </ToggleListItem>
            </template>
          </template>

        </ToggleList>
      </template>

      <hr/>
      <div class="listgroup default-listgroup">
        <template v-for="(tasklist, index) in map.get(0) || []" :key="index">
          <ToggleListItem @click="selectTaskList(tasklist)">
            <Card>{{tasklist.name}}</Card>
          </ToggleListItem>
        </template>
      </div>

    </div>

    <Row class="operate" cross-axis="center" main-axis="center">
      <div class="add-tasklist">
        <button @click="toggleAddTaskList">add tasklist</button>
      </div>

      <div class="add-listgroup">
        <button @click="toggleAddListGroup">add listgroup</button>
      </div>
    </Row>

    <Dialog v-model:show="showAddTaskList">
      <input type="text" v-model="text" placeholder="input tasklist name"/>
      <button class="submit" @click="submitAddTaskList">add</button>
    </Dialog>

    <Dialog v-model:show="showAddListGroup">
      <input type="text" v-model="text" placeholder="input listgroup name"/>
      <button class="submit" @click="submitAddListGroup">add</button>
    </Dialog>

    <Dialog v-model:show="showAddOrDeleteTaskList">
      <h3>Add Or Delete ListGroup</h3>
    </Dialog>

    <Dialog v-model:show="showRenameListGroup">
      <input type="text" v-model="text" placeholder="input listgroup name"/>
      <button class="submit" @click="submitRenameListGroup">save</button>
    </Dialog>

  </Column>
</template>

<script lang="ts" setup>
import {PropType, ref} from "vue";
import {ToggleList, ToggleListItem} from "@/components/togglelist";
import {Row, Column, Expanded} from "@/components/layouts";
import Dialog from "@/components/Dialog.vue";
import Card from "@/components/Card.vue"
import {Dropdown, DropdownItem} from "@/components/dropdown";

const props = defineProps({
  data: {
    type: Array as PropType<ListGroup[]>,
    required: true
  },

  map: {
    type: Map as PropType<Map<number, TaskList[]>>,
    required: true
  }
})

const emits = defineEmits(["select-tasklist", "add-tasklist", "add-listgroup", "cancel-listgroup", "rename-listgroup"])

const showAddTaskList = ref(false)
const showAddListGroup = ref(false)
const showAddOrDeleteTaskList = ref(false)
const showRenameListGroup = ref(false)
const currentListGroup = ref<ListGroup>({
  id: 0,
  name: "None"
})

const text = ref("")

function selectTaskList(tasklist: TaskList) {
  emits("select-tasklist", tasklist)
}

function toggleAddTaskList() {
  showAddTaskList.value = true
}

function toggleAddListGroup() {
  showAddListGroup.value = true
}

function submitAddTaskList() {
  emits("add-tasklist", text.value);
  text.value = "";
  showAddTaskList.value = false
}

function submitAddListGroup() {
  emits("add-listgroup", text.value);
  text.value = "";
  showAddListGroup.value = false
}

function submitRenameListGroup() {
  let listgroup = currentListGroup.value
  emits("rename-listgroup", {
    id: listgroup.id,
    name: text.value
  })

  text.value = ""
  showRenameListGroup.value = false
}

function toggleAddOrDeleteTaskList(listgroup: ListGroup) {
  showAddOrDeleteTaskList.value = true
  currentListGroup.value = listgroup
}

function toggleRenameListGroup(listgroup: ListGroup) {
  showRenameListGroup.value = true
  currentListGroup.value = listgroup
}

function toggleCancelListGroup(listgroup: ListGroup) {
  emits("cancel-listgroup", listgroup.id)
}
</script>

<style lang="scss" scoped>
div.task-sidebar {
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  div.listgroups {
    div.default-listgroup {
      margin-top: 20px;
    }

    .entry {
      font-size: 28px;
    }

    .tasklist {
      font-size: 20px;
    }
  }

  .operate {
    position: absolute;
    bottom: 0;
  }
}
</style>