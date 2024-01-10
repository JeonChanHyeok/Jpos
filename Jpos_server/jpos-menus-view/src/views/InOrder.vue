<template>
    <div class="container-fluid mt-4">
        <div class="row align-items-center">
            <div class="col-lg-4 col-sm-8">
                {{ this.$store.state.storeName }} - {{ this.seatId }}
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
                                <div class="col-2 border">
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
                                         v-for="(item) in menus.filter((c) => c.categoryId === category.id)">
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
                    <vsud-button class="m-1 float-end">닫기</vsud-button>
                    <vsud-button class="m-1 float-end">주문하기</vsud-button>
                    <vsud-button class="m-1 float-end">결제하기</vsud-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import setTooltip from "@/assets/js/tooltip.js";
import VsudButton from "@/components/VsudButton.vue";

export default {
    name: "InOrder",
    components: {
        VsudButton,
    },
    data() {
        return {
            categories: [], // 카테고리들
            menus: [], // 메뉴들
            posOrder: {},
            seatId: history.state.seatId,
        };
    },
    methods: {
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
    },
    mounted() {
        this.getMenus();
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