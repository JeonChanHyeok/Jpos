<template>
    <div class="modal fade show" v-if="this.menuModal">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">메뉴 추가</h5>
            </div>
            <div class="modal-body">
                <input type="text" style="width: 50%"
                       class="form-control"
                       :class="getClasses('default', false)"
                       v-model="newMenuName"
                       placeholder="메뉴명">
                <input type="text" style="width: 50%"
                       class="form-control"
                       :class="getClasses('default', false)"
                       v-model="newMenuPrice"
                       placeholder="가격">
            </div>
            <div class="modal-footer">
                <vsud-button @click="postMenu()">추가하기</vsud-button>
                <vsud-button @click="this.menuModal=false">닫기</vsud-button>
            </div>
        </div>
    </div>
    <div class="container-fluid mt-4">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header pb-0 p-3">
                            <div class="row">
                                <div class="col-8 d-flex align-items-center">
                                    <h6 class="mb-0">메뉴 설정</h6>
                                </div>

                            </div>
                        </div>
                        <div class="card-body p-3 pb-0 mb-0" style="overflow: scroll">
                            <div class="row border" style="height: 50px">
                                <div class="col-1"></div>
                                <div class="col-10 text-center my-auto">
                                    카테고리
                                </div>
                                <div class="col-1 text-center my-auto">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor"
                                         class="bi bi-dash-circle center" viewBox="0 0 16 16"
                                         @click="this.deleteCategory();">
                                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                        <path d="M4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8"/>
                                    </svg>
                                </div>
                            </div>
                            <div class="row row-cols-6">
                                <!--  기존 메뉴  -->
                                <div class="col border">
                                    <div class="row">
                                        <div class="col-9 border-end">
                                            <p class="text-truncate" style="margin-bottom: 0; text-overflow:ellipsis;">
                                                메뉴 1aaaaaaaaaaaaaaaaaa</p>
                                            <p style="margin-bottom: 0"> 7000</p>
                                        </div>
                                        <div class="col-3 text-center my-auto">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25"
                                                 fill="currentColor"
                                                 class="bi bi-dash-circle" viewBox="0 0 16 16"
                                                 @click="this.deleteMenu();">
                                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                                <path d="M4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8"/>
                                            </svg>
                                        </div>
                                    </div>
                                </div>
                                <!--  메뉴 추가  -->
                                <div class="col border">
                                    <div class="row">
                                        <div class="col text-center my-auto">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25"
                                                 fill="currentColor"
                                                 class="bi bi-plus-circle" viewBox="0 0 16 16" @click="this.addMenu();">
                                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                                <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
                                            </svg>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row border" style="height: 50px">
                                <div class="col-1"></div>
                                <div class="col-10 text-center my-auto">
                                    <input type="text" style="width: 100%"
                                           class="form-control"
                                           :class="getClasses('default', false)"
                                           v-model="newCategoryName"
                                           placeholder="카테고리 추가">
                                </div>
                                <div class="col-1 text-center my-auto">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor"
                                         class="bi bi-plus-circle" viewBox="0 0 16 16" @click="this.addCategory();">
                                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
                                    </svg>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import setTooltip from "@/assets/js/tooltip.js";
import NavPill from "./components/NavPill.vue";
import MasterCard from "@/examples/Cards/MasterCard.vue";
import DefaultInfoCard from "@/examples/Cards/DefaultInfoCard.vue";
import PaymentCard from "./components/PaymentCard.vue";
import InvoiceCard from "./components/InvoiceCard.vue";
import BillingCard from "./components/BillingCard.vue";
import TransactionCard from "./components/TransactionCard.vue";
import VsudButton from "@/components/VsudButton.vue";

export default {
    name: "MenuSetting",
    components: {
        VsudButton,
    },
    data() {
        return {
            newCategoryName: "",
            newMenuName: "",
            menuModal: false,
            newMenuPrice: null,
            categories: [], // 카테고리들
            menus: [], // 메뉴들
        };
    },
    methods: {
        getClasses: (size, valid) => {
            let sizeValue, isValidValue;

            sizeValue = size ? `form-control-${size}` : null;

            isValidValue = valid ? `${valid}` : "invalid";

            return `${sizeValue} ${isValidValue}`;
        },
        addCategory() {
        },
        deleteCategory() {
        },
        addMenu() {
            this.menuModal = true;
        },
        postMenu(){

        },
        deleteMenu() {
        },
    },
    mounted() {

    },
};
</script>
<style>

th, td {
    border: 1px solid black;
}

tr {
    background-color: #ffffff;
}

</style>