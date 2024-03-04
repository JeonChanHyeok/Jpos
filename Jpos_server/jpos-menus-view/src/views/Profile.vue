<template>

    <div class="modal-wrap" style="background: rgba(0,0,0,0); z-index: 999;" @click="modalOnOff()" v-if="this.modal">
        <div class="modal-com" @click.stop="">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">직원 추가</h5>
                </div>
                <div class="modal-body">
                    <input type="text" style="width: 50%"
                           class="form-control"
                           :class="getClasses('default', false)"
                           v-model="newId"
                           placeholder="Id">
                    <input type="text" style="width: 50%"
                           class="form-control mt-1"
                           :class="getClasses('default', false)"
                           v-model="newPw"
                           placeholder="Pw">
                    <input type="text" style="width: 50%"
                           class="form-control mt-1"
                           :class="getClasses('default', false)"
                           v-model="newName"
                           placeholder="Name">
                    <select style="width: 50%"
                            class="form-control"
                            :class="getClasses('default', false)"
                            v-model="newRole">
                        <option disabled value="">카테고리 선택</option>
                        <option :value="role_manager">매니저</option>
                        <option :value="role_customer">점원</option>
                    </select>
                </div>
                <div class="modal-footer">
                    <vsud-button @click="postNewUser()">추가하기</vsud-button>
                    <vsud-button @click="modalOnOff();">닫기</vsud-button>
                </div>
            </div>
        </div>
    </div>


    <div class="container-fluid">
        <div class="mx-4 overflow-hidden card card-body blur shadow-blur mt-6">
            <div class="row gx-4">
                <div class="col-auto my-auto">
                    <div class="h-100">
                        <h5 class="mb-1">{{ this.$store.state.storeName }}</h5>
                        <p class="mb-0 text-sm font-weight-bold">{{ this.loginName }} / {{ this.loginRole }}</p>
                    </div>
                </div>
                <div class="col-auto my-auto">
                    <vsud-button class="m-1 float-end">폐업</vsud-button>
                </div>
            </div>
        </div>
    </div>
    <div class="py-4 container-fluid">
        <div class="mt-3 row">
            <div class="mt-4 col-12 col-md-6 col-xl-6 mt-md-0">
                <div class="card h-100">
                    <div class="p-3 pb-0 card-header">
                        <div class="row">
                            <div class="col-md-8 d-flex align-items-center">
                                <h6 class="mb-0">Store Information</h6>
                            </div>
                            <div class="col-md-4 text-end">
                                <a href="javascript:;">
                                    <i
                                            class="text-sm fas fa-user-edit text-secondary"
                                            data-bs-toggle="tooltip"
                                            data-bs-placement="top"
                                            v-if="!this.editing"
                                            @click="editOnOff()"
                                    ></i>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="p-3 card-body">
                        <p class="text-sm" v-if="!this.editing">
                            {{ storeInfoDto.description }}
                        </p>
                        <textarea style="width: 100%"
                                  class="form-control"
                                  :class="getClasses('default', false)"
                                  v-model="editDesc"
                                  v-if="this.editing"/>
                        <hr class="my-4 horizontal gray-light"/>
                        <ul class="list-group">
                            <li class="text-sm border-0 list-group-item ps-0">
                                <strong class="text-dark">전화번호:</strong> &nbsp; <a
                                    v-if="!this.editing">{{ storeInfoDto.callNum }}</a> <input type="text"
                                                                                               style="width: 50%"
                                                                                               class="form-control"
                                                                                               :class="getClasses('default', false)"
                                                                                               v-model="editCallNum"
                                                                                               v-if="this.editing">
                            </li>
                            <li class="text-sm border-0 list-group-item ps-0">
                                <strong class="text-dark">주소:</strong> &nbsp; <a v-if="!this.editing">
                                {{ storeInfoDto.location }}</a><input type="text" style="width: 50%"
                                                                      class="form-control"
                                                                      :class="getClasses('default', false)"
                                                                      v-model="editLocation"
                                                                      v-if="this.editing">
                            </li>
                            <li class="text-sm border-0 list-group-item ps-0">
                                <strong class="text-dark">가입일자:</strong> &nbsp; <a>{{ createdDate.at(0) }} -
                                {{ createdDate.at(1) }} - {{ createdDate.at(2) }}</a>
                            </li>
                        </ul>
                    </div>
                    <div class="card-footer" v-if="this.editing">
                        <vsud-button @click="updateStoreInfo()">저장</vsud-button>
                        <vsud-button @click="editOnOff()">취소</vsud-button>
                    </div>
                </div>
            </div>
            <div class="mt-4 col-12 col-xl-6 mt-xl-0">
                <div class="card h-100">
                    <div class="p-3 pb-0 card-header">
                        <div class="row d-flex">
                            <div class="col-10"><h6 class="mb-0">직원목록</h6></div>
                            <div class="col-2">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor"
                                     class="bi bi-person-add float-end" viewBox="0 0 16 16" @click="modalOnOff();">
                                    <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0m-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0M8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4"/>
                                    <path d="M8.256 14a4.5 4.5 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10q.39 0 .74.025c.226-.341.496-.65.804-.918Q8.844 9.002 8 9c-5 0-6 3-6 4s1 1 1 1z"/>
                                </svg>
                            </div>
                        </div>
                    </div>
                    <div class="p-3 card-body">
                        <ul class="list-group">
                            <li v-for="(i) in userIdList.length"
                                class="px-0 mb-2 border-0 list-group-item d-flex align-items-center">
                                <div class="d-flex align-items-start flex-column justify-content-center">
                                    <h6 class="mb-0 text-sm">{{ userNameList.at(i - 1) }}</h6>
                                    <p class="mb-0 text-xs">{{ userRoleList.at(i - 1) }}</p>
                                </div>
                                <a class="mb-0 btn btn-link pe-3 ps-0 ms-auto" @click="deleteEmployee(i-1)"
                                   v-if="userRoleList.at(i-1) !== '점주'">삭제</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import setNavPills from "@/assets/js/nav-pills.js";
import setTooltip from "@/assets/js/tooltip.js";
import VsudButton from "@/components/VsudButton.vue";

export default {
    name: "ProfileOverview",
    components: {
        VsudButton,
    },
    data() {
        return {
            showMenu: false,
            editing: false,
            loginName: "",
            loginRole: "",
            newId: "",
            newPw: "",
            newName: "",
            newRole: "",
            role_manager: "ROLE_MANAGER",
            role_customer: "ROLE_CUSTOMER",
            modal: false,
            storeInfoDto: {},
            createdDate: null,
            editDesc: "",
            editCallNum: "",
            editLocation: "",
            userIdList: [],
            userNameList: [],
            getUserRoleList: [],
            userRoleList: [],
        };
    },
    methods: {
        getClasses: (size, valid) => {
            let sizeValue, isValidValue;

            sizeValue = size ? `form-control-${size}` : null;

            isValidValue = valid ? `${valid}` : "invalid";

            return `${sizeValue} ${isValidValue}`;
        },
        editOnOff() {
            this.editDesc = this.storeInfoDto.description;
            this.editCallNum = this.storeInfoDto.callNum;
            this.editLocation = this.storeInfoDto.location;
            this.editing = !this.editing;
        },
        updateStoreInfo() {
            const newStoreInfo = {
                description: this.editDesc,
                callNum: this.editCallNum,
                location: this.editLocation
            }
            this.axios.patch("/jpos/profile/storeInfo/update/" + this.$store.state.storeLoginId, JSON.stringify(newStoreInfo), {
                headers: {
                    "Content-Type": "application/json"
                },
            }).then((res) =>{
                this.get();
                this.editOnOff();
            });
        },
        modalOnOff() {
            this.modal = !this.modal;
            this.newId = "";
            this.newPw = "";
            this.newName = "";
            this.newRole = "";
        },
        get() {
            // 가게 설명, 전화번호, 주소, 가입일자
            // 가게 직원 목록 (아이디, 이름, role)
            this.loginName = JSON.parse(localStorage.getItem("accessToken")).userName;
            switch (JSON.parse(localStorage.getItem("accessToken")).roles.at(0)) {
                case 'ROLE_OWNER':
                    this.loginRole = "점주";
                    break;
                case 'ROLE_MANAGER':
                    this.loginRole = "매니저";
                    break;
                case 'ROLE_CUSTOMER':
                    this.loginRole = "점원";
                    break;
            }
            this.axios.get("/jpos/profile/" + JSON.parse(localStorage.getItem("accessToken")).loginId).then(res => {
                this.storeInfoDto = res.data.storeInfoDto;
                this.createdDate = JSON.parse(JSON.stringify(res.data.storeCreatedAt));
                this.userIdList = JSON.parse(JSON.stringify(res.data.userIdList));
                this.userNameList = JSON.parse(JSON.stringify(res.data.userNameList));
                this.getUserRoleList = JSON.parse(JSON.stringify(res.data.userRoleList));
                this.userRoleList = [];
                for (let i = 0; i < this.getUserRoleList.length; i++) {
                    switch (this.getUserRoleList.at(i)) {
                        case 'ROLE_OWNER':
                            this.userRoleList.push("점주");
                            break;
                        case 'ROLE_MANAGER':
                            this.userRoleList.push("매니저");
                            break;
                        case 'ROLE_CUSTOMER':
                            this.userRoleList.push("점원");
                            break;
                    }
                }
            })
        },
        postNewUser() {
            if(this.newId === "" || this.newPw === "" || this.newName === "" || this.newRole === ""){
                alert("정보를 제대로 입력해 주세요.")
                return;
            }
            const user = {
                loginId: this.newId,
                loginPw: this.newPw,
                userName: this.newName,
                storeId: this.$store.state.storeLoginId,
                role: this.newRole,
            }
            this.axios.post("/jpos/profile/signup", JSON.stringify(user), {
                headers: {
                    "Content-Type": "application/json"
                },
            }).then((res) => {
                this.get();
                this.modalOnOff();
            })
        },
        deleteEmployee(i) {
            if (confirm("정말 삭제하시겠습니까?") === true) {
                this.axios.delete("/jpos/profile/delete/" + this.userIdList.at(i)).then(res => {
                    alert(res.data);
                    this.get();
                })
            } else {
            }
        },
    },

    mounted() {
        this.$store.state.isAbsolute = true;
        this.$store.state.isNavFixed = false;
        setNavPills();
        setTooltip();
        this.get();
    },
    beforeUnmount() {
        this.$store.state.isAbsolute = false;
    },
};
</script>
