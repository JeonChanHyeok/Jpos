import {createRouter, createWebHistory} from 'vue-router'
import mainView from "@/views/Main.vue";
import Dashboard from "@/views/Dashboard.vue";
import Pos from "@/views/Pos.vue";
import Profile from "@/views/Profile.vue";
import SignIn from "@/views/SignIn.vue";
import SignUp from "@/views/SignUp.vue";
import InOrder from "@/views/InOrder.vue";
import MenuSetting from "@/views/MenuSetting.vue";
import TableSetting from "@/views/TableSetting.vue";
import QrCode from "@/views/QrCode.vue";
import QrOrder from "@/views/QrOrder.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/qrOrder/:storeName/:seatName',
            name: 'qrOrderView',
            component: QrOrder,
        },
        {
            path: "/sign-in",
            name: "Sign In",
            component: SignIn,
        },
        {
            path: "/sign-up",
            name: "Sign Up",
            component: SignUp,
        },
        {
            path: '/main',
            name: 'mainView',
            component: mainView,
            children: [
                {
                    path: "/main/dashboard",
                    name: "Dashboard",
                    component: Dashboard,
                },
                {
                    path: "/main/pos",
                    name: "Pos",
                    component: Pos,
                },
                {
                    path: "/main/inOrder",
                    name: "InOrder",
                    component: InOrder,
                },
                {
                    path: "/main/menuSetting",
                    name: "MenuSetting",
                    component: MenuSetting,
                },
                {
                    path: "/main/profile",
                    name: "Profile",
                    component: Profile,
                },
                {
                    path: "/main/tableSetting",
                    name: "TableSetting",
                    component: TableSetting,
                },
                {
                    path: "/main/qrCode",
                    name: "QrCode",
                    component: QrCode,
                },
            ],
        }
    ]
})

export default router
