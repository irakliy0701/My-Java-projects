<!-- Выпадающий список, в котором будем выбирать по какому полю будет
     идти сортировка (т.е по заголовку или по описанию)-->
<template>
  <select :value="modelValue" @change="changeOption">
<!--    Добавили 1 дефолтную опцию, по умолчанию она будет с пустым value и
        также она будет отключена (т.е выбрать ее самостоятельно будет невозможно)-->
    <option disabled value="">Выберите из списка</option>
    <option
        v-for="option in options"
        :key="option.value"
        :value="option.value"
    >
      {{option.name}}
    </option>
  </select>
</template>

<script>
export default {
  name: 'my-select',
  props:{
    modelValue: {
      type: String
    },
    options: {
      type: Array,
      default: () => []
    }
  },
  methods:{
    changeOption(event){
      this.$emit('update:modelValue', event.target.value)
    }
  }
}
</script>

<style scoped>

</style>