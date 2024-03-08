export default {
    install(Vue) {
        Vue.config.globalProperties.$idem = function () {
            return Math.random().toString(36).substring(2, 12);
        }
    }
}