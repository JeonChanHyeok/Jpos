<template>
    <div class="container top-0 position-sticky z-index-sticky">
        <div class="row">
            <div class="col-12">
            </div>
        </div>
    </div>
    <main class="mt-0 main-content main-content-bg">
        <section>
            <div class="page-header min-vh-75">
                <div class="container">
                    <div class="row">
                        <div class="mx-auto col-xl-4 col-lg-5 col-md-6 d-flex flex-column">
                            <div class="mt-8 card card-plain">
                                <div class="pb-0 card-header text-start">
                                    <h3 class="font-weight-bolder text-info text-gradient">Login</h3>
                                    <p class="mb-0">Enter your id and password to sign in</p>
                                </div>
                                <div class="card-body">
                                    <form role="form" class="text-start">
                                        <label>ID</label>
                                        <input type="text" class="form-control" :class="getClasses('default', false)"
                                               placeholder="Id" name="storeLoginId" v-model="storeLoginId"/>
                                        <label>Password</label>
                                        <input type="password" class="form-control"
                                               :class="getClasses('default', false)" placeholder="Password"
                                               name="storeLoginPw"
                                               v-model="storeLoginPw"/>
                                        <div class="text-center">
                                            <vsud-button
                                                    type="button"
                                                    class="my-4 mb-2"
                                                    variant="gradient"
                                                    color="info"
                                                    full-width
                                                    @click="loginSubmit()"
                                            >Sign in
                                            </vsud-button>
                                        </div>
                                    </form>
                                </div>
                                <div class="px-1 pt-0 text-center card-footer px-lg-2">
                                    <p class="mx-auto mb-4 text-sm">
                                        Don't have an account?
                                        <a
                                                href="/sign-up"
                                                class="text-info text-gradient font-weight-bold"
                                        >Sign up</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="top-0 oblique position-absolute h-100 d-md-block d-none me-n8">
                                <div
                                        class="bg-cover oblique-image position-absolute fixed-top ms-auto h-100 z-index-0 ms-n6"
                                        :style="{
                    backgroundImage:
                      `url(${bgImg})`,
                  }"
                                ></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
    <app-footer/>
</template>

<script>
import Navbar from "@/examples/PageLayout/Navbar.vue";
import AppFooter from "@/examples/PageLayout/Footer.vue";
import VsudInput from "@/components/VsudInput.vue";
import VsudSwitch from "@/components/VsudSwitch.vue";
import VsudButton from "@/components/VsudButton.vue";
import bgImg from "@/assets/img/curved-images/curved9.jpg"
import router from "@/router";

const body = document.getElementsByTagName("body")[0];

export default {
    name: "SigninPage",
    components: {
        Navbar,
        AppFooter,
        VsudInput,
        VsudSwitch,
        VsudButton,
    },
    data() {
        return {
            bgImg,
            storeLoginId: "",
            storeLoginPw: "",
            rememberMe: false,
        }
    },
    methods: {
        getClasses: (size, valid) => {
            let sizeValue, isValidValue;

            sizeValue = size ? `form-control-${size}` : null;

            isValidValue = valid ? `${valid}` : "invalid";

            return `${sizeValue} ${isValidValue}`;
        },
        loginSubmit() {
            try {
                this.axios.post("/jpos/store/sign-in", null, {
                    params: {
                        storeLoginId: this.storeLoginId,
                        storeLoginPw: this.storeLoginPw,
                        remamberMe: this.rememberMe,
                    }
                }).then((res) => {
                    if(res.data.status === 401){
                        alert(res.data.message);
                    }else if(res.data.status === 201){
                        this.$store.commit('login', res.data.message);
                        alert("로그인 성공");
                        router.push("/dashboard");
                    }
                });
            } catch (error) {
                console.error(error);
            }
        }
    },
    beforeMount() {
        this.$store.state.hideConfigButton = true;
        this.$store.state.showNavbar = false;
        this.$store.state.showSidenav = false;
        this.$store.state.showFooter = false;
        body.classList.remove("bg-gray-100");
    },
    beforeUnmount() {
        this.$store.state.hideConfigButton = false;
        this.$store.state.showNavbar = true;
        this.$store.state.showSidenav = true;
        this.$store.state.showFooter = true;
        body.classList.add("bg-gray-100");
    },
};
</script>
