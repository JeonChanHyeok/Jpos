<template>
    <header>
        <div class="row p-0 m-0">
            <div class="col-9 p-0 m-0">
                <h1 class="text-truncate">가게명: {{ this.storeName }}</h1>
            </div>
            <div class="col-3 p-0 m-0 text-end">
                <vsud-button @click="orderModalOpen()">주문</vsud-button>
            </div>
        </div>
        <h1>좌석: {{ this.seatName }}</h1>
    </header>
    <div class="modal-wrap" style="" v-show="this.orderModal" @click="orderModalOpen()">
        <div class="modal-mobile" @click.stop="">
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

    <div class="modal-wrap" style="" v-show="this.posModal">
        <div class="modal-mobile">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">주문 불가</h5>
                </div>
                <div class="modal-body">
                    <p>죄송합니다. 현재 카운터에서 주문을 수정하고 있습니다. 잠시만 기다려 주십시오.</p>
                    <p>저장된 주문이 모두 취소되오니 다시 주문해 주시기 바랍니다.</p>
                </div>
            </div>
        </div>
    </div>
  <!-- 카테고리 바 -->
    <swiper
            :slidesPerView="5"
            slideToClickedSlide="true"
            class="swiper"
            ref="categorySwiper"
            allowTouchMove="true"
            v-if="isLoading"
    >
        <!-- 선택한 카테고리는 bold체 표시 나머지는 bold 해제 -->
        <swiper-slide @click="clickSlide(0)" v-bind:style="{fontWeight : computuedFontWeight(0)}">맨 위</swiper-slide>
        <swiper-slide v-for="(category,i) in categories.filter((w) => w.index > 0)" :key="i"
                      @click="clickSlide(category.index)"
                      v-bind:style="{fontWeight : computuedFontWeight(category.index)}">
            {{ category.categoryName }}
        </swiper-slide>
    </swiper>
  <!-- 카테고리에 맞는 메뉴들 table로 표시 -->
    <div style="padding: 0">
        <div class="row m-0 p-0" v-for="(category,i) in categories.filter((w) => w.index > 0)" ref="categories"
             :key="i">
            <h2>{{ category.categoryName }}</h2>
            <div class="row m-0 p-0 border-top border-3">
                <div class="col-6 m-0 p-0 border text-center"><h5>이름</h5></div>
                <div class="col-3 m-0 p-0 border text-center"><h5>가격</h5></div>
                <div class="col-3 m-0 p-0 border text-center"><h5>수량</h5></div>
            </div>
            <div class="row m-0 p-0 border-top border-3"
                 v-for="(item) in menus.filter((c) => c.categoryId === category.id)">
                <div class="row m-0 p-0">
                    <div class="col-6 m-0 p-0 border text-center"><h5>{{ item.menuName }}</h5></div>
                    <div class="col-3 m-0 p-0 border text-center"><h5>{{ item.price }}</h5></div>
                    <div class="col-3 m-0 p-0 border text-center"><h5>{{ item.count }}</h5></div>
                </div>
                <div class="row m-0 p-0 border-bottom border-3">
                    <div class="col-6 m-0 p-1 text-center">
                        <vsud-button @click="minusOrder(item.index)" style="width: 100%">-1</vsud-button>
                    </div>
                    <div class="col-6 m-0 p-1 text-center">
                        <vsud-button @click="addOrder(item.index)" style="width: 100%">+1</vsud-button>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
// Import Swiper Vue.js components
import {Swiper, SwiperSlide} from 'swiper/vue';
import VsudButton from "@/components/VsudButton.vue";

// Import Swiper styles
import 'swiper/css';
import router from "@/router";


export default {
    name: 'Menus',
    components: {
        VsudButton,
        Swiper,
        SwiperSlide,
    },
    // 변수 정의
    data() {
        return {
            isLoading: false, // 카테고리바가 먼저 생성되고 후에 카테고리 데이터를 파싱해오는데, 그때 자동으로 안바꿔줘서 아예 데이터 불러오기 전까지 카테고리 바 생성 미루기 위한 변수.
            storeName: "", // 가게 이름
            seatName: "", // 좌석 이름
            menuTableHeads: ["이름", "가격", "수량"], // 테이블 헤더
            categories: [], // 카테고리들
            menus: [], // 메뉴들
            posOrder: {},
            orderModal: false,
            orderContentName: "",
            posModal: false,
        }
    },
    // 메소드 정의
    methods: {
        //포스기에서 자리 조작중엔 작동하면 안됨.
        connectEmitter() {
            const eventSource = new EventSource('http://116.123.197.103:8080/jpos/qrOrder/sub/' + this.$route.params.seatName)
            eventSource.addEventListener('qrOrderStart', event => {

            })
            eventSource.addEventListener('qrOrder', event => {
                this.get();
            });
        },

        // get 으로 데이터 파싱 /jpos/qr-order/가게id/좌석id 입력
        get() {
            this.axios.get("/jpos/qrOrder/" + this.$route.params.storeName + "/" + this.$route.params.seatName).then(res => {
                this.storeName = res.data.storeName;
                this.seatName = res.data.seatName;
                if(res.data.posUsing === 1){
                    this.orderModal = false;
                    this.posModal = true;
                }else{
                    this.posModal = false;
                }
                //카테고리 맨 처음에 맨 위로 버튼 만들기 위해 먼저 하나 푸쉬
                this.categories.push({
                    index: 0,
                    categoryName: '맨 위',
                    weight: 'normal',
                });
                const tempJson = JSON.parse(JSON.stringify(res.data.categoryDtoList)); // 코드 가독성 올리기 위해 만든 임시 변수
                for (let i = 1; i < tempJson.length + 1; i++) {
                    this.categories.push(tempJson.at(i - 1));
                    this.categories.at(i).index = i;
                    this.categories.at(i).weight = 'normal';
                } // 맨위로 버튼 다음에 쭉쭉 카테고리 추가
                this.menus = JSON.parse(JSON.stringify(res.data.menuDtoList)); // 메뉴 추가
                for (let i = 0; i < this.menus.length; i++) {
                    this.menus.at(i).count = 0;
                    this.menus.at(i).index = i;
                    this.menus.at(i).modal = false;
                }
                this.posOrder = JSON.parse(JSON.stringify(res.data.posOrderDto));
                alert(JSON.stringify(res.data.posOrderDto));
                if (this.posOrder.id !== 0) {
                    for (let i = 0; i < (this.posOrder.posOrderContent || '').split("/").length; i++) {
                        for (var j = 0; j < this.menus.length; j++) {
                            if (((this.posOrder.posOrderContent || '').split("/")[i] || '').split(",")[0] === "" + this.menus.at(j).id) {
                                this.menus.at(j).count = Number(((this.posOrder.posOrderContent || '').split("/")[i] || '').split(",")[1]);
                            }
                        }
                    }
                    for (let i = 0; i < this.menus.length; i++) {
                        if (this.menus.at(i).count !== 0) {
                            this.orderContentName += this.menus.at(i).menuName + " " + this.menus.at(i).count + "개/ " + this.menus.at(i).price * this.menus.at(i).count + "원<br/>";
                        }
                    }
                }

                this.isLoading = true; // true로 변경되고 카테고리 바 생성.
            });
        },
        postOrder() {
            if (this.orderContentName === "") {
                alert("주문을 추가해 주세요.")
                this.orderModalOpen()
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
                    storeId: this.$route.params.storeName,
                    seatId: this.$route.params.seatName,
                }
                this.axios.post("/jpos/qrOrder/order/add", JSON.stringify(orderData), {
                    headers: {
                        "Content-Type": "application/json"
                    },
                }).then((res) => {
                    alert(res.data);
                    router.go(0);
                });
                this.orderModalOpen();
            }
        },
        addOrder(n) {
            this.posOrder.posOrderPrice += this.menus.at(n).price;
            this.menus.at(n).count += 1;
        },
        minusOrder(n) {
            if (this.menus.at(n).count === 0) {
                alert("주문된 내용이 없습니다.")
            } else {
                this.posOrder.posOrderPrice -= this.menus.at(n).price;
                this.menus.at(n).count -= 1;
            }
        },
        orderModalOpen() {
            this.orderContentName = ""
            for (let i = 0; i < this.menus.length; i++) {
                if (this.menus.at(i).count !== 0) {
                    this.orderContentName += this.menus.at(i).menuName + " " + this.menus.at(i).count + "개/ " + this.menus.at(i).price * this.menus.at(i).count + "원<br/>";
                }
            }
            this.orderModal = !this.orderModal;
        },
        // 카테고리 바 클릭 시 해당 카테고리 위치로 이동 및 bold체 변경 ( 나머지 카테고리 bold 해제 ) .
        clickSlide(n) {
            for (let i = 0; i < this.categories.length; i++) {
                if (i === n) {
                    this.categories[i].weight = 'bold';
                    if (n !== 0) {
                        let loc_y = this.$refs.categories[i - 1].offsetTop;
                        window.scrollTo({
                            top: loc_y - 80,
                            behavior: "smooth"
                        });
                    } else {
                        window.scrollTo({
                            top: 0,
                            behavior: "smooth"
                        })
                    }
                } else {
                    this.categories[i].weight = 'normal';
                }
            }
        },
    },
    // 자동으로 계속 실행되고 있는 함수. 변경 감지 등에 사용
    computed: {
        swiper() {
            return this.$refs.categorySwiper.$swiper;
        },
        // clickSlide 메서드 실행 시 클릭된 카테고리를 제외하고 모두 weight값을 normal로 변경 후 이 메서드를 통해 카테고리들의 상태를 업데이트한다.
        computuedFontWeight() {
            return (index) => {
                return this.categories[index].weight;
            }
        },

    },
    // 화면 나올때 초기화 하는 부분
    mounted() {
        this.connectEmitter();
        this.get();
    },
}

</script>

<style>
html {
    margin-right: 0;
}

table {
    width: 100vw;
    text-align: left;
    text-size-adjust: auto;
    padding-top: 20px;
}

table th {
    padding: 12px;
    border-bottom: 2px solid darkgray;
    background-color: rgba(0, 0, 255, 0.1);
}

table td {
    padding: 12px;
}

table tr:nth-of-type(even) {
    background-color: rgba(0, 0, 255, 0.1);
}

.swiper {
    width: 100vw;
    height: 20vw;
    background-color: #181818;
    position: sticky;
    top: 0;
}

.swiper-slide {
    text-align: center;
    font-size: 18px;

    /* Center slide text vertically */
    display: flex;
    justify-content: center;
    align-items: center;

    padding-left: 5px;
    padding-right: 5px;
    width: 20%;
    height: 100%;
}


.swiper-slide img {
    display: block;
    width: 100%;
    height: 100%;
    object-fit: cover;
}


</style>