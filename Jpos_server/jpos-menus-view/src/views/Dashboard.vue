<template>
    <div class="py-4 container-fluid">
        <div class="row">
            <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
                <card
                        :title="topCard.todayPrice.title"
                        :value="topCard.todayPrice.value"
                        :icon-class="topCard.todayPrice.iconClass"
                        :icon-background="topCard.iconBackground"
                        direction-reverse
                ></card>
            </div>
            <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
                <card
                        :title="topCard.todayCount.title"
                        :value="topCard.todayCount.value"
                        :percentage="topCard.todayCount.percentage"
                        :icon-class="topCard.todayCount.iconClass"
                        :icon-background="topCard.iconBackground"
                        direction-reverse
                ></card>
            </div>
            <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
                <card
                        :title="topCard.monthPrice.title"
                        :value="topCard.monthPrice.value"
                        :icon-class="topCard.monthPrice.iconClass"
                        :icon-background="topCard.iconBackground"
                        direction-reverse
                ></card>
            </div>
            <div class="col-xl-3 col-sm-6 mb-xl-0">
                <card
                        :title="topCard.allPrice.title"
                        :value="topCard.allPrice.value"
                        :icon-class="topCard.allPrice.iconClass"
                        :icon-background="topCard.iconBackground"
                        direction-reverse
                ></card>
            </div>
        </div>
        <div class="card h-auto">
            <div class="card-header pb-0 p-3">
                <div class="row d-flex align-items-center ">
                    <h6 class="col-8">이전 주문 조회</h6>
                    <div class="col-4 p-3 d-flex">
                        <input type="date" v-model="startDate" min="2000-01-01" max="2099-12-31">
                        <h6>　~　</h6>
                        <input type="date" v-model="endDate" min="2000-01-01" max="2099-12-31">
                        <vsud-button class="m-1" @click="this.getEndPosOrder();">조회</vsud-button>
                    </div>
                </div>
            </div>
            <div class="card-body p-3 pb-0 mb-0" style="overflow: scroll">
                <div class="row">
                    <div class="col-2 border text-center">
                        날짜/시간/좌석
                    </div>
                    <div class="col-8 border text-center">
                        주문 내용
                    </div>
                    <div class="col-2 border text-center">
                        금액
                    </div>
                </div>
                <div class="row" v-for="(order) in orderList">
                    <div class="col-2 border text-center">
                        {{ order.createdDate.at(0) }}-{{ order.createdDate.at(1) }}-{{
                        order.createdDate.at(2)
                        }}/ {{ order.createdDate.at(3) }}:{{ order.createdDate.at(4) }}/ {{ order.seatName }}
                    </div>
                    <div class="col-8 border text-center" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap">
                        {{ order.posOrderContent }}
                    </div>
                    <div class="col-2 border text-center">
                        {{ order.posOrderPrice }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import Card from "@/examples/Cards/Card.vue";
import VsudButton from "@/components/VsudButton.vue";

export default {
    name: "DashboardDefault",
    components: {
        VsudButton,
        Card,
    },
    data() {
        return {
            startDate: "",
            endDate: "",
            topCard: {
                iconBackground: "bg-gradient-success",
                todayPrice: {
                    title: "오늘의 매출",
                    value: "",
                    iconClass: "ni ni-money-coins",
                },
                todayCount: {
                    title: "오늘 온 손님들",
                    value: "",
                    iconClass: "ni ni-world",
                },
                monthPrice: {
                    title: "30일 매출",
                    value: "",
                    iconClass: "ni ni-paper-diploma",
                },
                allPrice: {
                    title: "전체 매출",
                    value: "",
                    iconClass: "ni ni-cart",
                },
            },
            orderList: [],
        };
    },
    methods: {
        getEndPosOrder() {
            let start = new Date(this.startDate);
            let end = new Date(this.endDate);
            if(start > end){
                alert("시간을 확인해 주십시오.");
                return;
            }
            this.axios.get("/jpos/dashboard/find/" + this.$store.state.storeLoginId + "/" + this.startDate + "/" + this.endDate).then(res => {
                this.orderList = res.data.endPosOrderDtoList;
            });
        },
        get() {
            this.axios.get("/jpos/dashboard/" + this.$store.state.storeLoginId).then(res => {
                this.topCard.todayPrice.value = JSON.parse(JSON.stringify(res.data.todayPrice));
                this.topCard.todayCount.value = JSON.parse(JSON.stringify(res.data.todayCount));
                this.topCard.monthPrice.value = JSON.parse(JSON.stringify(res.data.monthPrice));
                this.topCard.allPrice.value = JSON.parse(JSON.stringify(res.data.allPrice));
                this.startDate = res.data.storeCreatedDate.at(0) + "-" + this.plusZero(res.data.storeCreatedDate.at(1)) + "-" + this.plusZero(res.data.storeCreatedDate.at(2));
                let today = new Date();
                this.endDate = today.getFullYear() + "-" + this.plusZero(today.getMonth() + 1) + "-" + this.plusZero(today.getDate());
            })
        },
        plusZero(date) {
            if (date < 10) {
                return "0" + date;
            } else {
                return date;
            }
        }
    },
    mounted() {
        this.get();
    }
};
</script>
