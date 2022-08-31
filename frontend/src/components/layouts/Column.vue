<template>
  <div class="column">
    <slot/>
  </div>
</template>

<script lang="ts" setup>
import {computed, PropType} from 'vue'
type MainAxisAlignment = 'center' | 'start' | 'end' | 'space-around' | 'space-between' | 'space-evenly'
type CrossAxisAlignment = 'center' | 'start' | 'end' | 'space-around' | 'space-between' | 'space-evenly'

const props = defineProps({
  mainAxis: {
    type: String as PropType<MainAxisAlignment>,
    default: 'start'
  },

  crossAxis: {
    type: String as PropType<CrossAxisAlignment>,
    default: 'start'
  }
})

const mainAxis = computed(() => mapping(props.mainAxis))
const crossAxis = computed(() => mapping(props.crossAxis))

function mapping(alignment: MainAxisAlignment | CrossAxisAlignment): string {
  if(alignment == 'center') return 'center';
  if(alignment == 'start')  return 'flex-start';
  if(alignment == 'end')    return 'flex-end';

  return alignment
}
</script>

<style lang="scss" scoped>
div.column {
  display: flex;
  width: fit-content;
  flex-direction: column;
  justify-content: v-bind(mainAxis);
  align-items: v-bind(crossAxis);
}
</style>