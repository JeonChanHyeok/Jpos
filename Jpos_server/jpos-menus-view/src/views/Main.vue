<template>
    <sidenav
            v-if="$store.state.showSidenav"
            :custom_class="$store.state.mcolor"
            :class="[
      $store.state.isTransparent,
      $store.state.isRTL ? 'fixed-end' : 'fixed-start',
    ]"
    />
    <main
            class="main-content position-relative max-height-vh-100 h-100 border-radius-lg"
            :style="$store.state.isRTL ? 'overflow-x: hidden' : ''"
    >
        <router-view/>
        <app-footer v-show="$store.state.showFooter"/>
    </main>
</template>
<script>
import Sidenav from "../examples/Sidenav/index.vue";
import AppFooter from "@/examples/Footer.vue";
import {mapMutations} from "vuex";

export default {
    name: "main",
    components: {
        Sidenav,
        AppFooter,
    },

    computed: {
    },
    beforeMount() {
        this.$store.state.isTransparent = "bg-transparent";
    },
    methods: {
        ...mapMutations(["toggleConfigurator", "navbarMinimize"]),
        initData(){
            if(localStorage.getItem("accessToken")){
                this.$store.commit("login", JSON.parse(localStorage.getItem("accessToken")).storeId);
            }
        }
    },
    created() {
        this.initData();
    }
};
</script>
