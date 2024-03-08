<template>
    <div class="modal-wrap" style="background: rgba(0,0,0,0); z-index: 999;" v-show="this.orderModal" @click="orderModalOpen()">
        <div class="modal-com" @click.stop="">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">현재 주문</h5>
                </div>
                <div class="modal-body">
                    <p v-html="this.orderContentName"></p>
                </div>
                <div class="modal-footer">
                    총 금액: {{ this.posOrder.posOrderPrice }} 원
                    <vsud-button @click="postOrder()">주문하기</vsud-button>
                    <vsud-button @click="orderModalOpen()">닫기</vsud-button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal-wrap" style="background: rgba(0,0,0,0); z-index: 999;" v-show="this.endOrderModal" @click="endOrderModalOpen()">
        <div class="modal-com" @click.stop="">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">결제</h5>
                </div>
                <div class="modal-body">
                    <p v-html="this.orderContentName"></p>
                </div>
                <div class="modal-footer">
                    총 금액: {{ this.posOrder.posOrderPrice }} 원
                    <vsud-button @click="endOrder()">결제하기</vsud-button>
                    <vsud-button @click="endOrderModalOpen()">닫기</vsud-button>
                </div>
            </div>
        </div>
    </div>



    <div v-for="(menu) in menus.filter((i) => this.menus.at(i.index).countModal)" class="modal-wrap"
         style="background: rgba(0,0,0,0); z-index: 999;" @click="countModalOff(menu.index)">
        <div class="modal-com" @click.stop="">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">{{menu.menuName}} 수량 수정</h5>
                </div>
                <div class="modal-body">
                    <div class="btn-group-vertical d-flex justify-content-center" role="group">
                        <div class="btn-group pb-3">
                            <input type="text" style="width: 50%"
                                   class="form-control"
                                   :class="getClasses('default', false)"
                                   v-model="this.menus.at(menu.index).count">
                        </div>
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,1)">1
                            </button>
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,2)">
                                2
                            </button>
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,3)">
                                3
                            </button>
                        </div>
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,4)">
                                4
                            </button>
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,5)">
                                5
                            </button>
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,6)">
                                6
                            </button>
                        </div>
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,7)">
                                7
                            </button>
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,8)">
                                8
                            </button>
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,9)">
                                9
                            </button>
                        </div>
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,-1)">
                                &lt;
                            </button>
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,0)">
                                0
                            </button>
                            <button type="button" class="btn btn-outline-secondary py-3"
                                    @click="this.setCount(menu.index,-2)">
                                -1
                            </button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <vsud-button @click="countModalOff(menu.index);">닫기</vsud-button>
                </div>
            </div>
        </div>
    </div>


    <div class="container-fluid mt-4">
        <div class="row align-items-center">
            <div class="col-lg-4 col-sm-8">
                {{ this.$store.state.storeName }} - {{ this.seatName }}
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-4">
                    <div class="card" style="height: 700px">
                        <div class="card-header pb-0 p-3">
                            <div class="row">
                                <div class="col-6 d-flex align-items-center">
                                    <h6 class="mb-0">주문</h6>
                                </div>
                            </div>
                        </div>
                        <div class="card-body p-3 pb-0 mb-0" style="overflow: scroll">
                            <div class="row">
                                <div class="col-7 border text-center">
                                    메뉴
                                </div>
                                <div class="col-2 border text-center">
                                    수량
                                </div>
                                <div class="col-3 border text-center">
                                    금액
                                </div>
                            </div>
                            <div class="row" v-for="(menu) in menus.filter((m) => m.count > 0)">
                                <div class="col-7 border">
                                    {{ menu.menuName }}
                                </div>
                                <div class="col-2 border" @click="this.menus.at(menu.index).countModal = true">
                                    {{ menu.count }}
                                </div>
                                <div class="col-3 border">
                                    {{ menu.count * menu.price }}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-8">
                    <div class="card" style="height: 700px">
                        <div class="card-header pb-0 p-3">
                            <div class="row">
                                <div class="col-6 d-flex align-items-center">
                                    <h6 class="mb-0">메뉴판</h6>
                                </div>
                            </div>
                        </div>
                        <div class="card-body p-0 m-0" style="overflow: scroll">
                            <div class="row p-0 m-0" v-for="(category) in categories">
                                <div class="row border p-0 m-0">
                                    <div class="col-1"></div>
                                    <div class="col-10 text-center my-auto">
                                        {{ category.categoryName }}
                                    </div>
                                </div>
                                <div class="row row-cols-6 p-0 m-0">
                                    <!--  기존 메뉴  -->
                                    <div class="col border"
                                         v-for="(item) in menus.filter((c) => c.categoryId === category.id)"
                                         @click="this.menus.at(item.index).count++;">
                                        <div class="row">
                                            <div class="border-end">
                                                <p class="text-truncate"
                                                   style="margin-bottom: 0; text-overflow:ellipsis;">
                                                    {{ item.menuName }}</p>
                                                <p style="margin-bottom: 0"> {{ item.price }}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-1">
                <div class="col-8"></div>
                <div class="col-4">
                    <vsud-button class="m-1 float-end" @click="this.goPos();">닫기</vsud-button>
                    <vsud-button class="m-1 float-end" @click="this.orderModalOpen();">주문하기</vsud-button>
                    <vsud-button class="m-1 float-end" @click="this.endOrderModalOpen();">결제하기</vsud-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import setTooltip from "@/assets/js/tooltip.js";
import VsudButton from "@/components/VsudButton.vue";
import router from "@/router";

export default {
    name: "InOrder",
    components: {
        VsudButton,
    },
    data() {
        return {
            categories: [], // 카테고리들
            menus: [], // 메뉴들
            posOrder: {}, // 주문
            orderModal: false,
            orderContentName: "",
            endOrderModal: false,
            seatId: history.state.seatId,
            seatName: history.state.seatName,
        };
    },
    methods: {
        getClasses: (size, valid) => {
            let sizeValue, isValidValue;

            sizeValue = size ? `form-control-${size}` : null;

            isValidValue = valid ? `${valid}` : "invalid";

            return `${sizeValue} ${isValidValue}`;
        },
        getMenus() {
            this.axios.get("/jpos/inOrder/" + this.$store.state.storeLoginId + "/" + this.seatId).then(res => {
                this.categories = JSON.parse(JSON.stringify(res.data.categoryDtoList));
                for (let i = 0; i < this.categories.length; i++) {
                    this.categories.at(i).index = i;
                }
                this.menus = JSON.parse(JSON.stringify(res.data.menuDtoList));
                for (let i = 0; i < this.menus.length; i++) {
                    this.menus.at(i).count = 0;
                    this.menus.at(i).index = i;
                    this.menus.at(i).countModal = false;
                }
                this.posOrder = JSON.parse(JSON.stringify(res.data.posOrderDto));
                if (this.posOrder.id !== 0) {
                    for (let i = 0; i < (this.posOrder.posOrderContent || '').split("/").length; i++) {
                        for (let j = 0; j < this.menus.length; j++) {
                            if (((this.posOrder.posOrderContent || '').split("/")[i] || '').split(",")[0] === "" + this.menus.at(j).id) {
                                this.menus.at(j).count = Number(((this.posOrder.posOrderContent || '').split("/")[i] || '').split(",")[1]);
                            }
                        }
                    }
                }
            });
        },
        setPosUsing() {
            this.axios.patch("/jpos/inOrder/" + this.seatId).then(res => {
            });
        },
        countModalOff(menuIndex) {
            this.menus.at(menuIndex).countModal = false;
        },
        setCount(menuIndex, num) {
            if(num === -1){
                let temp = this.menus.at(menuIndex).count.toString().slice(0,-1);
                if(temp === ""){
                    this.menus.at(menuIndex).count = 0;
                }else{
                    this.menus.at(menuIndex).count = Number(temp);
                }
            }else if(num === -2){
                if(this.menus.at(menuIndex).count > 0){
                    this.menus.at(menuIndex).count -= 1;
                }
            }else{
                let temp = this.menus.at(menuIndex).count.toString() + num.toString();
                this.menus.at(menuIndex).count = Number(temp);
            }
        },
        goPos(){
            router.push({
                name: 'Pos'
            })
        },
        orderModalOpen() {
            this.orderContentName = ""
            this.posOrder.posOrderPrice = 0;
            for (let i = 0; i < this.menus.length; i++) {
                if (this.menus.at(i).count !== 0) {
                    this.orderContentName += this.menus.at(i).menuName + " " + this.menus.at(i).count + "개/ " + this.menus.at(i).price * this.menus.at(i).count + "원<br/>";
                    this.posOrder.posOrderPrice += this.menus.at(i).price *  this.menus.at(i).count;
                }
            }
            this.orderModal = !this.orderModal;
        },
        postOrder() {
            if (this.orderContentName === "" && this.posOrder.id === 0) {
                alert("주문을 추가해 주세요.")
                this.orderModalOpen()
            } else if(this.orderContentName === "" && this.posOrder.id !== 0){
                if (confirm("주문을 취소 하시겠습니까?") === true) {
                    this.axios.delete("/jpos/inOrder/order/delete/" + this.posOrder.id).then(res => {
                        this.goPos();
                    })
                } else {
                }
            } else {
                this.posOrder.posOrderContent = "";
                for (let i = 0; i < this.menus.length; i++) {
                    if (this.menus.at(i).count !== 0) {
                        this.posOrder.posOrderContent += this.menus.at(i).id + "," + this.menus.at(i).count + "/";
                    }
                }

                const orderData = {
                    id: this.posOrder.id,
                    posOrderContent: this.posOrder.posOrderContent,
                    posOrderPrice: this.posOrder.posOrderPrice,
                    storeId: this.$store.state.storeLoginId,
                    seatId: this.seatId,
                }
                let idem =   this.$idem();
                this.axios.post("/jpos/inOrder/order/add", JSON.stringify(orderData), {
                    headers: {
                        "Content-Type": "application/json",
                        "Idempotency-Key" : idem,
                    },
                }).then((res) => {
                    this.goPos();
                });
                this.orderModalOpen();
            }
        },
        endOrderModalOpen() {
            this.orderContentName = "";
            this.posOrder.posOrderPrice = 0;
            for (let i = 0; i < this.menus.length; i++) {
                if (this.menus.at(i).count !== 0) {
                    this.orderContentName += this.menus.at(i).menuName + " " + this.menus.at(i).count + "개/ " + this.menus.at(i).price * this.menus.at(i).count + "원<br/>";
                    this.posOrder.posOrderPrice += (this.menus.at(i).price * this.menus.at(i).count);
                }
            }
            this.endOrderModal = !this.endOrderModal;
        },
        endOrder() {
            if (this.orderContentName === "") {
                alert("결제 내용이 없습니다.")
                this.orderModalOpen()
            } else {
                const orderData = {
                    id: this.posOrder.id,
                    posOrderContent: this.orderContentName,
                    posOrderPrice: this.posOrder.posOrderPrice,
                    storeId: this.$store.state.storeLoginId,
                    seatId: this.seatId,
                }
                let idem =   this.$idem();
                this.axios.post("/jpos/inOrder/order/end", JSON.stringify(orderData), {
                    headers: {
                        "Content-Type": "application/json",
                        "Idempotency-Key" : idem,
                    },
                }).then((res) => {
                    this.goPos();
                });
                this.orderModalOpen();
            }
        },

    },
    mounted() {
        this.getMenus();
    },
    unmounted() {
        this.setPosUsing();
    }
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