import {createRouter, createWebHistory} from 'vue-router'
import menusView from "@/views/Menus.vue";
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

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/menus/:storeName/:seatName',
            name: 'menusView',
            component: menusView,
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
                    path: "/",
                    name: "/",
                    redirect: "/dashboard",
                },
                {
                    path: "/dashboard",
                    name: "Dashboard",
                    component: Dashboard,
                },
                {
                    path: "/pos",
                    name: "Pos",
                    component: Pos,
                },
                {
                    path: "/inorder",
                    name: "InOrder",
                    component: InOrder,
                },
                {
                    path: "/menuSetting",
                    name: "MenuSetting",
                    component: MenuSetting,
                },
                {
                    path: "/profile",
                    name: "Profile",
                    component: Profile,
                },
                {
                    path: "/tableSetting",
                    name: "TableSetting",
                    component: TableSetting,
                },
                {
                    path: "/qrCode",
                    name: "QrCode",
                    component: QrCode,
                },
            ],
        }
    ]
})

export default router
