<template>
    <div
            class="pt-5 m-3 page-header align-items-start min-vh-50 pb-11 border-radius-lg"
            :style="{
      backgroundImage:   `url(${bgImg})`,
    }"
    >
        <span class="mask bg-gradient-dark opacity-6"></span>
        <div class="container">
            <div class="row justify-content-center">
                <div class="mx-auto text-center col-lg-5">
                    <h1 class="mt-5 mb-2 text-white">Welcome!</h1>
                    <p class="text-white text-lead">
                        Use these awesome forms to login or create new account in your
                        project for free.
                    </p>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row mt-lg-n10 mt-md-n11 mt-n10 justify-content-center">
            <div class="mx-auto col-xl-4 col-lg-5 col-md-7">
                <div class="card z-index-0">
                    <div class="pt-4 text-center card-header">
                        <h5>Register with</h5>
                    </div>
                    <div class="card-body">
                        <form role="form">
                            <div class="mb-3">
                                <input type="text" class="form-control" :class="getClasses('default', false)"
                                       aria-label="storeLoginId" v-model="loginId" placeholder="Id"/>
                            </div>
                            <div class="mb-3">
                                <input type="password" class="form-control" :class="getClasses('default', false)"
                                       aria-label="storeLoginPw" v-model="loginPw" placeholder="Password"/>
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" :class="getClasses('default', false)"
                                       aria-label="storeName" v-model="userName" placeholder="Name"/>
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" :class="getClasses('default', false)"
                                       aria-label="storeName" v-model="storeName" placeholder="StoreName"/>
                            </div>
                            <div class="mb-3">
                                <input type="number" class="form-control" :class="getClasses('default', false)"
                                       aria-label="storeLat" v-model="latitude" placeholder="Latitude"/>
                            </div>
                            <div class="mb-3">
                                <input type="number" class="form-control" :class="getClasses('default', false)"
                                       aria-label="storeLon" v-model="longitude" placeholder="Longitude"/>
                            </div>
                            <div class="text-center">
                                <vsud-button type="button" color="dark" full-width variant="gradient" class="my-4 mb-2"
                                             @click="signUp()">Sign up
                                </vsud-button>
                            </div>
                            <p class="text-sm mt-3 mb-0">
                                Already have an account
                                <a
                                        href="javascript:;"
                                        class="text-dark font-weight-bolder"
                                >Sign in</a>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <app-footer/>
</template>

<script>
import Navbar from "@/examples/PageLayout/Navbar.vue";
import AppFooter from "@/examples/PageLayout/Footer.vue";
import VsudInput from "@/components/VsudInput.vue";
import VsudCheckbox from "@/components/VsudCheckbox.vue";
import VsudButton from "@/components/VsudButton.vue";
import bgImg from "@/assets/img/curved-images/curved6.jpg"
import router from "@/router";

export default {
    name: "SignUp",
    components: {
        Navbar,
        AppFooter,
        VsudInput,
        VsudCheckbox,
        VsudButton,
    },
    data() {
        return {
            bgImg,
            loginId: "",
            loginPw: "",
            userName: "",
            storeName: "",
            latitude: "",
            longitude: "",
        }
    },
    methods: {
        getClasses: (size, valid) => {
            let sizeValue, isValidValue;

            sizeValue = size ? `form-control-${size}` : null;

            isValidValue = valid ? `${valid}` : "invalid";

            return `${sizeValue} ${isValidValue}`;
        },
        signUp() {
            const user = {
                loginId: this.loginId,
                loginPw: this.loginPw,
                userName: this.userName,
                storeName: this.storeName,
                role: "ROLE_OWNER",
                latitude: this.latitude,
                longitude: this.longitude
            }
            try {
                this.axios.post("/jpos/user/signup", JSON.stringify(user), {
                    headers: {
                        "Content-Type": "application/json"
                    },
                }).then((res) => {
                    if(res.data.errorCode === 400){
                        alert("중복된 아이디입니다.");
                    }else{
                        alert("가입 완료");
                        router.push('/sign-in')
                    }
                });
            } catch (error) {
                console.error(error);
            }
        },
    },
    created() {
        this.$store.state.hideConfigButton = true;
        this.$store.state.showNavbar = false;
        this.$store.state.showSidenav = false;
        this.$store.state.showFooter = false;
    }
    ,
    beforeUnmount() {
        this.$store.state.hideConfigButton = false;
        this.$store.state.showNavbar = true;
        this.$store.state.showSidenav = true;
        this.$store.state.showFooter = true;
    }
    ,
}
;
</script>
