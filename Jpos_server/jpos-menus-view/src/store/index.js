import {createStore} from "vuex";
import createPersistedState from "vuex-persistedstate";

export default createStore({
    state: {
        hideConfigButton: false,
        isPinned: true,
        showConfig: false,
        isTransparent: "",
        isRTL: false,
        mcolor: "",
        isNavFixed: false,
        isAbsolute: false,
        showNavs: true,
        showSidenav: true,
        showNavbar: true,
        showFooter: true,
        showMain: true,
        isLogined: false,
        storeLoginId: null, // 가게 id
        storeName: "", // 가게 이름
    },
    plugins: [
        createPersistedState({
            paths: ["createStore"]
        })
    ],
    mutations: {
        toggleConfigurator(state) {
            state.showConfig = !state.showConfig;
        },
        navbarMinimize(state) {
            const sidenav_show = document.querySelector(".g-sidenav-show");
            const sidenav = document.getElementById("sidenav-main");

            if (sidenav_show.classList.contains("g-sidenav-pinned")) {
                sidenav_show.classList.remove("g-sidenav-pinned");
                setTimeout(function () {
                    sidenav.classList.remove("bg-white");
                }, 100);
                sidenav.classList.remove("bg-transparent");
                state.isPinned = true;
            } else {
                sidenav_show.classList.add("g-sidenav-pinned");
                sidenav.classList.add("bg-white");
                sidenav.classList.remove("bg-transparent");
                state.isPinned = false;
            }
        },
        sidebarType(state, payload) {
            state.isTransparent = payload;
        },
        navbarFixed(state) {
            if (state.isNavFixed === false) {
                state.isNavFixed = true;
            } else {
                state.isNavFixed = false;
            }
        },
        login(state, payload) {
            state.storeLoginId = payload;
            state.isLogined = true;
        },
        logout(state) {
            state.storeLoginId = "";
            state.isLogined = false;
        },
        setStoreName(state, payload) {
            state.storeName = payload;
        },
    },
    actions: {
        toggleSidebarColor({commit}, payload) {
            commit("sidebarType", payload);
        },
    },
    getters: {
        getLoginedId: state => state.storeLoginId
    },
});
